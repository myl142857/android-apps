package org.notame.android.api;

//import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.notame.helpers.URLEncoder;


import android.util.Log;

public class NotaSender {
		
	public NotaSender(){
		
	}

	public void send(String url, String username,String key, String data) {

		InputStream in = null;
		
		HttpURLConnection connection = null;

		int respCode;
		int redirects = 0;
		URL purl = null;
		
		try {
			while (redirects < HTTPParams.MAX_REDIRECTS) {
				
				//String encoding = System.getProperty("microedition.encoding").toLowerCase();
				String encoding = "utf8";
				String urlpost = url + "?user=" + username + "&key=" + key + "&charset=" + encoding + "&text=" + URLEncoder.encode(data);
				Log.d("NotaSender", urlpost);
				purl = new URL(urlpost);
				System.out.println ("RssReader(thread): Start Connection on:" + url);
				connection = (HttpURLConnection) purl.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-Type", HTTPParams.CONTENT_TYPE);
				connection.setRequestProperty("User-Agent", HTTPParams.USER_AGENT);
				connection.setRequestProperty("Connection", "close");

				System.out.println("Status line code:" + connection.getResponseCode());
				//System.out.println("Status line message: " + connection.getResponseMessage());
				connection.connect();
				
				respCode = connection.getResponseCode();
				if (respCode == HttpURLConnection.HTTP_OK)
				{
					System.out.println("Connecting ok");
					break;
				} else if ((respCode == HttpURLConnection.HTTP_MOVED_PERM) ||
						(respCode == HttpURLConnection.HTTP_MOVED_TEMP) ||
						//(respCode == HttpURLConnection.HTTP_TEMP_REDIRECT) ||
						(respCode == HttpURLConnection.HTTP_SEE_OTHER)) 
				{
					System.out.println ("RssReader(thread): Redirection " + respCode);
					url = connection.getHeaderField("Location");
					//in.close();
					connection.disconnect();
					in = null;
					connection = null;
					if (++redirects > HTTPParams.MAX_REDIRECTS) {
						new java.io.IOException("Error: Too many redirects");
					}
				} else 
				{
					new java.io.IOException("Error " + connection.getResponseCode());
					break;
				}
			}
		} catch (Exception e) {
			Log.e("NotaSender", "->Exception: " + e.toString());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					Log.e("NotaSender","Ex2");
				}
			}
			if (connection != null){
				try {
					connection.disconnect();
				} catch (Exception e) {
					Log.e("NotaSender","Error closing connection: " + e.toString());
				}
			}
		}
	}
		
}
