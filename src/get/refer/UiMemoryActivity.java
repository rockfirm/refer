package get.refer;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * (1) UI used memory test
 * (2) registerReceiver listen wifi to 2/3G
 * @author JinZhenkun
 *
 */
public class UiMemoryActivity extends Activity{
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION) && UtilTools.checkNetworkInfo(UiMemoryActivity.this) == 2){
//        		Toast.makeText(UiMemoryTest.this, "net cahnge to 2/3G", Toast.LENGTH_SHORT).show();
        	}
        }
    };
	
	@Override
	protected void onCreate(Bundle a){
		super.onCreate(a);
		setContentView(R.layout.ui_memory_test);
		
		findViewById(R.id.play).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edit = (EditText) findViewById(R.id.edit);
				String text = edit.getText().toString();
				if(!"".equals(text)){
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.parse(text), "video/*");
					startActivity(intent);
				}
			}
		});
		
		findViewById(R.id.play_baidu).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edit = (EditText) findViewById(R.id.edit);
				String text = edit.getText().toString();
				if(!"".equals(text)){
					Intent it = new Intent(Intent.ACTION_VIEW);
					Uri uri = Uri.parse(text);
		            it.setClassName("com.baidu.cyberplayer.engine", "com.baidu.cyberplayer.engine.PlayingActivity");
		            it.setData(uri); 
		         	startActivity(it);
				}
			}
		});
		
		findViewById(R.id.testApk).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.1.101/file/Dmvideo_1_5_1006.apk")));
			}
		});
		
		
		
		//
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					HttpURLConnection conn = (HttpURLConnection) new URL("http://lol.data.shiwan.com/getSubjectList/?tag_id=211&page=1&num=3").openConnection();
					conn.setConnectTimeout(5000);
					if(conn.getResponseCode() == 200){
						String a = new String(UtilTools.read(conn.getInputStream()));
						Log.i("aa","a="+a);//???
						JSONObject obj = new JSONObject(a).getJSONObject("list");
						 Iterator<?> it = obj.keys();
						 while(it.hasNext()){
							Log.i("aa","it="+it.next().toString());
							
						 }
						
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		unregisterReceiver(mReceiver);
	}
	
}