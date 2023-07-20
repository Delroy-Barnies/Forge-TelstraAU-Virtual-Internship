package au.com.telstra.simcardactivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import au.com.telstra.simcardactivator.repos.SimCardRepository;

@EnableJpaRepositories(basePackageClasses=SimCardRepository.class)
@SpringBootApplication
public class SimCardActivator {
	
	private static final Logger log = LoggerFactory.getLogger(SimCardActivator.class);

    public static void main(String[] args) {
        SpringApplication.run(SimCardActivator.class, args);
    }
    
    /*@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
    
    @Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Sim sim = restTemplate.getForObject(
					"http://localhost:8444/actuate", Sim.class);
			log.info(sim.toString());
		};
	}*/

}
