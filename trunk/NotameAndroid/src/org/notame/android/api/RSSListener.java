package org.notame.android.api;

import org.notame.rss.RssFeed;

//import org.notame.android.rss.RssFeed;

public interface RSSListener {
	public void feedParsed(RssFeed feed);
	public void exception (java.io.IOException e);
}
