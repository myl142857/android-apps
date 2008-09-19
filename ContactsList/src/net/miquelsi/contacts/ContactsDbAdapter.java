package net.miquelsi.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactsDbAdapter {
	
	/**
	 * Database fields
	 */
	public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "ContactsDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
            "create table contacts (_id integer primary key autoincrement, "
                    + "name text not null, phone text not null, email text);";

    private static final String DATABASE_NAME = "dades";
    private static final String DATABASE_TABLE = "contacts";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

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
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public ContactsDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the contacts database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public ContactsDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new contact using the name, phone and email provided. If the contact is
     * successfully created return the new rowId for that contact, otherwise return
     * a -1 to indicate failure.
     * 
     * @param name the name of the contact
     * @param phone the phone of the contact
     * @param email the email of the contact
     * @return rowId or -1 if failed
     */
    public long createContact(String name, String phone, String email) {
    	if (name.equals("") && phone.equals("")){
    		return -1;
    	}
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_PHONE, phone);
        initialValues.put(KEY_EMAIL, email);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the contact with the given rowId
     * 
     * @param rowId id of contact to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteContact(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all contacts in the database
     * 
     * @return Cursor over all contacts
     */
    public Cursor fetchAllContacts() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_PHONE, KEY_EMAIL}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the contact that matches the given rowId
     * 
     * @param rowId id of contact to retrieve
     * @return Cursor positioned to matching contact, if found
     * @throws SQLException if contact could not be found/retrieved
     */
    public Cursor fetchContact(long rowId) throws SQLException {

        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                        KEY_PHONE, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the contact using the details provided. The contact to be updated is
     * specified using the rowId, and it is altered to use the name, phone and email
     * passed in
     * 
     * @param rowId id of note to update
     * @param name value to set contact name to
     * @param phone value to set contact phone to
     * @param email value to set contact email to
     * @return true if the contact was successfully updated, false otherwise
     */
    public boolean updateContact(long rowId, String name, String phone, String email) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_PHONE, phone);
        args.put(KEY_EMAIL, email);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
