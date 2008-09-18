package net.miquelsi.android;

import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.TextView;

public class About extends Activity {

	//private TextView mText;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		//mText.setText(findViewById(R.string.about_text).toString());
		//mText = (TextView) findViewById(R.string.about_text);
		Button button = (Button) findViewById(R.id.about_ok);
		
		button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                
                setResult(RESULT_OK);
                finish();
            }
          
        });
	}

}
