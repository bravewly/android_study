package com.example.googlemaptest9_5;

import java.io.IOException;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;

import android.location.Address;
import android.location.Geocoder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ProgressThread extends Thread {
	MainActivity mainActivity = new MainActivity();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// super.run();
		Looper.prepare();
		Log.d(MainActivity.TAG, "threadOutTime ");

		double latitudeD, longitudeD;
		String locationName = MainActivity.editTextInput.getText().toString()
				.trim();

		Geocoder geocoder = MainActivity.geocoder;
		Log.d(MainActivity.TAG, "am I logout?1");
		try {
			// 容易超时
			List<Address> addresses = geocoder.getFromLocationName(
					locationName, 1);

			Message message = new Message();
			message.what = MainActivity.MSG_SHOW;
			MainActivity.handler.sendMessage(message);

			Log.d(MainActivity.TAG, "am I logout?2");
			boolean isExist = false; // 判断所输入地点是否已经输入过
			BalloonOverlay balloonOverlay = null;
			if (addresses.size() > 0) {
				Address tempAddress = addresses.get(0);
				Log.d(MainActivity.TAG, "am I logout?3");
				latitudeD = tempAddress.getLatitude();
				longitudeD = tempAddress.getLongitude();
				Log.d(MainActivity.TAG, "longitudeD --> " + longitudeD);
				MainActivity.geoPoint = new GeoPoint((int) (latitudeD * 1e6),
						(int) (longitudeD * 1e6));
				List<Overlay> overlays = MainActivity.mapView.getOverlays();
				BalloonOverlay currentBalloonOverlay = null;
				for (Overlay overlay : overlays) {
					if (overlay instanceof BalloonOverlay) {
						// 关闭已经存在的景点的窗口
						// 注意：
						// 下边一行的代码没有意义，overlay的类型仍然是BallonOverlay.
						// overlay = (BalloonOverlay) overlay;
						BalloonOverlay tempOverlay = (BalloonOverlay) overlay;
						tempOverlay.showWindow = false;
						if (MainActivity.geoPoint.getLatitudeE6() == tempOverlay.mGeoPoint
								.getLatitudeE6()
								&& MainActivity.geoPoint.getLongitudeE6() == tempOverlay.mGeoPoint
										.getLongitudeE6()) {
							isExist = true;
							tempOverlay.showWindow = true;
							currentBalloonOverlay = tempOverlay;
							overlays.remove(tempOverlay);
							overlays.add(tempOverlay);
						}
					}
				}
				if (!isExist) {
					BalloonOverlay balloonOverlay2 = new BalloonOverlay(
							MainActivity.geoPoint, "坐标为: \n " + "经度："
									+ longitudeD + " \n 纬度：" + latitudeD,
							locationName, MainActivity.mapView);
					balloonOverlay2.showWindow = true;
					overlays.add(balloonOverlay2);
				}

				Log.d(MainActivity.TAG, "mainAcitvity --> " + mainActivity);
				Log.d(MainActivity.TAG, "mainActivity.mapController --> "
						+ mainActivity.mapController);
				Log.d(MainActivity.TAG, "MainActivity.geoPoint --> "
						+ MainActivity.geoPoint);
				if (mainActivity.mapController != null) {
					mainActivity.mapController.animateTo(MainActivity.geoPoint);
					Log.d(MainActivity.TAG, " is not null.");
				}

				else
					return;
			} else {
				// Toast..
				Toast.makeText(MainActivity.context, "对不起，您要找的景点没有找到！",
						Toast.LENGTH_SHORT).show();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Looper.loop();
	}
}
