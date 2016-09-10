package me.alexander.javabot.Web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WebUtils {

	public static String get(String url) throws WebFetchException {
		String result = "";
		try {
			URL url1 = new URL(url);
			URLConnection urlConn = url1.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

			String text;

			while ((text = in.readLine()) != null) {
				result = result + text;
			}

			in.close();
		} catch (MalformedURLException e) {
			throw new WebFetchException("The url is malformed");
		} catch (IOException e) {
			throw new WebFetchException("There was an I/O exception while fetching the requested page");
		}
		return result;
	}

}
