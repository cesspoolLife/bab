package com.cesspoollife.qtbab;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkTask extends AsyncTask<String, Void, String> {

	public AsyncResponse delegate=null;

	@Override
	protected String doInBackground(String... params) {
		String URL = params[0];
		// TODO Auto-generated method stub
		String endResult;
		try {
			Document doc  = Jsoup.connect(URL).get();
			Elements tables = doc.select("table");
			Element contents = tables.get(11);
			Elements tds = contents.select("td");
			Element td = tds.first();
			endResult = td.html();
			endResult = endResult.replaceAll("<br[^>]*>", "\n");
			endResult = endResult.replaceAll("&nbsp;", "");
			endResult = endResult.replaceAll("&quot;", "\"");
			endResult = endResult.replaceAll("<!--[^>]*-->","");
		} catch (Exception e) {
			return null;
		}
		return endResult;
	}

	@Override
	protected void onPostExecute(String result) {
		delegate.processFinish(result);
	}

	public interface AsyncResponse {
	    void processFinish(String output);
	}

}