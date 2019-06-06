
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PackageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Carrier;
import domain.Category;
import domain.Customer;
import domain.Fare;
import domain.Package;
import domain.Request;
import forms.AddPackageForm;
import forms.CreateRequestForm;

@Service
@Transactional
public class PackageService {

	//Repository
	@Autowired
	private PackageRepository	pacRepository;
	//Services
	@Autowired
	private RequestService		reqService;
	@Autowired
	private CustomerService		cusService;
	@Autowired
	private CarrierService		carService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private Validator			validator;


	//Constructor
	public PackageService() {

	}
	//CRUD
	public Package findOne(int id) {
		Package res;
		res = this.pacRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
	public Collection<Package> findAll() {
		Collection<Package> res;
		res = this.pacRepository.findAll();
		Assert.notNull(res);
		return res;
	}
	public Package create() {
		Package res = new Package();
		res.setWeight(0.0);
		res.setHeight(0.0);
		res.setWidth(0.0);
		res.setLength(0.0);
		res.setDetails("");
		res.setCategories(new ArrayList<Category>());
		return res;
	}
	public Package save(Package pac) {
		Assert.notNull(pac);
		Package res;
		Assert.isTrue(this.actorService.findActorType().equals("Customer") || this.actorService.findActorType().equals("Carrier"));
		if (pac.getId() == 0) {
			//Reconstruct
			//Guardo el package
			res = this.pacRepository.save(pac);
		} else {

			if (this.actorService.findActorType().equals("Customer")) {
				Package old = this.pacRepository.findOne(pac.getId());

				UserAccount principal = LoginService.getPrincipal();
				Customer logged = this.cusService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
				Customer owner = this.findCustomerByPackageId(pac.getId());
				Assert.isTrue(logged.getId() == owner.getId());
				Assert.isTrue(owner.getRequests().contains(this.findRequestByPackageId(old.getId())) || !this.findRequestByPackageId(old.getId()).isFinalMode());

				if (pac.getPrice() != null) {
					Assert.notNull(this.findRequestByPackageId(old.getId()).getOffer());
				}
				//				Package clon = (Package) pac.clone();
				//				clon.setHeight(pac.getHeight());
				//				clon.setLength(pac.getLength());
				//				clon.setWidth(pac.getWidth());
				//				clon.setWeight(pac.getWeight());
				//				clon.setDetails(pac.getDetails());
				//				clon.setCategories(pac.getCategories());
				res = this.pacRepository.save(pac);
				Assert.notNull(res);
				Request r = this.findRequestByPackageId(res.getId());
				this.computeVolumeWeight(r.getId());
			} else {
				res = this.pacRepository.findOne(pac.getId());
				UserAccount principal = LoginService.getPrincipal();
				Carrier logged = this.carService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
				Carrier owner = this.findCarrierByPackageId(pac.getId());
				Assert.isTrue(logged.getId() == owner.getId());
				Assert.isTrue(this.findRequestByPackageId(res.getId()).isFinalMode());
				Package clon = (Package) res.clone();
				clon.setPrice(pac.getPrice());
				res = clon;
				this.pacRepository.save(res);
				this.flush();
			}
		}
		Assert.notNull(res);

		return res;
	}

	public void computeVolumeWeight(int id) {
		Assert.isTrue(id > 0);

		Request req = this.reqService.findOne(id);

		double weight = 0.0;
		double volume = 0.0;

		for (Package p : req.getPackages()) {
			weight += p.getWeight();
			volume += p.getHeight() * p.getWidth() * p.getLength();
		}

		this.pacRepository.updateWeightVolumeRequest(req.getId(), volume, weight);
	}

	public void delete(Package pac) {
		Request req = this.findRequestByPackageId(pac.getId());
		Assert.isTrue(!req.isFinalMode());
		req.getPackages().remove(this.findOne(pac.getId()));
		this.reqService.save(req);
		this.pacRepository.delete(pac.getId());

	}
	public void flush() {
		this.pacRepository.flush();
	}
	//Business methods
	public Customer findCustomerByPackageId(int id) {
		Customer res;
		res = this.pacRepository.findCustomerByPackageId(id);
		Assert.notNull(res);
		return res;
	}

	public Request findRequestByPackageId(int id) {
		Request res;
		res = this.pacRepository.findRequestByPackageId(id);
		Assert.notNull(res);
		return res;
	}
	public void changePrice(Package pac, Fare fare) {
		Package clon = (Package) pac.clone();
		clon.setPrice(fare.getPrice());
		pac = clon;
		this.pacRepository.save(pac);
	}
	public Carrier findCarrierByPackageId(int id) {
		Carrier res;
		res = this.pacRepository.findCarrierByPackageId(id);
		Assert.notNull(res);
		return res;
	}

	//Reconstruct 
	public Package reconstruct(Package pac, BindingResult binding) {
		Package result;
		if (pac.getId() == 0) {
			Assert.isTrue(this.actorService.findActorType().equals("Customer"));
			Assert.notNull(pac);
			result = pac;
			result.setPrice(null);
		} else {
			Assert.notNull(pac);
			UserAccount principal = LoginService.getPrincipal();
			Customer cus = this.cusService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());

			Request req = this.findRequestByPackageId(pac.getId());
			Assert.isTrue(cus.getRequests().contains(req));
			Assert.isTrue(!req.isFinalMode());

			result = this.pacRepository.findOne(pac.getId());
			Assert.isTrue(req.getPackages().contains(result));

			Package clon = (Package) result.clone();
			clon.setCategories(pac.getCategories());
			clon.setWeight(pac.getWeight());
			clon.setHeight(pac.getHeight());
			clon.setWidth(pac.getWidth());
			clon.setLength(pac.getLength());
			clon.setDetails(pac.getDetails());
			result = clon;
		}
		this.validator.validate(result, binding);

		return result;
	}
	//Reconstruct addPackage
	public Package reconstruct(AddPackageForm apf, BindingResult binding) {
		Package result = this.create();
		result.setWeight(apf.getWeight());
		result.setDetails(apf.getDetails());
		result.setLength(apf.getLength());
		result.setWidth(apf.getWidth());
		result.setHeight(apf.getHeight());
		result.setCategories(apf.getCategories());
		result.setPrice(null);

		this.validator.validate(result, binding);

		return result;
	}
	public Package setearCampos(CreateRequestForm crf) {
		Package pac = this.create();
		pac.setWeight(crf.getWeight());
		pac.setDetails(crf.getDetails());
		pac.setLength(crf.getLength());
		pac.setWidth(crf.getWidth());
		pac.setHeight(crf.getHeight());
		pac.setCategories(crf.getCategories());
		return pac;
	}
}
