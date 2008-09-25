/*
 * The source code packaged with this file is Free Software, Copyright (c) 2007
 * by Carlos Fernandez (carlos.fernandez at podsonoro.com).
 * It's licensed under the AFERO GENERAL PUBLIC LICENSE unless stated otherwise.
 * You can get copies of the licenses here:
 * 		http://www.affero.org/oagpl.html
 * AFFERO GENERAL PUBLIC LICENSE is also included in the file called "COPYING".
 */

package org.notame.android.api;


public interface NotaSenderListener { 
	public static int OK = 1; //Note send correctly
	
	public void notaSent(int status);
	public void error (String error);
}
