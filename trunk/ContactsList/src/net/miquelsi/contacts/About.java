package net.miquelsi.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		Button button = (Button) findViewById(R.id.about_ok);
		
		button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                
                setResult(RESULT_OK);
                finish();
            }
          
        });
	}

}
