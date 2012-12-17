package android.chap6;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceTwo extends Service {

	public ServiceTwo() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d("ServiceTwo", "ServiceTwo 被隐式调用。。。");
		super.onCreate();
		
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//		// TODO Auto-generated method stub
//		return super.onStartCommand(intent, flags, startId);
//	}

}
