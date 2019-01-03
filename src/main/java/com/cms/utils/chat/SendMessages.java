package com.cms.utils.chat;

import java.io.IOException;
import java.util.Date;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;

public class SendMessages {
	public static void main(String[] args) throws XMPPException, SmackException, IOException, InterruptedException {
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder().setSecurityMode(ConnectionConfiguration.SecurityMode.disabled).setCompressionEnabled(true)
				.setSendPresence(true).setXmppDomain("localhost").setPort(Integer.parseInt("5222")).build();

		AbstractXMPPConnection conn = new XMPPTCPConnection(config);

		conn.connect().login("admin", "Mohamed1");
		ChatManager chatManager = ChatManager.getInstanceFor(conn);
		chatManager.addIncomingListener(new IncomingChatMessageListener() {
			@Override
			public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
				System.out.println("New message from " + from + ": " + message.getBody());
			}
		});

		AbstractXMPPConnection c = new XMPPTCPConnection(config);
		c.connect().login("youssef", "Youssef1");
		chatManager = ChatManager.getInstanceFor(c);
		chatManager.addIncomingListener(new IncomingChatMessageListener() {
			@Override
			public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
				System.out.println("New message from " + from + ": " + message.getBody() + " date " + new Date().getTime());
			}
		});
		EntityBareJid jid = JidCreate.entityBareFrom("admin" + "@" + c.getXMPPServiceDomain());
		Chat chat = chatManager.chatWith(jid);
		chat.send("Hellow Admin une deuxieme fois!");

		/*
		 * AbstractXMPPConnection connection = new XMPPTCPConnection(config);
		 * connection.connect().login("toto", "Toto1");
		 * 
		 * // Now we create the account: // AccountManager accountManager = //
		 * AccountManager.getInstance(connection); //
		 * accountManager.sensitiveOperationOverInsecureConnection(true); //
		 * accountManager.createAccount(Localpart.from("tata"), "Tata1");
		 * 
		 * // The account has been created, so we can now login
		 * 
		 * Presence p = new Presence(Presence.Type.available);
		 * p.setMode(Presence.Mode.available); // p.setPriority(24);
		 * p.setFrom(connection.getUser());
		 * p.setStatus("en plein développemnt du chat live");
		 * 
		 * connection.sendStanza(p);
		 * 
		 * Message msg = new Message(); msg.setBody("Hello connard");
		 * msg.setTo("youssef");
		 * 
		 * msg.setFrom("toto"); // wait for messages connection.sendStanza(msg);
		 * ChatManager chatmanager = ChatManager.getInstanceFor(connection); //
		 * sendMsg(connection, "Hello connard", "youssef");
		 * chatmanager.addIncomingListener(new IncomingChatMessageListener() {
		 * 
		 * @Override public void newIncomingMessage(EntityBareJid from, Message
		 * message, Chat chat) { try { System.out.println(message); } catch
		 * (Exception ex) { ex.printStackTrace(); } }
		 * 
		 * });
		 */
	}

	public static void sendMsg(AbstractXMPPConnection connection, String body, String toJid) {
		try {
			Jid jid = JidCreate.from(toJid);
			Chat chat = ChatManager.getInstanceFor(connection).chatWith((EntityBareJid) jid);
			chat.send(body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
