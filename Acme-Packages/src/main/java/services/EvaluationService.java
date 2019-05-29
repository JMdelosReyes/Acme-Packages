
package services;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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

	@Autowired
	private Validator				validator;


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

		Offer offer = this.offerService.findOne(result.getOffer().getId());
		offer.getEvaluations().add(result);

		//		this.customerService.save(customer);
		//
		//		Offer f = result.getOffer();
		//
		//		this.offerService.addEvaluation(result, f.getId());

		this.computeOfferScore(offer.getId());

		return result;
	}

	public void delete(Evaluation evaluation) {
		Assert.notNull(evaluation);
		Assert.isTrue(evaluation.getId() > 0);

		Evaluation old = this.evaluationRepository.findOne(evaluation.getId());

		int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		Customer customer = this.customerService.findOne(actorId);
		Assert.isTrue(customer.getId() == old.getCustomer().getId());

		Offer offer = this.offerService.findOne(old.getOffer().getId());
		customer.getEvaluations().remove(old);
		offer.getEvaluations().remove(old);

		this.evaluationRepository.delete(old.getId());

		this.computeOfferScore(offer.getId());
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

	public Collection<Evaluation> findEvaluationsByCustomer(int id) {
		Assert.isTrue(id > 0);
		Collection<Evaluation> evaluations = this.evaluationRepository.findEvaluationsByCustomer(id);
		Assert.notNull(evaluations);
		return evaluations;
	}

	public Collection<Evaluation> findEvaluationsByCarrier(int id) {
		Assert.isTrue(id > 0);
		Collection<Evaluation> evaluations = this.evaluationRepository.findEvaluationsByCarrier(id);
		Assert.notNull(evaluations);
		return evaluations;
	}

	public Collection<Offer> findEvaluableOffersByCustomer(int id) {
		Assert.isTrue(id > 0);
		Collection<Offer> offers = this.evaluationRepository.findEvaluableOffersByCustomer(id);
		Assert.notNull(offers);
		return offers;
	}

	public Evaluation reconstruct(final Evaluation evaluation, Offer offer, final BindingResult binding) {
		Evaluation result;

		Assert.isTrue(this.actorService.findActorType().equals("Customer"));

		int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		Customer customer = this.customerService.findOne(actorId);

		Collection<Offer> offers = this.evaluationRepository.findEvaluableOffersByCustomer(actorId);
		Assert.isTrue(offers.contains(offer));

		result = evaluation;
		result.setOffer(offer);
		result.setCustomer(customer);
		result.setMoment(DateTime.now().minusMillis(1000).toDate());

		this.validator.validate(result, binding);

		return result;
	}

	public void computeOfferScore(int id) {
		Assert.isTrue(id > 0);

		Offer offer = this.offerService.findOne(id);

		double score = 0;
		for (Evaluation e : offer.getEvaluations()) {
			score += e.getMark();
		}
		if (offer.getEvaluations().size() > 0) {
			score = score / offer.getEvaluations().size();
		}

		this.evaluationRepository.updateOfferScore(offer.getId(), score);
	}
}
