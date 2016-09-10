package me.alexander.javabot.Client.Buffers;

import me.alexander.javabot.Client.Protocol.Packet;
import me.alexander.javabot.Utils.CommonUtils;

public class PacketBuffer extends CommonUtils {

	public void AddPacket(Packet p) {
		DoLog(LogType.Debug, "Added packet in buffer: " + p.getPacketType().toString() + ": " + p.getPacketContent());
		p.Execute();
	}

	/**
	 * Used if the client sends too many packets at once to avoid a excess flood
	 * 
	 * @param p
	 */
	public void AddPacketToBuffer(final Packet p) {
		DoLog(LogType.Debug,
				"Added packet in delayed buffer: " + p.getPacketType().toString() + ": " + p.getPacketContent());
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				p.Execute();
			}
		}.start();
	}

}
