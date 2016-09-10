package me.alexander.javabot.Client;

import me.alexander.javabot.Client.Protocol.Packet;

public class ChatProccessor extends Packet {

	public ChatProccessor(final String message, String username, IRCBot instance) {
		new Thread() {
			@Override
			public void run() {
				try {
					int count = 0;
					boolean kicked = false;
					for (String s : message.split(" ")) {
						if (Filter.filter(s)) {
							instance.getPacketSender()
									.SendChannelMessage(username + " Was kicked for saying banned words . ~ SkyNet AI");
							instance.KickUser(username, "Don't swear or be rude. ~ SkyNet AI");
							kicked = true;
							break;
						}
					}

					String s = message.replace(" ", "");
					int countCaps = countCaps(s.substring(1));
					if (countCaps >= ((s.length() / 2) + 2)) {
						count = 1;
					}

					if (count != 0 && !kicked) {
						if (instance.getUserBuffer().getUserWarnings().containsKey(username)) {
							instance.getUserBuffer().getUserWarnings().remove(username);
							instance.KickUser(username, "Stop using caps please. ~ SkyNet AI");
							instance.getPacketSender()
									.SendChannelMessage(username + " Was kicked for using caps. ~ SkyNet AI");
						} else {
							instance.getPacketSender().SendPrivateMessage(username,
									"Stop using caps please. ~ SkyNet AI");
							instance.getPacketSender().SendChannelMessage("Please mind the caps " + username
									+ " or action will be taken. You have one more warning. ~ SkyNet AI");
							instance.getUserBuffer().getUserWarnings().put(username, "true");
						}
					} else {
						if (!kicked) {
							FAQ.HandleFAQ(message, username);
						}
					}
				} catch (Exception ex) {
					;
				}
			}
		}.start();
	}

}
