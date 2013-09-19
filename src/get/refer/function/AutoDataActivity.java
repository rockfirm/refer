package get.refer.function;

import get.refer.R;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AutoDataActivity extends Activity{

	private ArrayAdapter<String> adapter;
	private int page = 1;
	private ArrayList<String> data;
	private Handler handler = new Handler();
	private ListView lv;
	private ProgressDialog pd;
	private boolean isLoading;
	private TextView footer;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		lv = new ListView(this);
		setContentView(lv);
		
		pd = new ProgressDialog(this);
		pd.setMessage(getString(R.string.load_data));
		pd.setCancelable(false);
		pd.show();
		
		getData();
	}
	
	private void getData(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(page == 5){
					handler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "没有啦", Toast.LENGTH_SHORT).show();
							if(footer != null && lv.getFooterViewsCount() > 0){
//								lv.setAdapter(adapter);
								lv.removeFooterView(footer);
							}
							isLoading = false;//实时性不强的可以把这行注释掉
						}
					});
					return ;
				}
				if(data == null){
					data = new ArrayList<String>();
				}
				for(int i = 0; i < 20; ++i){
					data.add("page  "+page+"  content  "+ ((page -1)*20+i));
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						if(page == 1 && pd != null){
							pd.dismiss();
							pd = null;
						}
						flushView();
					}
				});
			}
		}).start();
	}
	
	private void flushView(){
		if(adapter == null && data != null && data.size() > 0){
			footer = new TextView(this);
			footer.setGravity(Gravity.CENTER);
			footer.setPadding(0, 10, 0, 10);
			footer.setText(R.string.get_more);
			lv.addFooterView(footer);
			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data);
			lv.setAdapter(adapter);
			lv.setOnScrollListener(new OnScrollListener() {
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					if(!isLoading && scrollState == OnScrollListener.SCROLL_STATE_IDLE){
						if(view.getLastVisiblePosition() == view.getCount() - 1){
							isLoading = true;
							footer.setText(R.string.load_data);
							getData();
						}
					}
				}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
				}
			});
		}else{
			adapter.notifyDataSetChanged();
		}
		
		page ++;
		footer.setText(R.string.get_more);
		isLoading = false;
	}
}
