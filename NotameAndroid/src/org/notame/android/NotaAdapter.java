package org.notame.android;

import java.util.Vector;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NotaAdapter extends BaseAdapter{
	private Context mContext;
	private Vector<NotaView> notas;
	
	public NotaAdapter(Context context){
		mContext = context;
		//registerContentObserver(mContext);
		notas = new Vector<NotaView>();
		//addNota(new NotaView(mContext,"no","notas1"));
		//addNota(new NotaView(mContext,"no","notas2"));
	}
	public int getCount() {
		return this.notas.size();
	}
	public Object getItem(int position) {
		return position;
	}
	public long getItemId(int position){
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent){
		if ((position < 0) || (position >= this.notas.size())) return null;
		return (NotaView) this.notas.elementAt(position);
	}
	public void addNota(NotaView nota){
		notas.addElement(nota);
		//notifyChange();
	}
	public void deleteAll(){
		notas.clear();
		//notifyChange();
	}

}
