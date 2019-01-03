package com.cms.service;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

public interface RepositoryService {
	Node addNode(String nodeName) throws LoginException, RepositoryException;
}