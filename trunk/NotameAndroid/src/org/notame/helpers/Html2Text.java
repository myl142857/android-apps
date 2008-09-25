/*
 * The source code packaged with this file is Free Software, Copyright (c) 2007
 * by Carlos Fernandez (carlos.fernandez at podsonoro.com).
 * It's licensed under the AFERO GENERAL PUBLIC LICENSE unless stated otherwise.
 * You can get copies of the licenses here:
 * 		http://www.affero.org/oagpl.html
 * AFFERO GENERAL PUBLIC LICENSE is also included in the file called "COPYING".
 */

package org.notame.helpers;

public class Html2Text {
	
	private int pos;  //position of cursor
	private int len;  //length of string (source)
	private String source;  //text to process
	 
	public Html2Text(){
	}
	public String getText(String source) {
		//System.out.println ("Html2Text: " + source);
		this.pos = 0;
		this.source = source;
		this.len = source.length();
		
		StringBuffer result = new StringBuffer();

		try {
			String text = null;
			while (pos < len) {
				//System.out.println("par: " + pos + "," + len);
				int c = source.charAt(pos);
				if (c == '<') //Found a tag
				{
					String tag = getTag();
					text = convertTag(tag);
				} else if (c == '&')
				{
					jumpTo(';');  //Get rid of special chars (TODO: add special chars)
				}
				else {
					if (c!='\n') text = "" + (char)c;
				}
			    pos++;
				result.append(text);
			}
		} catch (Exception e) {
			System.out.println("Exception parsing to text: " + e.toString());
			return "";
		}
		//System.out.println ("Html2Text result: " + result);
		return result.toString();
	}
	public String removeFrom (String source, String text){
		int i = source.indexOf(text);
		return source.substring(0,i);
	}
	private void jumpTo (char sc){
		while(pos < len) {
			int c = source.charAt(pos);
			if (c == sc) return;
			pos++;
		}
		return;
	}
	private boolean isTag (String tag, String tagint)
	{
		tag = tag.toLowerCase();
		String t1 = "<" + tagint.toLowerCase() + ">";
		String t2 = "<" + tagint.toLowerCase() + " ";
		return tag.startsWith(t1) || tag.startsWith(t2);
	}
	/*
	 * Tag to text
	 */
	private String convertTag(String tag) {
		//System.out.println("Convert tag: " + tag);
		String result = "";
		if (isTag(tag, "img")){
			int start = tag.indexOf("alt=\"");
			if (start != -1)
			{
				start += 5;
				int end = tag.indexOf("\"", start);
				result = tag.substring(start, end);
			}
		}else if (isTag (tag, "br")){
			result = "\n";
		}
		return result;
	}
	
	
	/*
	 * Get the tag in the actual position:
	 * pos: Actual position, it should be in the '<' char.
	 * returns the complete tag <a href="kjkj" ... />
	 */
	private String getTag(){
		int level = 1;
		boolean isText = false; // check if it is between " "
		StringBuffer result = new StringBuffer();
		result.append(source.charAt(pos));
		while(level > 0 && pos++ < len){
			int c = source.charAt(pos);
			if (c=='"') isText = !isText;
			
			if (c == '<' && !isText) level ++; else if (c == '>' && !isText) level --;
			result.append((char)c);
		}
		return result.toString();
	}
}
