/*
 * The source code packaged with this file is Free Software, Copyright (c) 2007
 * by Carlos Fernandez (carlos.fernandez at podsonoro.com).
 * It's licensed under the AFERO GENERAL PUBLIC LICENSE unless stated otherwise.
 * You can get copies of the licenses here:
 * 		http://www.affero.org/oagpl.html
 * AFFERO GENERAL PUBLIC LICENSE is also included in the file called "COPYING".
 */

package org.notame.rss;

import android.util.Log;


public class RssFeed {
	private RssItems items;
	private String title;
	private String description;
	private String pubDate;
	private String link;
	    
	public RssFeed() {
		this.items = new RssItems();
	} 
	
	public void addItem(RssItem item){
		this.items.addItem(item);
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the pubDate
	 */
	public String getPubDate() {
		return pubDate;
	}

	/**
	 * @param pubDate the pubDate to set
	 */
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the items
	 */
	public RssItems getItems() {
		return items;
	}
	public void dump(){
		Log.d("RssFeed","Title: " + getTitle());
		Log.d("RssFeed","Link: " + getLink());
		Log.d("RssFeed","PubDate: " + getPubDate());
		Log.d("RssFeed","Description: " + getDescription());
		items.dump();
	}

	
	
}
