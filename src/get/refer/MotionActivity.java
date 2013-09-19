package get.refer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.webkit.WebView;

public class MotionActivity extends Activity{
	
	private GestureDetector gd;
	WebView w;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		w = new WebView(this);
//		w.setHorizontalScrollBarEnabled(false);
//		w.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		w.getSettings().setJavaScriptEnabled(true);
		w.loadUrl("http://www.baidu.com");
//		w.loadUrl("http://yskingdata.bbs.shiwan.com/dataListOne/?start=1");
		setContentView(w);
		
		gd = new GestureDetector(this, new MyGestureListener());
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("aa","event.getAction"+event.getAction()+
				";getActionIndex="+event.getActionIndex()+";count="+event.getPointerCount());
		return super.onTouchEvent(event);
	}
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {    //注意这里不能用ONTOUCHEVENT方法，不然无效的
            gd.onTouchEvent(ev);
            if(w != null)
            	w.onTouchEvent(ev);//这几行代码也要执行，将webview载入MotionEvent对象一下，况且用载入把，不知道用什么表述合适
            return false;
    }

	
	private class MyGestureListener extends SimpleOnGestureListener {
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                float velocityY) {
			Log.i("aa","ysk :velocityX="+velocityX+";e1.getX()"+e1.getX()+";e2.getX()="+e2.getX()+";len="+(e2.getX()-e1.getX()));//???
			if(velocityX > 1000 && (e2.getX()-e1.getX() > 200)){
				finish();
			}
            return super.onFling(e1, e2, velocityX, velocityY);
        }
	}

}
