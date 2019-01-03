package com.cms.utils.chat;

import java.io.IOException;
import java.util.Date;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.parts.Localpart;

public class CreateAccount {
	public static void main(String[] args) throws XMPPException, SmackException, IOException, InterruptedException {
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder().setSecurityMode(ConnectionConfiguration.SecurityMode.disabled).setHost("localhost").setXmppDomain("localhost")
				.setPort(Integer.parseInt("5222")).build();

		AbstractXMPPConnection connection = new XMPPTCPConnection(config);
		connection.connect().login("admin", "Mohamed1");

		// // Now we create the account:
		AccountManager accountManager = AccountManager.getInstance(connection);
		accountManager.sensitiveOperationOverInsecureConnection(true);
		accountManager.createAccount(Localpart.from("bachir" + new Date().getTime()), "Bachir1");

	
	}
}