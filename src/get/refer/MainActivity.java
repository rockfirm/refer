package get.refer;

import get.refer.function.AnimationActivity;
import get.refer.function.AutoDataActivity;
import get.refer.function.FileUploadActivity;
import get.refer.function.GetPhoneInfoActivity;
import get.refer.function.OutLoginActivity;
import get.refer.function.QuickMarkActivity;
import get.refer.function.SlideDeleteIOSActivity;
import get.refer.opensouce.SimpleSlidingMenuActivty;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.baidu.mobstat.StatService;

/**
 * example for android
 * @author JinZhenkun
 * @since 2013/6/14 create
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//disabled title
		setContentView(R.layout.activity_main);
//		startService(new Intent(this, TestServerWithActivity.class));
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void eventListen(View target){
		switch(target.getId()){
		case R.id.horizontalListView://ListView for horizontal
			startActivity(new Intent(this, HorizontalListViewActivity.class));
			break;
		case R.id.test_ui_memory://test
			startActivity(new Intent(this, UiMemoryActivity.class));
			break;
		case R.id.view_pager://show image as ad
			startActivity(new Intent(this, ViewPagerActivity.class));
			break;
		case R.id.view_flipper:
			startActivity(new Intent(this, GuideActivity.class));
			break;
		case R.id.tab_activity:
			startActivity(new Intent(this, MyTabActivity.class));
			break;
		case R.id.stardand_listview:
			startActivity(new Intent(this, StandardListView.class));
			break;
		case R.id.web_view://WebView load html
			startActivity(new Intent(this, WebViewActivity.class));
			break;
		case R.id.get_phone_info:
			startActivity(new Intent(this, GetPhoneInfoActivity.class));
			break;
		case R.id.java_api://Java Api
			startActivity(new Intent(this, JavaApiActivity.class));
			overridePendingTransition(R.anim.zoom_out, R.anim.zoom_in);
			break;
		case R.id.common_widgets:
			startActivity(new Intent(this, CommonWidgetsActivity.class));
			break;
		case R.id.java_manual:
			break;
		case R.id.file_upload:
			startActivity(new Intent(this, FileUploadActivity.class));
			break;
		case R.id.qucik_mark://二维码扫描
			startActivity(new Intent(this, QuickMarkActivity.class));
			break;
		case R.id.ios_list_del://仿IOS ListView滑动删除
			startActivity(new Intent(this, SlideDeleteIOSActivity.class));
			break;
		case R.id.out_login:
			startActivity(new Intent(this, OutLoginActivity.class));
			break;
		case R.id.list_view_auto_data:
			startActivity(new Intent(this,AutoDataActivity.class));
			break;
		case R.id.animate_example:
			startActivity(new Intent(this,AnimationActivity.class));
			break;
		case R.id.media_test:
			startActivity(new Intent(this, MediaTestActivity.class));
			break;
		case R.id.test_shape:
			startActivity(new Intent(this, ShaderEffect.class));
			break;
		case R.id.event_motion://事件/手势
			startActivity(new Intent(this, MotionActivity.class));
			break;
		case R.id.simple_sliding_menu://简单的SlidingMenu
			startActivity(new Intent(this, SimpleSlidingMenuActivty.class));
			break;
		
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

}
