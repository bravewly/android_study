package android.chap6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityTwo extends Activity {

	private Button buttonBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		buttonBack = (Button) findViewById(R.id.btn_back2);
		buttonBack.setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent =  new Intent();
						startActivity(intent.setClass(ActivityTwo.this, MainActivity.class));
					}
				});
	}

}
