/*
 * The source code packaged with this file is Free Software, Copyright (c) 2007
 * by Carlos Fernandez (carlos.fernandez at podsonoro.com).
 * It's licensed under the AFERO GENERAL PUBLIC LICENSE unless stated otherwise.
 * You can get copies of the licenses here:
 * 		http://www.affero.org/oagpl.html
 * AFFERO GENERAL PUBLIC LICENSE is also included in the file called "COPYING".
 */

package org.notame.rss;

import java.util.Vector;

public class RssItems {
	private Vector items;
	
	public RssItems() {
		items = new Vector();
	}
	 
	public RssItem getItemAt(int index) {
		if ((index < 0) || (index >= this.items.size())) return null;
		return (RssItem) this.items.elementAt(index);
	}
	public void addItem(RssItem item){
		items.addElement(item);
	}
	public int count(){
		return this.items.size();
	}
	public void dump(){
		for (int i=0; i<count(); i++){
			System.out.println("Item: " + i);
			getItemAt(i).dump();
		}
	}
}
