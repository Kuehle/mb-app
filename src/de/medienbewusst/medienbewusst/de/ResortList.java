package de.medienbewusst.medienbewusst.de;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.medienbewusst.medienbewusst.de.dummy.DummyContent.DummyItem;



public class ResortList {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Resort> ITEMS = new ArrayList<Resort>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, Resort> ITEM_MAP = new HashMap<String, Resort>();

	public int id;
	public String caption;
	public String url;

	public void Artikel(int id, String caption, String url) {
		this.id = id;
		this.caption = caption;
		this.url = url;
	}
	
    public static void addItem(Resort item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    
    public static void clear(){
    	ITEMS = new ArrayList<Resort>();
    	ITEM_MAP = new HashMap<String, Resort>();
    }
	
    public static class Resort {
        public String id;
        public String heading;
        public String url;

        public Resort(String i, String heading, String url) {
            this.id = i;
            this.heading = heading;
            this.url = url;
        }

        @Override
        public String toString() {
            return heading;
        }
    }
    
	static {
		addItem(new Resort("1", "Fernsehen", "http://medienbewusst.de/ressort/fernsehen"));
		addItem(new Resort("2", "Digitale Spiele", "http://medienbewusst.de/ressort/computer-und-videospiele"));
		addItem(new Resort("3", "Internet", "http://medienbewusst.de/ressort/internet"));
		addItem(new Resort("4", "Kino", "http://medienbewusst.de/ressort/kino"));
		addItem(new Resort("5", "Mobile Medien", "http://medienbewusst.de/ressort/handy"));
		addItem(new Resort("6", "Musik und Hörbücher", "http://medienbewusst.de/ressort/musik-und-hoerbuecher"));
		addItem(new Resort("7", "Bücher", "http://medienbewusst.de/ressort/buecher"));
		addItem(new Resort("8", "Spezial", "http://medienbewusst.de/ressort/medienbewusst-spezial"));
	}

	@Override
	public String toString() {
		return this.caption;
	}

}
