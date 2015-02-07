package pl.ticketer.activity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import pl.ticketer.R;
import pl.ticketer.config.Settings;
import pl.ticketer.config.User;
import pl.ticketer.help.ConnectionHandler;
import pl.ticketer.help.JSONParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void loginButtonAction(View view){
		TextView labelError = (TextView)findViewById(R.id.label_error);
		
		labelError.setText("");
		
		String result = "";
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		EditText loginInput = (EditText)findViewById(R.id.input_username);
		EditText passwordInput = (EditText)findViewById(R.id.input_password);
		
		parameters.add(new BasicNameValuePair("action","login"));
		parameters.add(new BasicNameValuePair("username", loginInput.getText().toString()));
		parameters.add(new BasicNameValuePair("password", passwordInput.getText().toString()));
		
		try {
			result = new ConnectionHandler(Settings.SERVER_URL, parameters).execute().get();
		} catch (InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		} catch (ExecutionException executionException) {
			executionException.printStackTrace();
		}
		
		if(JSONParser.getAttr(result, "error").contains("T000")){
			User.ID = JSONParser.getAttr(result, "user};ID");
			User.FullName = JSONParser.getAttr(result, "user};FullName");
			
			Intent userDashboard = new Intent(getApplicationContext(), UserDashboard.class);
            startActivity(userDashboard);
		}
		else
			labelError.setText("User not recognized.\nTry again or contact your administrator.");
	}
}
