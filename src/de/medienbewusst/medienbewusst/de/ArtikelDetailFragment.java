package de.medienbewusst.medienbewusst.de;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import de.medienbewusst.medienbewusst.de.dummy.DummyContent;

/**
 * A fragment representing a single Artikel detail screen.
 * This fragment is either contained in a {@link ArtikelListActivity}
 * in two-pane mode (on tablets) or a {@link ArtikelDetailActivity}
 * on handsets.
 */
public class ArtikelDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ArtikelList.Artikel mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArtikelDetailFragment() {
    }


    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ArtikelList.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artikel_detail, container, false);
        
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
        
        // Show the dummy content as text in a TextView.
        
        if (mItem != null) {
            //((WebView) rootView.findViewById(R.id.artikel_detail)).loadUrl(mItem.url);
        	String html = "";
        	InputStream stream = getInputStreamFromUrl(mItem.url);
        	try {
				html = convertStreamToString(stream);
			} catch (Exception e) {
				// TODO: handle exception
			}
        	
        	String div = html.substring(html.indexOf("<div class=\"postarea\">"), html.indexOf("aehnliche Beitraege ausgeben")-5);
        	((WebView) rootView.findViewById(R.id.artikel_detail)).loadDataWithBaseURL(null, div, "text/html", "utf-8", null);
        }

        return rootView;
    }
    
	public static InputStream getInputStreamFromUrl(String adresse) {
		try {
			URL url = new URL(adresse);
			URLConnection conn = url.openConnection();
			Log.e("BOOOOOOM", "inputstream fast geladen");
			InputStream dummy = conn.getInputStream();
			Log.e("BOOOOOOM", "inputstream geladen");
			return dummy;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("BOOOOOOM", "inputstream NICHT geladen");
		return null;
	}

	public static String convertStreamToString(InputStream is)
			throws IOException {
		Log.e("BOOOOOOM", "0");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		Log.e("BOOOOOOM", "1");
		StringBuilder sb = new StringBuilder();
		Log.e("BOOOOOOM", "2");
		String line = null;
		Log.e("BOOOOOOM", "3");
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		Log.e("BOOOOOOM", "4");
		is.close();
		Log.e("BOOOOOOM", "5");
		return sb.toString();
	}
}
