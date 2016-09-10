package me.alexander.javabot.Utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.alexander.javabot.Main.Main;

public class CommonUtils {

	public static boolean debugMode = false;

	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static enum LogType {
		Info, Error, Warning, Debug, Fatal
	}

	public void DoLog(LogType type, String message) {
		boolean shouldSend = true;

		if (type.equals(LogType.Debug)) {
			shouldSend = debugMode;
		}

		if (shouldSend) {
			System.out.println(getFormat(type.toString()) + message);
			Main.instance.bot.getChatHistory().add(getFormat(type.toString()) + message);
		}
	}

	private String getTime() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	private String getFormat(String suffix) {
		String format = "[" + getTime() + "] [" + Thread.currentThread().getName() + "/";
		format += suffix;
		format += "]: ";
		return format;
	}

}
