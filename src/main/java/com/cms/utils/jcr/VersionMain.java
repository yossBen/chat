package com.cms.utils.jcr;

import java.net.UnknownHostException;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.apache.jackrabbit.JcrConstants;

public class VersionMain {
	private static final String NODE_PATH = "/test-version";

	public static void main(String[] args) throws UnknownHostException, LoginException, RepositoryException {
		// createNode("Argenteuil");
		addVersion("Tétouan");
		getVersions();
	}

	private static void createNode(String value) throws RepositoryException, UnknownHostException {
		Session session = BdStructure.getSession();
		Node parentNode = session.getRootNode();
		Node childNode = parentNode.addNode(NODE_PATH);
		childNode.setProperty("ville", value);
		childNode.addMixin(JcrConstants.MIX_VERSIONABLE);
		session.save();
		session.logout();
	}

	private static void addVersion(String value) throws RepositoryException, UnknownHostException {
		Session session = BdStructure.getSession();
		Node childNode = session.getNode(NODE_PATH);

		if (!childNode.isCheckedOut()) {
			session.getWorkspace().getVersionManager().checkout(NODE_PATH);
		}
		childNode.setProperty("ville", value);

		session.save();
		session.getWorkspace().getVersionManager().checkin(NODE_PATH);
		session.logout();
	}

	private static void getVersions() throws RepositoryException, UnknownHostException {
		Session session = BdStructure.getSession();
		VersionHistory history = session.getWorkspace().getVersionManager().getVersionHistory(NODE_PATH);

		for (VersionIterator vIter = history.getAllVersions(); vIter.hasNext();) {
			Version v = vIter.nextVersion();
			System.out.println(v.getName());

			for (NodeIterator nodeIter = v.getNodes(); nodeIter.hasNext();) {
				Node n = nodeIter.nextNode();
				if (n.hasProperty("ville")) {
					System.out.println(n.getProperty("ville").getString());
				}
				System.out.println(n.getPath());
				for (NodeIterator nIter = n.getNodes(); nIter.hasNext();) {
					Node n1 = nIter.nextNode();
					if (n1.hasProperty("ville")) {
						System.out.println(n1.getProperty("ville").getString());
					}
					System.out.println(n1.getPath());
				}
			}
		}

		session.logout();
		System.out.println("FIN");
	}
}