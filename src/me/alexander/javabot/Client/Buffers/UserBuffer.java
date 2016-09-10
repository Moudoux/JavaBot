package me.alexander.javabot.Client.Buffers;

import java.util.HashMap;

import me.alexander.javabot.Client.Protocol.Packet;

public class UserBuffer extends Packet {

	/**
	 * Username, IP Address
	 */
	private HashMap<String, String> userBuffer = new HashMap<String, String>();
	private HashMap<String, String> userBufferIPS = new HashMap<String, String>();

	/**
	 * Username, value
	 */
	private HashMap<String, String> userWarnings = new HashMap<String, String>();

	public void AddUser(String username, String host) {
		String ip = translateHostName(host);
		if (!userBuffer.containsKey(username)) {
			userBuffer.put(username, ip);
			userBufferIPS.put(ip, username);
		}
	}

	public void RemoveUser(String username) {
		if (userBuffer.containsKey(username)) {
			userBufferIPS.remove(userBuffer.get(username));
			userBuffer.remove(username);
			userWarnings.remove(username);
		}
	}

	public String getUserIP(String username) {
		if (userBuffer.containsKey(username)) {
			return userBuffer.get(username);
		}
		return "Error";
	}

	public String getUserName(String ip) {
		if (userBufferIPS.containsKey(ip)) {
			return userBufferIPS.get(ip);
		}
		return "Error";
	}

	public HashMap<String, String> getUserWarnings() {
		return this.userWarnings;
	}

}
