package org.notame.android;

import org.notame.android.api.RSSReader;
import org.notame.android.rms.Settings;
import org.notame.helpers.Html2Text;
import org.notame.rss.RssFeed;
import org.notame.rss.RssItems;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

//Ver ejemplo un ejemplo de ListActivity en:
// http://code.google.com/android/samples/ApiDemos/src/com/google/android/samples/view/List4.html

public class NotameView extends ListActivity{

	private boolean b_isNotasAlready = false;
	protected static final String NUM_REQ_PARAM = "rows";
	private NotaAdapter notaAdapter;
	protected Settings settings;
	final Handler mHandler = new Handler();
	private RssFeed mFeed;
	
	final Runnable mFeedParsed = new Runnable(){
		public void run() {
			feedParsed();
		}
	};
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		settings = new Settings(this);
		
		notaAdapter = new NotaAdapter(this);
		
		setListAdapter(notaAdapter);
		if (!b_isNotasAlready) setFeed();
		//notaAdapter.addNota(new NotaView(this,"test","test2"));
		//notaAdapter.notifyAll(true);
	}
	public void setFeed() {
		//ProgressDialog.show(NotameView.this,null, "Please wait while loading...", true, true);
		
		this.notaAdapter.addNota(new NotaView(this,"", "No hay notas"));
		//this.notaAdapter.notifyChange(true);
		this.notaAdapter.notifyDataSetChanged();
		 
		Log.d("NotameView", "Setting feed");
		
		Thread t = new Thread() {
			public void run() {
				RSSReader reader = new RSSReader();
				mFeed = reader.readFeed(getURLFeed());
				mHandler.post(mFeedParsed);
			}
		};
		t.start();
	   	//RSSReader reader = new RSSReader();
	   	//reader.setListener(this);
	   	 
	   	//reader.readFeed(getURLFeed());
	}
	
	public String getURLFeed(){
		System.out.println("NotameView:GetURLFeed");
		return settings.getNotameUrl() + "?" 
   	 		+ NotameView.NUM_REQ_PARAM + "=" 
   	 		+ settings.getNumNotas();
	}
	
	//public void feedParsed(RssFeed feed){
	public void feedParsed(){
		 //this.deleteAll();
		
		//feed.dump();
		 RssFeed feed = mFeed;
		 RssItems items = null;
		 int num = 0;
		 this.notaAdapter.deleteAll();
		 
		 if (feed != null){
			 items = feed.getItems();
		 	 num = items.count();
		 }
		 	 
		 Log.d("NotameView","NotameView: Adding elements");
		 Html2Text texter = new Html2Text();
		 
		 for (int i=0; i<num; i++)
		 {
			 String creator = items.getItemAt(i).getDc_creator();
			 String description = items.getItemAt(i).getDescription();
			 
			 Log.d("NotameView","Adding Nota:" + i + " de:" + creator);
			 
			 description = texter.getText(description); //extract text from html
			 description = texter.removeFrom(description, "autor:");  //erase autor from desc
			 this.notaAdapter.addNota(new NotaView(this,creator,description));
			 
			 //this.append(new StringItem(creator, description));
		 }
		 if (num > 0) b_isNotasAlready = true;
		 Log.d("NotameView", "Content has changed");
		 //onContentChanged();
		 this.notaAdapter.notifyDataSetChanged();
		 setSelection(0);
		 
		 Log.d("NotameView", "onContentChanged, called");
		 System.out.println("Notameview: Elements added");	
			
	}
	public void exception(java.io.IOException e) {
		//this.notaAdapter.deleteAll();
		this.notaAdapter.addNota(new NotaView(this,"Error", "Imposible leer las notas")); //Chapuza
		System.out.println("NotameView: Exception thrown" + e.toString());
	}	
	

}
