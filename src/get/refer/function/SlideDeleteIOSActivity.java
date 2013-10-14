package get.refer.function;

import get.refer.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 仿IOS滑动删除
 * @author JinZhenkun
 * @since 2013/10/9
 *
 */
public class SlideDeleteIOSActivity extends Activity {

	private ListView lv;
	private String data[] = {"aa","bb","cc","dd","ee","ff","gg","hh","ii","jj","kk","ll","mm","nn","oo","pp","ww","xx","yy","zz"};
	private MyAdapter adapter;
	private int flyingPos = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_delete_ios);
		lv = (ListView) findViewById(R.id.list_view);
		adapter = new MyAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "onClick pos="+position, Toast.LENGTH_SHORT).show();
			}
		});
		
		lv.setOnTouchListener(new View.OnTouchListener() {
			float x, y, upX, upY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					x = event.getX();
					y = event.getY();
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					upX = event.getX();
					upY = event.getY();
					int pos1 = ((ListView)v).pointToPosition((int)x, (int)y);
					int pos2 = ((ListView)v).pointToPosition((int)upX, (int)upY);
					if(pos1 == pos2 && Math.abs(x - upX) > 10){// && Math.abs(y - upY) < 20
						Log.i("aa","show: pos="+pos1+";distanceX="+Math.abs(x - upX)+";distanceY="+Math.abs(y - upY)+";flyingPos="+flyingPos);//???
						if(flyingPos == pos1){
							flyingPos = -1;
						}else{
							flyingPos = pos1;
						}
					}else{
						flyingPos = -1;
					}
					adapter.notifyDataSetChanged();
				}
				
				return false;
			}
		});
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return data.length;
		}

		@Override
		public Object getItem(int position) {
			return data[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder h;
			if(convertView == null){
//				Log.i("aa","null pos="+position);//???
				h = new Holder();
				convertView = getLayoutInflater().inflate(R.layout.inflate_list_del, null);
				h.t = (TextView) convertView.findViewById(R.id.text);
				h.del = (ImageView) convertView.findViewById(R.id.del);
				h.del.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.i("aa","del onClick");
					}
				});
				convertView.setTag(h);
			}else{
//				Log.i("aa","has Holder pos="+position);//???
				h = (Holder) convertView.getTag();
			}
			h.t.setText(data[position]);
			h.del.setVisibility(flyingPos == position ? View.VISIBLE : View.GONE);
			return convertView;
		}

	}

	private class Holder{
		TextView t;
		ImageView del;
	}
	
}
