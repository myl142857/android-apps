package org.notame.android;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NotaView extends LinearLayout {
    public NotaView(Context context, String title, String words) {
        super(context);

        this.setOrientation(VERTICAL);

        mTitle = new TextView(context);
        mTitle.setText(title);
        //mTitle.setTextColor(65000);
        mTitle.setTextSize(11);
        addView(mTitle, new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

        mBody = new TextView(context);
        mBody.setText(words);
        //mBody.setTextColor(65000);
        mBody.setTextSize(8);
        addView(mBody, new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setBody(String words) {
    	mBody.setText(words);
    }

    private TextView mTitle;
    private TextView mBody;
}
