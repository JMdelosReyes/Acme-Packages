
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.IssueRepository;
import security.LoginService;
import domain.Auditor;
import domain.Carrier;
import domain.Comment;
import domain.Customer;
import domain.Issue;
import domain.Offer;
import domain.Request;

@Service
@Transactional
public class IssueService {

	@Autowired
	private IssueRepository	issueRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CarrierService	carrierService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private RequestService	requestService;


	public IssueService() {

	}

	public Collection<Issue> findAll() {
		final Collection<Issue> issue = this.issueRepository.findAll();
		Assert.notNull(issue);
		return issue;
	}

	public Issue findOne(final int id) {
		Assert.isTrue(id > 0);
		Issue result;
		result = this.issueRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Issue create() {

		Assert.isTrue(this.actorService.findActorType().equals("Customer"));
		final Issue result = new Issue();
		result.setClosed(false);
		result.setComments(new ArrayList<Comment>());
		result.setDescription("");
		result.setMoment(null);
		result.setOffer(null);
		result.setTicker("");

		return result;
	}

	//El requestId solo es necesario para la creación, para edición se puede pasar null
	public Issue save(final Issue issue, final Integer requestId) {
		Assert.notNull(issue);
		Issue result = null;

		if (issue.getId() == 0) {

			Assert.isTrue(this.actorService.findActorType().equals("Customer"));

			final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Customer customer = this.customerService.findOne(id);
			Assert.notNull(customer);

			final Request request = this.requestService.findOne(requestId);

			Assert.isTrue(customer.getRequests().contains(request));
			Assert.isTrue(request.getIssue() == null);
			issue.setOffer(request.getOffer());
			issue.setTicker(request.getOffer().getTicker() == null ? "" : request.getOffer().getTicker() + UUID.randomUUID().toString().substring(0, 2).toUpperCase());
			issue.setMoment(DateTime.now().minusMillis(1000).toDate());
			issue.setClosed(false);

			result = this.issueRepository.save(issue);
			Assert.notNull(result);

			this.requestService.addIssue(result, request);

		} else if (this.actorService.findActorType().equals("Customer")) {
			final Issue old = this.issueRepository.findOne(issue.getId());
			Assert.notNull(old);

			final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Customer customer = this.customerService.findOne(id);
			Assert.notNull(customer);

			Assert.isTrue(this.findIssuesOfCustomer(id).contains(old));

			Assert.isTrue(old.getDescription().equals(issue.getDescription()));
			Assert.isTrue(old.getMoment().equals(issue.getMoment()));
			Assert.isTrue(old.getOffer().equals(issue.getOffer()));
			Assert.isTrue(old.getTicker().equals(issue.getTicker()));
			Assert.isTrue(old.isClosed() == issue.isClosed());
			Assert.isTrue(old.isClosed() == false);

			result = this.issueRepository.save(issue);
			Assert.notNull(result);
		} else if (this.actorService.findActorType().equals("Auditor")) {
			final Issue old = this.issueRepository.findOne(issue.getId());
			Assert.notNull(old);

			final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Auditor auditor = this.auditorService.findOne(id);
			Assert.notNull(auditor);

			Assert.isTrue(auditor.getIssues().contains(issue));

			Assert.isTrue(old.getDescription().equals(issue.getDescription()));
			Assert.isTrue(old.getMoment().equals(issue.getMoment()));
			Assert.isTrue(old.getOffer().equals(issue.getOffer()));
			Assert.isTrue(old.getTicker().equals(issue.getTicker()));
			Assert.isTrue(old.isClosed() == false);

			result = this.issueRepository.save(issue);
			Assert.notNull(result);
		} else if (this.actorService.findActorType().equals("Carrier")) {
			final Issue old = this.issueRepository.findOne(issue.getId());
			Assert.notNull(old);

			final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(id);
			Assert.notNull(carrier);

			Assert.isTrue(carrier.getOffers().contains(old.getOffer()));

			Assert.isTrue(old.getDescription().equals(issue.getDescription()));
			Assert.isTrue(old.getMoment().equals(issue.getMoment()));
			Assert.isTrue(old.getOffer().equals(issue.getOffer()));
			Assert.isTrue(old.getTicker().equals(issue.getTicker()));
			Assert.isTrue(old.isClosed() == issue.isClosed());
			Assert.isTrue(old.isClosed() == false);

			result = this.issueRepository.save(issue);
			Assert.notNull(result);
		}

		return result;
	}

	public void delete(final Issue issue) {
		Assert.notNull(issue);
		Assert.isTrue(issue.getId() > 0);

		final Issue old = this.issueRepository.findOne(issue.getId());
		Assert.notNull(old);

		Assert.isTrue(this.actorService.findActorType().equals("Customer"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Customer customer = this.customerService.findOne(id);
		Assert.notNull(customer);

		Assert.isTrue(this.findIssuesOfCustomer(id).contains(old));

		Assert.isTrue(old.isClosed());

		this.issueRepository.delete(old.getId());
	}

	public void flush() {
		this.issueRepository.flush();
	}

	public Collection<Issue> findIssuesOfCustomer(int idCustomer) {
		final Collection<Issue> issue = this.issueRepository.issuesOfCustomer(idCustomer);
		Assert.notNull(issue);
		return issue;
	}

	public void deleteIssuesOfOffer(Offer o) {
		for (Issue i : this.issueRepository.findIssuesOfOffer(o.getId())) {
			Auditor auditor = this.issueRepository.findAuditorOfIssue(i.getId());
			auditor.getIssues().remove(i);
			this.issueRepository.delete(i.getId());
		}
	}

	public Collection<Issue> findIssuesOfCarrier(int id) {
		Assert.isTrue(id > 0);
		Collection<Issue> issues = this.issueRepository.findIssuesOfCarrier(id);
		Assert.notNull(issues);
		return issues;
	}

	public Collection<Issue> findUnassigned() {
		Collection<Issue> issues = this.issueRepository.findUnassigned();
		Assert.notNull(issues);
		return issues;
	}

	public void assign(Issue issue, int auditorId) {
		Assert.isTrue(this.issueRepository.findUnassigned().contains(issue));
		Auditor auditor = this.auditorService.findOne(auditorId);
		auditor.getIssues().add(issue);
	}

}
