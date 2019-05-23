
package services;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import security.LoginService;
import domain.Actor;
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
		result.setComment("");
		result.setMoment(null);

		return result;
	}

	public Comment save(final Comment comment, final Integer issueId) {
		Assert.notNull(comment);
		Comment result = null;

		Assert.isTrue(comment.getId() == 0);

		Assert.isTrue(this.actorService.findActorType().equals("Carrier") || this.actorService.findActorType().equals("Auditor") || this.actorService.findActorType().equals("Customer"));

		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Actor a = this.customerService.findOne(id);
		Assert.notNull(a);

		comment.setUsername(a.getUserAccount().getUsername());
		comment.setMoment(DateTime.now().minusMillis(1000).toDate());

		result = this.commentRepository.save(comment);
		Assert.notNull(result);

		Issue issue = this.issueService.findOne(issueId);
		issue.getComments().add(result);

		try {
			this.issueService.save(issue, null);
		} catch (Throwable t) {
			this.commentRepository.delete(result);
			throw new IllegalArgumentException();
		}

		return result;
	}

	public void flush() {
		this.commentRepository.flush();
	}

}
