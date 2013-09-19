package get.refer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

public class UtilTools {
	
	/**
	 * 监测网络
	 * @param context
	 * @return int
	 */
	public static int checkNetworkInfo(Context context){
		int net = 0;
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = connManager.getActiveNetworkInfo();
		try {
			if(netinfo != null && netinfo.isAvailable()){
				if(netinfo.getType() == ConnectivityManager.TYPE_WIFI){
					net = 1;
				}else if (netinfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					net = 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return net;
	}
	
	/**
	 * 获取可写目录路径
	 * @return String
	 */
	public static String getCanWriteDirPath () {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return Environment.getExternalStorageDirectory().getPath();
		}else {
			File w = new File("/mnt/");
			if(w.exists()){
				File[] f = w.listFiles();
				for(int i = 0, j = f.length; i < j; ++i){
					if(f[i].isDirectory() && f[i].canWrite()){
						return f[i].getAbsolutePath();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * 递归删除文件
	 * @param file
	 */
	public static void delFile(File file){
		if(file.exists()){
			if(file.isDirectory() && file.listFiles().length > 0){
				File[] files = file.listFiles();
				for(int i = 0, j = files.length; i < j; ++i){
					if(files[i].exists()){
						if(files[i].isDirectory() && files[i].listFiles().length > 0){
							delFile(files[i]);
						}else {
							files[i].delete();
						}
					}
				}
			}
			file.delete();
		}
	}
	
	/**
	 * 字符流的形式把String写入文件
	 * @param file
	 * @param str
	 */
	public static void saveStringToFile(File file, String str){
		Writer writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(str);
			writer.close();
			writer = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 以字节流的形式从文件读取String
	 * @param file
	 * @return String
	 * @throws IOException
	 */
	public static String readStringFromFile(File file) throws IOException{
		InputStream in = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = -1;
		while((len = in.read(b)) != -1){
			out.write(b, 0, len);
		}
		in.close();
		in = null;
		return new String(out.toByteArray());
	}
	
	/**
	 * 获取版本号
	 * @param context
	 * @return int
	 */
	public static int getCurrentVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 计算可用空间
	 * @param context
	 * @return long
	 */
	public static long getCanUsedSpace(Context context){
		String path = getCanWriteDirPath();
		if(!"".equals(path)){
			StatFs statfs=new StatFs(path);
			long size=statfs.getBlockSize();//每个block的大小
			long available=statfs.getAvailableBlocks();//可用block的大小
			return available*size;
		}
		return 0;
	}
	
	public static String post(String path, Map<String,String> params, String filePath) throws Exception{
		String BOUNDARY = "---------------------------7db1c523809b2";
		File file = new File(filePath);
		Log.i("aa","path="+path);//???
		StringBuilder sb = new StringBuilder();
		if(params != null){
			for(Map.Entry<String, String> entry : params.entrySet() ){
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
				sb.append(entry.getValue());
				sb.append("\r\n");
			}
		}
		sb.append("--" + BOUNDARY + "\r\n");
		sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + "\r\n");
		sb.append("Content-Type: application/octet-stream" + "\r\n");
		sb.append("\r\n");
		Log.i("aa","file.length()"+file.length()+";sb="+sb.toString());//???
		// 将开头和结尾部分转为字节数组，因为设置Content-Type时长度是字节长度
		byte[] before = sb.toString().getBytes("UTF-8");
		byte[] after = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);// 允许输入 
		conn.setDoOutput(true);// 允许输出 
		conn.setUseCaches(false);// 不允许使用缓存 
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
//		conn.setRequestProperty("Content-Length", before.length + file.length() + after.length + "");
//		conn.setRequestProperty("HOST", uri.getHost() + ":" + uri.getPort());
		// 获取输入输出流
		OutputStream out = conn.getOutputStream();
		FileInputStream fis = new FileInputStream(file);
		// 将开头部分写出
		out.write(before);
		// 写出文件数据
		byte[] buf = new byte[1024];
		int len;
		while ((len = fis.read(buf)) != -1){
			out.write(buf, 0, len);
			Log.i("aa","len="+len);//???
		}
		// 将结尾部分写出
		out.write(after);
		out.flush();
		fis.close();
		out.close();
		if(conn.getResponseCode() == 200){
			String str = new String(read(conn.getInputStream()));
			Log.i("aa","post result="+str);//???
			return str;
		}
		conn.disconnect();
		conn = null;
		return null;
	}
	
	/**
	 * 把输出流读入byte数组
	 * @param inStream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] read(InputStream inStream) throws Exception{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = inStream.read(buffer)) != -1){
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		inStream.close();
		return outputStream.toByteArray();
	}
	
	/*判断文件类型*/
	public static String getImageType(String path) throws Exception {
		String fileType="";
		FileInputStream inputStream=new FileInputStream(path);
        byte[] buffer=new byte[2];
        String filecode = "";
        //通过读取出来的前两个字节来判断文件类型
        if (inputStream.read(buffer)!=-1) {
        	for (int i = 0; i < buffer.length; i++) {
        		//获取每个字节与0xFF进行与运算来获取高位，这个读取出来的数据不是出现负数并转换成字符串
                filecode+=Integer.toString((buffer[i]&0xFF));
            }
        	inputStream.close();
        	inputStream = null;
            // 把字符串再转换成Integer进行类型判断
            switch (Integer.parseInt(filecode)) {
            /*case 7790:  
            	fileType = "exe";
                break;  
            case 7784:  
                fileType =  "midi";
                break;  
            case 8297:  
                fileType = "rar";
                break;
            case 8075:  
            	fileType = "zip";
                break;  */
            case 255216:  
            	fileType = "jpg";
                break;  
            case 7173:  
                fileType = "gif";
                break;  
            case 6677:  
            	fileType = "bmp";
                break;  
            case 13780:  
                fileType = "png";
                break;
            default:  
            	fileType = "";
            }  
        }
		return fileType;
    }
	
	/**
	 * 用于统计、提交错误报告，调用前需手动监测网络
	 * @param url
	 */
	public static void simpleGet(final String url){
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection conn = null;
				try {
					conn = (HttpURLConnection) new URL(url).openConnection();
					conn.setReadTimeout(5000);
					conn.setRequestMethod("GET");
					if(conn.getResponseCode() == 200){
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
//	private void openGPSSettings(Context context) {
//		LocationManager alm = (LocationManager) this
//		.getSystemService(Context.LOCATION_SERVICE);
//		if (alm
//		.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//		Toast.makeText(context, "GPS模块正常", Toast.LENGTH_SHORT)
//		.show();
//		return;
//		}
//
//		Toast.makeText(context, "请开启GPS！", Toast.LENGTH_SHORT).show();
//		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
//		startActivityForResult(intent,0); //此为设置完成后返回到获取界面
//
//	}
	
//	private void getLocation()
//	{
//	// 获取位置管理服务
//	LocationManager locationManager;
//	String serviceName = Context.LOCATION_SERVICE;
//	locationManager = (LocationManager) this.getSystemService(serviceName);
//	// 查找到服务信息
//	Criteria criteria = new Criteria();
//	criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
//	criteria.setAltitudeRequired(false);
//	criteria.setBearingRequired(false);
//	criteria.setCostAllowed(true);
//	criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
//
//	String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
//	Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
//	updateToNewLocation(location);
//	// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
//	locationManager.requestLocationUpdates(provider, 100 * 1000, 500,
//	locationListener);
//	}
}
