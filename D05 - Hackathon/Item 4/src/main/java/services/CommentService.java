
package services;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import security.LoginService;
import domain.Actor;
import domain.Auditor;
import domain.Comment;
import domain.Issue;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private IssueService		issueService;

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private Validator			validator;


	public CommentService() {

	}

	public Collection<Comment> findAll() {
		final Collection<Comment> comment = this.commentRepository.findAll();
		Assert.notNull(comment);
		return comment;
	}

	public Comment findOne(final int id) {
		Assert.isTrue(id > 0);
		Comment result;
		result = this.commentRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Comment create() {

		Assert.isTrue(this.actorService.findActorType().equals("Carrier") || this.actorService.findActorType().equals("Auditor") || this.actorService.findActorType().equals("Customer"));

		final Comment result = new Comment();
		result.setUsername("");
		result.setUserComment("");
		result.setMoment(null);

		return result;
	}

	public Comment save(final Comment comment, final Integer issueId) {
		Assert.notNull(comment);
		Comment result = null;

		Assert.isTrue(comment.getId() == 0);

		Assert.isTrue(this.actorService.findActorType().equals("Carrier") || this.actorService.findActorType().equals("Auditor") || this.actorService.findActorType().equals("Customer"));

		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Actor a = this.actorService.findOne(id);
		Assert.notNull(a);

		//		comment.setUsername(a.getUserAccount().getUsername());
		//		comment.setMoment(DateTime.now().minusMillis(1000).toDate());

		result = this.commentRepository.save(comment);
		Assert.notNull(result);

		Issue issue = this.issueService.findOne(issueId);

		issue.getComments().add(result);

		//		Issue clon = (Issue) issue.clone();
		//		List<Comment> comments = new ArrayList<>(clon.getComments());
		//		clon.setComments(comments);
		//
		//		this.issueService.save(clon, null);

		return result;
	}

	public void flush() {
		this.commentRepository.flush();
	}

	public Comment reconstruct(Comment comment, final Issue issue, final BindingResult binding) {
		Comment result;

		Assert.isTrue(!issue.isClosed());
		Assert.isTrue(!this.issueService.findUnassigned().contains(issue));

		String actorType = this.actorService.findActorType();
		final int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Actor a = this.actorService.findOne(actorId);

		Collection<Issue> issues;

		if (actorType.equals("Carrier")) {
			issues = this.issueService.findIssuesOfCarrier(actorId);
		} else if (actorType.equals("Customer")) {
			issues = this.issueService.findIssuesOfCustomer(actorId);
		} else {
			Auditor auditor = this.auditorService.findOne(actorId);
			issues = auditor.getIssues();
		}
		Assert.isTrue(issues.contains(issue));

		result = comment;
		result.setUsername(a.getUserAccount().getUsername());
		result.setMoment(DateTime.now().minusMillis(1000).toDate());

		this.validator.validate(result, binding);

		return result;
	}

}
