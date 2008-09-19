package net.miquelsi.contacts;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

	public class ContactsList extends ListActivity {
	
	private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    private static final int ACTIVITY_ABOUT=2;
    
    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int ABOUT_ID = Menu.FIRST + 2;
	
	private ContactsDbAdapter mDbAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDbAdapter = new ContactsDbAdapter(this);
        mDbAdapter.open();
        fillData();
    }

	private void fillData() {
		Cursor contactsCursor = mDbAdapter.fetchAllContacts();
		startManagingCursor(contactsCursor);
        
        // Create an array to specify the fields we want to display in the list (only NAME)
        String[] from = new String[]{ContactsDbAdapter.KEY_NAME, ContactsDbAdapter.KEY_PHONE};
        
        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1, R.id.text2};
        
        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.contact_row, contactsCursor, from, to);
        setListAdapter(notes);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item;
        item = menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        item.setIcon(R.drawable.ic_menu_add);
        item = menu.add(0, DELETE_ID, 0,  R.string.menu_delete);
        item.setIcon(R.drawable.ic_menu_delete);
        item = menu.add(0, ABOUT_ID, 0, R.string.menu_about);
        item.setIcon(R.drawable.ic_menu_help);
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case INSERT_ID:
            createContact();
            return true;
        case DELETE_ID:
            mDbAdapter.deleteContact(getListView().getSelectedItemId());
            fillData();
            return true;
        case ABOUT_ID:
        	showAbout();
        	return true;
        }
       
        return super.onMenuItemSelected(featureId, item);
    }

	private void showAbout() {
		startActivityForResult(new Intent(this, About.class), ACTIVITY_ABOUT);		
	}

	private void createContact() {
		Intent i = new Intent(this, ContacEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
	}
	
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        Intent i = new Intent(this, ContacEdit.class);
        i.putExtra(ContactsDbAdapter.KEY_ROWID, id);
        
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);        
            fillData();
    }
	
}