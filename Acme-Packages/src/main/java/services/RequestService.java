
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.LoginService;
import security.UserAccount;
import utilities.Tickers;
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
import forms.CreateRequestForm;

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
	@Autowired
	private Validator			validator;


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
			this.calculateWeightVolume(req);
			res = this.reqRepository.save(req);
			UserAccount principal = LoginService.getPrincipal();
			Customer cus = this.cusService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			cus.getRequests().add(res);
		} else {
			Assert.isTrue(req.getId() != 0);
			Assert.isTrue(this.actorService.findActorType().equals("Customer"));
			UserAccount principal = LoginService.getPrincipal();
			Customer cus = this.cusService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			res = this.findOne(req.getId());
			Assert.isTrue(cus.getRequests().contains(res));
			Request clon = (Request) req.clone();
			this.calculateWeightVolume(clon);
			res = this.reqRepository.save(req);
		}
		return res;

	}
	public void delete(Request req) {
		Assert.notNull(req);
		Assert.isTrue(!req.isFinalMode());

		Customer cus = this.reqRepository.findCustomerByRequestId(req.getId());
		cus.getRequests().remove(req);
		this.cusService.save(cus);

		this.reqRepository.delete(req.getId());
	}

	private Request calculateWeightVolume(Request req) {
		double volume = 0.0;
		double weight = 0.0;
		Request result;
		if (req.getId() == 0) {
			result = req;
			List<Package> packs = new ArrayList<>(req.getPackages());
			Package firstPac = packs.get(0);
			volume = (firstPac.getHeight() * firstPac.getLength() * firstPac.getWidth());
			weight = firstPac.getWeight();
			result.setVolume(volume);
			result.setWeight(weight);

		} else {
			for (Package p : req.getPackages()) {
				volume += (p.getHeight() * p.getLength() * p.getWidth());
				weight += p.getWeight();
			}
			req.setVolume(volume);
			req.setWeight(weight);
			result = req;
		}
		return result;
	}
	public void actualizaValores(Request req) {
		req.setVolume(this.reqRepository.calculateVolume(req.getId()));
		req.setWeight(this.reqRepository.calculateWeight(req.getId()));
	}
	public void flush() {
		this.reqRepository.flush();
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
	public Collection<Category> findCategoriesPackagesByRequestId(Request req) {
		Collection<Category> res = new ArrayList<Category>();
		res = this.reqRepository.findCategoriesPackagesByRequestId(req.getId());
		Assert.notNull(res);
		return res;
	}

	public Collection<Fare> findFaresOrderedByPriceByPackageId(Package pac) {
		Collection<Fare> res = new ArrayList<Fare>();
		res = this.reqRepository.findFaresOrderedByPriceByPackageId(pac.getId());
		Assert.notEmpty(res);
		return res;
	}
	public Collection<Request> findRequestsByCarrierId(int actorId) {
		Collection<Request> res = new ArrayList<Request>();
		res = this.reqRepository.findRequestsByCarrierId(actorId);
		Assert.notEmpty(res);
		return res;
	}

	//Anyadir paquetes
	public void anyadePackage(Package pack, Request req) {
		Assert.isTrue(!req.isFinalMode());
		Request clon = (Request) req.clone();
		Package res = this.pacService.save(pack);
		List<Package> newPackages = new ArrayList<>(req.getPackages());
		newPackages.add(res);
		clon.setPackages(newPackages);
		this.save(clon);
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

	public void deleteRequestOfOffer(Offer o) {
		for (Request r : o.getRequests()) {
			this.reqRepository.findCustomerByRequestId(r.getId()).getRequests().remove(r);
			this.reqRepository.delete(r);
		}
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
	//Buscar por issue
	public Request findRequestByIssueId(int id) {
		Assert.isTrue(id != 0);
		Assert.notNull(id);
		Request res;
		res = this.reqRepository.findRequestByIssueId(id);
		Assert.notNull(res);
		return res;
	}

	//Aceptar o Rechazar la request
	public void changeStatus(int id, String status) {
		Request result;
		if (status.equals(Request.ACCEPTED)) {
			UserAccount principal = LoginService.getPrincipal();
			Carrier owner = this.reqRepository.findCarrierByOfferId(this.findOne(id).getOffer().getId());
			Carrier logged = this.carService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			Assert.isTrue(owner.getId() == logged.getId());
			result = this.findOne(id);
			Assert.isTrue(result.isFinalMode());
			Request clon = (Request) result.clone();
			clon.setStatus(Request.ACCEPTED);
			result = clon;
			result = this.reqRepository.save(result);
			//Contiene en la ruta la ciudad destino
			Assert.isTrue(this.findTownsByOfferId(result.getOffer()).contains(result.getTown()));
			//Fecha de reparto estimada anterior o igual a la fecha de maxima de entrega
			Assert.isTrue(this.findTraverseTownOfDestinationTownByRequestId(result).getEstimatedDate().before(result.getDeadline()) || this.findTraverseTownOfDestinationTownByRequestId(result).getEstimatedDate().equals(result.getDeadline()));
			//Contiene todas las categorias para transportar todos los paquetes
			Assert.isTrue(this.findOfferCategoriesByOfferId(result.getOffer()).containsAll(this.findCategoriesPackagesByRequestId(result)));
			//Tiene volumen y peso disponible para llevar la request
			Assert.isTrue(this.volumeCanBeAddedToOfferByVolumeOfRequestAndOfferId(result, result.getOffer()));
			Assert.isTrue(this.weightCanBeAddedToOfferByWeightOfRequestAndOfferId(result, result.getOffer()));
			//No se si se me pasa algun caso por comprobar m�s 

			//Actualizar prices de packages
			//Comprobar que no se pasa de maxPrice
			double price = 0.0;
			for (Package p : result.getPackages()) {
				List<Fare> fares = new ArrayList<>(this.findFaresOrderedByPriceByPackageId(p));
				Assert.notEmpty(fares);
				Package pclon = (Package) p.clone();
				pclon.setPrice(fares.get(0).getPrice());
				p = pclon;
				this.pacService.save(p);
				price += p.getPrice();
				Assert.isTrue(result.getMaxPrice() >= price);
			}

		} else {
			UserAccount principal = LoginService.getPrincipal();
			Carrier owner = this.reqRepository.findCarrierByOfferId(this.findOne(id).getOffer().getId());
			Carrier logged = this.carService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			Assert.isTrue(owner.getId() == logged.getId());
			result = this.findOne(id);
			Assert.isTrue(result.isFinalMode());
			Request clon = (Request) result.clone();
			clon.setStatus(Request.REJECTED);
			result = clon;
			result = this.reqRepository.save(result);
		}

	}
	//Reconstruct TODOOOOOOOOOO
	public Request reconstruct(Request req, BindingResult binding) {
		Request result;
		if (req.getId() == 0) {
			Assert.isTrue(this.actorService.findActorType().equals("Customer"));
			result = req;
			req.setTicker(Tickers.generateTicker());
			if (req.isFinalMode()) {
				req.setStatus(Request.SUBMITTED);
				req.setMoment(DateTime.now().minusMillis(1000).toDate());
			}
			req.setOffer(null);

		} else {
			result = this.findOne(req.getId());
			Assert.isTrue(!result.isFinalMode());

			Request clon = (Request) result.clone();
			clon.setDescription(req.getDescription());
			clon.setMaxPrice(req.getMaxPrice());
			clon.setFinalMode(req.isFinalMode());
			clon.setStreetAddress(req.getStreetAddress());
			clon.setComment(req.getComment());
			clon.setTown(req.getTown());

			result = clon;
		}

		this.validator.validate(result, binding);

		return result;

	}
	public Request setearCampos(CreateRequestForm crf) {
		Request res = this.create();
		res.setDescription(crf.getDescription());
		res.setMaxPrice(crf.getMaxPrice());
		res.setDeadline(crf.getDeadline());
		res.setFinalMode(crf.isFinalMode());
		res.setStreetAddress(crf.getStreetAddress());
		res.setComment(crf.getComment());
		res.setTown(crf.getTown());
		return res;
	}

}
