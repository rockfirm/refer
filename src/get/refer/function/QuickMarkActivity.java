package get.refer.function;
import get.refer.CameraTestActivity;
import get.refer.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class QuickMarkActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_quick_mark);
		findViewById(R.id.begin_scan).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(QuickMarkActivity.this, CameraTestActivity.class),0);
			}
		});
		findViewById(R.id.copy).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ClipboardManager cb = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
				TextView t = (TextView) findViewById(R.id.text);
				cb.setText(t.getText());
				Toast.makeText(QuickMarkActivity.this, "已复制到粘贴版", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 0 && data != null){
			String res = data.getStringExtra("result");
			if(res == null || "".equals(res)){
				res = "扫描失败，请重试";
			}
			TextView t = (TextView) findViewById(R.id.text);
			t.setText(res);
			findViewById(R.id.copy).setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}
