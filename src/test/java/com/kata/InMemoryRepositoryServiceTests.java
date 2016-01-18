package com.kata;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.kata.domain.RepositoryNode;
import com.kata.domain.RepositoryNodeType;
import com.kata.exception.InvalidRepositoryNodeDefinitionException;
import com.kata.service.repository.InMemoryRepositoryService;
import com.kata.service.repository.RepositoryService;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryRepositoryServiceTests {

	@Test
	public void testaddRepositoryNodeRoot() throws InvalidRepositoryNodeDefinitionException {
		InMemoryRepositoryService service = new InMemoryRepositoryService();
		RepositoryNode node = new RepositoryNode(RepositoryNodeType.PROJECT, "test");

		boolean result = service.addRepositoryNode(node);

		assertTrue(result);
		assertTrue(node.getParentRepositoryNode() != null);
		assertTrue(node.getParentRepositoryNode().getName().equalsIgnoreCase("root"));
		assertTrue(node.getParentRepositoryNode().getChildRepositoryNodes().contains(node));
	}

	@Test
	public void testaddRepositoryNodeChild() throws InvalidRepositoryNodeDefinitionException {
		InMemoryRepositoryService service = new InMemoryRepositoryService();
		RepositoryNode projectNode = new RepositoryNode(RepositoryNodeType.PROJECT, "testProject");
		RepositoryNode docNode = new RepositoryNode(RepositoryNodeType.DOCUMENT, "testDoc", projectNode);

		boolean result = service.addRepositoryNode(docNode);

		assertTrue(result);

		// parent should be associated with root
		assertTrue(projectNode.getParentRepositoryNode() != null);
		assertTrue(projectNode.getParentRepositoryNode().getName().equalsIgnoreCase("root"));

		// child should maintain its parent
		assertEquals(projectNode, docNode.getParentRepositoryNode());

		// child should exist in parent's children list
		assertTrue(docNode.getParentRepositoryNode().getChildRepositoryNodes().contains(docNode));

	}

	@Test
	public void testFindRepositoryNodeValidRoot() {
		InMemoryRepositoryService service = new InMemoryRepositoryService();
		RepositoryNode node = service.findRepositoryNode("/");

		assertNotNull(node);
		assertTrue(node.getName().equalsIgnoreCase("root"));

		node = service.findRepositoryNode("/root");

		assertNotNull(node);
		assertTrue(node.getName().equalsIgnoreCase("root"));
	}

	@Test
	public void testFindRepositoryNodeInvalidQuery() {
		InMemoryRepositoryService service = new InMemoryRepositoryService();
		RepositoryNode node = service.findRepositoryNode("+");

		assertNull(node);

		node = service.findRepositoryNode("/bleh\bleh2");

		assertNull(node);
		
		node = service.findRepositoryNode("//stuff");

		assertNull(node);

	}
	
	@Test
	public void testFindRepositoryNodeValidChildQuery() throws InvalidRepositoryNodeDefinitionException {
		InMemoryRepositoryService service = new InMemoryRepositoryService();
		RepositoryNode projectNode = new RepositoryNode(RepositoryNodeType.PROJECT, "testProject");
		RepositoryNode docNode = new RepositoryNode(RepositoryNodeType.DOCUMENT, "testDocument" , projectNode);

		service.addRepositoryNode(docNode);

		RepositoryNode foundNode = service.findRepositoryNode("/root/testProject");
		
		assertNotNull(foundNode);
		
		// parent should be associated with root
		assertTrue(foundNode.getParentRepositoryNode() != null);
		assertTrue(foundNode.getParentRepositoryNode().getName().equalsIgnoreCase("root"));

		//make sure this is the correct node with the correct children
		assertTrue(foundNode.getChildRepositoryNodes().contains(docNode));
		
	}
	
	@Test(expected=InvalidRepositoryNodeDefinitionException.class)
	public void testInvalidNodeName() throws InvalidRepositoryNodeDefinitionException {
		RepositoryNode projectNode = new RepositoryNode(RepositoryNodeType.PROJECT, null);
	}
	
	@Test(expected=InvalidRepositoryNodeDefinitionException.class)
	public void testInvalidNodeNameDelimiter() throws InvalidRepositoryNodeDefinitionException {
		RepositoryNode projectNode = new RepositoryNode(RepositoryNodeType.PROJECT, "name/name");
	}
	
	@Test(expected=InvalidRepositoryNodeDefinitionException.class)
	public void testInvalidNodeType() throws InvalidRepositoryNodeDefinitionException {
		RepositoryNode projectNode = new RepositoryNode(null, "testName");
	}
	
	@Test(expected=InvalidRepositoryNodeDefinitionException.class)
	public void testInvalidNodeTypeAndName() throws InvalidRepositoryNodeDefinitionException {
		RepositoryNode projectNode = new RepositoryNode(null, null);
	}
	
}
