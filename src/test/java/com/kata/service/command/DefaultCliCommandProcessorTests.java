package com.kata.service.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.kata.domain.RepositoryNode;
import com.kata.domain.RepositoryNodeType;
import com.kata.exception.InvalidRepositoryNodeDefinitionException;
import com.kata.helper.SystemInScanner;
import com.kata.service.command.DefaultCliCommandProcessorService;
import com.kata.service.output.OutputService;
import com.kata.service.repository.RepositoryService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCliCommandProcessorTests {

	@Mock
	private OutputService outputService;
	
	@Mock
	private RepositoryService repositoryService;
	
	@Mock
	private SystemInScanner inputScanner;
	
	@InjectMocks 
	private DefaultCliCommandProcessorService processorService = new DefaultCliCommandProcessorService();
	
	@Test
	public void testProcessCommandQuit() {
		assertFalse(processorService.processCommand("q"));
		
		Mockito.verify(outputService).sendMessage(DefaultCliCommandProcessorService.QUITTING_MESSAGE);
		
	}
	
	@Test
	public void testProcessCommandAdd() {
		assertTrue(processorService.processCommand("a"));
		
		Mockito.verify(outputService).sendMessage(DefaultCliCommandProcessorService.ADDING_MESSAGE);
		
	}

	@Test
	public void testProcessCommandList() throws InvalidRepositoryNodeDefinitionException {
		
		RepositoryNode returnedNode = new RepositoryNode(RepositoryNodeType.PROJECT, "root");
		
		Mockito.when(repositoryService.findRepositoryNode("/")).thenReturn(returnedNode);
		
		assertTrue(processorService.processCommand("l"));
		assertEquals(repositoryService.findRepositoryNode("/"), returnedNode);
		
		Mockito.verify(outputService).sendMessage(DefaultCliCommandProcessorService.LISTING_MESSAGE);
		Mockito.verify(outputService, Mockito.never()).sendMessage(DefaultCliCommandProcessorService.NODE_NOT_FOUND_MESSAGE);
		
		
	}
	
	@Test
	public void testProcessCommandSearchValid() throws InvalidRepositoryNodeDefinitionException {
		
		RepositoryNode returnedNode = new RepositoryNode(RepositoryNodeType.PROJECT, "root");
		
		Mockito.when(inputScanner.nextLine()).thenReturn("/");
		Mockito.when(repositoryService.findRepositoryNode("/")).thenReturn(returnedNode);
		
		assertTrue(processorService.processCommand("s"));
				
		Mockito.verify(outputService).sendMessage(DefaultCliCommandProcessorService.SEARCH_MESSAGE);
		Mockito.verify(outputService, Mockito.never()).sendMessage(DefaultCliCommandProcessorService.NODE_NOT_FOUND_MESSAGE);
		
	}
	
	
	
	@Test
	public void testProcessCommandSearchInValid()  {
		
		assertTrue(processorService.processCommand("s"));
		Mockito.when(inputScanner.nextLine()).thenReturn("/+/+");
		
		Mockito.verify(outputService).sendMessage(DefaultCliCommandProcessorService.SEARCH_MESSAGE);
		Mockito.verify(outputService).sendMessage(DefaultCliCommandProcessorService.NODE_NOT_FOUND_MESSAGE);
		
	}
	
	@Test
	public void testProcessCommandSearchNotFound()  {
		
		assertTrue(processorService.processCommand("s"));
		Mockito.when(inputScanner.nextLine()).thenReturn("/root/test");
		
		Mockito.verify(outputService).sendMessage(DefaultCliCommandProcessorService.SEARCH_MESSAGE);
		Mockito.verify(outputService).sendMessage(DefaultCliCommandProcessorService.NODE_NOT_FOUND_MESSAGE);
		
	}
	
}
