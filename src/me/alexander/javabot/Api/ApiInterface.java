package me.alexander.javabot.Api;

import me.alexander.javabot.Client.Protocol.Packet;
import me.alexander.javabot.Main.Main;
import me.alexander.javabot.Web.WebUtils;

public class ApiInterface extends Packet {

	public static boolean isBanned(String username) {
		// You can add code here to check if a user is banned in any external database
		return false;
	}

	public static void banUser(String username) {
		// You can add code here to ban users in a external database
	}

	public static boolean RunChecks(String username, String ipaddress) {
		// You can add custom checks here for a username, Imposter checks maybe
		return false;
	}

	public static void handleWhois(String whois) {
		// TODO
	}

}
