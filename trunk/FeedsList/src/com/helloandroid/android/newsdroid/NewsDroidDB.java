package com.helloandroid.android.newsdroid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NewsDroidDB {

	private static final String CREATE_TABLE_FEEDS = "create table feeds (feed_id integer primary key autoincrement, "
			+ "title text not null, url text not null, desc text not null);";

	private static final String CREATE_TABLE_ARTICLES = "create table articles (article_id integer primary key autoincrement, "
			+ "feed_id int not null, title text not null, url text not null, desc text not null);";

	private static final String FEEDS_TABLE = "feeds";
	private static final String ARTICLES_TABLE = "articles";
	private static final String DATABASE_NAME = "newdroid";
	private static final int DATABASE_VERSION = 1;

	private SQLiteDatabase db;
	private DatabaseHelper mDbHelper;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_ARTICLES);
            db.execSQL(CREATE_TABLE_FEEDS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("NewsDroidDbAdapter", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS feeds");
            db.execSQL("DROP TABLE IF EXISTS articles");
            onCreate(db);
        }
    }

	public NewsDroidDB(Context ctx) {
		mDbHelper = new DatabaseHelper(ctx);
		try {
			db = mDbHelper.getReadableDatabase();
		} catch (Exception e) {
			try {
				db = mDbHelper.getReadableDatabase();
				db.execSQL(CREATE_TABLE_FEEDS);
				db.execSQL(CREATE_TABLE_ARTICLES);
			} catch (Exception e1) {
				db = null;
			}
		}
	}
	
	public void close() {
        mDbHelper.close();
    }

	public boolean insertFeed(String title, URL url, String text) {
		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("url", url.toString());
		values.put("desc", text);
		return (db.insert(FEEDS_TABLE, null, values) > 0);
	}

	public boolean deleteFeed(Long feedId) {
		return (db.delete(FEEDS_TABLE, "feed_id=" + feedId.toString(), null) > 0);
	}

	public boolean insertArticle(Long feedId, String title, URL url, String desc) {
		ContentValues values = new ContentValues();
		values.put("feed_id", feedId);
		values.put("title", title);
		values.put("url", url.toString());
		values.put("desc", desc);
		return (db.insert(ARTICLES_TABLE, null, values) > 0);
	}

	public boolean deleteAricles(Long feedId) {
		return (db.delete(ARTICLES_TABLE, "feed_id=" + feedId.toString(), null) > 0);
	}

	public List<Feed> getFeeds() {
		ArrayList<Feed> feeds = new ArrayList<Feed>();
		try {
			Cursor c = db.query(FEEDS_TABLE, new String[] { "feed_id", "title",
					"url", "desc" }, null, null, null, null, null);

			int numRows = c.getCount();
			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				Feed feed = new Feed();
				feed.feedId = c.getLong(0);
				feed.title = c.getString(1);
				feed.url = new URL(c.getString(2));
				feed.text = c.getString(3);
				feeds.add(feed);
				c.moveToNext();
			}
		} catch (SQLException e) {
			Log.e("NewsDroid", e.toString());
		} catch (MalformedURLException e) {
			Log.e("NewsDroid", e.toString());
		}
		return feeds;
	}

	public List<Article> getArticles(Long feedId) {
		ArrayList<Article> articles = new ArrayList<Article>();
		try {
			Cursor c = db.query(ARTICLES_TABLE, new String[] { "article_id",
					"feed_id", "title", "url", "desc" },
					"feed_id=" + feedId.toString(), null, null, null, null);

			int numRows = c.getCount();
			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				Article article = new Article();
				article.articleId = c.getLong(0);
				article.feedId = c.getLong(1);
				article.title = c.getString(2);
				article.url = new URL(c.getString(3));
				article.description = c.getString(4);
				articles.add(article);
				c.moveToNext();
			}
		} catch (SQLException e) {
			Log.e("NewsDroid", e.toString());
		} catch (MalformedURLException e) {
			Log.e("NewsDroid", e.toString());
		}
		return articles;
	}

}
