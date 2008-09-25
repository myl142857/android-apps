package org.notame.android;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class NotameAndroid extends Activity {
	
	private static final int ACTIVITY_CREATE=0;
	
    private static final int MENU_ID_NOTAME_TODOS = Menu.FIRST;
    private static final int MENU_ID_NOTAME_AMIGOS = Menu.FIRST + 1;
    private static final int MENU_ID_NUEVA_NOTA = Menu.FIRST + 2;
    private static final int MENU_ID_CONFIGURAR = Menu.FIRST + 3;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	    	
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ID_NOTAME_TODOS, 0, R.string.menu_1_notame_todos);
        menu.add(0, MENU_ID_NOTAME_AMIGOS, 0, R.string.menu_1_notame_amigos);
        menu.add(0, MENU_ID_NUEVA_NOTA, 0, R.string.menu_1_nueva_nota);
        menu.add(0, MENU_ID_CONFIGURAR, 0, R.string.menu_1_configurar);
        return true;
    }
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		Intent i;
		switch (item.getItemId()){
		case MENU_ID_CONFIGURAR:
	        i = new Intent(this, NotameSettings.class);
	        startActivityForResult(i, ACTIVITY_CREATE);
	        break;
		case MENU_ID_NOTAME_TODOS:
	        i = new Intent(this, NotameView.class);
	        startActivityForResult(i, ACTIVITY_CREATE);
	        break;			
		case MENU_ID_NOTAME_AMIGOS:
	        i = new Intent(this, NotameFriendsView.class);
	        startActivityForResult(i, ACTIVITY_CREATE);
	        break;		
		case MENU_ID_NUEVA_NOTA:
	        i = new Intent(this, SendNoteView.class);
	        startActivityForResult(i, ACTIVITY_CREATE);
	        break;	
		}
		return true;
	}
    
	
	
}