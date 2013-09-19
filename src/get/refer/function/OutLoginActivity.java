package get.refer.function;

import get.refer.R;
import android.app.Activity;
import android.os.Bundle;

public class OutLoginActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_out);
		
	}
	
	public void onResume(){
		super.onResume();
//		LinearLayout ll = new LinearLayout(this);
//		ll.setOrientation(LinearLayout.VERTICAL);
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
//		lp.setMargins(0, 20, 0, 0);
//		
//		Button sina = new Button(this);
//		lp.setMargins(60, 20, 60, 0);
//		sina.setLayoutParams(lp);
//		sina.setText("sina login");
//		ll.addView(sina);
//		sina = null;
//		
//		Button qq = new Button(this);
//		qq.setLayoutParams(lp);
//		qq.setText("qq login");
//		ll.addView(qq);
//		qq = null;
//		
//		lp = null;
//		
//		setContentView(ll);
//		ll = null;
		
	}
}
