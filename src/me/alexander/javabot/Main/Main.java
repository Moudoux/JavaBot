package me.alexander.javabot.Main;

import me.alexander.javabot.Client.IRCBot;
import me.alexander.javabot.Utils.CommonUtils;
import me.alexander.javabot.Web.WebServer;

public class Main extends CommonUtils {

	public IRCBot bot = null;
	public static Main instance = null;
	public WebServer webServer = null;

	private void initialize(String[] args) {

		String username = "SkyNet_AI" + "_" + ((int) (Math.random() * 9000) + 1000);

		bot = new IRCBot("irc.freenode.net", 6667, "#aristois_hack_beta", username, username);
		webServer = new WebServer();
		webServer.Start();
		bot.Start();
	}

	public static void main(String[] args) {
		System.out.println("Starting bot... Please wait...");
		instance = new Main();
		instance.initialize(args);
	}

}
