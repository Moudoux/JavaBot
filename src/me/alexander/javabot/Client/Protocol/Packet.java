package me.alexander.javabot.Client.Protocol;

import java.net.InetAddress;
import java.net.UnknownHostException;

import me.alexander.javabot.Client.IRCBot;
import me.alexander.javabot.Main.Main;
import me.alexander.javabot.Utils.CommonUtils;

public abstract class Packet extends CommonUtils {

	private PacketType packetType;
	private String command;
	private boolean executed = false;

	public PacketType getPacketType() {
		return this.packetType;
	}

	public void SetPacketType(PacketType packetType) {
		this.packetType = packetType;
	}

	protected IRCBot getBot() {
		return Main.instance.bot;
	}

	protected void SetPacketContent(String content) {
		this.command = content;
	}

	public String getPacketContent() {
		return command;
	}

	public enum PacketType {
		PUBLIC_MESSAGE, PRIVATE_MESSAGE, KICK, BAN, WHOIS, MODE, NAMES, PONG;
	}

	public int countCaps(String line) {
		int result = 0;

		for (String d : line.split("")) {
			boolean hasUppercase = !d.equals(d.toLowerCase());
			if (hasUppercase) {
				result += 1;
			}
		}

		return result;
	}

	public String translateHostName(String hostName) {
		InetAddress translate;
		try {
			translate = InetAddress.getByName(hostName);
			return translate.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return hostName;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void Execute() {
		executed = true;
		try {
			Main.instance.bot.getBufferedWriter().write(command + "\r\n");
			Main.instance.bot.getBufferedWriter().flush();
		} catch (Exception ex) {
			DoLog(LogType.Error,
					"Failed to send packet: Type: " + this.packetType.toString() + "\nContent: " + this.command);
		}
	}

}
