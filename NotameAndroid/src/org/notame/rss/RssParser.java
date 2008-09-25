/*
 * The source code packaged with this file is Free Software, Copyright (c) 2007
 * by Carlos Fernandez (carlos.fernandez at podsonoro.com).
 * It's licensed under the AFERO GENERAL PUBLIC LICENSE unless stated otherwise.
 * You can get copies of the licenses here:
 * 		http://www.affero.org/oagpl.html
 * AFFERO GENERAL PUBLIC LICENSE is also included in the file called "COPYING".
 */

package org.notame.rss;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParserException;

public class RssParser {
	private RssFeed feed;
	
	public RssParser() {

		this.feed = new RssFeed();
	}
	
	public void parse(InputStream in){
		System.out.println ("RssParser: Start parsing");
		KXmlParser parser = new KXmlParser();
		
		try {
			parser.setInput(new InputStreamReader(in, "UTF-8"));

			int eventType = KXmlParser.START_TAG;

			while (eventType != KXmlParser.END_DOCUMENT)
			{
				eventType = parser.next();
				if (eventType == KXmlParser.START_TAG) {
					if (parser.getName().equals("channel"))
					{
						parseChannel(parser);
					}
				}
			}
			System.out.println("RssParser: Stop parsing");
		} catch (Exception e) {
			System.out.println("Error while parsing RSS:" + e.toString());
		}
	}
	private void parseChannel(KXmlParser parser) throws XmlPullParserException, IOException {

		int eventType = parser.next();

		int deep = parser.getDepth();
		while(!"channel".equals(parser.getName()))
		{
			eventType = parser.next();
			deep = parser.getDepth();
			if (eventType == KXmlParser.START_TAG && deep == 3){

				// It must be deep 3, that is <title>... <item> etc
				// otherwise it will get into <image> tags, overriding data.
				
				if (parser.getName().equals("title"))
				{
					this.feed.setTitle(parser.nextText());
				}
				else if (parser.getName().equals("link"))
				{
					this.feed.setLink(parser.nextText());
				}
				else if (parser.getName().equals("description"))
				{
					this.feed.setDescription(parser.nextText());
				}
				else if (parser.getName().equals("pubDate"))
				{
					this.feed.setPubDate(parser.nextText());
				}
				else if (parser.getName().equals("item"))
				{
					this.parseItem(parser);
				}
			}
		}	
	}
	private void parseItem(KXmlParser parser) throws XmlPullParserException, IOException {
		int eventType = parser.next();
		RssItem item = new RssItem();

		int deep = parser.getDepth();
		while(!"item".equals(parser.getName()))
		{
			eventType = parser.next();
			deep = parser.getDepth();
			if (eventType == KXmlParser.START_TAG && deep == 4){
				if (parser.getName().equals("title")){
					parser.next();
					item.setTitle(parser.getText());
				}
				else if (parser.getName().equals("link")){
					parser.next();
					item.setLink(parser.getText());
				}
				else if (parser.getName().equals("description")){
					parser.next();
					item.setDescription(parser.getText());
				}
				else if (parser.getName().equals("pubDate")){
					parser.next();
					item.setPubDate(parser.getText());
				}
				else if (parser.getName().equals("dc:creator"))
				{
					parser.next();
					item.setDc_creator(parser.getText());
				}
			}
			
		}		
		feed.addItem(item);
	}

	/**
	 * @return the feed
	 */
	public RssFeed getFeed() {
		return feed;
	}
}
