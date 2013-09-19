package get.refer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestServerWithActivity extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onCreate() {
		
		//杀死进程后service内的线程也会被杀死
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while(true){
					Log.i("aa","log "+i);
					i++;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("aa","TestServerWidthActivity onStartCommand");
		return startId;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.i("aa","TestServerWidthActivity onDestroy");
	}

}
