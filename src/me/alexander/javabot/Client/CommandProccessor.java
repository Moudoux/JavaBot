package me.alexander.javabot.Client;

import me.alexander.javabot.Api.ApiInterface;
import me.alexander.javabot.Client.Protocol.Packet;
import me.alexander.javabot.Main.Main;

public class CommandProccessor extends Packet {

	private void sendAnswer(String username, String message) {
		Main.instance.bot.getPacketSender().SendPrivateMessage(username, message);
	}

	public CommandProccessor(String message, String username, IRCBot instance) {
		try {

			String command = message;
			if (message.contains(" ")) {
				command = message.split(" ")[0];
			}
			command = command.toLowerCase();

			if (command.equals("help")) {
				sendAnswer(username, "== Help ==");
				sendAnswer(username, "whois <Username>");
			} else if (command.equals("whois")) {
				if (!ApiInterface.RunChecks(username, getBot().getUserBuffer().getUserIP(username))) {
					String whois = message.split(" ")[1];
					sendAnswer(username, "== Whois " + whois + " ==");
					ApiInterface.handleWhois(whois);
				} else {
					sendAnswer(username, "You're not permitted to perform that command");
				}
			} else if (command.equals("say")) {
				if (!ApiInterface.RunChecks(username, getBot().getUserBuffer().getUserIP(username))) {
					String say = message.replace(message.split(" ")[0] + " ", "");
					Main.instance.bot.getPacketSender().SendChannelMessage(say);
				} else {
					sendAnswer(username, "You're not permitted to perform that command");
				}
			} else {
				sendAnswer(username, "Unknown command, type help for all commands");
			}

		} catch (Exception ex) {
			getBot().getPacketSender().SendPrivateMessage(username, "Invalid command, type help for all commands");
		}
	}

}
