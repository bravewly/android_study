package com.example.exercise7_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends Activity {
	private TextView textViewShow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);
		textViewShow = (TextView) findViewById(R.id.textViewDisplayInfo);
		StringBuilder stringBuilder = new StringBuilder();
		Intent intent = getIntent();
		Employee employee = (Employee) intent.getSerializableExtra(MainActivity.KEY);
		stringBuilder.append("员工信息是：");

		stringBuilder.append("姓名-->" + employee.getName() + 
				",工号--> " + employee.getNo() + 
				",职位--> " + employee.getPost() +
				",工龄--> " + employee.getWorkyears());
		
		
//		stringBuilder.append(intent.getStringExtra("key_name"));
//		stringBuilder.append(", ");
//		stringBuilder.append(intent.getIntExtra("key_no", 0));
//		stringBuilder.append(", ");
//		stringBuilder.append(intent.getStringExtra("key_post"));
//		stringBuilder.append(", ");
//		stringBuilder.append(intent.getIntExtra("key_workyears", 0));
		textViewShow.setText(stringBuilder.toString());

	}

}
