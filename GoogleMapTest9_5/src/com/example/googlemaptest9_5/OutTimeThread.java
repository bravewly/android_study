package com.example.googlemaptest9_5;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.googlemaptest9_5.MainActivity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;

public class OutTimeThread extends Thread {


	@Override
	public void run() {
		// TODO Auto-generated method stub
		// super.run();
		Looper.prepare();
		Log.d(MainActivity.TAG, "threadProgressBar ");
		try {
			Thread.sleep(10000);
			Message message = new Message();
			message.what = MainActivity.MSG_BAR;
			MainActivity.handler.sendMessage(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Looper.loop();
	}

}
