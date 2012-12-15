package com.example.googlemaptest9_4;

import java.util.List;

import com.example.googlemaptest9_4.R.id;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MapActivityDemo extends MapActivity {

	private static final String TAG = "WangMapActivityDemo";
	private static Button buttonGetLocaion;
	private static EditText editTextLongitude, editTextLatitude;
	static Bitmap bitmap;
	private MapView mapView;
	private MapController mapController; // 地图控制器，设置地图显示地点，放大倍数等参数
	private double longitudeD = 116.3975 * 1E6, latitudeD = 39.9083 * 1E6;
	ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// progressBar = new ProgressBar(this);
		// progressBar.incrementProgressBy(1);
		// progressBar

		setContentView(R.layout.activity_map_activity_demo);
		buttonGetLocaion = (Button) findViewById(R.id.button1);
		editTextLatitude = (EditText) findViewById(R.id.editTextLatitude);
		editTextLongitude = (EditText) findViewById(R.id.editTextLongitude);
		buttonGetLocaion.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String longitudeS, latitudeS;
				longitudeS = editTextLongitude.getText().toString().trim();
				if (longitudeS.length() == 0) {
					longitudeS = 116.3975 + "";
				}

				latitudeS = editTextLatitude.getText().toString().trim();
				if (latitudeS.length() == 0) {
					latitudeS = 39.9083 + "";
				}

				try {
					longitudeD = Double.parseDouble(longitudeS);
					latitudeD = Double.parseDouble(latitudeS);

				} catch (NumberFormatException e) {
					// TODO: handle exception
					Toast.makeText(MapActivityDemo.this,
							"对不起，您输入经度或纬度格式不正确(只能输入数字)", Toast.LENGTH_SHORT)
							.show();
					editTextLongitude.setText("");
					editTextLatitude.setText("");
					return;
				}

				GeoPoint geoPoint = new GeoPoint((int) (latitudeD * 1e6),
						(int) (longitudeD * 1e6));
				mapController.animateTo(geoPoint);
				List<Overlay> overlays = mapView.getOverlays();
				BalloonOverlay balloonOverlay = new BalloonOverlay(geoPoint,
						"\n\n" + "欢迎您的使用。\n" + "地理坐标为：\n" + "经度:" + longitudeS
								+ "\n" + "纬度:" + latitudeS, mapView);
				balloonOverlay.showWindow = true;
				overlays.add(balloonOverlay);
				// 隐藏调出的输入法
				Log.d(TAG, "getSystemService(INPUT_METHOD_SERVICE) --> "
						+ getSystemService(INPUT_METHOD_SERVICE));
				Log.d(TAG, "MapActivityDemo.this.getCurrentFocus() --> "
						+ MapActivityDemo.this.getCurrentFocus());
				Log.d(TAG,
						"MapActivityDemo.this.getCurrentFocus().getWindowToken() --> "
								+ MapActivityDemo.this.getCurrentFocus()
										.getWindowToken());
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(MapActivityDemo.this
								.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.balloon);

		// LocationManager 用于获取当前位置信息和卫星信息
		LocationManager locationManager = (LocationManager) this
				.getSystemService(LOCATION_SERVICE);

		// 位置变化监听器
		LocationListener locationListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				// 当位置变化时触发

				 updateWhenNewLocaion(location);
			}
		};

		// 添加位置变化监听器
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 5, locationListener);

		// 对地图初始化
		mapView = (MapView) findViewById(R.id.myMapView);
		mapView.setBuiltInZoomControls(true);
		mapController = mapView.getController();
		mapController.setZoom(14);
		GeoPoint geoPoint = new GeoPoint((int) latitudeD, (int) longitudeD);
		// GeoPoint geoPoint = new GeoPoint((int)(39.9083*1E6), //纬度
		// (int)(116.3975*1E6));
		mapController.animateTo(geoPoint);
	}

	protected void updateWhenNewLocaion(Location location) {
		// TODO Auto-generated method stub
		String result;
		if (location != null) {
			String stringLatitude, stringLongitude;
//			int intLongitude = (int) location.getLongitude();
//			int intLatitude = (int) location.getLatitude();
			double intLongitude = location.getLongitude();
			double intLatitude = location.getLatitude();
			Log.d(TAG, "location.getLatitude() --> " + location.getLatitude());
			Log.d(TAG, "location.getLongitude() --> " + location.getLongitude());

			stringLongitude = String.valueOf(intLongitude);
			stringLatitude = String.valueOf(intLatitude);
			result = "longitude: " + stringLongitude + " latitude:"
					+ stringLatitude;
			editTextLongitude.setText(stringLongitude);
			editTextLatitude.setText(stringLatitude);
			List<Overlay> overlays = mapView.getOverlays();

			GeoPoint geoPoint = new GeoPoint((int) (intLatitude * 1e6),
					(int) (intLongitude * 1e6));
			mapController.animateTo(geoPoint);
			BalloonOverlay balloonOverlay = new BalloonOverlay(geoPoint, "\n\n"
					+ "欢迎您的使用。\n" + "地理坐标为：\n" + "经度:" + stringLongitude + "\n"
					+ "纬度:" + stringLatitude, mapView);
			balloonOverlay.showWindow = true;
			overlays.add(balloonOverlay);

		} else {
			result = "Sorry! The GPS does not work. Please check your setting";
			Toast.makeText(MapActivityDemo.this, result, Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
