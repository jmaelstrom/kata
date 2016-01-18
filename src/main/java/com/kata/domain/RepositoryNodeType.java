package com.kata.domain;

public enum RepositoryNodeType {
	PROJECT ("Project"),
	DOCUMENT ("Document");
	
	private final String name;

	private RepositoryNodeType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
