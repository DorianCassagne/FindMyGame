package me.dcal.gamemanager.model;

public class Element {
	public String name;
	public String url;
	public static Element urlToElem(String url) {
		Element e = new Element();
		e.name = urlToName(url);
		e.url=url;
		return e;
		
	}
	public static String urlToName(String url) {
		int start = url.lastIndexOf('/');
		return url.substring(start+1, url.length());
	}
	

	public String toString() {
		return name + " *-* "+ url;
	}
	
	public String getName() {
		return this.name;
	}
			
}
