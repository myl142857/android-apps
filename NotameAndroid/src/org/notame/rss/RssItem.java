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

/**
 * @author cafernan
 *
 */
public class RssItem {
	private String title;
	private String link;
	private String image;
	private String dc_creator;
	private String guid;
	private String description;
	private String pubDate;
	
 

	public RssItem(){
	}

	/**
	 * @return the dc_creator
	 */
	public String getDc_creator() {
		return dc_creator;
	}

	/**
	 * @param dc_creator the dc_creator to set
	 */
	public void setDc_creator(String dc_creator) {
		this.dc_creator = dc_creator;
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
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
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
	public void dump(){
		Log.d("RssItem"," Title: " + getTitle());
		Log.d("RssItem"," Link: " + getLink());
		Log.d("RssItem"," Description: " + getDescription());
	}

}
