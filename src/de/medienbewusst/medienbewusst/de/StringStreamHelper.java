package de.medienbewusst.medienbewusst.de;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;

public class StringStreamHelper {
		
	public static InputStream getInputStreamFromUrl(String adresse) {
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		
		try {
			URL url = new URL(adresse);
			URLConnection conn = url.openConnection();
			Log.e("BOOOOOOM", "inputstream fast geladen 1");
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
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		
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
