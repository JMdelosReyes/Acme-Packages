
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PackageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Customer;
import domain.Package;
import domain.Request;

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
	public Package save(Package pac, Request req) {
		Assert.notNull(pac);
		Package res;
		Assert.isTrue(this.actorService.findActorType().equals("Customer"));
		if (pac.getId() == 0) {
			//Comprobar que esa req es de ese customer(solo si ya esta creada la req en bbdd)
			if ((req.getId() != 0) && !req.isFinalMode()) {
				Customer cus = this.cusService.findOne(this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId());
				Assert.isTrue(cus.getRequests().contains(req));
			}
			//Guardo el package
			res = this.pacRepository.save(pac);

			//Lo incluyo en la request
			req.getPackages().add(res);
			this.reqService.save(req);
		} else {
			Assert.isTrue(!req.isFinalMode());
			Package old = this.pacRepository.findOne(pac.getId());
			//Owner es el customer logged y misma request
			UserAccount principal = LoginService.getPrincipal();
			Customer logged = this.cusService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			Customer owner = this.findCustomerByPackageId(pac.getId());
			Assert.isTrue(logged.equals(owner));
			Assert.isTrue(this.findRequestByPackageId(pac.getId()).equals(req.getId()));
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
		this.reqService.save(req);

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

}
