package com.cms.utils.chat;

import java.io.IOException;
import java.util.Set;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;

public class CreateGroup {
	public static void main(String[] args) throws XMPPException, SmackException, IOException, InterruptedException {
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder().setSecurityMode(ConnectionConfiguration.SecurityMode.disabled).setCompressionEnabled(true)
				.setSendPresence(true).setXmppDomain("localhost").setPort(Integer.parseInt("5222")).build();
		AbstractXMPPConnection conn = new XMPPTCPConnection(config);
		conn.connect().login("admin", "Mohamed1");

		// EntityBareJid mucAddress =
		// JidCreate.entityBareFrom(Localpart.from("smack-inttest-join-leave-" +
		// randomString),
		// mucService.getDomain());

		MultiUserChat muc = MultiUserChatManager.getInstanceFor(conn).getMultiUserChat(JidCreate.entityBareFrom("room1" + "@" + conn.getXMPPServiceDomain()));
		Message message = new Message();
		message.setBody("HELLO ROOM");
		message.setType(Message.Type.chat);

		muc.sendMessage(message);

		// muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
		// MultiUserChat chatRoom = new MultiUserChat(conn,
		// "room786@conference.dishaserver");
		// chatRoom.create("nagarjuna");
		// Form form = muc.getConfigurationForm().createAnswerForm();
		// form.setAnswer("muc#roomconfig_publicroom", true);
		// form.setAnswer("muc#roomconfig_roomname", "room786");
		// form.setAnswer("muc#roomconfig_roomowners", owners);
		// form.setAnswer("muc#roomconfig_persistentroom", true);
		// muc.sendConfigurationForm(form);

	}

	private static void createRoster(AbstractXMPPConnection conn) throws Exception {
		Roster roster = Roster.getInstanceFor(conn);
		RosterGroup group = roster.getGroup("team");
		if (null == group) {
			group = roster.createGroup("team");
		}

		EntityBareJid jid = JidCreate.entityBareFrom("bachir1546266496911" + "@" + conn.getXMPPServiceDomain());
		RosterEntry entry = roster.getEntry(jid);
		if (entry == null) {
			roster.createEntry(jid, "bachir1546266496911", null);
		}
		entry = roster.getEntry(jid);
		if (entry != null) {
			try {
				group.addEntry(entry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void deleteAllRoster(AbstractXMPPConnection conn) throws Exception {
		Roster roster = Roster.getInstanceFor(conn);
		Set<RosterEntry> entries = roster.getEntries();
		for (RosterEntry entry : entries) {
			System.out.println(entry);
			roster.removeEntry(entry);
		}
	}

}
