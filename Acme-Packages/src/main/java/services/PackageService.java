
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
		res.setDescription("");
		res.setCategories(new ArrayList<Category>());
		return res;
	}
	public Package save(Package pac) {
		Assert.notNull(pac);
		Package res;
		Assert.isTrue(this.actorService.findActorType().equals("Customer"));
		if (pac.getId() == 0) {
			//Reconstruct
			//Guardo el package
			res = this.pacRepository.save(pac);
		} else {
			Package old = this.pacRepository.findOne(pac.getId());
			//Owner es el customer logged y misma request
			UserAccount principal = LoginService.getPrincipal();
			Customer logged = this.cusService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			Customer owner = this.findCustomerByPackageId(pac.getId());
			Assert.isTrue(logged.getId() == owner.getId());
			//Guardo el package
			res = this.pacRepository.save(pac);
		}
		return res;
	}

	public void delete(Package pac) {
		Assert.notNull(pac);
		Request res = this.findRequestByPackageId(pac.getId());
		Assert.isTrue(!res.isFinalMode());

		//Borro el package de la request en la que estaba
		Request req = this.findRequestByPackageId(pac.getId());
		req.getPackages().remove(pac);
		this.reqService.removePackage(pac);

		this.pacRepository.delete(pac);

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

	//Reconstruct 
	public Package reconstruct(Package pac, BindingResult binding) {
		Package result;
		if (pac.getId() == 0) {
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
			result = this.findOne(pac.getId());
			Assert.isTrue(req.getPackages().contains(result));
			Package clon = (Package) pac.clone();
			clon.setCategories(pac.getCategories());
			clon.setWeight(pac.getWeight());
			clon.setHeight(pac.getHeight());
			clon.setWidth(pac.getWidth());
			clon.setLength(pac.getLength());
			clon.setDescription(pac.getDescription());
			result = clon;
		}
		this.validator.validate(result, binding);

		return result;
	}
	//Reconstruct addPackage
	public Package reconstruct(AddPackageForm apf, BindingResult binding) {
		Package result = this.create();
		result.setWeight(apf.getWeight());
		result.setDescription(apf.getDescription());
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
		pac.setDescription(crf.getPacDescription());
		pac.setLength(crf.getLength());
		pac.setWidth(crf.getWidth());
		pac.setHeight(crf.getHeight());
		pac.setCategories(crf.getCategories());
		return pac;
	}
}
