
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Mess;
import domain.MessBox;

@Service
@Transactional
public class MessService {

	@Autowired
	private MessRepository			messRepository;
	@Autowired
	private MessBoxService			messBoxService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	confService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private Validator				validator;


	public MessService() {

	}

	public Mess create() {
		final UserAccount principal = LoginService.getPrincipal();

		final Mess m = new Mess();
		m.setSendDate(DateTime.now().minusMillis(1000).toDate());
		m.setSubject("");
		m.setBody("");
		m.setPriority("");
		m.setSender(this.actorService.findByUserAccountId(principal.getId()));
		m.setRecipients(new ArrayList<Actor>());
		m.setTags(new ArrayList<String>());
		return m;
	}

	public Mess createPago() {

		final Mess m = new Mess();
		m.setSendDate(DateTime.now().minusMillis(1000).toDate());
		m.setSubject("");
		m.setBody("");
		m.setPriority("");
		final UserAccount admin = this.userAccountService.findByUsername("admin");
		m.setSender(this.actorService.findByUserAccountId(admin.getId()));
		m.setRecipients(new ArrayList<Actor>());
		m.setTags(new ArrayList<String>());
		return m;
	}

	public Mess findOne(final int messageId) {
		Assert.isTrue(messageId != 0);

		final Mess m = this.messRepository.findOne(messageId);
		Assert.notNull(m);

		return m;
	}
	public Mess send(final Mess mess, final boolean noti) {
		Assert.isTrue(mess.getId() == 0);
		Assert.notNull(mess);
		Assert.isTrue((mess.getSubject() != null) && (mess.getSubject() != ""));
		Assert.notEmpty(mess.getRecipients());
		Assert.isTrue((mess.getBody() != null) && (mess.getBody() != ""));
		Assert.isTrue(this.confService.findOne().getMessPriorities().contains(mess.getPriority()));

		if (!noti) {
			final UserAccount principal = LoginService.getPrincipal();
			Assert.isTrue(principal.equals(mess.getSender().getUserAccount()));
		}
		final Integer total = this.actorService.findAll().size();
		final String type = this.actorService.findActorType();
		if (type != "Administrator") {
			Assert.isTrue(mess.getRecipients().size() <= (total - 2));
		}

		//Save in BBDD
		final Mess result = this.messRepository.save(mess);

		//Save in outbox 
		final MessBox outbox = this.messBoxService.findMessageBoxByNameAndActorId("Out box", mess.getSender().getId());
		outbox.getMessages().add(result);
		this.messBoxService.save(outbox);

		//look if is spam

		if (this.checkTabooWords(mess) == true) {
			//this.actorService.activateSpammer(this.actorService.findByUserAccountId(principal.getId()));
			for (final Actor actor : mess.getRecipients()) {
				//GUARDO MESSAGE EN EL SPAMBOX DE CADA ACTOR
				final Mess spamMess = this.messRepository.save(mess);
				final MessBox spambox = this.messBoxService.findMessageBoxByNameAndActorId("Spam box", actor.getId());
				spambox.getMessages().add(spamMess);
				this.messBoxService.save(spambox);
			}
		} else if (noti == true) {
			for (final Actor actor : mess.getRecipients()) {
				//GUARDO MESSAGE EN LA INBOX DE CADA ACTOR
				final Mess inMess = this.messRepository.save(mess);
				final MessBox inbox = this.messBoxService.findMessageBoxByNameAndActorId("Notification box", actor.getId());
				inbox.getMessages().add(inMess);
				this.messBoxService.save(inbox);
			}
		} else {
			for (final Actor actor : mess.getRecipients()) {
				//GUARDO MESSAGE EN LA INBOX DE CADA ACTOR
				final Mess inMess = this.messRepository.save(mess);
				final MessBox inbox = this.messBoxService.findMessageBoxByNameAndActorId("In box", actor.getId());
				inbox.getMessages().add(inMess);
				this.messBoxService.save(inbox);
			}
		}
		return result;

	}
	private boolean checkTabooWords(final Mess m) {

		final Authority adAuth = new Authority();
		adAuth.setAuthority(Authority.ADMIN);

		boolean result = false;
		final Collection<String> tabooWords = new ArrayList<String>(this.confService.findSpamWords());
		for (final String spam : tabooWords) {
			if (m.getBody().contains(spam) || m.getSubject().contains(spam)) {
				result = true;
				//this.actorService.activateSpammer(m.getSender());
				break;
			}
		}
		return result;
	}

	public void delete(final Mess mess, final MessBox source) {
		Assert.notNull(mess);
		Assert.isTrue(this.messBoxService.findOwnMessageBoxes().contains(source));
		Assert.isTrue(mess.getId() != 0);

		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());
		final MessBox trash = this.messBoxService.findMessageBoxByNameAndActorId("Trash box", a.getId());
		final List<MessBox> containers = new ArrayList<MessBox>(this.messBoxService.findByMessageId(mess.getId()));

		if ((trash != null) && (source.getId() != trash.getId())) {
			if (!(trash.getMessages().contains(mess))) {
				if (trash != null) {
					this.move(mess, source, trash);
				}
			} else {
				source.getMessages().remove(mess);
				this.messBoxService.save(source);
			}
		} else {
			if (containers.size() > 1) {
				for (final MessBox mb : containers) {
					mb.getMessages().remove(mess);
					this.messBoxService.save(mb);
				}
			} else {
				source.getMessages().remove(mess);
				this.messBoxService.save(source);
			}
			this.messRepository.delete(mess);
		}
	}

	public Mess reconstruct(final Mess mess, final BindingResult binding) {
		Mess result;
		final String type = this.actorService.findActorType();
		Assert.isTrue(type != "None");
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());

		result = mess;
		result.setSendDate(DateTime.now().minusMillis(1000).toDate());
		result.setSender(a);

		this.validator.validate(result, binding);

		return result;
	}
	public Mess reconstructNotification(final Mess mess, final BindingResult binding) {
		Mess result;
		final String type = this.actorService.findActorType();
		Assert.isTrue(type == "Administrator");
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());

		result = mess;
		result.setSendDate(DateTime.now().minusMillis(1000).toDate());
		result.setSender(a);
		final Collection<Actor> all = this.actorService.findAll();
		all.remove(a);
		mess.setRecipients(all);

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.messRepository.flush();
	}

	//Other business methods

	public Mess sendNotification(final Mess mess) {
		Assert.notNull(mess);
		Assert.isTrue(mess.getId() == 0);

		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));

		mess.setSendDate(DateTime.now().minusMillis(1000).toDate());
		mess.setSender(a);
		final Collection<Actor> all = this.actorService.findAll();
		all.remove(a);
		mess.setRecipients(all);

		Assert.notNull(mess.getBody());
		Assert.notNull(mess.getSubject());

		final Mess result = this.send(mess, true);

		return result;
	}

	public void move(final Mess mess, final MessBox source, final MessBox destination) {

		Assert.notNull(mess);
		Assert.notNull(destination);
		//El mensaje pertenece al actor logeado
		final Collection<Mess> mowned = this.findOwnMessages();
		Assert.isTrue(mowned.contains(mess));
		//La carpeta introducida pertenece al actor
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());
		Assert.isTrue(a.getMessageBoxes().contains(destination));
		Assert.isTrue(a.getMessageBoxes().contains(source));
		//La carpeta no tiene dicho mensaje incluido
		Assert.isTrue(!(destination.getMessages().contains(mess)));

		source.getMessages().remove(mess);
		destination.getMessages().add(mess);

		this.messBoxService.save(source);
		this.messBoxService.save(destination);

		this.messRepository.flush();

	}

	public void copy(final Mess m, final MessBox destination) {
		Assert.notNull(m);
		Assert.notNull(destination);
		//El mensaje pertenece al actor logeado
		final Collection<Mess> mowned = this.findOwnMessages();
		Assert.isTrue(mowned.contains(m));
		//La carpeta introducida pertenece al actor
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());
		Assert.isTrue(a.getMessageBoxes().contains(destination));

		//La carpeta no tiene dicho mensaje incluido
		Assert.isTrue(!(destination.getMessages().contains(m)));

		//Incluir el mensaje
		destination.getMessages().add(m);
		this.messBoxService.save(destination);
		this.messBoxService.flush();
	}

	public Collection<Mess> findOwnMessages() {
		final UserAccount ua = LoginService.getPrincipal();
		return this.messRepository.findOwnMessages(this.actorService.findByUserAccountId(ua.getId()).getId());
	}

}
