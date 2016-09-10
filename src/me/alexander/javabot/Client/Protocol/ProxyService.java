package me.alexander.javabot.Client.Protocol;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;

import me.alexander.javabot.Client.IRCBot;

public class ProxyService {

	public static Proxy getProxy() {

		Authenticator.setDefault(new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(IRCBot.ProxyUsername, IRCBot.ProxyPassword.toCharArray());
			}
		});

		SocketAddress proxyAddr = new InetSocketAddress(IRCBot.ProxyServerAddr, IRCBot.ProxyServerPort);
		Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddr);
		return proxy;
	}

}
