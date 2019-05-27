
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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

	@Autowired
	private Validator				validator;


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
	public Solicitation save(Solicitation solicitation, final Integer vehicleId) {
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

			if (solicitation.getStatus().equals("REJECTED")) {
				Solicitation clon = (Solicitation) solicitation.clone();
				clon.setStartDate(null);
				clon.setEndDate(null);
				solicitation = clon;
			} else {

			}

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

		Auditor auditor = this.solicitationRepository.findAuditorBySolicitation(old.getId());
		if (auditor != null) {
			auditor.getSolicitations().remove(old);
		}

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
		Assert.isTrue(solicitation.getStatus().equals("PENDING"));
		auditor.getSolicitations().add(solicitation);
	}

	public Solicitation reconstruct(final Solicitation solicitation, final BindingResult binding) {
		Solicitation result;

		if (solicitation.getId() == 0) {
			Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
			result = solicitation;
			result.setMoment(DateTime.now().minusMillis(1000).toDate());
			result.setStartDate(null);
			result.setEndDate(null);
			result.setStatus("PENDING");

			this.validator.validate(result, binding);
		} else {
			Assert.isTrue(this.actorService.findActorType().equals("Auditor"));
			result = this.solicitationRepository.findOne(solicitation.getId());
			Assert.notNull(result);
			Assert.isTrue(result.getStatus().equals("PENDING"));

			int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Auditor auditor = this.auditorService.findOne(actorId);
			Assert.isTrue(auditor.getSolicitations().contains(result));

			Solicitation clon = (Solicitation) result.clone();

			clon.setStatus(solicitation.getStatus());
			clon.setStartDate(solicitation.getStartDate());
			clon.setEndDate(solicitation.getEndDate());

			result = clon;

			this.validator.validate(result, binding);

			if (binding.getFieldError("status") == null) {
				if (result.getStatus().equals("PENDING")) {
					binding.rejectValue("status", "sol.commit.error.changeStatus");
				}
			}

			if (binding.getFieldError("status") == null) {
				if (result.getStatus().equals("ACCEPTED")) {
					if ((binding.getFieldError("startDate") == null) && (binding.getFieldError("endDate") == null)) {
						Date date1 = DateUtils.truncate(DateTime.now().toDate(), java.util.Calendar.DAY_OF_MONTH);
						Date date2 = DateUtils.truncate(result.getStartDate(), java.util.Calendar.DAY_OF_MONTH);
						if (date1.after(date2)) {
							binding.rejectValue("startDate", "sol.commit.error.startDateBef");
						}
					}

					if ((binding.getFieldError("startDate") == null) && (binding.getFieldError("endDate") == null)) {
						Date date1 = DateUtils.truncate(result.getEndDate(), java.util.Calendar.DAY_OF_MONTH);
						Date date2 = DateUtils.truncate(result.getStartDate(), java.util.Calendar.DAY_OF_MONTH);
						if (date1.before(date2) || date1.equals(date2)) {
							binding.rejectValue("endDate", "sol.commit.error.endDateBef");
						}
					}
				}
			}
		}

		return result;
	}
	public Collection<String> findStatus() {
		Collection<String> status = new ArrayList<String>();
		status.add("ACCEPTED");
		status.add("REJECTED");
		status.add("PENDING");
		return status;
	}

}
