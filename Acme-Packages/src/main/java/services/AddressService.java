
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AddressRepository;
import domain.Address;
import domain.Town;

@Service
@Transactional
public class AddressService {

	//Repository
	@Autowired
	private AddressRepository	addRepository;
	//Services
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private ActorService		actorService;


	//Constructor
	public AddressService() {

	}
	//CRUD
	public Address findOne(int id) {
		Address res;
		res = this.addRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
	public Collection<Address> findAll() {
		Collection<Address> res;
		res = this.addRepository.findAll();
		Assert.notNull(res);
		return res;
	}
	public Address create() {
		Assert.isTrue(this.actorService.findActorType().equals("Customer"));
		Address res = new Address();
		res.setStreetAddress("");
		res.setComment("");
		res.setTown(new Town());
		return res;
	}
	public Address save(Address address) {
		Address res;
		if (address.getId() == 0) {
			Assert.notNull(address.getTown());
			res = this.addRepository.save(address);
		} else {

			res = this.addRepository.save(address);
		}
		return res;
	}
	//Business methods

}
