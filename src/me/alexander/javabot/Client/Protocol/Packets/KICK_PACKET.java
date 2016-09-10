package me.alexander.javabot.Client.Protocol.Packets;

import me.alexander.javabot.Client.Protocol.Packet;

public class KICK_PACKET extends Packet {

	public KICK_PACKET(String username) {
		this.SetPacketType(PacketType.PUBLIC_MESSAGE);
		this.SetPacketContent("KICK " + getBot().getChannel() + " " + username);
	}

}
