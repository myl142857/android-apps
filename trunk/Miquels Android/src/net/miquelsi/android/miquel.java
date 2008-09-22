package net.miquelsi.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class miquel extends Activity {
	
	private static final int ACTIVITY_ABOUT=0;
	
	/** IDs for the menu entries (next would be Menu.FIRST+1, Menu.FIRST+2, etc) */
    private static final int ABOUT_ID = Menu.FIRST;
    
    /** Layout components */
    private Button okButton;
    private EditText userName;
    private TextView greetingLabel;
    private ImageView image;
    private Button cleanButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /** Select the layout for this activity */
        setContentView(R.layout.main);
        
        /** We get the components from the layout */
        okButton 		= (Button) 		findViewById(R.id.button);
        userName 		= (EditText) 	findViewById(R.id.entry);
        greetingLabel 	= (TextView) 	findViewById(R.id.greetingLabel);
        image 			= (ImageView) 	findViewById(R.id.image);
        cleanButton 	= (Button) 		findViewById(R.id.buttonClean);
        
        /** Some components will not be shown at first */
        greetingLabel.setVisibility(EditText.INVISIBLE);
        image.setVisibility(ImageView.INVISIBLE);
        cleanButton.setVisibility(Button.INVISIBLE);
        
        /** Action for the click on the "OK" button */
        okButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	/** Set the text for the greeting label */
            	greetingLabel.setText("Hola " + userName.getText().toString());
            	
            	/** Make components visible */
            	greetingLabel.setVisibility(EditText.VISIBLE);
            	image.setVisibility(ImageView.VISIBLE);
            	cleanButton.setVisibility(Button.VISIBLE);
            	
            	/** Code for showing an alert dialog */
            	/*new AlertDialog.Builder(miquel.this).setMessage("Hola " + userName.getText().toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            		public void onClick(DialogInterface dialog, int whichButton) {
            		}
            		}).show();*/
            }
          
        });
        
        /** Action for the click on "Clear" button */
        cleanButton.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View view){
        		/** We just reset all the fields and make some components invisible again */
        		userName.setText("");
        		greetingLabel.setText("");
        		greetingLabel.setVisibility(EditText.INVISIBLE);
        		image.setVisibility(ImageView.INVISIBLE);
        		cleanButton.setVisibility(Button.INVISIBLE);
        	}
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        /** Add an entry to the menu */
        menu.add(0, ABOUT_ID, 0,  R.string.menu_about);
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case ABOUT_ID:
            showAbout();
            return true;
        }
       
        return super.onMenuItemSelected(featureId, item);
    }

	private void showAbout() {
		startActivityForResult(new Intent(this, About.class), ACTIVITY_ABOUT);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
    
}