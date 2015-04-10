package de.medienbewusst.medienbewusst.de;

import java.io.InputStream;
import org.apache.commons.lang3.StringEscapeUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.medienbewusst.medienbewusst.de.ArtikelList.Artikel;

/**
 * A list fragment representing a list of Artikel. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link ArtikelDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ArtikelListFragment extends ListFragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String id);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ArtikelListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);

		
		
		Bundle extras = getActivity().getIntent().getExtras();
		String url = extras.getString("resort");
		Log.e("resort url", url);
		
		//String url = "http://medienbewusst.de/ressort/fernsehen";
		try {
			setNewArrayList(url);
		} catch (Exception e) {
			e.printStackTrace();
			ArtikelList.addItem(new Artikel("1000", "failed",
					"http://www.fail.de"));
		}

		ArrayAdapter<Artikel> adapter = new ArrayAdapter<Artikel>(
				getActivity(), android.R.layout.simple_list_item_1,
				ArtikelList.ITEMS);
		setListAdapter(adapter);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Artikel item = (Artikel)list.get(position);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.

		mCallbacks.onItemSelected(ArtikelList.ITEMS.get(position).id);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

	public void setNewArrayList(String url) {
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		
		ArtikelList.clear();
		Log.e("get new array list", "started");
		// ArrayList<Artikel> list = new ArrayList<Artikel>();

		String linkurl = "", linkcaption = "", linkcaption1 ="";
		String html = getHtmlFromUrl(url);
		Log.e("get new array list", "0");
		
		Log.e("get new array list", "1");
		html = html.substring(html.indexOf("<div class=\"breadcrumb\">"));
		Log.e("html", html);

		int j2 = 0;
		String[] artikellist = getArtikelList(url);
		for (String test : artikellist) {
			if(j2>0){
			//Log.e("string part", test);
			linkurl = test.substring(test.indexOf("href=\"") + 6,
					test.indexOf("\" rel=\""));
			linkcaption1 = test.substring(
					test.indexOf("bookmark\">") + 10,
					test.indexOf("</a>"));
			linkcaption = StringEscapeUtils.unescapeHtml4(linkcaption1);
			Log.e("url", linkurl);
			Log.e("caption", linkcaption);
			Artikel current = new Artikel(""+j2, linkcaption, linkurl);
			ArtikelList.addItem(current);			
			}
			j2++;
		}
		;

		System.out.print("getNewArrayList worked");
		Log.e("get new array list", "worked");

	}

	public static String getHtmlFromUrl(String url) {
		String html = "";
		Log.e("get html from url", "started");
		InputStream stream;
		try {
			stream = StringStreamHelper.getInputStreamFromUrl(url);
		} catch (Exception e) {
			Log.e("html download", "Fehler beim Laden des html1");
		}
		stream = StringStreamHelper.getInputStreamFromUrl(url);
		try {
			html = StringStreamHelper.convertStreamToString(stream);
		} catch (Exception e) {
			Log.e("html download", "Fehler beim Laden des html2");
			e.printStackTrace();
		}

		return html;
	}

	public static Artikel getSampleArtikel() {
		return new Artikel("2", "google", "http://www.google.de");
	}

	public static String[] getArtikelList(String url) {
		String html = getHtmlFromUrl(url);
		String[] result = html.split("<h1>");

		return result;
	}
}
