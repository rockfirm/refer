package get.refer.opensouce;

import get.refer.R;
import get.refer.widget.SlideHolder;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SimpleSlidingMenuActivty extends Activity {

	private SlideHolder slideHolder;
	private String[] list;
	private MySimpleAdapter belowAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_simple_slidingmenu);
		slideHolder = (SlideHolder) findViewById(R.id.slideHolder);
//		slideHolder.setAlwaysOpened(true);
		slideHolder.setDirection(SlideHolder.DIRECTION_RIGHT);

		initBelow();
	}

	private void initBelow() {
		list = getResources().getStringArray(R.array.cate);
		if(list.length == 0){
			return ;
		}
		ListView lv = (ListView) findViewById(R.id.meun_below);
		belowAdapter = new MySimpleAdapter();
		belowAdapter.setSelectItem(0);
		lv.setAdapter(belowAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				belowAdapter.setSelectItem(position);
				belowAdapter.notifyDataSetChanged();
			}
		});
	}
	
	class MySimpleAdapter extends BaseAdapter{

		private int selectItem;
		
		@Override
		public int getCount() {
			return list.length;
		}

		@Override
		public Object getItem(int position) {
			return list[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public  void setSelectItem(int selectItem) {
	        this.selectItem = selectItem;
	    }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView t = null;
			Log.i("aa","position="+position);//???
			if(convertView == null){
				t = new TextView(SimpleSlidingMenuActivty.this);
				t.setTextSize(18);
				t.setPadding(20, 20, 20, 20);
				convertView = t;
			}else{
				Log.i("aa","asdfg");//???
				t = (TextView) convertView;
			}
			t.setText(list[position]);
			t.setBackgroundColor(position == selectItem ? Color.WHITE :Color.parseColor("#ffeeeeee"));
			return convertView;
		}
		
	}
}
