package org.notame.android;


import org.notame.android.rms.Settings;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotameSettings extends Activity {

	private EditText m_etUsername;
	private EditText m_etAPIKey;
	private EditText m_etNumItems;
	
	private Settings m_oSettings;

	
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.notame_settings);
		m_etUsername = (EditText) findViewById(R.id.username);
		m_etAPIKey = (EditText) findViewById(R.id.apikey);
		m_etNumItems = (EditText) findViewById(R.id.numnotes);
		
		
		Button confirmButton = (Button) findViewById(R.id.confirm);
		
		m_oSettings = new Settings(this);
		populateFields();
		confirmButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}
		});
		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (m_oSettings == null){
			m_oSettings = new Settings(this);
		}
		populateFields();
	}	
	private void populateFields(){
		m_etUsername.setText(m_oSettings.getUsername());
		m_etAPIKey.setText(m_oSettings.getKey());
		m_etNumItems.setText(Integer.toString( (int) m_oSettings.getNumNotas()));
		
	}
	/*@Override
	protected void onFreeze(Bundle outState) {
		super.onFreeze(outState);
		//outState.putLong(Notepadv3.KEY_ROW_ID, rowId);
		
	}*/
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		saveState();
		m_oSettings.close();
		m_oSettings=null;
	}	
	private void saveState(){
		String username = m_etUsername.getText().toString();
		String apikey = m_etAPIKey.getText().toString();
		long numitems = (long) Integer.parseInt(m_etNumItems.getText().toString());
		
		m_oSettings.setUsername(username);
		m_oSettings.setAPIKey(apikey);
		m_oSettings.setNumNotas(numitems);
		m_oSettings.saveSettings();
	}

}
