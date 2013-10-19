package get.refer;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AlarmService extends Service {

	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {
			Toast.makeText(AlarmService.this, "alarm time", Toast.LENGTH_LONG).show();
			handler.postDelayed(runnable, 60000);
			Calendar calendar = Calendar.getInstance();
			String time = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE)+"-"+calendar.get(Calendar.HOUR)+"-"+calendar.get(Calendar.MINUTE)+"-"+calendar.get(Calendar.SECOND);
			File f = new File(Environment.getExternalStorageDirectory()+"/1006/"+time+".txt");
			if(!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("aa","onCreate");//????
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("aa","onStartCommand");//????
		return super.onStartCommand(intent, flags, startId);
	}
	
	

}
