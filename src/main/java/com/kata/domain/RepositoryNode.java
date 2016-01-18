package com.kata.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.context.annotation.Bean;

import com.kata.exception.InvalidRepositoryNodeDefinitionException;

/*
 * All node types in the repo are RepositoryNodes - Projects, Documents, etc
 */
public class RepositoryNode {

	private RepositoryNodeType nodeType;
	private String name;
	private RepositoryNode parentRepositoryNode;
	private List<RepositoryNode> childRepositoryNodes = new ArrayList<RepositoryNode>();

	public RepositoryNode(RepositoryNodeType nodeType, String name) throws InvalidRepositoryNodeDefinitionException {
		super();

		validate(nodeType, name);

		this.nodeType = nodeType;
		this.name = name;
	}

	public RepositoryNode(RepositoryNodeType nodeType, String name, RepositoryNode parentRepositoryNode)
			throws InvalidRepositoryNodeDefinitionException {
		super();
		
		validate(nodeType, name);
		
		this.nodeType = nodeType;
		this.name = name;
		this.parentRepositoryNode = parentRepositoryNode;
		
		addToParentRepositoryNode(parentRepositoryNode);
	}

	protected void addToParentRepositoryNode(RepositoryNode parentRepositoryNode) {
		if (this.parentRepositoryNode != null) {
			
			if (!this.parentRepositoryNode.equals(parentRepositoryNode)) {
				if (parentRepositoryNode.getChildRepositoryNodes().contains(this)) {
					parentRepositoryNode.getChildRepositoryNodes().remove(this);
				}
			}
			
			if (!this.parentRepositoryNode.getChildRepositoryNodes().contains(this)) {
				this.parentRepositoryNode.getChildRepositoryNodes().add(this);
			}
		}
	}

	protected void validate(RepositoryNodeType nodeType, String name) throws InvalidRepositoryNodeDefinitionException {
		if (nodeType == null) {
			throw new InvalidRepositoryNodeDefinitionException("RepositoryNode nodeType cannot be null");
		}

		if (name == null) {
			throw new InvalidRepositoryNodeDefinitionException("RepositoryNode name cannot be null");
		}

		if (StringUtils.contains(name, "/")) {
			throw new InvalidRepositoryNodeDefinitionException(
					"RepositoryNode name cannot contain the Url delimiter '/'");
		}
	}

	public RepositoryNodeType getNodeType() {
		return nodeType;
	}

	public String getName() {
		return name;
	}

	public RepositoryNode getParentRepositoryNode() {
		return parentRepositoryNode;
	}

	public List<RepositoryNode> getChildRepositoryNodes() {
		return childRepositoryNodes;
	}

	public void setNodeType(RepositoryNodeType nodeType) {
		this.nodeType = nodeType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentRepositoryNode(RepositoryNode parentRepositoryNode) {
		this.parentRepositoryNode = parentRepositoryNode;
		
		addToParentRepositoryNode(parentRepositoryNode);
	}

	public void setChildRepositoryNodes(List<RepositoryNode> childRepositoryNodes) {
		this.childRepositoryNodes = childRepositoryNodes;
	}

	/*
	 * This method recursively calls itself with its parentRepositoryNode to
	 * generate this particular object's URL
	 */
	public String getUrl() {
		List<String> urlElements = new ArrayList<String>();
		urlElements.add(name);

		if (parentRepositoryNode != null) {
			urlElements.add(parentRepositoryNode.getUrl());
		}

		/*
		 * Elements need reversed as they are originally in child-first order
		 */
		Collections.reverse(urlElements);

		return StringUtils.join(urlElements, "/");
	}

	public String toDisplayString() {
		if (nodeType == RepositoryNodeType.PROJECT) {
			return String.format("- %s: %s - URL: %s\n", nodeType.getName(), name, getUrl());
		} else {
			String extension = FilenameUtils.getExtension(name).length() == 0 ? "N/A"
					: FilenameUtils.getExtension(name);
			return String.format("- %s: %s - Extension: %s - URL: %s\n", nodeType.getName(),
					FilenameUtils.getBaseName(name), extension, getUrl());
		}
	}

	public String toTreeString() {
		return toTreeString("");
	}

	/*
	 * Method that recursivle calls itself given child repository nodes to
	 * generate the complete repository tree using this instance as a root
	 */
	public String toTreeString(String indentString) {
		String treeString = indentString + toDisplayString();

		Collections.sort(childRepositoryNodes, new Comparator<RepositoryNode>() {
			@Override
			public int compare(RepositoryNode o1, RepositoryNode o2) {
				return o1.nodeType.compareTo(o2.nodeType);
			}

		});

		for (RepositoryNode node : childRepositoryNodes) {
			treeString += node.toTreeString(indentString + "\t");
		}

		return treeString;
	}
	
	@Override
	public int hashCode(){
	    return new HashCodeBuilder()
	        .append(name)
	        .append(parentRepositoryNode)
	        .append(nodeType)
	        .append(getUrl())
	        .toHashCode();
	}

	@Override
	public boolean equals(final Object obj){
	    if(obj instanceof RepositoryNode){
	        final RepositoryNode other = (RepositoryNode) obj;
	        return new EqualsBuilder()
	            .append(name, other.name)
	            .append(parentRepositoryNode, other.parentRepositoryNode)
	            .append(nodeType, other.nodeType)
	            .append(getUrl(), other.getUrl())
	            .isEquals();
	    } else{
	        return false;
	    }
	}
}
