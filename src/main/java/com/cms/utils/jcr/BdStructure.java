package com.cms.utils.jcr;

import java.net.UnknownHostException;

import javax.jcr.AccessDeniedException;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.plugins.document.DocumentNodeStore;
import org.apache.jackrabbit.oak.plugins.document.mongo.MongoDocumentNodeStoreBuilder;

public class BdStructure {
	public static final String DEV_PATH_NODE = "/sites/portail/dev";
	public static final String PROD_PATH_NODE = "/sites/portail/prod";

	public static void main(String[] args) throws UnknownHostException, LoginException, RepositoryException {
		// create();
		delete(DEV_PATH_NODE);
	}

	private static void delete(String path)
			throws AccessDeniedException, VersionException, LockException, ConstraintViolationException, PathNotFoundException, RepositoryException, UnknownHostException {
		Session session = BdStructure.getSession();
		session.getNode(path).remove();
		session.save();
		session.logout();
		System.out.println("FIN");
	}

	public static void create() throws RepositoryException, UnknownHostException {
		Session session = BdStructure.getSession();
		Node parentNode = session.getRootNode();
		Node node = parentNode.addNode("sites");
		Node siteNode = node.addNode("portail");
		Node env = siteNode.addNode("dev");

		session.save();
		session.logout();
		System.out.println("FIN");
	}

	public static Session getSession() throws UnknownHostException, LoginException, NoSuchWorkspaceException, RepositoryException {
		return getSession(Oak.DEFAULT_WORKSPACE_NAME);
	}

	public static Session getSession(String workspace) throws UnknownHostException, LoginException, NoSuchWorkspaceException, RepositoryException {
		String uri = "mongodb://localhost:27017";
		System.out.println("START CONNEXION");
		DocumentNodeStore ns = new MongoDocumentNodeStoreBuilder().setMongoDB(uri, "cms", 16).build();
		Repository repo = new Jcr(new Oak(ns)).createRepository();
		System.out.println("END CONNEXION");
		return repo.login(new SimpleCredentials("admin", "admin".toCharArray()), workspace);
	}
}