package com.cms.utils.chat;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.JidCreate;

public class SendPresence {
	public static void main(String[] args) throws XMPPException, SmackException, IOException, InterruptedException {

		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				.setUsernameAndPassword("toto", "Toto1").setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
				.setCompressionEnabled(true).setXmppDomain(JidCreate.domainBareFrom("localhost"))
				.setPort(Integer.parseInt("5222")).build();

		AbstractXMPPConnection connection = new XMPPTCPConnection(config);
		connection.connect().login();

//		Now we create the account:
//		AccountManager accountManager = AccountManager.getInstance(connection);
//		accountManager.sensitiveOperationOverInsecureConnection(true);
//		accountManager.createAccount(Localpart.from("tata"), "Tata1");

		// The account has been created, so we can now login

		Presence p = new Presence(Presence.Type.available);
		p.setMode(Presence.Mode.away);
//		p.setPriority(24);
		p.setFrom(connection.getUser());
		p.setStatus("en plein développemnt du chat live");

		connection.sendStanza(p);
	}
}
