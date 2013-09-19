package get.refer.function;

import get.refer.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.baidu.mobstat.StatService;

public class GetPhoneInfoActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//disabled title
		setContentView(R.layout.activity_phone_info);
		
		findViewById(R.id.button1).setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			String res = getCpuInfo();
			if(res != null || !"".equals(res)){
				TextView t = (TextView) findViewById(R.id.textView1);
				String s = (String) t.getText();
				if(!"".equals(s)){
					s += "\n";
				}
				s += res;
				t.setText(s);
				t = null;
			}
			break;
		}
	}
	
	public static String getCpuInfo(){
		 try {
             FileReader fr = new FileReader("/proc/cpuinfo");
             BufferedReader br = new BufferedReader(fr);
             String text = br.readLine();
             br.close();
             br = null;
             String[] arr = text.split(":\\s+", 2);
             return arr[1];
	     } catch (FileNotFoundException e) {
	             e.printStackTrace();
	     } catch (IOException e) {
	             e.printStackTrace();
	     }
	     return null;
	}
}
