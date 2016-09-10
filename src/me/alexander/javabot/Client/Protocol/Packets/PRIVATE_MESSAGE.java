package me.alexander.javabot.Client.Protocol.Packets;

import me.alexander.javabot.Client.Protocol.Packet;

public class PRIVATE_MESSAGE extends Packet {

	public PRIVATE_MESSAGE(String username, String message) {
		this.SetPacketType(PacketType.PRIVATE_MESSAGE);
		this.SetPacketContent("PRIVMSG " + username + " :" + message);
	}

}
