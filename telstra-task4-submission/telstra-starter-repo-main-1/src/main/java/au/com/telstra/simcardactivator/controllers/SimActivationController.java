package au.com.telstra.simcardactivator.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import au.com.telstra.simcardactivator.Sim;
import au.com.telstra.simcardactivator.entities.SimCard;
import au.com.telstra.simcardactivator.repos.SimCardRepository;
import au.com.telstra.simcardactivator.status.ActivationRequest;
import au.com.telstra.simcardactivator.status.ActivationResponse;

@RestController
public class SimActivationController {

	@Autowired
	SimCardRepository repository;

	private static final Logger log = LoggerFactory.getLogger(SimActivationController.class);

	@PostMapping("/simapi/actuate")
	public ResponseEntity<String> activateSimCard(@RequestBody Sim sim) {

		if (sim.iccid() == null || sim.iccid().isEmpty() || sim.email() == null || sim.email().isEmpty()) {
			return ResponseEntity.badRequest().body("ICCID and customer email must be provided.");
		}

		String actuatorEndpoint = "http://localhost:8444/actuate";
		RestTemplate restTemplate = new RestTemplate();
		ActivationResponse response;
		System.out.println(sim.iccid() + sim.email());

		try {
			response = restTemplate.postForObject(actuatorEndpoint, new ActivationRequest(sim.iccid(), sim.email()),
					ActivationResponse.class);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error communicating with the activation microservice.");
		}

		if (response == null || response.success() == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Invalid response from the activation microservice.");
		}

		SimCard simCard = new SimCard(sim.iccid(), sim.email(), response.success());

		if (response.success()) {
			repository.save(simCard);
			return ResponseEntity.ok("SIM card activated successfully.");
		} else {
			repository.save(simCard);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SIM card activation failed.");
		}
	}

	@GetMapping("/simapi/{id}")
	public ResponseEntity<SimCard> findSimById(@PathVariable("id") long id) {
		Optional<SimCard> optionalSim = repository.findById(id);
        if (optionalSim.isPresent()) {
            SimCard simCard = optionalSim.get();
            return ResponseEntity.ok(simCard);
        } else {
            System.out.println("Sim Card Doesn't Exist in Database");
            return ResponseEntity.notFound().build();
        }
    }
}