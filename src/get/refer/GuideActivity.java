package get.refer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

public class GuideActivity extends Activity{

	ArrayList<View> list;
	ImageView[] imageViews;
	static String has;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = getLayoutInflater();
		has = "test static";
		list = new ArrayList<View>();
		list.add(getView(R.drawable.guide_1));
		list.add(getView(R.drawable.guide_2));
		list.add(getView(R.drawable.guide_3));

		imageViews = new ImageView[list.size()];
		ViewGroup main = (ViewGroup) inflater.inflate(R.layout.activity_guide, null);
		// group是R.layou.main中的负责包裹小圆点的LinearLayout.
		ViewGroup group = (ViewGroup) main.findViewById(R.id.viewGroup);

		for (int i = 0; i < list.size(); i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			imageView.setPadding(10, 0, 10, 0);
			imageViews[i] = imageView;
			if (i == 0) {
				// 默认进入程序后第一张图片被选中;
				imageViews[i].setBackgroundResource(R.drawable.feature_point_cur);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.feature_point);
			}
			group.addView(imageView);
		}
		group = null;

		setContentView(main);
		ViewPager viewPager = (ViewPager) main.findViewById(R.id.viewPager);
		main = null;
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyListener());
		
		findViewById(R.id.goNow).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});
	}
	
	private View getView(int dr){
		ImageView img = new ImageView(this);
		img.setImageResource(dr);
		return img;
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(list.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(list.get(arg1));
			return list.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

		@Override
		public void finishUpdate(View arg0) {
		}
	}

	class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.feature_point_cur);
				if (arg0 != i) {
					imageViews[i].setBackgroundResource(R.drawable.feature_point);
				}
			}
			if(arg0 == (list.size() - 2)){
				findViewById(R.id.goNow).setVisibility(View.GONE);
			}else if(arg0 == (list.size()-1)){
				findViewById(R.id.goNow).setVisibility(View.VISIBLE);
			}
		}

	}
}
