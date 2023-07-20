package stepDefinitions;

import static org.junit.Assert.assertEquals;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {

    private String iccid;
    private ResponseEntity<String> responseEntity;

    @Given("a SIM card with ICCID {string}")
    public void setSimCardIccid(String iccid) {
        this.iccid = iccid;
    }

    @When("the SIM card is activated")
    public void activateSimCard() {
        String actuatorEndpoint = "http://localhost:8080/simapi/actuate";
        RestTemplate restTemplate = new RestTemplate();

        try {
            responseEntity = restTemplate.postForEntity(actuatorEndpoint, null, String.class);
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with the activation microservice.");
        }
    }

    @Then("the activation should be successful")
    public void verifyActivationSuccess() {
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SIM card activated successfully.", responseEntity.getBody());
    }

    @Then("the activation should fail")
    public void verifyActivationFailure() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("SIM card activation failed.", responseEntity.getBody());
    }

    @When("I query the activation status using the ICCID")
    public void queryActivationStatus() {
        String queryEndpoint = "http://localhost:8080/simapi/{id}";
        RestTemplate restTemplate = new RestTemplate();

        try {
            responseEntity = restTemplate.getForEntity(queryEndpoint, String.class, iccid);
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with the activation microservice.");
        }
    }

    @Then("the activation status should be {string}")
    public void verifyActivationStatus(String expectedStatus) {
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStatus, responseEntity.getBody());
    }
}