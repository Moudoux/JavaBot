package me.alexander.javabot.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import me.alexander.javabot.Client.Buffers.PacketBuffer;
import me.alexander.javabot.Client.Buffers.UserBuffer;
import me.alexander.javabot.Client.Protocol.PacketSender;
import me.alexander.javabot.Client.Protocol.ProxyService;
import me.alexander.javabot.Utils.CommonUtils;

public class IRCBot extends CommonUtils {

	// Objects

	private PacketBuffer packetBuffer = new PacketBuffer();
	private PacketSender packetSender = new PacketSender();
	private UserBuffer userBuffer = new UserBuffer();
	private PacketReader packetReader = new PacketReader();
	private Notifier notifier = new Notifier();

	private ArrayList<String> history = new ArrayList<String>();

	private Socket socket;
	private BufferedWriter writer;

	// Settings

	private String address;
	private int port;

	private String nickname;
	private String username;
	private String realname;

	private String channel;

	/**
	 * If it should not send global chat packets ?
	 */
	public boolean silent = false;

	// Proxy Settings

	public static final String ProxyUsername = "";
	public static final String ProxyPassword = "";
	public static final String ProxyServerAddr = "";
	public static final int ProxyServerPort = 1080;

	/**
	 * If the server should connect via a proxy server
	 */
	private boolean useProxy = false;

	public PacketBuffer getPacketBuffer() {
		return this.packetBuffer;
	}

	public PacketSender getPacketSender() {
		return this.packetSender;
	}

	public ArrayList<String> getChatHistory() {
		return this.history;
	}

	public UserBuffer getUserBuffer() {
		return this.userBuffer;
	}

	public String getChannel() {
		return this.channel;
	}

	public String getUsername() {
		return this.username;
	}

	public Notifier getNotifier() {
		return this.notifier;
	}

	public BufferedWriter getBufferedWriter() {
		return this.writer;
	}

	public IRCBot(String address, int port, String channel, String username, String realname) {
		this.address = address;
		this.port = port;
		this.channel = channel;
		this.username = username;
		this.nickname = username;
		this.realname = realname;
	}

	public void KickUser(String username) {
		try {
			getUserBuffer().RemoveUser(username);
			DoLog(LogType.Info, "Kicking " + username + "...");
			this.getPacketSender().SendPrivateMessage(username, "You were kicked ~ SkyNet AI");
			this.getPacketSender().SendKickPacket(username);
		} catch (Exception ex) {
			DoLog(LogType.Error, "Failed to kick user " + username + "\n" + ex.getMessage());
		}
	}

	public void KickUser(String username, String reason) {
		try {
			getUserBuffer().RemoveUser(username);
			DoLog(LogType.Info, "Kicking " + username + "...");
			this.getPacketSender().SendPrivateMessage(username, reason);
			this.getPacketSender().SendKickPacket(username);
		} catch (Exception ex) {
			DoLog(LogType.Error, "Failed to kick user " + username + "\n" + ex.getMessage());
		}
	}

	public void Shutdown() {

	}

	public void Start() {
		new Thread() {
			@Override
			public void run() {
				Thread.currentThread().setName("Client");
				try {

					DoLog(LogType.Info, "Connecting to IRC server...");

					if (useProxy) {
						socket = new Socket(ProxyService.getProxy());
					} else {
						socket = new Socket();
					}

					socket.connect(new InetSocketAddress(address, port));

					DoLog(LogType.Info, "Connected to IRC server");

					writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					writer.write("NICK " + nickname + "\r\n");
					writer.write("USER " + username + " 8 * : " + realname + "\r\n");
					writer.flush();

					String line = null;
					while ((line = reader.readLine()) != null) {
						if (line.indexOf("004") >= 0) {
							break;
						} else if (line.indexOf("433") >= 0) {
							DoLog(LogType.Error, "Failed to connect to IRC server... Username/Nickname in use");
							return;
						}
					}

					writer.write("JOIN " + channel + "\r\n");
					writer.flush();

					DoLog(LogType.Info, "Joining IRC channel...");

					while ((line = reader.readLine()) != null && !socket.isClosed()) {
						if (line.toLowerCase().contains("join") && line.toLowerCase().contains(nickname.toLowerCase())
								&& line.toLowerCase().contains(channel.toLowerCase())) {
							DoLog(LogType.Info, "Joined IRC channel");
							break;
						}
					}

					while ((line = reader.readLine()) != null && !socket.isClosed()) {
						try {
							// Ping packet
							if (line.toLowerCase().startsWith("ping ")) {
								writer.write("PONG " + line.substring(5) + "\r\n");
								writer.flush();
							} else {
								packetReader.ReadPacket(line);
							}
						} catch (Exception ex) {
							DoLog(LogType.Error, "An error occurred while reading a packet");
						}
					}

				} catch (Exception ex) {
					DoLog(LogType.Fatal, "Failed to start IRCBot");
					ex.printStackTrace();
				}
			}
		}.start();
	}

}