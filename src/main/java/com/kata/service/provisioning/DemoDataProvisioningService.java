package com.kata.service.provisioning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kata.domain.RepositoryNode;
import com.kata.domain.RepositoryNodeType;
import com.kata.exception.InvalidRepositoryNodeDefinitionException;
import com.kata.service.repository.RepositoryService;

@Service
/**
 * DataProvisioningService that will pre-populate the injected repositoryService
 * with a few hierarchical nodes for demo purposes.
 * 
 * @author jason
 *
 */
public class DemoDataProvisioningService implements DataProvisioningService {

	private static final Logger logger = LoggerFactory.getLogger(DemoDataProvisioningService.class.getName());

	@Autowired
	@Qualifier("dynamicRepositoryService")
	private RepositoryService repositoryService;

	@Override
	public void provision() {

		/*
		 * - Project: Main Project - URL: Main Project
- Project: Project 1 - URL: Main Project/Project 1
- Document: WordFile1 - Extension: .doc - URL: Main Project/Project 1/WordFile1.doc
- Document: ExcelFile1 - Extension: .xls - URL: Main Project/Project 1/ExcelFile1.xls
- Document: PPTFile1 - Extension: .ppt - URL: Main Project/Project 1/ PPTFile1.ppt
- Project: Project 2 - URL: Main Project/Project 2
- Document: WordFile2 - Extension: .doc - URL: Main Project/Project 2/WordFile2.doc
- Document: ExcelFile2 - Extension: .xls - URL: Main Project/Project 2/ExcelFile2.xls
- Project: Project 3 - URL: Main Project/Project 3
T
		 */
		// extremely non-sexy method of populating the repository
		try {
			RepositoryNode mainProject = new RepositoryNode(RepositoryNodeType.PROJECT, "Main Project");
			RepositoryNode project1 = new RepositoryNode(RepositoryNodeType.PROJECT, "Project 1", mainProject);
			RepositoryNode wordFile1 = new RepositoryNode(RepositoryNodeType.DOCUMENT, "WordFile1.doc", project1);
			RepositoryNode excelFile1 = new RepositoryNode(RepositoryNodeType.DOCUMENT, "ExcelFile1.xls", project1);
			RepositoryNode pptFile1 = new RepositoryNode(RepositoryNodeType.DOCUMENT, "PPTFile1.xls", project1);
			
			RepositoryNode project2 = new RepositoryNode(RepositoryNodeType.PROJECT, "Project 2", mainProject);
			RepositoryNode wordFile2 = new RepositoryNode(RepositoryNodeType.DOCUMENT, "WordFile2.doc", project2);
			RepositoryNode excelFile2 = new RepositoryNode(RepositoryNodeType.DOCUMENT, "ExcelFile2.xls", project2);
			
			RepositoryNode project3 = new RepositoryNode(RepositoryNodeType.PROJECT, "Project 3", mainProject);
			
			repositoryService.addRepositoryNode(mainProject);
		} catch (InvalidRepositoryNodeDefinitionException e) {
			logger.warn("Error creating repositoryNodes", e);
		}

	}

}
