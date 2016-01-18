package com.kata.service.config;

import java.util.Scanner;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kata.exception.ServiceLocationException;
import com.kata.service.command.CommandProcessorService;
import com.kata.service.output.OutputService;
import com.kata.service.provisioning.DataProvisioningService;
import com.kata.service.repository.RepositoryService;

@Configuration
@ConfigurationProperties(prefix="application")
/*
 * This class is a service wrapper used to allow for the autowiring of services
 * based upon the included or externalized application.yml
 * 
 * The usage of inner classes here is to allow for the nesting of @ConfigurationProperties
 * within the application.yml. For example: application.services.outputServiceName
 * 
 * Implementing ApplicationContextAware allows Spring to inject the context into this component 
 */
public class DynamicServiceFactory implements ApplicationContextAware {
	private Services services;
	private ApplicationContext applicationContext;
		
	/*
	 * These service methods (dynamic*Service) allow the application to use a simple @Autowire annotation on an 
	 * OutputService attribute with a @Qualifier of dynamic*Service. With this implementation 
	 * we can now vary the actual implementation based on values in the application.yml 
	 * and not have it hardcoded in the application
	 */
	@Bean
	public OutputService dynamicOutputService() throws ServiceLocationException {
		return returnServiceObject(services.getOutputServiceName(), OutputService.class);
	}
	
	@Bean
	public CommandProcessorService dynamicCommandProcessorService() throws ServiceLocationException {
		return returnServiceObject(services.getCommandProcessorServiceName(), CommandProcessorService.class);
	}
	
	@Bean
	public RepositoryService dynamicRepositoryService() throws ServiceLocationException {
		return returnServiceObject(services.getRepositoryServiceName(), RepositoryService.class);
	}
	
	@Bean
	public DataProvisioningService dynamicDataProvisioningService() throws ServiceLocationException {
		return returnServiceObject(services.getDataProvisioningServiceName(), DataProvisioningService.class);
	}
	
	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}
	
	@SuppressWarnings("unchecked")
	/*
	 * Paramaterized method to be used to retrieve beans for all dynamic*Service calls
	 */
	protected <T> T returnServiceObject(String serviceName, Class<T> clazz) throws ServiceLocationException {
		if (serviceName != null && applicationContext.containsBean(serviceName)) {
			try {
				return (T)applicationContext.getBean(serviceName);
			} catch (Exception e) {
				throw new ServiceLocationException("Could not locate service name: " + serviceName);	
			}
		} else {
			throw new ServiceLocationException("Could not locate service name: " + serviceName);
		}
	}
		
	@Override	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;		
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public static class Services {
		private String outputServiceName;
		private String repositoryServiceName;
		private String commandProcessorServiceName;
		private String dataProvisioningServiceName;
		
		public String getOutputServiceName() {
			return outputServiceName;
		}
		public String getRepositoryServiceName() {
			return repositoryServiceName;
		}
		public String getCommandProcessorServiceName() {
			return commandProcessorServiceName;
		}
		public void setOutputServiceName(String outputServiceName) {
			this.outputServiceName = outputServiceName;
		}
		public void setRepositoryServiceName(String repositoryServiceName) {
			this.repositoryServiceName = repositoryServiceName;
		}
		public void setCommandProcessorServiceName(String commandProcessorServiceName) {
			this.commandProcessorServiceName = commandProcessorServiceName;
		}
		public String getDataProvisioningServiceName() {
			return dataProvisioningServiceName;
		}
		public void setDataProvisioningServiceName(String dataProvisionServiceName) {
			this.dataProvisioningServiceName = dataProvisionServiceName;
		}
		
		
	}

}
