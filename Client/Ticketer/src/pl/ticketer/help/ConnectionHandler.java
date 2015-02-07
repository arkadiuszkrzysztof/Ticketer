package pl.ticketer.help;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import pl.ticketer.config.Settings;
import pl.ticketer.config.Ticketer;
import pl.ticketer.config.User;

import android.os.AsyncTask;

public class ConnectionHandler extends AsyncTask<String, String, String>{
	DefaultHttpClient defaultHttpClient;
	HttpPost httpPost;
	ArrayList<NameValuePair> requestParameters;
	
	public ConnectionHandler(String connectionURL, ArrayList<NameValuePair> requestParameters) {
		this.requestParameters = requestParameters;
		this.defaultHttpClient = new DefaultHttpClient();
		this.httpPost = new HttpPost(connectionURL);
	}
	
	
	@Override
	protected void onPreExecute(){
		requestParameters.add(new BasicNameValuePair("client", Ticketer.NAME));
		requestParameters.add(new BasicNameValuePair("version", Ticketer.VERSION));
		requestParameters.add(new BasicNameValuePair("user_id", User.ID));
		requestParameters.add(new BasicNameValuePair("language", Settings.LANGUAGE));
	}

	@Override
	protected String doInBackground(String... params) {
		InputStream inputStream = null;
		String jsonResult = "";
		
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(requestParameters));
			HttpResponse httpResponse = defaultHttpClient.execute(httpPost);           
		    HttpEntity httpEntity = httpResponse.getEntity();
	
		    inputStream = httpEntity.getContent();
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
		    StringBuilder stringBuilder = new StringBuilder();
	
		    String line = null;
		    while ((line = bufferedReader.readLine()) != null)
		    {
		        stringBuilder.append(line + "\n");
		    }
		    jsonResult = stringBuilder.toString();
		} 
		catch(ClientProtocolException clientProtocolException){
			clientProtocolException.printStackTrace();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		
		return jsonResult;
	}
}
