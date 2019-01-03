package com.cms.utils.jcr;

import java.net.UnknownHostException;
import java.util.Calendar;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.util.ISO8601;

public class TestOak {
	public static void main(String[] args) throws UnknownHostException, LoginException, RepositoryException {

		Session session = BdStructure.getSession();
		Node parentNode = session.getRootNode();
		Node childNode = parentNode.addNode("test-type", "pnt:actualite");
		childNode.setProperty("ville", "Argenteuil");
		childNode.addMixin(JcrConstants.MIX_VERSIONABLE);

		childNode.setProperty("date", ISO8601.format(Calendar.getInstance()));

		// Node node = session.getNode("/test-type");
//		node.setProperty("pays", "France");
		// node.checkin();
		// node.checkout();

		// System.out.println(childNode.getProperty("country").getString());

		// childNode.addMixin(JcrConstants.MIX_VERSIONABLE);
		//
		// Node dev = childNode.addNode("tata");
		// dev.addMixin(JcrConstants.MIX_VERSIONABLE);

		// childNode.addMixin(JcrConstants.MIX_VERSIONABLE);
		// VersionHistory history =
		// session.getWorkspace().getVersionManager().getVersionHistory(childNode.getPath());
		// dev.setProperty("ville", "Casa");

		// session.getWorkspace().copy(srcAbsPath, destAbsPath);
		session.save();
		session.logout();
		System.out.println("FIN");
	}
}