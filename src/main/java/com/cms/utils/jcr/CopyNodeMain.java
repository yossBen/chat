package com.cms.utils.jcr;

import java.net.UnknownHostException;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class CopyNodeMain {
	public static void main(String[] args) throws UnknownHostException, LoginException, RepositoryException {

		Session session = BdStructure.getSession();
		Node childNode = session.getNode(BdStructure.DEV_PATH_NODE);
		session.getWorkspace().copy(childNode.getPath(), BdStructure.PROD_PATH_NODE);

		session.save();
		session.logout();
		System.out.println("FIN");
	}
}