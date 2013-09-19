package get.refer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StandardListView extends Activity {

	private StandardAdapter adapter;

	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standard_listview);

		getData();
	}

	private void getData() {
		for (int i = 0; i < 20; ++i) {
			// list.add(new Person("man" + i, 20, "aaaa"));
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", "man" + i);
			map.put("age", "20");
			list.add(map);
			map = null;
		}
		initListView();
	}

	private void initListView() {
		ListView lv = (ListView) findViewById(R.id.list_view);
		adapter = new StandardAdapter();
		lv.setAdapter(adapter);
	}

	class StandardAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public StandardAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			Log.i("aa","getCount()="+list.size());//???
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			  System.out.println("1111getView " + position + " " + convertView); 
			ViewHolder holder = null;
			if (convertView == null) {
				Log.i("aa", "getView null=" + position);// ???
				convertView = mInflater.inflate(R.layout.inflate_listview, null);
				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				Log.i("aa", "getView not=" + position);// ???
				holder = (ViewHolder) convertView.getTag();
			}
			Log.i("aa", "name=" + list.get(position).get("name") + ";pos="
					+ position);// ???
			holder.text.setText(list.get(position).get("name"));
			return convertView;
		}

	}

	class ViewHolder {
		private TextView text;
	}

}

class Person {

	private String name;
	private int age;
	private String info;

	public Person(String name, int age, String info) {
		super();
		this.name = name;
		this.age = age;
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}