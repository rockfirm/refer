package get.refer.function;

import get.refer.R;
import get.refer.UtilTools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

public class FileUploadActivity extends Activity {

	private ProgressDialog pd;
	private Handler handler = new Handler();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//渲染界面
		LinearLayout ll = new LinearLayout(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll.setLayoutParams(lp);
		lp = null;
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setId(R.id.content);
		
		Button b = new Button(this);
		b.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 40, Gravity.CENTER));
		b.setText("上传头像");
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, 0);
			}
		});
		
		ll.addView(b);
		b = null;
		setContentView(ll);
		ll = null;
		
	}
	
	@Override
	public void onActivityResult(int requestCode , int resultCode , Intent data){
		if(resultCode == Activity.RESULT_OK && requestCode == 0 && data != null){
			Uri uri = data.getData();
			if(uri == null){
				Toast.makeText(this, "选择图片出错1", Toast.LENGTH_SHORT).show();
				return ;
			}
			String[] filePathColumn = {MediaStore.Images.Media.DATA};
			String addr = "";
			Cursor cursor = getContentResolver().query(uri,filePathColumn, null, null, null);
			if(cursor != null){
				cursor.moveToFirst();//将游标移到第一位
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				addr = cursor.getString(columnIndex);//获取图片路径
				cursor.close();//关闭游标
				cursor = null;
			}
			Log.i("aa","uri="+uri+";addr="+addr);//???
			if(addr == null || "".equals(addr) || (!addr.endsWith("jpg") && !addr.endsWith("png")) || !new File(addr).exists()){
				Toast.makeText(this, "选择图片出错2", Toast.LENGTH_SHORT).show();
				return ;
			}
			pd = new ProgressDialog(this);
			pd.setCancelable(false);
			pd.setMessage("上传中...");
			pd.show();
			
			final String imgPath = addr;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {//http://192.168.1.101/upload.php
						final String img = UtilTools.post("http://passport.shiwan.com/bin/user_photo_upload.php?uid=1970860",null,imgPath);
						handler.post(new Runnable() {
							@Override
							public void run() {
								if(pd != null){
									pd.dismiss();
									pd = null;
								}
								if(img != null && !"".equals(img)){
									SmartImageView image = new SmartImageView(FileUploadActivity.this);
									image.setImageUrl(img);
									LinearLayout ll = (LinearLayout) findViewById(R.id.content);
									ll.addView(image);
									image = null;
									ll = null;
									Toast.makeText(FileUploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(FileUploadActivity.this, "上传失败，请重试", Toast.LENGTH_SHORT).show();
								}
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
						handler.post(new Runnable() {
							@Override
							public void run() {
								if(pd != null){
									pd.dismiss();
									pd = null;
								}
								Toast.makeText(FileUploadActivity.this, "连接服务器超时，请重试", Toast.LENGTH_SHORT).show();
							}
						});
					}
				}
			}).start();
		}
	}
	
	public static String post(String url, String path) throws Exception{
		String BOUNDARY = "---------7d4a6d158c9"; // 定义数据分隔线
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5000);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		
		StringBuilder sb = new StringBuilder();
		sb.append("--").append(BOUNDARY).append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ path + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		Log.i("aa","sb="+sb.toString());//???
		
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(sb.toString().getBytes());
		sb = null;
		
		DataInputStream in = new DataInputStream(new FileInputStream(path));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		in = null;
		
		out.write( ("\r\n--" + BOUNDARY + "--\r\n").getBytes());//最后数据分隔线
		out.flush();
		out.close();
		out = null;
		
		if(conn.getResponseCode() == 200){
			InputStream is = conn.getInputStream();
	        int ch;
	        StringBuffer b =new StringBuffer();
	        while( ( ch = is.read() ) !=-1 ){
	        	b.append( (char)ch );
	        }
	        is.close();
	        is = null;
	        Log.i("aa","b.toString()="+b.toString()+";conn.getResponseCode="+conn.getResponseCode());//???
	        return b.toString();
		}
		return null;
	}
}
