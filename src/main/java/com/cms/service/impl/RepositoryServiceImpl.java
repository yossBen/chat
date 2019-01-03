package com.cms.service.impl;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.plugins.document.DocumentNodeStore;
import org.apache.jackrabbit.oak.plugins.document.mongo.MongoDocumentNodeStoreBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cms.service.RepositoryService;

@Service
public class RepositoryServiceImpl implements RepositoryService {
	private Repository repository;

	@Value("${mongo.host}")
	private String host;

	@Value("${mongo.port}")
	private int port;

	@Value("${mongo.database}")
	private String database;

	@PostConstruct
	public void init() throws UnknownHostException {
		if (repository == null) {
			synchronized (this) {
				if (repository == null) {
					String uri = "mongodb://" + host + ":" + port;
					DocumentNodeStore ns = new MongoDocumentNodeStoreBuilder().setMongoDB(uri, database, 16).build();
					repository = new Jcr(new Oak(ns)).createRepository();
				}
			}
		}
	}

	@PreDestroy
	public void destroy() {
		if (repository != null && repository instanceof JackrabbitRepository) {
			((JackrabbitRepository) repository).shutdown();
		}
	}

	@Override
	public Node addNode(String nodeName) throws LoginException, RepositoryException {
		Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
		Node parentNode = session.getRootNode();
		Node toReturn = parentNode.addNode(nodeName);
		session.save();
		session.logout();
		return toReturn;
	}
}