package com.example.exercise7_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ShowActivity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setTextSize(24.5f);
		textView.setTextColor(Color.BLUE);
		setContentView(textView);
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		BookParcelable bookParcelable = intent.getParcelableExtra(MainActivity.PARCLE_KEY);
		StringBuilder stringBuilder = new StringBuilder();
		Log.d("test", "2. show values by the object of BookParcelable who geted by getParcelableExtra.");
		stringBuilder.append(bookParcelable.getAuthor());
		stringBuilder.append(", book name --> ");
		stringBuilder.append(bookParcelable.getBookName());
		stringBuilder.append(", book time --> ");
		stringBuilder.append(bookParcelable.getPublishTime());
		textView.setText(stringBuilder.toString());
	}

}
