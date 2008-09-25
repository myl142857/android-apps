package org.notame.android.api;

//import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.notame.rss.RssFeed;
import org.notame.rss.RssParser;

import android.util.Log;

public class RSSReader {
		
	public RSSReader(){
		
	}


	/**
	 * @param url
	 * @return
	 */
	public RssFeed readFeed(String url) {
		InputStream in = null;
		RssFeed feed = null;
		HttpURLConnection connection = null;
		//String url = purl;
		int respCode;
		int redirects = 0;
		URL purl = null;
		
		try {
			while (redirects < HTTPParams.MAX_REDIRECTS) {
				purl = new URL(url);
				System.out.println ("RssReader(thread): Start Connection on:" + url);
				connection = (HttpURLConnection) purl.openConnection();
				
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-Type", HTTPParams.CONTENT_TYPE);
				connection.setRequestProperty("User-Agent", HTTPParams.USER_AGENT);
				connection.setRequestProperty("Connection", "close");

				//System.out.println("Status line code:" + connection.getResponseCode());
				//System.out.println("Status line message: " + connection.getResponseMessage());
				connection.connect();
				
				respCode = connection.getResponseCode();
				if (respCode == HttpURLConnection.HTTP_OK)
				{
					System.out.println("Connecting ok");
					in = connection.getInputStream();
					RssParser parser = new RssParser();
					parser.parse(in);
					feed = parser.getFeed();
					break;
				} else if ((respCode == HttpURLConnection.HTTP_MOVED_PERM) ||
						(respCode == HttpURLConnection.HTTP_MOVED_TEMP) ||
						//(respCode == HttpURLConnection.HTTP_TEMP_REDIRECT) ||
						(respCode == HttpURLConnection.HTTP_SEE_OTHER)) 
				{
					System.out.println ("RssReader(thread): Redirection " + respCode);
					url = connection.getHeaderField("Location");
					connection.disconnect();
					in = null;
					connection = null;
					if (++redirects > HTTPParams.MAX_REDIRECTS) {
						new java.io.IOException("Error: Too many redirects");
					}
				} else 
				{
					System.out.println ("Connect: Response Code" + connection.getResponseCode());  
					//m_rssListener.exception(new java.io.IOException("Error " + connection.getResponseCode()));
					new java.io.IOException("Error " + connection.getResponseCode());
					break;
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Log.e("RSSReader","Exception: " + e.toString());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					Log.e("RSSReader","Exception: " + e.toString());
				}
			}
			if (connection != null){
				try {
					connection.disconnect();
				} catch (Exception e) {
					Log.e("RSSReader","Error closing connection: " + e.toString());
				}
			}
		}
		return feed;
	}	
}
