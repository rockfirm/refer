package get.refer;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * 1.TabActivity
 * 2.TabActivity内跳转其他页动画
 * @author jin
 *
 */
public class MyTabActivity extends TabActivity {
	
	static boolean needUpDownAnimate;//决定是否使用上下动画
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_mytab);
		
		TabHost tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("0").setIndicator("AA", getResources().getDrawable(R.drawable.video_focus)).setContent(new Intent(this, GuideActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("1").setIndicator("BB", getResources().getDrawable(R.drawable.cache)).setContent(new Intent(this, MediaTestActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("2").setIndicator("CC", getResources().getDrawable(R.drawable.tool)).setContent(new Intent(this, MotionActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("3").setIndicator("DD", getResources().getDrawable(R.drawable.setting)).setContent(new Intent(this, WebViewActivity.class)));
		tabHost.setCurrentTab(0);
		
		tabHost = null;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(needUpDownAnimate){
			needUpDownAnimate = false;
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
		}
	}

}
