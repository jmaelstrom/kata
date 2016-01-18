package com.kata.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kata.domain.RepositoryNode;
import com.kata.helper.SystemInScanner;
import com.kata.service.output.OutputService;
import com.kata.service.repository.RepositoryService;


/**
 * Command processor for standard single character cli command input and configurable output.
 * 
 *  Note that all static messages should be contained with resource files but was overkill for 
 *  the purposes of this application.
 *  
 * @author jason
 *
 */
@Service
public class DefaultCliCommandProcessorService implements CommandProcessorService {
	private static String AVAIL_COMMAND_STRING="Press A <Enter> to add node, L <Enter> to list nodes, S <Enter> to search nodes, Q <Enter> to quit.";
	protected static String QUITTING_MESSAGE = "Quitting";
	protected static String ADDING_MESSAGE = "Adding to repository functionality has not been implemented";
	protected static String LISTING_MESSAGE = "Listing:";
	protected static String NODE_NOT_FOUND_MESSAGE = "Repository node not found!";
	protected static String INVALID_COMMAND_SUFFIX = " is an invalid command.";
	protected static String SEARCH_MESSAGE = "Enter Search URL:";
	
	@Autowired
	@Qualifier("dynamicOutputService")
	private OutputService outputService;
	
	@Autowired
	@Qualifier("dynamicRepositoryService")
	private RepositoryService repositoryService;
	
	@Autowired
	private SystemInScanner inputScanner;
	
	@Override
	public void run() {
		boolean doContinue = true;

		while (doContinue) {

			outputService.sendMessage(AVAIL_COMMAND_STRING);
			
			String command = inputScanner.next();
			
			//clears newline
			inputScanner.nextLine();
			
			doContinue = processCommand(command);

		}
		
	}
	
	/*
	 * Typically I would remove the branching statements and create each command as 
	 * a separate Command object that implements an interface with an execute method, 
	 * but that is overkill for this demonstration.
	 */
	protected boolean processCommand(String command) {
				
		if (command.equalsIgnoreCase("q")) {
			outputService.sendMessage(QUITTING_MESSAGE);
			return false;
		}
		
		if (command.equalsIgnoreCase("a")) {
			outputService.sendMessage(ADDING_MESSAGE);
			
			return true;
		}

		if (command.equalsIgnoreCase("l")) {
			outputService.sendMessage(LISTING_MESSAGE);
			// find the root repository node using the injected repositoryService
			RepositoryNode foundNode = repositoryService.findRepositoryNode("/");
			
			if (foundNode != null) {
				outputService.sendMessage(foundNode.toTreeString());
			} else {
				outputService.sendMessage(NODE_NOT_FOUND_MESSAGE);
			}
			
			return true;
		}
		
		if (command.equalsIgnoreCase("s")) {
			outputService.sendMessage(SEARCH_MESSAGE);
			
			/*
			 * Allows for the searching of the repository tree for the requested URL
			 * (i.e. /root/test/testChild)
			 */
			RepositoryNode foundNode = repositoryService.findRepositoryNode(inputScanner.nextLine());
			
			if (foundNode != null) {
				outputService.sendMessage(foundNode.toTreeString());
			} else {
				outputService.sendMessage(NODE_NOT_FOUND_MESSAGE);
			}
			
			return true;
		}
		outputService.sendMessage(command + INVALID_COMMAND_SUFFIX);
		
		return true;
		
	}
	

}
