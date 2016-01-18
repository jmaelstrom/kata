package com.kata.service.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kata.domain.RepositoryNode;
import com.kata.domain.RepositoryNodeType;

@Service
/**
 * Basic repository implementation that stores the nodes in memory and is lost upon system restart.
 * 
 * Created for demo purposes only.
 * @author jason
 *
 */
public class InMemoryRepositoryService implements RepositoryService {
	private RepositoryNode rootRepositoryNode;
	private JXPathContext jxpathContext;

	public InMemoryRepositoryService() {
		try {
			 rootRepositoryNode = new RepositoryNode(RepositoryNodeType.PROJECT, "root");
			 jxpathContext  = JXPathContext.newContext(rootRepositoryNode);
		} catch (Exception e) {
			//will never happen in this instance
		}
	}
	
	@Override
	public boolean removeRepositoryNode(String repositoryNodeUrl) {
		// TODO Not implemented
		return false;
	}

	@Override
	/**
	 * Adds a new RepositoryNode to the Repository. Will climb the parent
	 * RepositoryNode tree and link to the root RepositoryNode if necessary.
	 */
	public boolean addRepositoryNode(RepositoryNode repositoryNode) {
		if (repositoryNode.getParentRepositoryNode() == null) {
			repositoryNode.setParentRepositoryNode(rootRepositoryNode);
			if (!rootRepositoryNode.getChildRepositoryNodes().contains(repositoryNode)) {
				rootRepositoryNode.getChildRepositoryNodes().add(repositoryNode);
			}

			return true;
		} else {
			/*
			 * recursively add RepositoryNodes to verify this node eventually
			 * connects to root via it's parent RepositoryNode
			 */
			if (!repositoryNode.getParentRepositoryNode().getName().equals(rootRepositoryNode.getName())) {
				addRepositoryNode(repositoryNode.getParentRepositoryNode());

				if (!repositoryNode.getParentRepositoryNode().getChildRepositoryNodes().contains(repositoryNode)) {
					repositoryNode.getParentRepositoryNode().getChildRepositoryNodes().add(repositoryNode);
				}
			}

			return true;
		}

	}

	@Override
	public boolean removeRepositoryNode(RepositoryNode repositoryNode) {
		// TODO Not implemented. Would need to remove node from parent as well as remove this node
		return false;
	}

	@Override
	/**
	 * Uses JXPath to search the repositoryNode object graph. Only supports hierarchical search.
	 */
	public RepositoryNode findRepositoryNode(String repositoryNodeUrl) {
		StringTokenizer tokenizer = new StringTokenizer(repositoryNodeUrl, "/");
		List<String> xpathList = new ArrayList<String>();
		
		// automatically add the root xpath node to search
		xpathList.add(".");

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();

			//generates the correct xpath expression to find the requested node for each token
			if (!token.equalsIgnoreCase("root")) {
				xpathList.add(String.format("childRepositoryNodes[name='%s']", token));
			}
		}

		//combines all xpath expressions to create the full expression for finding the node
		String xpathString = StringUtils.join(xpathList, "/");
		
		try {
			return (RepositoryNode) jxpathContext.getValue(xpathString);
		} catch (Exception e) {
			
			//will only throw exception if node isn't found
			return null;
		}
	}

	@Override
	public boolean moveRepositoryNode(RepositoryNode repositoryNodeToMove, RepositoryNode newRepositoryNodeParent) {
		// TODO Not implemented. Entails switching parents and modifying old / new parent child lists
		return false;
	}

}
