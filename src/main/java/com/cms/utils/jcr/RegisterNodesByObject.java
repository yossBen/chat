package com.cms.utils.jcr;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.nodetype.NodeTypeManager;
import javax.jcr.nodetype.NodeTypeTemplate;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.nodetype.PropertyDefinitionTemplate;

import org.apache.jackrabbit.commons.cnd.ParseException;

/**
 * {@link https://www.programcreek.com/java-api-examples/?api=org.apache.jackrabbit.commons.cnd.CndImporter}
 */
public class RegisterNodesByObject {
	public static void main(String[] args) throws LoginException, RepositoryException, ParseException, IOException {
//		modifyNodeType();
		createNodeType();
	}

	private static void modifyNodeType()
			throws UnknownHostException, LoginException, NoSuchWorkspaceException, RepositoryException {
		Session session = BdStructure.getSession();
		NodeTypeManager manager = session.getWorkspace().getNodeTypeManager();
		PropertyDefinition[] defs = manager.getNodeType("pnt:actualite").getPropertyDefinitions();
		for (PropertyDefinition d : defs) {
			System.out.println(d.getName() + " , " + PropertyType.nameFromValue(d.getRequiredType()));
		}
	}

	@SuppressWarnings("unchecked")
	private static void createNodeType()
			throws UnknownHostException, LoginException, NoSuchWorkspaceException, RepositoryException {
		Session session = BdStructure.getSession();
		session.getWorkspace().getNamespaceRegistry().registerNamespace("pnt", "http://www.papilloncms.fr/cms");

		// Retrieve node type manager from the session
		NodeTypeManager manager = session.getWorkspace().getNodeTypeManager();
		// Create node type
		NodeTypeTemplate nodeType = manager.createNodeTypeTemplate();
		nodeType.setName("pnt:actualite");

		// Create a new property
		PropertyDefinitionTemplate prop = manager.createPropertyDefinitionTemplate();
		prop.setName("ville");
		prop.setRequiredType(PropertyType.STRING);
		// Add property to node type
		nodeType.getPropertyDefinitionTemplates().add(prop);

		prop = manager.createPropertyDefinitionTemplate();
		prop.setName("date");
		prop.setRequiredType(PropertyType.DATE);
		// Add property to node type
		nodeType.getPropertyDefinitionTemplates().add(prop);

		manager.registerNodeType(nodeType, true);

		session.save();
		session.logout();
	}
}