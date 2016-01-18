package com.kata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kata.service.command.CommandProcessorService;
import com.kata.service.provisioning.DataProvisioningService;

@SpringBootApplication
/*
 * Main application starting point. By implementing CommandLineRunner 
 * Spring will automatically run this class's run method after 
 * application initialization
 */
public class KataRepositoryApplication implements CommandLineRunner {

	@Autowired
	@Qualifier("dynamicCommandProcessorService")
	private CommandProcessorService commandProcessorService;
	
	@Autowired
	@Qualifier("dynamicDataProvisioningService")
	private DataProvisioningService dataProvisioningService;
	
	
	public static void main(String[] args) {
		SpringApplication.run(KataRepositoryApplication.class, args);
	}
	
	@Override
	/*
	 * Application entry point - provisions data with the injected service and 
	 * start the inject commandprocessorService
	 * (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	public void run(String... arg0) throws Exception {
		dataProvisioningService.provision();
		commandProcessorService.run();
	}	
	
}
