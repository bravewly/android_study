package com.example.exercise7_1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private static final String TAG = "wangActivity";
	private EditText editTextName;
	private EditText editTextNo;
	private EditText editTextPost;
	private EditText editTextWorkyears;
	private Button buttonOk, buttonParcel;
	static final String KEY = "employee";
	static final String PARCLE_KEY = "parcel";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		setBackground();
		buttonParcel.setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						parcelTest();
					}

					private void parcelTest() {
						// TODO Auto-generated method stub
						BookParcelable bookParcelable = new BookParcelable();
						Intent intent = new Intent();
						Log.d("test", "1. set values by the object of BookParcelable.");
						bookParcelable.setAuthor("Hero_wly");
						bookParcelable.setBookName("Think in Java");
						bookParcelable.setPublishTime(2012);
						Bundle bundle = new Bundle();
						bundle.putParcelable(PARCLE_KEY, bookParcelable);
						intent.putExtras(bundle);
						intent.setClass(MainActivity.this, ShowActivity2.class);
						startActivity(intent);
						MainActivity.this.finish();
					}
				});
		buttonOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = "";
				String post = "";
				String noString = "";
				String workyearsString = "";
				int no = 0;
				int workyears = 0;
				name = editTextName.getText().toString().trim();
				post = editTextPost.getText().toString().trim();
				noString = editTextNo.getText().toString().trim();
				workyearsString = editTextWorkyears.getText().toString().trim();
				if (noString.length() < 1 || noString.length() > 3) {
					Toast.makeText(MainActivity.this, "工号只能是1，2或3位数",
							Toast.LENGTH_SHORT).show();
					return;
				}
				for (int i = 0; i < noString.length(); i++) {
					if ((int) noString.charAt(i) < 48
							|| (int) noString.charAt(i) > 57) {
						Toast.makeText(MainActivity.this, "工号只能是0~9的数字",
								Toast.LENGTH_SHORT).show();
						return;
					}

				}

				if (workyearsString.length() < 1
						|| workyearsString.length() > 2) {
					Toast.makeText(MainActivity.this, "工龄只能是1或2位数",
							Toast.LENGTH_SHORT).show();
					return;
				}
				for (int i = 0; i < workyearsString.length(); i++) {
					if ((int) workyearsString.charAt(i) < 48
							|| (int) workyearsString.charAt(i) > 57) {
						Toast.makeText(MainActivity.this, "工龄只能是0~9的数字",
								Toast.LENGTH_SHORT).show();
						return;
					}

				}
				no = Integer.parseInt(noString);
				workyears = Integer.parseInt(workyearsString);
				Employee employee = new Employee(name, post, no, workyears);

				Bundle bundle = new Bundle();
				bundle.putSerializable(KEY, employee);
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(getApplication(), ShowActivity.class);
				startActivity(intent);
				MainActivity.this.finish();

			}
		});
	}

	private void setBackground() {
		// TODO Auto-generated method stub
		GradientDrawable gradientDrawable = new GradientDrawable(
				Orientation.TL_BR, new int[] { Color.BLUE, Color.BLACK });
		getWindow().setBackgroundDrawable(gradientDrawable);
	}

	private void init() {
		// TODO Auto-generated method stub
		editTextName = (EditText) findViewById(R.id.editTextname);
		editTextNo = (EditText) findViewById(R.id.editTextNo);
		editTextPost = (EditText) findViewById(R.id.editTextPost);
		editTextWorkyears = (EditText) findViewById(R.id.editTextWork);
		buttonOk = (Button) findViewById(R.id.buttonOk);
		buttonParcel = (Button) findViewById(R.id.buttonParcel);

	}

}
