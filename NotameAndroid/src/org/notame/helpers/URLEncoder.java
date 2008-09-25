/*
 * The source code packaged with this file is Free Software, Copyright (c) 2007
 * by Carlos Fernandez (carlos.fernandez at podsonoro.com).
 * It's licensed under the AFERO GENERAL PUBLIC LICENSE unless stated otherwise.
 * You can get copies of the licenses here:
 * 		http://www.affero.org/oagpl.html
 * AFFERO GENERAL PUBLIC LICENSE is also included in the file called "COPYING".
 */

package org.notame.helpers;

public class URLEncoder {
	/* 
	 * encode, obtained from:
	 * http://forum.java.sun.com/thread.jspa?threadID=341790&messageID=1408555
	 * 
	 * TODO: Check if nicer way to encode text to Notame
	 */
	public static String encode(String s)
	{
		if (s!=null) {
			StringBuffer tmp = new StringBuffer();
			int i=0;
			try {
				while (true) {
					int b = (int)s.charAt(i++);
					if ((b>=0x30 && b<=0x39) || (b>=0x41 && b<=0x5A) || (b>=0x61 && b<=0x7A)) {
						tmp.append((char)b);
					}
					else {
						tmp.append("%");
						if (b <= 0xf) tmp.append("0");
						tmp.append(Integer.toHexString(b));
					}
				}
			}
			catch (Exception e) {}
			return tmp.toString();
		}
		return null;
	}
}
