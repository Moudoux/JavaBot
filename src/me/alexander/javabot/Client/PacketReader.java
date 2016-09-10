package me.alexander.javabot.Client;

import me.alexander.javabot.Api.ApiInterface;
import me.alexander.javabot.Client.Protocol.Packet;
import me.alexander.javabot.Main.Main;

public class PacketReader extends Packet {

	// Readers

	private void JoinPacket(String line) {
		// User joined
		String username = line.split("!")[0].substring(1);
		String ip = line.split("@")[1].split("\\.x")[0].split(" JOIN ")[0];
		String translatedIP = this.translateHostName(ip);
		DoLog(LogType.Info, username + "@" + translatedIP + " has joined");

		boolean flag = false;

		if (translatedIP.equals(ip)) {
			// IP Hostname translation failed
			flag = true;
			this.getBot().getPacketSender().SendKickPacket(username);
		}

		if (!flag && !ApiInterface.RunChecks(username, translatedIP)) {
			if (ApiInterface.isBanned(username)) {
				Main.instance.bot.KickUser(username, "You were kicked ~ SkyNet AI");
			} else {

				if (username.toLowerCase().contains("skynet")
						|| username.toLowerCase().contains("sky") && username.toLowerCase().contains("net")) {
					Main.instance.bot.KickUser(username, "You were kicked ~ SkyNet AI");
					flag = true;
				}

				if (!flag) {
					Main.instance.bot.getUserBuffer().AddUser(username, ip);
				}
			}
		}
	}

	private void PrivateMessagePacket(String line) {
		// Private message to skynet

		String username = line.split("!")[0].substring(1);
		String message = line.split("PRIVMSG " + Main.instance.bot.getUsername() + " :")[1];

		String userIP = Main.instance.bot.getUserBuffer().getUserIP(username);

		if (!ApiInterface.RunChecks(username, userIP)) {

			if (ApiInterface.isBanned(username)) {
				Main.instance.bot.getUserBuffer().RemoveUser(username);
				Main.instance.bot.KickUser(username, "You were kicked ~ SkyNet AI");
			} else {
				@SuppressWarnings("unused")
				CommandProccessor j = new CommandProccessor(message, username, Main.instance.bot);
			}
		}
	}

	private void ChannelMessage(String line) {
		// Public channel message

		String username = line.split("!")[0].substring(1);

		if (!username.toLowerCase().equals(Main.instance.bot.getUsername().toLowerCase())) {

			String message = line.split("PRIVMSG " + Main.instance.bot.getChannel() + " :")[1];
			String userIP = Main.instance.bot.getUserBuffer().getUserIP(username);

			DoLog(LogType.Info, username + "@" + userIP + " " + Main.instance.bot.getChannel() + ": " + message);

			if (!ApiInterface.RunChecks(username, userIP)) {
				if (ApiInterface.isBanned(username)) {
					Main.instance.bot.getUserBuffer().RemoveUser(username);
					Main.instance.bot.KickUser(username, "You were kicked ~ SkyNet AI");
				} else {
					@SuppressWarnings("unused")
					ChatProccessor j = new ChatProccessor(message, username, Main.instance.bot);
				}

			}
		}

	}

	private void QuitPacket(String line) {
		// User quit

		String username = line.substring(1).split("!")[0];
		String host = line.substring(1).split("@")[1].split(" QUIT ")[0];
		String ip = this.translateHostName(host);
		String reason = line.split("QUIT :")[1];

		Main.instance.bot.getUserBuffer().RemoveUser(username);

		DoLog(LogType.Info, username + "@" + ip + " has left: " + reason);

	}

	private void KickPacket(String line) {
		// SkyNet got kicked

		Main.instance.bot.Shutdown();

	}

	private void UserBannedPacket(String line) {
		// User in IRC was banned

		String banner = line.substring(1).split("!")[0];
		String host = line.split("MODE " + Main.instance.bot.getChannel())[1].replace(" +b ", "").split("@")[1];
		String ip = this.translateHostName(host);
		String username = Main.instance.bot.getUserBuffer().getUserName(ip);

		Main.instance.bot.getUserBuffer().RemoveUser(username);

		DoLog(LogType.Info, username + "@" + ip + " was banned by " + banner);
	}

	public void UserOpped(String line) {
		String opped = line.split("MODE")[1].replace(" " + getBot().getChannel() + " +o ", "");
		String opper = line.substring(1).split("!")[0];
		String host = line.substring(1).split("!")[1].split("@")[1].split(" ")[0];
		String ip = this.translateHostName(host);
		String oppedIP = this.getBot().getUserBuffer().getUserIP(opped);

		if (opped.equals(getBot().getUsername())) {
			DoLog(LogType.Info, "SkyNet recived op by " + opper + "@" + ip);
		} else {
			DoLog(LogType.Info, opper + "@" + ip + " sets mode +o on " + opped + "@" + oppedIP);
		}

		@SuppressWarnings("unused")
		boolean b = ApiInterface.RunChecks(opped, oppedIP);
		b = ApiInterface.RunChecks(opper, ip);
	}

	public void ReadPacket(String line) {
		DoLog(LogType.Debug, "Recived Packet: " + line);
		if (line.toLowerCase().contains("join")) {
			JoinPacket(line);
		} else if (line.contains("PRIVMSG #")) {
			ChannelMessage(line);
		} else if (line.toLowerCase().contains("quit")) {
			QuitPacket(line);
		} else if (line.contains("PRIVMSG " + Main.instance.bot.getUsername() + " :")) {
			DoLog(LogType.Debug, "DM1");
			PrivateMessagePacket(line);
		} else if (line.contains("KICK " + Main.instance.bot.getChannel() + " " + Main.instance.bot.getUsername() + " :"
				+ Main.instance.bot.getUsername())) {
			KickPacket(line);
		} else if (line.contains("MODE " + Main.instance.bot.getChannel() + " +b")) {
			UserBannedPacket(line);
		} else if (line.contains("MODE " + Main.instance.bot.getChannel() + " +o")) {
			UserOpped(line);
		} else {
			// Unkown packet
			DoLog(LogType.Debug, "Failed to parse packet");
		}
	}

}
