package get.refer;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mobstat.StatService;

/**
 * 基于ViewPager的轮播图
 * @author JinZhenkun
 * @since 2013/6/14
 *
 */
public class ViewPagerActivity extends Activity{
	
	private ViewPager pager;
	private int[] list = {R.drawable.t1, R.drawable.t2, R.drawable.t3};
	private int pos;
	private MessageHandler handler;
	private FocusThread focusThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		handler = new MessageHandler(this);
		
		FrameLayout frame = new FrameLayout(this);
		frame.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, 300));
		setContentView(frame);
		
		pager = new ViewPager(this);
		pager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,300));//must set height otherwise can't visiable
		frame.addView(pager);
		pager.setAdapter(new ImagePagerAdapter());
    	pager.setOnPageChangeListener(new MyPageChangeListener());
		
		//添加小圆点
    	LinearLayout pointWrap = new LinearLayout(this);
    	pointWrap.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 300));
    	pointWrap.setGravity(Gravity.BOTTOM);
    	
    	LinearLayout pointLinear = new LinearLayout(this);
    	pointLinear.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    	pointLinear.setId(R.string.app_name);
    	pointLinear.setGravity(Gravity.CENTER);
        for (int i = 0, j = list.length; i < j; ++i) {
        	ImageView pointView = new ImageView(this);
        	if(i==0){
        		pointView.setBackgroundResource(R.drawable.feature_point_cur);
        	}else{
        		pointView.setBackgroundResource(R.drawable.feature_point);
        	}
        	pointLinear.addView(pointView);
        	pointView = null;
		}
        
        pointWrap.addView(pointLinear);
        frame.addView(pointWrap);
        pointLinear = null;
        pointWrap = null;
        frame = null;
        
        focusThread = new FocusThread();
        focusThread.start();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		StatService.onResume(this);
		if(list != null && list.length > 0 && focusThread == null){
  			focusThread = new FocusThread();
  			focusThread.start();
  		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPause(this);
		if(focusThread != null){
  			focusThread.stopRun();
  			focusThread = null;
  		}
	}
	
	private class ImagePagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			pos = position;
			position = position % list.length;
			if(position > list.length){
				return null;
			}
			
			final int imgth = position;
			
			ImageView img = new ImageView(ViewPagerActivity.this);
			img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 300));
			img.setImageResource(list[position]);
			img.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Toast.makeText(ViewPagerActivity.this, "this is " + imgth + "th image", Toast.LENGTH_SHORT).show();
				}
			});
			((ViewPager) view).addView(img, 0);
			return img;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

	}
	
	private class MyPageChangeListener implements OnPageChangeListener {
        private int oldPosition = 0;
  
        public void onPageSelected(int position) {
        	position = position % list.length;
        	LinearLayout pointLinear = (LinearLayout) findViewById(R.string.app_name);
        	View view = pointLinear.getChildAt(oldPosition);
        	View curView = pointLinear.getChildAt(position);
        	if(view!=null&& curView!=null){
        		ImageView pointView = (ImageView)view;
        		ImageView curPointView = (ImageView)curView;
        		pointView.setBackgroundResource(R.drawable.feature_point);
        		curPointView.setBackgroundResource(R.drawable.feature_point_cur);
        		oldPosition = position;
        	}
        	pointLinear = null;
        }  
  
        public void onPageScrollStateChanged(int arg0) {
        }
  
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }
	
	private class FocusThread extends Thread{
		private boolean stop;
		
		@Override
		public void run(){
			while(!stop){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}
		
		protected void stopRun(){
			stop = true;
		}
	}
	
	private static class MessageHandler extends Handler{
		private WeakReference<ViewPagerActivity> tmp;
		
		public MessageHandler(ViewPagerActivity activity){
			tmp = new WeakReference<ViewPagerActivity>(activity);
		}
		
		@Override
		public void handleMessage(Message msg){
			ViewPagerActivity activity = tmp.get();
			switch(msg.what){
			case 0:
				activity.pager.setCurrentItem(activity.pos);
				break;
			}
		}
	}
	

}
