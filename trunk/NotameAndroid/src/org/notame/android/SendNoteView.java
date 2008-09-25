package org.notame.android;

import org.notame.android.api.NotaSender;
import org.notame.android.api.NotaSenderListener;
import org.notame.android.rms.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendNoteView extends Activity implements NotaSenderListener{

	private EditText m_etData;
	final Handler mHandler = new Handler();
	String username,key,url,data;
	
	final Runnable mNotaSent = new Runnable(){
		public void run() {
			notaSent(1);
		}
	};	
	
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.notame_sendnota);
		m_etData = (EditText) findViewById(R.id.notadata);
		
		
		Button confirmButton = (Button) findViewById(R.id.send);
		
		confirmButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view) {
				sendNota();
				//finish();
			}
		});
		
	}
	
	private void sendNota(){
		Settings settings = new Settings(this);
		username = settings.getUsername();
		key = settings.getKey();
		url = settings.getNotameSendUrl();
		data = m_etData.getText().toString();
		
		Thread t = new Thread() {
			public void run() {
				NotaSender sender = new NotaSender();
				sender.send(url, username, key, data);
				mHandler.post(mNotaSent);
			}
		};
		t.start();
				
	}
	public void notaSent(int status)
	{
		m_etData.setText("");
		finish();
	}
	public void error(String error) {
		Log.e("SendNoteView", "Error en:" + error);
		finish();
	}	
	
	
}
