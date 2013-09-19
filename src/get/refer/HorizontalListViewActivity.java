package get.refer;

import get.refer.widget.HorizontalListView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.loopj.android.image.SmartImageView;

public class HorizontalListViewActivity extends Activity{
	
	private ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private HorizontalAdapt horizontalAdapt;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			horizontalAdapt.notifyDataSetChanged();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		HorizontalListView listView = new HorizontalListView(this, null);
		listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 300));
		setContentView(listView);
		horizontalAdapt = new HorizontalAdapt();
		listView.setAdapter(horizontalAdapt);
		new Thread(new Runnable() {
			@Override
			public void run() {
				getDate();
			}
		}).start();
	}
	
	private void getDate(){
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = (HttpURLConnection) new URL("http://dmv.iphone.shiwan.com/").openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			if(conn.getResponseCode() == 200){
				in = conn.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while((len = in.read(b)) != -1){
					out.write(b, 0, len);
				}
				String data = new String(out.toByteArray());
				out = null;
				JSONObject jsonObject = new JSONObject(data);
				data = null;
				if(jsonObject.getInt("error_code") != 0){
					return ;
				}
				JSONArray jsonArray = jsonObject.getJSONArray("list");
				jsonObject = null;
				JSONArray tmp = null;
				Map<String, String> map = null;
				for(int i = 0, j = jsonArray.length(); i < j; ++i){
					tmp = jsonArray.getJSONObject(i).getJSONArray("info");
					for(int m = 0, n = tmp.length(); m < n; ++m){
						map = new HashMap<String, String>();
						map.put("title", tmp.getJSONObject(m).getString("title"));
						map.put("img", tmp.getJSONObject(m).getString("img"));
						list.add(map);
					}
				}
				map = null;
				tmp = null;
				jsonArray = null;
				handler.sendEmptyMessage(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn != null){
				conn.disconnect();
			}
			if(in != null){
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class HorizontalAdapt extends BaseAdapter{
		
		private LayoutInflater inflater = getLayoutInflater();

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			Holder holder = null;
			if(arg1 == null){
				arg1 = inflater.inflate(R.layout.horizontal_inflate, null);
				holder = new Holder();
				holder.text = (TextView) arg1.findViewById(R.id.text);
				holder.img = (SmartImageView) arg1.findViewById(R.id.img);
				arg1.setTag(holder);
			}else {
				holder = (Holder) arg1.getTag();
			}
			holder.text.setText(list.get(arg0).get("title"));
			holder.img.setImageUrl(list.get(arg0).get("img"));
			return arg1;
		}
		
		private class Holder{
			TextView text;
			SmartImageView img;
		}
		
	}
	
	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}
}
