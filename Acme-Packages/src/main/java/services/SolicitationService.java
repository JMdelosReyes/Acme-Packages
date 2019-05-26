
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SolicitationRepository;
import security.LoginService;
import domain.Auditor;
import domain.Carrier;
import domain.Solicitation;
import domain.Vehicle;

@Service
@Transactional
public class SolicitationService {

	@Autowired
	private SolicitationRepository	solicitationRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CarrierService			carrierService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private VehicleService			vehicleService;


	public SolicitationService() {

	}

	public Collection<Solicitation> findAll() {
		final Collection<Solicitation> solicitations = this.solicitationRepository.findAll();
		Assert.notNull(solicitations);
		return solicitations;
	}

	public Solicitation findOne(final int id) {
		Assert.isTrue(id > 0);
		Solicitation result;
		result = this.solicitationRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Solicitation create() {

		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

		final Solicitation result = new Solicitation();
		result.setCategory(null);
		result.setComments(new ArrayList<String>());
		result.setEndDate(null);
		result.setMoment(null);
		result.setStartDate(null);
		result.setStatus("");

		return result;
	}

	//VehicleId solo es necesario cuando es una nueva solicitud, en otro caso se puede pasar null
	public Solicitation save(final Solicitation solicitation, final Integer vehicleId) {
		Assert.notNull(solicitation);
		Solicitation result = null;

		if (solicitation.getId() == 0) {

			Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

			final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(id);
			Assert.notNull(carrier);

			final Vehicle vehicle = this.vehicleService.findOne(vehicleId);

			Assert.isTrue(carrier.getVehicles().contains(vehicle));

			solicitation.setMoment(DateTime.now().minusMillis(1000).toDate());

			solicitation.setStatus("PENDING");
			solicitation.setEndDate(null);
			solicitation.setStartDate(null);

			result = this.solicitationRepository.save(solicitation);
			Assert.notNull(result);

			vehicle.getSolicitations().add(result);

			this.vehicleService.save(vehicle);
		} else if (this.actorService.findActorType().equals("Carrier")) {
			final Solicitation old = this.solicitationRepository.findOne(solicitation.getId());
			Assert.notNull(old);

			final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();

			Assert.isTrue(this.isSolicitationofCarrier(old, id));

			Assert.isTrue(old.getStatus().equals("PENDING"));
			Assert.isTrue(old.getStatus().equals(solicitation.getStatus()));
			Assert.isTrue(((old.getEndDate() == null) && (solicitation.getEndDate() == null)) || old.getEndDate().equals(solicitation.getEndDate()));
			Assert.isTrue(old.getMoment().equals(solicitation.getMoment()));
			Assert.isTrue(((old.getStartDate() == null) && (solicitation.getStartDate() == null)) || old.getStartDate().equals(solicitation.getStartDate()));

			result = this.solicitationRepository.save(solicitation);
			Assert.notNull(result);
		} else if (this.actorService.findActorType().equals("Auditor")) {
			final Solicitation old = this.solicitationRepository.findOne(solicitation.getId());
			Assert.notNull(old);

			final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Auditor auditor = this.auditorService.findOne(id);
			Assert.notNull(auditor);

			Assert.isTrue(auditor.getSolicitations().contains(old));

			Assert.isTrue(old.getStatus().equals("PENDING"));
			Assert.isTrue(old.getMoment().equals(solicitation.getMoment()));
			Assert.isTrue(old.getCategory().equals(solicitation.getCategory()));

			result = this.solicitationRepository.save(solicitation);
			Assert.notNull(result);
		}

		return result;
	}

	public void delete(final Solicitation solicitation) {
		Assert.notNull(solicitation);
		Assert.isTrue(solicitation.getId() > 0);

		final Solicitation old = this.solicitationRepository.findOne(solicitation.getId());
		Assert.notNull(old);

		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		Assert.isTrue(this.findSolicitationsOfCarrier(id).contains(old));

		Assert.isTrue(old.getStatus().equals("PENDING"));

		Vehicle v = this.vehicleOfSolicitation(old.getId());
		v.getSolicitations().remove(old);

		this.solicitationRepository.delete(old.getId());
	}

	public void flush() {
		this.solicitationRepository.flush();
	}

	public Collection<Solicitation> findSolicitationsOfCarrier(int idCarrier) {
		final Collection<Solicitation> solicitations = new ArrayList<>(this.solicitationRepository.solicitationsOfCarrier(idCarrier));
		Assert.notNull(solicitations);
		return solicitations;
	}

	private Vehicle vehicleOfSolicitation(int idVehicle) {
		return this.solicitationRepository.vehicleOfSolicitation(idVehicle);
	}

	private Boolean isSolicitationofCarrier(Solicitation s, int idCarrier) {

		return this.findSolicitationsOfCarrier(idCarrier).contains(s);
	}

	public Collection<Solicitation> findUnassigned() {
		return this.solicitationRepository.findUnassigned();
	}

	public void assign(Solicitation solicitation, int auditorId) {
		Assert.isTrue(this.solicitationRepository.findUnassigned().contains(solicitation));
		Auditor auditor = this.auditorService.findOne(auditorId);
		auditor.getSolicitations().add(solicitation);
	}

}
