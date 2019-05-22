
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import utilities.Tickers;
import domain.Address;
import domain.Offer;
import domain.Package;
import domain.Request;

@Service
@Transactional
public class RequestService {

	//Repository
	@Autowired
	private RequestRepository	reqRepository;
	//Services
	@Autowired
	private OfferService		offService;
	@Autowired
	private CustomerService		cusService;
	@Autowired
	private CarrierService		carService;
	@Autowired
	private ActorService		actorService;


	//Constructor
	public RequestService() {

	}
	//CRUD
	public Request findOne(int id) {
		Request res;
		res = this.reqRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Collection<Request> findAll() {
		Collection<Request> res;
		res = this.reqRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Request create() {
		Assert.isTrue(this.actorService.findActorType().equals("Customer"));
		Request res = new Request();
		res.setMoment(DateTime.now().minusMillis(1000).toDate());
		res.setMaxPrice(1.0);
		res.setDeadline(DateTime.now().plusDays(2).toDate());
		res.setFinalMode(false);
		res.setVolume(0.0);
		res.setWeight(0.0);

		res.setAddress(new Address());
		Collection<Package> packages = new ArrayList<>();
		res.setPackages(packages);
		return res;
	}

	public Request save(Request req) {
		Assert.notNull(req);
		Request res;
		if (req.getId() == 0) {
			Assert.isTrue(this.actorService.findActorType().equals("Customer"));
			req.setTicker(Tickers.generateTicker());
			if (req.isFinalMode()) {
				req.setMoment(DateTime.now().minusMillis(1000).toDate());
				req.setStatus(Request.SUBMITTED);
				//this.messService.sendNotiChangeStatus(req);
			} else {
				Assert.isTrue(req.getOffer() == null);
			}

			res = this.reqRepository.save(req);
		} else {
			Request old = this.reqRepository.findOne(req.getId());
			//Carrier
			if (this.actorService.findActorType().equals("Carrier")) {
				Assert.isTrue(old.isFinalMode());
				//TODO: Controlar en el reconstruct que el Carrier es 
				//		el propietario de la offer de esta request
				//		Solo puede modificar el status
				Assert.isTrue(old.getTicker().equals(req.getTicker()));
				Assert.isTrue(old.getMoment().equals(req.getMoment()));
				Assert.isTrue(old.getDescription().equals(req.getDescription()));
				Assert.isTrue(old.getMaxPrice() == req.getMaxPrice());
				Assert.isTrue(old.getDeadline().equals(req.getDeadline()));
				Assert.isTrue(old.getVolume() == req.getVolume());
				Assert.isTrue(old.getWeight() == req.getWeight());
				Assert.isTrue(old.isFinalMode() == req.isFinalMode());
				Assert.isTrue(old.getOffer().equals(req.getOffer()));
				Assert.isTrue(old.getAddress().equals(req.getAddress()));
				Assert.isTrue(old.getPackages().equals(req.getPackages()));
				Assert.isTrue(old.getIssue().equals(req.getIssue()));
				if (!old.getStatus().equals(req.getStatus())) {
					Assert.isTrue(req.getStatus().equals(Request.ACCEPTED) || req.getStatus().equals(Request.REJECTED) || req.getStatus().equals(Request.DELIVERED));
					//this.messService.sendNotiChangeStatus(req);
				}
				this.calculateWeightVolume(req);
				res = this.reqRepository.save(req);
			}//Customer
			else {
				Assert.isTrue(this.actorService.findActorType().equals("Customer"));
				//TODO: Controlar en el reconstruct que el customer es 
				//		el propietario de esta request
				//		Modifica todo menos el ticker, 
				Assert.isTrue(!old.isFinalMode());
				Assert.isTrue(old.getTicker().equals(req.getTicker()));

				Request clon = (Request) req.clone();
				this.calculateWeightVolume(clon);

				if (old.isFinalMode() != clon.isFinalMode()) {
					clon.setMoment(DateTime.now().minusMillis(1000).toDate());
					clon.setStatus(Request.SUBMITTED);
					//this.messService.sendNotiChangeStatus(req);
				}
			}

			res = this.reqRepository.save(req);
		}
		return res;
	}

	public void delete(Request req) {
		Assert.notNull(req);

		this.reqRepository.delete(req);
	}

	private void calculateWeightVolume(Request req) {
		Double volume = this.reqRepository.calculateVolume(req.getId());
		Double weight = this.reqRepository.calculateWeight(req.getId());

		req.setVolume(volume);
		req.setWeight(weight);

	}
	//Business methods
	//Mirar si hay alguna offer para esta request en el controller
	public Collection<Offer> findOffersByRequest(Request req) {
		return null;
	}

}
