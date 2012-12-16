package com.example.googlemaptest9_5;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends MapActivity {
	static final String TAG = "WangMainActivtiy";
	private final int HISTORY_DIALOG = 0;
	private final int MODE_DIALOG = 1;
	private int initLatitude = 45;
	private int initLongitude = 116;
	static MapController mapController;
	static MapView mapView;
	static Bitmap bitmap;
	static Handler handler;
	static GeoPoint geoPoint;
	static Geocoder geocoder;
	static EditText editTextInput;
	private ImageButton imageButtonSearch;
	private ImageButton imageButtonHistory;
	private ImageButton imageButtonMode;
	private boolean tempB = false;
	static final int MSG_SHOW = 0;
	static final int MSG_BAR = 1;
	static Context context;
	Thread threadOutTime, threadProgressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

		context = MainActivity.this;
		mapController = mapView.getController();
		mapController.setZoom(14);
		mapController.animateTo(geoPoint);
		mapView.setBuiltInZoomControls(true);
		final Thread tempThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d(TAG, "I am --> " + Thread.currentThread().getName());
			}
		});

		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		Log.d(TAG, "mainThread");
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == MSG_SHOW) {
					progressBar.setVisibility(View.GONE);

				} else if (msg.what == MSG_BAR) {
					progressBar.setVisibility(View.GONE);
					Log.d(TAG, "网络不好，请稍候搜索");
					Toast.makeText(MainActivity.this, "网络不好，请稍候搜索",
							Toast.LENGTH_SHORT).show();
				}
				// super.handleMessage(msg);
			}

		};

		imageButtonSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				progressBar.setVisibility(View.VISIBLE);
				threadProgressBar = new ProgressThread(mapView);
				threadOutTime = new OutTimeThread();
				geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
				if (!threadProgressBar.isAlive()) {
					threadProgressBar.start();
				}

				if (!threadOutTime.isAlive()) {
					threadOutTime.start();
				}
			}
		});
		imageButtonHistory.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG,
						"threadOutTime's name is --> "
								+ threadOutTime.getName());
				Log.d(TAG,
						"threadOutTime's state is --> "
								+ threadOutTime.getState());

				Log.d(TAG, "threadProgressBar's name is --> "
						+ threadProgressBar.getName());
				Log.d(TAG, "threadProgressBar's state is --> "
						+ threadProgressBar.getState());
				Log.d(TAG, "tempThread name is --> " + tempThread.getName());
				Log.d(TAG, "tempThread state is --> " + tempThread.getState());
				Log.d(TAG, "tempThread isAlive() --> " + tempThread.isAlive());
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.ballon);
		editTextInput = (EditText) findViewById(R.id.editTextInput);
		imageButtonSearch = (ImageButton) findViewById(R.id.imageSearch);
		imageButtonHistory = (ImageButton) findViewById(R.id.imageHistory);
		imageButtonMode = (ImageButton) findViewById(R.id.imageMapMode);
		mapView = (MapView) findViewById(R.id.mapView);
//		threadProgressBar = new ProgressThread();
		threadOutTime = new OutTimeThread();
		geoPoint = new GeoPoint((int) (initLatitude * 1e6),
				(int) (initLongitude * 1e6));

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
