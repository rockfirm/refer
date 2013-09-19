package get.refer;

import get.refer.function.AnimationActivity;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

public class MediaTestActivity extends Activity{
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				MediaPlayer mp = MediaPlayer.create(MediaTestActivity.this, R.raw.hero1_1);
				String url = Environment.getExternalStorageDirectory()+"/1006/a.mp3";
				if(!new File(url).exists()){
					return ;
				}
				MediaPlayer mp = MediaPlayer.create(MediaTestActivity.this, Uri.parse(url));
				mp.start();
			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyTabActivity.needUpDownAnimate = true;
				startActivity(new Intent(MediaTestActivity.this, AnimationActivity.class));
			}
		});
//		SearchView s = new SearchView(this);
//		LinearLayout ll = (LinearLayout) findViewById(R.id.parent);
//		ll.addView(s);
	}
	

}
