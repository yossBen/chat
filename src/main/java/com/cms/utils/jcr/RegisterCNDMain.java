package com.cms.utils.jcr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.commons.cnd.CndImporter;
import org.apache.jackrabbit.commons.cnd.ParseException;

public class RegisterCNDMain {
	public static void main(String[] args) throws LoginException, RepositoryException, ParseException, IOException {
		Session session = BdStructure.getSession();
		// NodeTypeManager manager =
		// session.getWorkspace().getNodeTypeManager();
		String cnd = "<pnt='http://www.papilloncms.fr/cms'> \n" + "[pnt:article] > nt:base\n- ville (string)\n- pays (long)\n- departement (string)";
		System.out.println(cnd);
		Reader cndReader = new InputStreamReader(new ByteArrayInputStream(cnd.getBytes()));

		CndImporter.registerNodeTypes(cndReader, session, true);
		// NodeTypeTemplate ntd = manager.createNodeTypeTemplate();
		// System.out.println(manager.hasNodeType(UserConstants.NT_REP_MEMBER_REFERENCES));

		// Register the custom node types defined in the CND file
		// manager.registerNodeTypes(new FileInputStream(cndFileName),
		// JackrabbitNodeTypeManager.TEXT_X_JCR_CND);
	}
}