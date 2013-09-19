package get.refer.function;
import get.refer.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AnimationActivity extends Activity implements OnClickListener{

	private int cacheStatus = 0;//标识title animation停留位置 0=cached | 1=caching
	private TextView mMenu;
    private Button mButton;
    private ImageButton b1;
    private ImageButton b2;
    private TranslateAnimation mShowAnimation;
    private TranslateAnimation mHideAnimation;
    
    private TranslateAnimation show1;
    private TranslateAnimation hide1;
    private LinearLayout ll;
    private boolean isShow;
    
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_animation);
		initView();
        initAnimation();
	}
	
	private void initView() {  
        mMenu = (TextView) findViewById(R.id.menu);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        b1 = (ImageButton) findViewById(R.id.b1);
        b2 = (ImageButton) findViewById(R.id.b2);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        isShow = false;
        ll = (LinearLayout) findViewById(R.id.content);
        ll.setVisibility(View.GONE);
        
        //title animation
        findViewById(R.id.cached).setOnClickListener(this);
        findViewById(R.id.caching).setOnClickListener(this);
    }  
  
    private void initAnimation() {  
        // 从自已-1倍的位置移到自己原来的位置  
        mShowAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,  
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,  
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,  
                0.0f);
        mHideAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,  
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,  
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,  
                -1.0f);
        mShowAnimation.setDuration(500);
        mHideAnimation.setDuration(500);
        
        show1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF, 0.0f,
        		Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,0.0f);
        show1.setDuration(200);
        
        hide1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 1.0f,
        		Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,0.0f);
        hide1.setDuration(200);
  
    }
    
    @Override
    public void onBackPressed(){
    	super.onBackPressed();
    	overridePendingTransition(R.anim.pull_down_in, R.anim.pull_down_out);
    }
  
    @Override  
    public void onClick(View v) {  
    	switch(v.getId()){
    	case R.id.button:
    		if (isShow) {  
                isShow = false;
                mMenu.startAnimation(mHideAnimation);
                mMenu.setVisibility(View.GONE);
            } else {  
                isShow = true;
                mMenu.startAnimation(mShowAnimation);
                mMenu.setVisibility(View.VISIBLE);
            }
    		break;
    	case R.id.b1:
    		ll.startAnimation(hide1);
    		ll.setVisibility(View.GONE);
    		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					b2.setVisibility(View.VISIBLE);
				}
			},200);
    		break;
    	case R.id.b2:
    		ll.startAnimation(show1);
    		ll.setVisibility(View.VISIBLE);
    		b2.setVisibility(View.GONE);
    		break;
    	case R.id.cached://缓存完成
    		if(cacheStatus == 0){
    			return ;
    		}
    		TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
    		anim.setDuration(200);
    		TextView t = (TextView) findViewById(R.id.status);
    		t.setAnimation(anim);
    		anim = null;
    		t.setText(R.string.cached);
    		t = null;
    		cacheStatus = 0;
    		//loading data
    		break;
    	case R.id.caching://正在缓存
    		if(cacheStatus == 1){
    			return ;
    		}
    		TranslateAnimation anim1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, 1, 1.0f, 1, 0.0f, 1, 0.0f);
    		anim1.setDuration(200);
    		anim1.setFillAfter(true);
    		TextView t1 = (TextView) findViewById(R.id.status);
    		t1.setAnimation(anim1);
    		anim1 = null;
    		t1.setText(R.string.caching);
    		t1 = null;
    		cacheStatus = 1;
    		//loading data
    		break;
    	}
    }  
}
