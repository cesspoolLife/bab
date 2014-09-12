package com.cesspoollife.qtbab;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cesspoollife.qtbab.NetworkTask.AsyncResponse;

public class QTActivity extends Activity implements AsyncResponse {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qt);

		Date day = new Date();
		Long dayCount = ( day.getTime() + ( 3600 * 9 * 1000 ) ) / 86400 / 1000;
		int index = (int)(dayCount - 14202);

		String URL = "http://qtbab.com/bbs/zboard.php?id=BABSHARE&no="+String.valueOf(index);
		NetworkTask n = new NetworkTask();
		n.delegate = this;
		n.execute(URL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qt, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void processFinish(String output) {
		// TODO Auto-generated method stub
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				LinearLayout splash = (LinearLayout) findViewById(R.id.splash);
				ScrollView splash_main = (ScrollView) findViewById(R.id.splash_main);
				splash.setAnimation(AnimationUtils.loadAnimation( QTActivity.this, R.anim.disappear_to_right));
				splash_main.setAnimation(AnimationUtils.loadAnimation( QTActivity.this, R.anim.appear_to_left));
				splash.setVisibility(View.GONE);
			}
		};

		handler.sendEmptyMessageDelayed(0, 500);

		TextView contents = (TextView) findViewById(R.id.contents);
		contents.setText(output);
	}

}
