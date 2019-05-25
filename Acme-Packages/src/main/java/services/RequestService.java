
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.LoginService;
import security.UserAccount;
import domain.Carrier;
import domain.Category;
import domain.Customer;
import domain.Fare;
import domain.Issue;
import domain.Offer;
import domain.Package;
import domain.Request;
import domain.Town;
import domain.TraverseTown;

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
	@Autowired
	private TownService			townService;


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
			res = this.reqRepository.save(req);
		} else {
			res = this.reqRepository.save(req);
		}
		return res;

	}
	public void delete(Request req) {
		Assert.notNull(req);
		Assert.isTrue(!req.isFinalMode());

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
	public Boolean weightCanBeAddedToOfferByWeightOfRequestAndOfferId(Request req, Offer of) {
		Boolean res;
		res = this.reqRepository.weightCanBeAddedToOfferByWeightOfRequestAndOfferId(req.getWeight(), of.getId());
		return res;
	}

	public Boolean volumeCanBeAddedToOfferByVolumeOfRequestAndOfferId(Request req, Offer of) {
		Boolean res;
		res = this.reqRepository.volumeCanBeAddedToOfferByVolumeOfRequestAndOfferId(req.getWeight(), of.getId());
		return res;
	}
	public Collection<Town> findTownsByOfferId(Offer of) {
		Collection<Town> res = new ArrayList<Town>();
		res = this.reqRepository.findTownsByOfferId(of.getId());
		Assert.notNull(res);
		return res;
	}
	public TraverseTown findTraverseTownOfDestinationTownByRequestId(Request req) {
		TraverseTown res;
		res = this.reqRepository.findTraverseTownOfDestinationTownByRequestId(req.getId());
		Assert.notNull(res);
		return res;
	}
	public Collection<Category> findOfferCategoriesByOfferId(Offer of) {
		Collection<Category> res = new ArrayList<Category>();
		res = this.reqRepository.findOfferCategoriesByOfferId(of.getId());
		Assert.notNull(res);
		return res;
	}
	public Collection<Fare> findFaresOrderedByPriceByPackageId(Package pac) {
		Collection<Fare> res = new ArrayList<Fare>();
		res = this.reqRepository.findFaresOrderedByPriceByPackageId(pac.getId());
		Assert.notEmpty(res);
		return res;
	}

	//Anyadir paquetes
	public void anyadePackage(Package pack, Request req) {
		Assert.isTrue(!req.isFinalMode());
		Package res = this.pacService.save(pack);
		req.getPackages().add(res);
		this.reqRepository.save(req);
	}
	//Eliminar paquetes
	public void eliminaPackage(Package pack) {
		Assert.notNull(pack);
		this.pacService.delete(pack);
	}
	//Aplicar a una offer
	public void applyOffer(Request req, Offer of) {
		Request old = this.findOne(req.getId());
		Assert.isTrue(of.isFinalMode());

		Assert.isTrue(this.weightCanBeAddedToOfferByWeightOfRequestAndOfferId(req, of));
		Assert.isTrue(this.volumeCanBeAddedToOfferByVolumeOfRequestAndOfferId(req, of));

		Assert.isTrue(this.findTownsByOfferId(of).contains(req.getTown()));

		//TODO: Fijo que petardean los formatos de fecha
		Date estimatedDate = this.findTraverseTownOfDestinationTownByRequestId(req).getEstimatedDate();
		Assert.isTrue(estimatedDate.after(req.getDeadline()) || estimatedDate.equals(req.getDeadline()));

		List<Category> catAvailables = new ArrayList<>(this.findOfferCategoriesByOfferId(of));
		for (Package p : req.getPackages()) {
			Assert.isTrue(catAvailables.containsAll(p.getCategories()) || catAvailables.equals(p.getCategories()));
			List<Fare> fares = new ArrayList<>();
			fares = new ArrayList<>(this.findFaresOrderedByPriceByPackageId(p));
			Assert.notEmpty(fares);
			this.pacService.changePrice(p, fares.get(0));
		}

		this.offService.addRequest(this.reqRepository.save(req), of.getId());
	}

	//Crear una issue
	public void addIssue(Issue issue, Request req) {
		Assert.notNull(issue);
		Assert.notNull(req);
		Assert.isTrue(req.isFinalMode());
		UserAccount principal = LoginService.getPrincipal();
		Customer logged = this.cusService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
		Customer owner = this.reqRepository.findCustomerByRequestId(req.getId());
		Request clon = (Request) req.clone();
		clon.setIssue(issue);
		req = clon;
		this.reqRepository.save(req);
	}
	//Rechazar la request
	public void changeStatus(Request req) {
		Assert.isTrue(req.isFinalMode());
		UserAccount principal = LoginService.getPrincipal();
		Carrier owner = this.reqRepository.findCarrierByOfferId(req.getOffer().getId());
		Carrier logged = this.carService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
		Assert.isTrue(owner.getId() == logged.getId());
		Request old = this.findOne(req.getId());
		Request clon = (Request) old.clone();
		clon.setStatus(req.getStatus());
		old = clon;
		this.reqRepository.save(old);
	}

	public void deleteRequestOfOffer(Offer o) {
		for (Request r : o.getRequests()) {
			this.reqRepository.findCustomerByRequestId(r.getId()).getRequests().remove(r);
			this.reqRepository.delete(r);
		}
	}
}
