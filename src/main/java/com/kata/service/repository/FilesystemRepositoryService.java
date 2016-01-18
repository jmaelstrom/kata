package com.kata.service.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kata.domain.RepositoryNode;

@Service
/**
 * Not implemented. Could be used to store all repository nodes on the filesystem. 
 * 
 * Implementation could use standard directories / files, serialized objects, XML files, etc.
 * @author jason
 *
 */
public class FilesystemRepositoryService implements RepositoryService {

	@Override
	public boolean addRepositoryNode(RepositoryNode repositoryNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeRepositoryNode(RepositoryNode repositoryNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RepositoryNode findRepositoryNode(String repositoryNodeUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveRepositoryNode(RepositoryNode repositoryNodeToMove, RepositoryNode newRepositoryNodeParent) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean removeRepositoryNode(String repositoryNodeUrl) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
