package android.chap6;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;

public class ServiceOne extends Service {
	private static final String TAG = "wang";
	private Button buttonServiceBack;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d(TAG, "Service One被显式调用");
		
		super.onCreate();
//
//		for (int i = 0;;i++) {
//			while (i % 100000 == 0) {
//				Log.d(TAG, "service");
//			}
//		}

		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
