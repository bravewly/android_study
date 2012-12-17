package android.chap6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityOne extends Activity {

	private Button buttonActivityBack;
	private Button buttonActivityModify;
	private TextView textView;
	private EditText editTextModify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1);
		textView = (TextView) findViewById(R.id.receive_text);
		Intent intent = getIntent();
//		Bundle bundle = new Bundle();
//		bundle = intent.getExtras();
//		String s = bundle.getString("key");
		String s = intent.getStringExtra("name");
		textView.setText(s);
//
//		for (int i = 0;i < 1000000000; i++) {
//			while (i % 100000 == 0) {
//				Log.d("wang", "activity");
//			}
//		}

		buttonActivityBack = (Button) findViewById(R.id.btn_back);
		buttonActivityModify = (Button) findViewById(R.id.btn_modify);
		editTextModify = (EditText) findViewById(R.id.modify);
		
		buttonActivityBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent().setClass(ActivityOne.this,
						MainActivity.class));
				ActivityOne.this.finish();

			}
		});
		
		buttonActivityModify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = editTextModify.getText().toString();
				Bundle bundle = new Bundle();
				bundle.putString("key", s);
				Intent intent = new Intent();
				intent.putExtras(bundle);
				startActivity(intent.setClass(ActivityOne.this,
						MainActivity.class));
				ActivityOne.this.finish();
				
			}
		});
		

		// intent.getst
	}

}
