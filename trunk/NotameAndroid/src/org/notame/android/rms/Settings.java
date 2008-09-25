package org.notame.android.rms;


import java.io.FileNotFoundException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.*;
public class Settings {

    private static final long NUM_DEFAULT_NOTAS = 10; //Default notes to read
    
	private String m_sUsername;  //Username
	private String m_sAPIKey;		// 10 key string for updating status
	private long m_iNumNotas; //Number of notes to read from rss
	
	
	private String m_sNotameUrl = "http://meneame.net/sneakme_rss2.php";
	private String m_sNotameFriendsUrl = "http://meneame.net/sneakme_rss2.php"; //?user_id=";
	private String m_sNotameSendUrl = "http://meneame.net/api/newpost.php"; //?user=usuario&key=key&text=texto
	/*
	private String m_sNotameUrl = "http://localhost/sneakme_rss2.php";
	private String m_sNotameFriendsUrl = "http://localhost/sneakme_rss2.php"; //?user_id=";
	private String m_sNotameSendUrl = "http://localhost/notame_newpost.php"; //?user=usuario&key=key&text=texto
	*/
    private static final String DATABASE_CREATE =
        "create table settings (rowid integer primary key autoincrement, "
            + "username text, apikey text, numnotas integer);";

    private static final String DATABASE_NAME = "notame.db";
    private static final String DATABASE_TABLE = "settings";
    private static final int DATABASE_VERSION = 1;
    private static final int RECORD_ID = 1;

    private SQLiteDatabase db;
    private DatabaseHelper mDbHelper;
    
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("NotameDbAdapter", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS settings");
            onCreate(db);
        }
    }
    
    public Settings(Context ctx) {
    	Log.d("Settings", "Creating Settings object");
    	
        this.m_sUsername = "";
        this.m_sAPIKey = "";
        this.m_iNumNotas = Settings.NUM_DEFAULT_NOTAS;
        mDbHelper = new DatabaseHelper(ctx);
        
        try {
        	
            db = mDbHelper.getReadableDatabase(); //ctx.openDatabase(DATABASE_NAME, null);
        } catch (Exception e) {
            try {
            	db = mDbHelper.getReadableDatabase();
                //db = ctx.createDatabase(DATABASE_NAME, DATABASE_VERSION, 0,null);
                db.execSQL(DATABASE_CREATE);
            } catch (Exception e1) {
                db = null;
            }
        }
        loadSettings();
    }
    public void close() {
        db.close();
    }
    
    public void loadSettings() {
    	System.out.println ("Settings: loading data");
        
        Cursor c = db.query(true, DATABASE_TABLE, new String[] {
        		"rowid", "username", "apikey", "numnotas"}, 
        		"rowid=" + RECORD_ID, null, null, null,null, null );
        
        if (c.getCount() > 0) {
        	c.moveToFirst();
        	this.m_sUsername = c.getString(1);
        	this.m_sAPIKey = c.getString(2);
        	this.m_iNumNotas = c.getLong(3);
        } else {
        	//No settings so we add the default record.
            ContentValues args = new ContentValues();
            args.put("username", this.m_sUsername);
            args.put("apikey", this.m_sAPIKey);
            args.put("numnotas", this.m_iNumNotas);
            
            db.insert(DATABASE_TABLE, null, args);
            
        }
    }
    
    public void saveSettings(){
        ContentValues args = new ContentValues();
        args.put("username", this.m_sUsername);
        args.put("apikey", this.m_sAPIKey);
        args.put("numnotas", this.m_iNumNotas);
        
        db.update(DATABASE_TABLE, args, "rowid=" + RECORD_ID, null);
    }
	/**
	 * @return the m_sAPIKey
	 */
	public String getKey() {
		return m_sAPIKey;
	}

	/**
	 * @param key the m_sAPIKey to set
	 */
	public void setAPIKey(String key) {
		m_sAPIKey = key;
	}

	/**
	 * @return the m_sUsername
	 */
	public String getUsername() {
		return m_sUsername;
	}

	/**
	 * @param username the m_sUsername to set
	 */
	public void setUsername(String username) {
		m_sUsername = username;
	}


	/**
	 * @return the m_sNotameFriendsUrl
	 */
	public String getNotameFriendsUrl() {
		return m_sNotameFriendsUrl;
	}

	/**
	 * @param notameFriendsUrl the m_sNotameFriendsUrl to set
	 */
	public void setNotameFriendsUrl(String notameFriendsUrl) {
		m_sNotameFriendsUrl = notameFriendsUrl;
	}

	/**
	 * @return the m_sNotameUrl
	 */
	public String getNotameUrl() {
		return m_sNotameUrl;
	}

	/**
	 * @param notameUrl the m_sNotameUrl to set
	 */
	public void setNotameUrl(String notameUrl) {
		m_sNotameUrl = notameUrl;
	}

	/**
	 * @return the m_sNotameSendUrl
	 */
	public String getNotameSendUrl() {
		return m_sNotameSendUrl;
	}

	/**
	 * @param notameSendUrl the m_sNotameSendUrl to set
	 */
	public void setNotameSendUrl(String notameSendUrl) {
		m_sNotameSendUrl = notameSendUrl;
	}

	/**
	 * @return the m_iNumNotas
	 */
	public long getNumNotas() {
		return m_iNumNotas;
	}

	/**
	 * @param numNotas the m_iNumNotas to set
	 */
	public void setNumNotas(long numNotas) {
		m_iNumNotas = numNotas;
	}	
	
}
