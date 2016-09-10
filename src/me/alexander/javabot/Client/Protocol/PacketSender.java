package me.alexander.javabot.Client.Protocol;

import me.alexander.javabot.Client.Protocol.Packets.KICK_PACKET;
import me.alexander.javabot.Client.Protocol.Packets.PRIVATE_MESSAGE;
import me.alexander.javabot.Client.Protocol.Packets.PUBLIC_MESSAGE;

public class PacketSender extends Packet {

	public void SendChannelMessage(String message) {
		PUBLIC_MESSAGE packet = new PUBLIC_MESSAGE(message);
		this.getBot().getPacketBuffer().AddPacket(packet);
	}

	public void SendPrivateMessage(String username, String message) {
		PRIVATE_MESSAGE packet = new PRIVATE_MESSAGE(username, message);
		this.getBot().getPacketBuffer().AddPacket(packet);
	}

	public void SendKickPacket(String username) {
		KICK_PACKET packet = new KICK_PACKET(username);
		this.getBot().getPacketBuffer().AddPacket(packet);
	}

}
