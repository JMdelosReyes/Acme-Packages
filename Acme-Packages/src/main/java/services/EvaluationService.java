
package services;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EvaluationRepository;
import security.LoginService;
import domain.Customer;
import domain.Evaluation;
import domain.Offer;

@Service
@Transactional
public class EvaluationService {

	@Autowired
	private EvaluationRepository	evaluationRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private OfferService			offerService;


	public EvaluationService() {

	}

	public Collection<Evaluation> findAll() {
		final Collection<Evaluation> issue = this.evaluationRepository.findAll();
		Assert.notNull(issue);
		return issue;
	}

	public Evaluation findOne(final int id) {
		Assert.isTrue(id > 0);
		Evaluation result;
		result = this.evaluationRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Evaluation create() {

		Assert.isTrue(this.actorService.findActorType().equals("Customer"));

		final Evaluation result = new Evaluation();
		result.setComment("");
		result.setCustomer(null);
		result.setMark(null);
		result.setMoment(null);
		result.setOffer(null);

		return result;
	}

	public Evaluation save(Evaluation evaluation) {
		Assert.notNull(evaluation);
		Evaluation result = null;

		Assert.isTrue(this.actorService.findActorType().equals("Customer"));

		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Customer customer = this.customerService.findOne(id);
		Assert.notNull(customer);

		Assert.isTrue(evaluation.getId() == 0);

		evaluation.setCustomer(customer);
		evaluation.setMoment(DateTime.now().minusMillis(1000).toDate());

		Assert.isTrue(this.findOffersOfCustomer(id).contains(evaluation.getOffer()));
		Assert.isTrue(!this.findOffersOfCustomerEvaluated(id).contains(evaluation.getOffer()));

		result = this.evaluationRepository.save(evaluation);
		Assert.notNull(result);

		customer.getEvaluations().add(result);
		this.customerService.save(customer);

		Offer f = result.getOffer();

		this.offerService.addEvaluation(result, f.getId());

		return result;
	}

	public void flush() {
		this.evaluationRepository.flush();
	}

	private Collection<Offer> findOffersOfCustomer(int idCustomer) {
		final Collection<Offer> issue = this.evaluationRepository.offerOfCustomer(idCustomer);
		Assert.notNull(issue);
		return issue;
	}

	private Collection<Offer> findOffersOfCustomerEvaluated(int idCustomer) {
		final Collection<Offer> issue = this.evaluationRepository.offerOfCustomerEvaluateds(idCustomer);
		Assert.notNull(issue);
		return issue;
	}

	public void deleteEvaluationsOfOffer(Offer o) {
		for (Evaluation e : o.getEvaluations()) {
			this.evaluationRepository.delete(e);
		}
	}

}
