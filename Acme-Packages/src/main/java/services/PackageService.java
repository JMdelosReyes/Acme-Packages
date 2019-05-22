
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PackageRepository;
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

		return res;
	}
	public Package save(Package pac, Request req) {
		Assert.notNull(pac);
		Package res;
		if (pac.getId() == 0) {
			Assert.isTrue(this.actorService.findActorType().equals("Customer"));

			//Guardo el package
			res = this.pacRepository.save(pac);

			//Lo incluyo en el request
			req.getPackages().add(res);
			this.reqService.save(req);
		} else {
			Package old = this.pacRepository.findOne(pac.getId());

			//Guardo el package
			res = this.pacRepository.save(pac);
			//Lo incluyo en el request
			req.getPackages().add(res);
			this.reqService.save(req);

		}
		return res;
	}
	public void delete(Package pac) {

	}
	//Business methods

}
