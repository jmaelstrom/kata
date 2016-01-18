package com.kata.service.repository;

import com.kata.domain.RepositoryNode;

public interface RepositoryService {
	public boolean addRepositoryNode(RepositoryNode repositoryNode);	
	public boolean removeRepositoryNode(RepositoryNode repositoryNode);
	public boolean removeRepositoryNode(String repositoryNodeUrl);
	public RepositoryNode findRepositoryNode(String repositoryNodeUrl);	
	public boolean moveRepositoryNode(RepositoryNode repositoryNodeToMove, RepositoryNode newRepositoryNodeParent);	
}
