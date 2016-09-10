package me.alexander.javabot.Client.Protocol.Packets;

import me.alexander.javabot.Client.Protocol.Packet;

public class PUBLIC_MESSAGE extends Packet {

	public PUBLIC_MESSAGE(String message) {
		this.SetPacketType(PacketType.PUBLIC_MESSAGE);
		this.SetPacketContent("PRIVMSG " + this.getBot().getChannel() + " :" + message);
	}

}
