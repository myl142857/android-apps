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
	
	private static final int NEW_ID = Menu.FIRST + 1;
    private static final int ABOUT_ID = Menu.FIRST;
    
    private EditText userName;
    private TextView greetingLabel;
    private ImageView image;
    private Button cleanButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button okButton = (Button) findViewById(R.id.button);
        
        cleanButton = (Button) findViewById(R.id.buttonClean);
        cleanButton.setVisibility(Button.INVISIBLE);
        
        userName = (EditText) findViewById(R.id.entry);
        
        greetingLabel = (TextView) findViewById(R.id.greetingLabel);
        greetingLabel.setVisibility(EditText.INVISIBLE);
        
        image = (ImageView) findViewById(R.id.image);
        image.setVisibility(ImageView.INVISIBLE);
        
        okButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	greetingLabel.setText("Hola " + userName.getText().toString());
            	greetingLabel.setVisibility(EditText.VISIBLE);
            	image.setVisibility(ImageView.VISIBLE);
            	cleanButton.setVisibility(Button.VISIBLE);
            	
            	/*new AlertDialog.Builder(miquel.this).setMessage("Hola " + userName.getText().toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            		public void onClick(DialogInterface dialog, int whichButton) {
            		}
            		}).show();*/
            }
          
        });
        
        cleanButton.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View view){
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
        //menu.add(0, NEW_ID, 0, R.string.menu_new);
        menu.add(0, ABOUT_ID, 0,  R.string.menu_about);
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case NEW_ID:
            
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
    
}