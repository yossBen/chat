package com.cms.utils.chat;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class TestCHat {
	public static void main(String[] args) throws XMPPException, SmackException, IOException, InterruptedException {
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				.setUsernameAndPassword("bachir1546266466935", "Bachir1").setHost("localhost")
				.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled).setXmppDomain("localhost")
				.setPort(Integer.parseInt("5222")).build();

		AbstractXMPPConnection connection = new XMPPTCPConnection(config);
		connection.connect().login();
	}
}
