package me.alexander.javabot.Web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import me.alexander.javabot.Main.Main;
import me.alexander.javabot.Utils.CommonUtils;

public class WebServer extends CommonUtils {

	public void Start() {
		new Thread() {
			@Override
			public void run() {
				currentThread().setName("Web Server");

				String webPath = "";

				try {
					webPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}

				try {
					HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

					File webDir = new File(webPath);

					DoLog(LogType.Info, "Scanning web folder for .html files");

					for (File file : webDir.listFiles()) {
						if (file.getName().endsWith(".html")) {
							server.createContext("/" + file.getName(), new RequestHandler());
							DoLog(LogType.Info, "Found file " + file.getName());
						}
					}

					server.createContext("/", new RequestHandler());

					server.setExecutor(null);
					server.start();
					DoLog(LogType.Info, "Web server running at port 8080");
				} catch (Exception ex) {
					DoLog(LogType.Error, "Failed to start web server");
				}

			}
		}.start();
	}

	class RequestHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange req) throws IOException {
			String response = "<html><h2>SkyNet AI Bot History:</h2><p>";

			for (String s : Main.instance.bot.getChatHistory()) {
				response = response + s + "<br>";
			}

			response = response + "</p><html>";

			req.sendResponseHeaders(200, response.length());

			OutputStream os = req.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}

	}

}
