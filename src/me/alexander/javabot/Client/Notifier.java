package me.alexander.javabot.Client;

import me.alexander.javabot.Client.Buffers.PacketBuffer;
import me.alexander.javabot.Client.Protocol.Packets.PRIVATE_MESSAGE;
import me.alexander.javabot.Main.Main;

public class Notifier {

	// Put usernames here
	private final String admins = "";

	public void notify(String message) {
		PacketBuffer buff = Main.instance.bot.getPacketBuffer();

		if (admins.contains(",")) {
			for (String s : admins.split(",")) {
				PRIVATE_MESSAGE packet = new PRIVATE_MESSAGE(s, message);
				buff.AddPacketToBuffer(packet);
			}
		} else {
			PRIVATE_MESSAGE packet = new PRIVATE_MESSAGE(admins, message);
			buff.AddPacket(packet);
		}

	}

}
