
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessBoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Carrier;
import domain.Customer;
import domain.Mess;
import domain.MessBox;
import domain.Sponsor;

@Service
@Transactional
public class MessBoxService {

	@Autowired
	private MessBoxRepository		messBoxRepository;
	@Autowired
	private MessService				messService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private CarrierService			carrierService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private AuditorService			auditorService;
	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private Validator				validator;


	public MessBoxService() {

	}

	public MessBox findOne(final int messageBoxId) {
		MessBox result;
		Assert.isTrue(messageBoxId != 0);
		result = this.messBoxRepository.findOne(messageBoxId);
		return result;
	}

	public MessBox create() {
		final MessBox result = new MessBox();
		result.setName("");
		result.setIsSystem(false);
		result.setParent(null);
		result.setMessages(new ArrayList<Mess>());

		return result;
	}
	public void deleteMessBoxes() {
		final UserAccount principal = LoginService.getPrincipal();
		final String type = this.actorService.findActorType();

		while (!(this.findMessBoxesNoChildrenByActorId().isEmpty())) {
			final Collection<MessBox> noChild = this.findMessBoxesNoChildrenByActorId();

			if (type == "Administrator") {
				final Administrator ad = (Administrator) this.actorService.findByUserAccountId(principal.getId());
				for (final MessBox mb : noChild) {
					if (!mb.getMessages().isEmpty()) {
						this.clean(mb);
					}
					ad.getMessageBoxes().remove(mb);
					this.messBoxRepository.delete(mb.getId());
				}

			} else if (type == "Carrier") {
				final Carrier car = (Carrier) this.actorService.findByUserAccountId(principal.getId());
				for (final MessBox mb : noChild) {
					if (!mb.getMessages().isEmpty()) {
						this.clean(mb);
					}
					car.getMessageBoxes().remove(mb);
					this.messBoxRepository.delete(mb.getId());
				}

			} else if (type == "Auditor") {
				final Auditor aud = (Auditor) this.actorService.findByUserAccountId(principal.getId());
				for (final MessBox mb : noChild) {
					if (!mb.getMessages().isEmpty()) {
						this.clean(mb);
					}
					aud.getMessageBoxes().remove(mb);
					this.messBoxRepository.delete(mb.getId());
				}
			} else if (type == "Sponsor") {
				final Sponsor sp = (Sponsor) this.actorService.findByUserAccountId(principal.getId());
				for (final MessBox mb : noChild) {
					if (!mb.getMessages().isEmpty()) {
						this.clean(mb);
					}
					sp.getMessageBoxes().remove(mb);
					this.messBoxRepository.delete(mb.getId());
				}
			} else {
				final Customer cus = (Customer) this.actorService.findByUserAccountId(principal.getId());
				for (final MessBox mb : noChild) {
					if (!mb.getMessages().isEmpty()) {
						this.clean(mb);
					}
					cus.getMessageBoxes().remove(mb);
					this.messBoxRepository.delete(mb.getId());
				}
			}

			if (type == "Administrator") {
				final Administrator ad = (Administrator) this.actorService.findByUserAccountId(principal.getId());
				this.administratorService.save(ad);
			} else if (type == "Customer") {
				final Customer cus = (Customer) this.actorService.findByUserAccountId(principal.getId());
				this.customerService.save(cus);
			} else if (type == "Auditor") {
				final Auditor aud = (Auditor) this.actorService.findByUserAccountId(principal.getId());
				this.auditorService.save(aud);
			} else if (type == "Sponsor") {
				final Sponsor sp = (Sponsor) this.actorService.findByUserAccountId(principal.getId());
				this.sponsorService.save(sp);
			} else {
				final Carrier car = (Carrier) this.actorService.findByUserAccountId(principal.getId());
				this.carrierService.save(car);
			}
		}

		final List<MessBox> owned = new ArrayList<>(this.findOwnMessageBoxes());
		Assert.isTrue(owned.isEmpty());

	}
	public Collection<MessBox> findMessBoxesNoChildrenByActorId() {
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());

		final List<MessBox> result = new ArrayList<>(this.messBoxRepository.findMessBoxesNoChildrenByActorId(a.getId()));

		Assert.isTrue(this.findOwnMessageBoxes().containsAll(result));
		return result;

	}
	public MessBox save(final MessBox messBox) {
		MessBox result;
		Assert.notNull(messBox);

		//New
		if (messBox.getId() == 0) {
			final UserAccount principal = LoginService.getPrincipal();
			Assert.isTrue((messBox.getName() != "In box") && (messBox.getName() != "Out box") && (messBox.getName() != "Trash box") && (messBox.getName() != "Spam box") && (messBox.getName() != "Notification box"));
			//Comprobar, si parent!=null, que este en bbdd
			//Y que dicho parent sea de ese actor
			if (messBox.getParent() != null) {
				Assert.notNull(this.messBoxRepository.findOne(messBox.getParent().getId()));
				Assert.isTrue(this.findOwnMessageBoxes().contains(messBox.getParent()));
			}
			//Guardamos en BBDD
			result = this.messBoxRepository.saveAndFlush(messBox);

			//Buscamos el actor al que pertenece dicha messBox y lo actualizamos
			final String type = this.actorService.findActorType(principal);
			if (type == "Administrator") {
				final Administrator ad = (Administrator) this.actorService.findByUserAccountId(principal.getId());
				ad.getMessageBoxes().add(result);
				this.administratorService.save(ad);
				Assert.isTrue(ad.getMessageBoxes().contains(result));
			} else if (type == "Customer") {
				final Customer cus = (Customer) this.actorService.findByUserAccountId(principal.getId());
				cus.getMessageBoxes().add(result);
				this.customerService.save(cus);
				Assert.isTrue(cus.getMessageBoxes().contains(result));
			} else if (type == "Sponsor") {
				final Sponsor sp = (Sponsor) this.actorService.findByUserAccountId(principal.getId());
				sp.getMessageBoxes().add(result);
				this.sponsorService.save(sp);
				Assert.isTrue(sp.getMessageBoxes().contains(result));
			} else if (type == "Auditor") {
				final Auditor aud = (Auditor) this.actorService.findByUserAccountId(principal.getId());
				aud.getMessageBoxes().add(result);
				this.auditorService.save(aud);
				Assert.isTrue(aud.getMessageBoxes().contains(result));
			} else {
				final Carrier car = (Carrier) this.actorService.findByUserAccountId(principal.getId());
				car.getMessageBoxes().add(result);
				this.carrierService.save(car);
				Assert.isTrue(car.getMessageBoxes().contains(result));
			}
		}
		//Edit
		else {
			final MessBox bbdd = this.messBoxRepository.findOne(messBox.getId());
			Assert.isTrue(bbdd.getIsSystem() == messBox.getIsSystem());
			if (bbdd.getParent() != null) {
				Assert.isTrue(bbdd.getParent().equals(messBox.getParent()));
			}
			if (messBox.getIsSystem()) {
				Assert.isTrue((messBox.getName() != "In box") && (messBox.getName() != "Out box") && (messBox.getName() != "Trash box") && (messBox.getName() != "Spam box") && (messBox.getName() != "Notification box"));
				Assert.isTrue(messBox.getName() == bbdd.getName());
			}

			result = this.messBoxRepository.save(messBox);
		}

		return result;
	}
	public void delete(final MessBox messBox) {
		Assert.notNull(messBox);
		Assert.isTrue(this.findOwnMessageBoxes().contains(messBox));
		final UserAccount principal = LoginService.getPrincipal();
		Assert.isTrue(messBox.getIsSystem() == false);
		//Actor a = this.actorService.findByUserAccountId(principal.getId());

		final String type = this.actorService.findActorType(principal);
		final Collection<MessBox> children = this.findChildrenMessBoxes(messBox);
		if (children.size() > 0) {
			for (final MessBox box : children) {
				if (box.getMessages().size() > 0) {
					this.clean(box);
				}
			}
		}
		final Collection<MessBox> toRemove = new ArrayList<>(children);
		toRemove.add(messBox);
		//Buscamos el actor al que pertenece dicha messBox y lo actualizamos

		if (type == "Administrator") {
			final Administrator ad = (Administrator) this.actorService.findByUserAccountId(principal.getId());
			ad.getMessageBoxes().removeAll(toRemove);
			this.administratorService.save(ad);
		} else if (type == "Customer") {
			final Customer cus = (Customer) this.actorService.findByUserAccountId(principal.getId());
			cus.getMessageBoxes().removeAll(toRemove);
			this.customerService.save(cus);
		} else if (type == "Sponsor") {
			final Sponsor sp = (Sponsor) this.actorService.findByUserAccountId(principal.getId());
			sp.getMessageBoxes().removeAll(toRemove);
			this.sponsorService.save(sp);
		} else if (type == "Carrier") {
			final Carrier car = (Carrier) this.actorService.findByUserAccountId(principal.getId());
			car.getMessageBoxes().removeAll(toRemove);
			this.carrierService.save(car);
		} else {
			final Auditor aud = (Auditor) this.actorService.findByUserAccountId(principal.getId());
			aud.getMessageBoxes().removeAll(toRemove);
			this.auditorService.save(aud);
		}

		//If the messagebox is not empty and the message is in another box,
		//we remove the relationship between the message and the messagebox
		//If the message is only in the message box that will be eliminated, 
		//the message will go to the trash box
		if (messBox.getMessages().isEmpty() == false) {
			for (final Mess m : messBox.getMessages()) {
				if (this.findByMessageId(m.getId()).size() > 1) {
					messBox.getMessages().remove(m);
				} else {
					this.messService.delete(m, messBox);
				}
			}
			this.messBoxRepository.delete(messBox.getId());
		} else {
			this.messBoxRepository.delete(messBox.getId());
		}
		this.messBoxRepository.flush();
	}
	public void clean(final MessBox messageBox) {
		Assert.notNull(messageBox);
		Assert.isTrue(messageBox.getId() != 0);
		//UserAccount principal = LoginService.getPrincipal();
		//Actor a = this.actorService.findByUserAccountId(principal.getId());

		Assert.isTrue(this.findOwnMessageBoxes().contains(messageBox));
		final List<Mess> messages = new ArrayList<Mess>(messageBox.getMessages());
		Assert.notEmpty(messages);

		for (final Mess m : messages) {
			this.messService.delete(m, messageBox);
		}
		this.messBoxRepository.save(messageBox);

	}
	public void flush() {
		this.messBoxRepository.flush();

	}

	public MessBox reconstruct(final MessBox messBox, final BindingResult binding) {
		MessBox result;

		if (messBox.getId() == 0) {
			result = messBox;
			result.setMessages(new ArrayList<Mess>());

		} else {
			result = this.messBoxRepository.findOne(messBox.getId());

			final MessBox clon = (MessBox) result.clone();
			clon.setName(messBox.getName());

			result = clon;
		}
		this.validator.validate(result, binding);

		return result;
	}

	//Other business methods
	public MessBox findMessageBoxByNameAndActorId(final String name, final int actorId) {
		Assert.isTrue(actorId != 0);
		final MessBox result = this.messBoxRepository.findMessageBoxByNameAndActorId(name, actorId);

		return result;
	}
	public Collection<MessBox> findOwnMessageBoxes() {
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());
		return this.messBoxRepository.findMessageBoxesByActorId(a.getId());

	}
	public Collection<MessBox> findByMessageId(final int messageId) {
		return this.messBoxRepository.findByMessageId(messageId);
	}
	//Operations with system boxes
	public Collection<MessBox> createSystemMessageBoxes() {
		final Collection<MessBox> result = new ArrayList<MessBox>();
		final List<String> names = Arrays.asList("In box", "Out box", "Trash box", "Spam box", "Notification box");
		for (final String n : names) {
			final MessBox messageBox = this.create();
			messageBox.setName(n);
			messageBox.setIsSystem(true);
			messageBox.setMessages(new ArrayList<Mess>());
			messageBox.setParent(null);
			result.add(messageBox);
		}
		return result;
	}

	public Collection<MessBox> saveSystemBoxes(final Collection<MessBox> systemBoxes) {
		final Collection<MessBox> result = new ArrayList<MessBox>();

		for (final MessBox mb : systemBoxes) {
			final MessBox saved = this.messBoxRepository.save(mb);
			result.add(saved);
		}
		return result;
	}
	public Collection<MessBox> findSystemBoxes(final int actorId) {
		Assert.notNull(actorId);
		Assert.isTrue(actorId != 0);
		return this.messBoxRepository.findSystemBoxes(actorId);
	}

	//Destinations to move or copy messages
	public Collection<MessBox> findPosibleDestinations(final int messId) {
		Assert.notNull(messId);
		Assert.isTrue(messId != 0);
		final UserAccount principal = LoginService.getPrincipal();
		final List<MessBox> messBoxes = new ArrayList<>(this.findOwnMessageBoxes());

		final Actor a = this.actorService.findByUserAccountId(principal.getId());
		messBoxes.removeAll(this.findSystemBoxes(a.getId()));
		messBoxes.removeAll(this.findByMessageId(messId));

		return messBoxes;
	}
	//Children messBoxes given a MessBox
	public Collection<MessBox> findChildrenMessBoxes(final MessBox messBox) {
		Assert.notNull(messBox);
		Assert.isTrue(messBox.getId() != 0);
		Collection<MessBox> result;

		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());

		Assert.isTrue(this.findOwnMessageBoxes().contains(messBox));
		result = this.messBoxRepository.findChildrenMessBoxesByParent(messBox.getId(), a.getId());

		if (result == null) {
			result = new ArrayList<MessBox>();
		}
		return result;
	}

	public Collection<MessBox> findMessBoxesNoChildren() {
		Collection<MessBox> result;
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(principal.getId());

		result = this.messBoxRepository.findMessBoxesNoChildren(a.getId());

		return result;
	}

	public Collection<MessBox> findAll() {
		Collection<MessBox> res;
		res = this.messBoxRepository.findAll();
		return res;
	}
}
