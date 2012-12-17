package android.chap6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;
	private Button buttonAct, buttonSer;
	private Button buttonAct2, buttonSer2;
	private Button buttonModify;
	private EditText editText;
	private String User_Action1 = "android.chap6.IntentExercise.UserAction1";
	private String User_Action2 = "android.chap6.IntentExercise.UserAction2";
	private static final String TAG = "wang";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		String s = "";
		Intent intent = getIntent();
		Log.d(TAG, "intent --> " + intent);
		Bundle bundle = new Bundle();
		bundle = intent.getExtras();
		Log.d(TAG, "bundle --> " + bundle);
		if (intent != null && bundle != null)
			s = bundle.getString("key");
		if (s.length() != 0) {
			Log.d(TAG, "textView --> " + textView);
			textView.setText(s);
			
		}

		// explicit intent.
		buttonAct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = editText.getText().toString();
				Intent intent = new Intent();
				// Bundle bundle = new Bundle();
				// bundle.putString("key", s);
				// intent.putExtras(bundle);
				intent.putExtra("name", s);
				// bundle.
				intent.setClass(MainActivity.this, ActivityOne.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});

		buttonSer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ServiceOne.class);
				startService(intent);
				MainActivity.this.finish();
			}
		});

		// implicit intent
		buttonAct2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(User_Action1);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});

		buttonSer2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(User_Action2);
				startService(intent);
				// MainActivity.this.finish();
			}
		});
		buttonModify.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = editText.getText().toString();
				Intent intent = new Intent();
				// Bundle bundle = new Bundle();
				// bundle.putString("key", s);
				// intent.putExtras(bundle);
				intent.putExtra("name", s);
				// bundle.
				intent.setClass(MainActivity.this, ActivityOne.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		// textView = (TextView) findViewById(R.id.)
		buttonAct = (Button) findViewById(R.id.bt1);
		buttonSer = (Button) findViewById(R.id.bt2);
		buttonAct2 = (Button) findViewById(R.id.bt3);
		buttonSer2 = (Button) findViewById(R.id.bt4);
		buttonModify = (Button) findViewById(R.id.bt5);
		editText = (EditText) findViewById(R.id.send_text);
		textView = (TextView) findViewById(R.id.textView2);
		Log.d(TAG, "init_textView --> " + textView);
	}

}
