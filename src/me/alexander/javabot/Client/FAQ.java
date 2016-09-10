package me.alexander.javabot.Client;

import me.alexander.javabot.Client.Protocol.Packet;
import me.alexander.javabot.Main.Main;

public class FAQ extends Packet {

	private static boolean flag = false;
	private static String username = "";
	private static String question = "";

	private static void Answer(String answer) {
		Main.instance.bot.getPacketSender().SendChannelMessage(answer);
	}

	private static void handle(String[] q, String response) {
		if (!flag) {

			int c = 0;

			for (String s : q) {
				s = s.toLowerCase();
				if (question.contains(s)) {
					c += 1;
				}
			}

			if (c == q.length) {
				flag = true;

				if (response.contains("\n")) {
					for (String d : response.split("\n")) {
						Answer(d);
					}
				} else {
					Answer(response);
				}

			}

		}
	}

	public static void HandleFAQ(String q, String user) {
		q = q.toLowerCase().replace("'", "");
		flag = false;
		username = user;
		question = q;

		handle(new String[] { "cant", "see", "combat" }, username
				+ " Set your GUI Scale to Normal, Go into ESC -> Options -> Video Settings -> Scale -> Normal.");

		handle(new String[] { "irc", "rule" },
				"== IRC Rules ==\n1. No spam\n2. No racism\n3. Don't be rude\n4. Watch your language\n5. Use your common sense");

		handle(new String[] { "anyone", "here" }, "Im always here");

		handle(new String[] { "oh", "skynet" }, "Oh " + user);
	}

}
