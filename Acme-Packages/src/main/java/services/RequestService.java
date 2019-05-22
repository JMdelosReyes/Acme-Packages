
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import utilities.Tickers;
import domain.Fare;
import domain.Offer;
import domain.Package;
import domain.Request;
import domain.Town;

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
	@Autowired
	private PackageService		pacService;


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
		res.setStreetAddress("");
		res.setComment("");
		res.setTown(new Town());
		Collection<Package> packages = new ArrayList<>();
		res.setPackages(packages);
		return res;
	}

	public Request save(Request req) {
		Assert.notNull(req);
		Request res;
		if (req.getId() == 0) {
			Assert.isTrue(this.actorService.findActorType().equals("Customer"));
			Assert.notEmpty(req.getPackages());
			req.setTicker(Tickers.generateTicker());
			if (req.isFinalMode()) {
				req.setMoment(DateTime.now().minusMillis(1000).toDate());
				req.setStatus(Request.SUBMITTED);
				//this.messService.sendNotiChangeStatus(req);
			} else {
				Assert.isTrue(req.getOffer() == null);
			}
			for (Package p : req.getPackages()) {
				Package pac = this.pacService.save(p, req);
				req.getPackages().add(pac);
			}
			//Probar que la offer sea final, que los fares den soporte a todos los 
			//paquetes de la request, que el vehicle tenga contempladas todas las 
			//categorias que tienen los paquetes, que la town de la request este en la offer
			if (req.getOffer() != null) {
				Offer of = req.getOffer();
				Assert.isTrue(of.isFinalMode() && !of.isCanceled());
				List<Fare> fares = new ArrayList<>(of.getFares());

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
				Assert.isTrue(old.getStreetAddress().equals(req.getStreetAddress()));
				Assert.isTrue(old.getComment().equals(req.getComment()));
				Assert.isTrue(old.getTown().equals(req.getTown()));
				Assert.isTrue(old.getPackages().equals(req.getPackages()));
				Assert.isTrue(old.getIssue().equals(req.getIssue()));
				if (!old.getStatus().equals(req.getStatus())) {
					Assert.isTrue(req.getStatus().equals(Request.ACCEPTED) || req.getStatus().equals(Request.REJECTED) || req.getStatus().equals(Request.DELIVERED));
					//this.messService.sendNotiChangeStatus(req);
				}
				res = this.reqRepository.save(this.calculateWeightVolume(req));
			}//Customer
			else {
				Assert.isTrue(this.actorService.findActorType().equals("Customer"));
				//TODO: Controlar en el reconstruct que el customer es 
				//		el propietario de esta request
				//		Modifica todo menos el ticker 
				Assert.isTrue(!old.isFinalMode());
				Assert.isTrue(old.getTicker().equals(req.getTicker()));

				Request recalculated = this.calculateWeightVolume(req);

				if (old.isFinalMode() != recalculated.isFinalMode()) {
					recalculated.setMoment(DateTime.now().minusMillis(1000).toDate());
					recalculated.setStatus(Request.SUBMITTED);
					//this.messService.sendNotiChangeStatus(req);
				}
				res = this.reqRepository.save(recalculated);
			}
		}
		return res;
	}

	public void delete(Request req) {
		Assert.notNull(req);

		this.reqRepository.delete(req);
	}

	private Request calculateWeightVolume(Request req) {
		Request res = (Request) req.clone();
		Double volume = this.reqRepository.calculateVolume(res.getId());
		Double weight = this.reqRepository.calculateWeight(res.getId());

		res.setVolume(volume);
		res.setWeight(weight);
		return res;
	}
	//Business methods
	//Mirar si hay alguna offer para esta request en el controller
	public Collection<Offer> findOffersByRequest(Request req) {
		return null;
	}

}
