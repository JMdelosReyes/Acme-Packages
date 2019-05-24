
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VehicleRepository;
import security.LoginService;
import utilities.Validators;
import domain.Carrier;
import domain.Solicitation;
import domain.Vehicle;

@Service
@Transactional
public class VehicleService {

	@Autowired
	private VehicleRepository	vehicleRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CarrierService		carrierService;


	public VehicleService() {

	}

	public Collection<Vehicle> findAll() {
		final Collection<Vehicle> result = this.vehicleRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Vehicle findOne(final int id) {
		Assert.isTrue(id > 0);
		Vehicle result;
		result = this.vehicleRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Vehicle create() {
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		final Vehicle result = new Vehicle();
		result.setComment("");
		result.setMaxVolume(0.);
		result.setMaxWeight(0.);
		result.setPictures(new ArrayList<String>());
		result.setPlate("");
		result.setSolicitations(new ArrayList<Solicitation>());
		result.setType("");

		return result;
	}

	public Vehicle save(final Vehicle vehicle) {
		Assert.notNull(vehicle);
		final Vehicle result;

		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		Assert.isTrue(Validators.checkImageCollection(vehicle.getPictures()));

		if (vehicle.getId() == 0) {
			result = this.vehicleRepository.save(vehicle);
			Assert.notNull(result);
			carrier.getVehicles().add(result);
			this.carrierService.save(carrier);
		} else {
			final Vehicle old = this.vehicleRepository.findOne(vehicle.getId());
			Assert.notNull(old);
			Assert.isTrue(carrier.getVehicles().contains(old));

			if (!this.vehicleRepository.canBeEditedOrDeleted(vehicle.getId())) {
				Assert.isTrue(old.getMaxVolume().equals(vehicle.getMaxVolume()));
				Assert.isTrue(old.getMaxWeight().equals(vehicle.getMaxWeight()));
				Assert.isTrue(old.getPictures().equals(vehicle.getPictures()));
				Assert.isTrue(old.getPlate().equals(vehicle.getPlate()));
				Assert.isTrue(old.getComment().equals(vehicle.getComment()));
				Assert.isTrue(old.getType().equals(vehicle.getType()));
			}

			result = this.vehicleRepository.save(vehicle);
			Assert.notNull(result);
		}

		return result;
	}
	public void delete(final Vehicle vehicle) {
		Assert.notNull(vehicle);
		Assert.isTrue(vehicle.getId() > 0);

		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		final Vehicle old = this.vehicleRepository.findOne(vehicle.getId());
		Assert.notNull(old);
		Assert.isTrue(carrier.getVehicles().contains(old));

		Assert.isTrue(this.vehicleRepository.canBeEditedOrDeleted(vehicle.getId()));

		carrier.getVehicles().remove(old);
		this.vehicleRepository.delete(old.getId());
	}

	public void flush() {
		this.vehicleRepository.flush();
	}

	public Boolean canBeEditedOrDeleted(int id) {
		Assert.isTrue(id > 0);
		return this.canBeEditedOrDeleted(id);
	}

}
