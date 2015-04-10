package de.medienbewusst.medienbewusst.de;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.medienbewusst.medienbewusst.de.dummy.DummyContent.DummyItem;



public class ArtikelList {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Artikel> ITEMS = new ArrayList<Artikel>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, Artikel> ITEM_MAP = new HashMap<String, Artikel>();

	public int id;
	public String caption;
	public String url;

	public void Artikel(int id, String caption, String url) {
		this.id = id;
		this.caption = caption;
		this.url = url;
	}
	
    public static void addItem(Artikel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    
    public static void clear(){
    	ITEMS = new ArrayList<Artikel>();
    	ITEM_MAP = new HashMap<String, Artikel>();
    }
	
    public static class Artikel {
        public String id;
        public String heading;
        public String url;

        public Artikel(String i, String heading, String url) {
            this.id = i;
            this.heading = heading;
            this.url = url;
        }

        @Override
        public String toString() {
            return heading;
        }
    }

	@Override
	public String toString() {
		return this.caption;
	}

}
