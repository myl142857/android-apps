package net.miquelsi.contacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContacEdit extends Activity {
	
	private EditText mNameText;
    private EditText mPhoneText;
    private EditText mEmailText;
    private Long mRowId;
    private ContactsDbAdapter mDbHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new ContactsDbAdapter(this);
        mDbHelper.open();
        setContentView(R.layout.contact_edit);
       
        mNameText = (EditText) findViewById(R.id.name);
        mPhoneText = (EditText) findViewById(R.id.phone);
        mEmailText = (EditText) findViewById(R.id.email);
      
        Button confirmButton = (Button) findViewById(R.id.confirm);
        Button delButton = (Button) findViewById(R.id.delete);
       
        mRowId = savedInstanceState != null ? savedInstanceState
				.getLong(ContactsDbAdapter.KEY_ROWID) : null;
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(ContactsDbAdapter.KEY_ROWID)
					: null;
		}

		populateFields();
       
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
          
        });
        
        delButton.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View view){
        		/// TODO: Show a confirmation dialog for deleting the contact
        		mDbHelper.deleteContact(mRowId);
        		setResult(RESULT_OK);
        		finish();
        	}
        });
    }

	private void populateFields() {
		if (mRowId != null) {
            Cursor note = mDbHelper.fetchContact(mRowId);
            startManagingCursor(note);
            mNameText.setText(note.getString(
    	            note.getColumnIndexOrThrow(ContactsDbAdapter.KEY_NAME)));
            mPhoneText.setText(note.getString(
                    note.getColumnIndexOrThrow(ContactsDbAdapter.KEY_PHONE)));
            mEmailText.setText(note.getString(
                    note.getColumnIndexOrThrow(ContactsDbAdapter.KEY_EMAIL)));
        }
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ContactsDbAdapter.KEY_ROWID, mRowId);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }
    
    private void saveState() {
        String name = mNameText.getText().toString();
        String phone = mPhoneText.getText().toString();
        String email = mEmailText.getText().toString();

        if (mRowId == null) {
            long id = mDbHelper.createContact(name, phone, email);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateContact(mRowId, name, phone, email);
        }
    }

}
