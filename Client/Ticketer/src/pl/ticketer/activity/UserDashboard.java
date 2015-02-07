package pl.ticketer.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.ticketer.R;
import pl.ticketer.config.User;
import pl.ticketer.help.FragmentAnimation;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserDashboard extends Activity{
	private boolean isSmartActive = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_dashboard);
		
		TextView statusBar = (TextView)findViewById(R.id.statusBar);
		statusBar.setText(R.string.status_dashboard);
		
		TextView greeter = (TextView)findViewById(R.id.greeter);
		greeter.setText(User.FullName);
		
		
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		TextView dateTime = (TextView)findViewById(R.id.dateTime);
		dateTime.setText(days[dayOfWeek-1] + "\n" + dateFormat.format(date));
	}
	
	
	public void userIconAction(View view){
		TextView textView = (TextView) findViewById(R.id.smartShorts);
		
		ImageView[] images = new ImageView[5];
		
		images[0] = (ImageView)findViewById(R.id.smartHome);
		images[1] = (ImageView)findViewById(R.id.smartEvents);
		images[2] = (ImageView)findViewById(R.id.smartValidate);
		images[3] = (ImageView)findViewById(R.id.smartSettings);
		images[4] = (ImageView)findViewById(R.id.smartLogout);
		
		FragmentAnimation animation = null;
        if(isSmartActive) {
            animation = new FragmentAnimation(textView, 1000, 1, UserDashboard.this, images);
            isSmartActive = false;
        } else {
            animation = new FragmentAnimation(textView, 1000, 0, UserDashboard.this, images);
            isSmartActive = true;
        }
        
        textView.startAnimation(animation);
	}
}
