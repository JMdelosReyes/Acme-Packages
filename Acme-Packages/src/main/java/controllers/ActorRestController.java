
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pojos.ActorPojo;
import services.ActorService;

@RestController
public class ActorRestController {

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "getActor", method = RequestMethod.GET)
	public ActorPojo getPojo() {
		final ActorPojo actor = this.actorService.findActorPojo();

		return actor;
	}

}
