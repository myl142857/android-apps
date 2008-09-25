/*
 * The source code packaged with this file is Free Software, Copyright (c) 2007
 * by Carlos Fernandez (carlos.fernandez at podsonoro.com).
 * It's licensed under the AFERO GENERAL PUBLIC LICENSE unless stated otherwise.
 * You can get copies of the licenses here:
 * 		http://www.affero.org/oagpl.html
 * AFFERO GENERAL PUBLIC LICENSE is also included in the file called "COPYING".
 */

package org.notame.android;

public class NotameFriendsView extends NotameView {
	
	protected static final String FRIENDS_ID_PARAM = "friends_of";
	
	public String getURLFeed(){
		/*System.out.println("Notame Friends:getURLFeed");
		return midlet.getSettings().getNotameFriendsUrl() + "?" 
   	 	+ NotameView.NUM_REQ_PARAM + "=" 
   	 	+ midlet.getSettings().getNumNotas() + "&"
   	 	+ NotameFriendsView.FRIENDS_ID_PARAM + "="
   	 	+ midlet.getSettings().getUsername();*/
		
		return settings.getNotameFriendsUrl() + "?" 
	 		+ NotameView.NUM_REQ_PARAM + "=" 
	 		+ settings.getNumNotas() + "&"
	 		+ NotameFriendsView.FRIENDS_ID_PARAM + "="
	 		+ settings.getUsername();
	} 
}
