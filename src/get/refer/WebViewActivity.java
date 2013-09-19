package get.refer;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.baidu.mobstat.StatService;

/**
 * load html
 * 
 * @author JinZhenkun
 * @since 2013/6/14
 */
public class WebViewActivity extends Activity {

	private WebView webView;
	// private String url = "http://v.iphone.shiwan.com/score/a";
	// private String url = "file:///android_asset/testWebView.html";
	private String url = "http://1006.tv/detail/lolvideo";
	private MessageHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);//disabled title
		// Adds Progrss bar Support
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		// Makes Progress bar Visible
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);
		handler = new MessageHandler(this);
		webView = new WebView(this);
		setContentView(webView);
		// webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		WebSettings ws = webView.getSettings();
		ws.setJavaScriptEnabled(true);// support js
		ws.setDomStorageEnabled(true);// support html5 LocalStorage
		// ws.setSupportZoom(false);

		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			// 设置网页加载的进度条
			public void onProgressChanged(WebView view, int progress) {
				WebViewActivity.this.setProgress(progress * 100);
				// WebViewActivity.this.getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				// progress * 100);
				super.onProgressChanged(view, progress);
				// Log.i("aa","progress="+progress);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {// alert
				new AlertDialog.Builder(WebViewActivity.this)
						.setMessage(message)
						.setPositiveButton(getString(R.string.sure), null)
						.create().show();
				result.confirm();// must!
				return true;
			}

			// 设置应用程序的标题title
			public void onReceivedTitle(WebView view, String title) {
				WebViewActivity.this.setTitle(title);
				super.onReceivedTitle(view, title);
			}
		});

		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		// Java&javascript call eachother
//		webView.addJavascriptInterface(new DemoJavaScriptInterface(), "app");
		
		//get limit memory size  big 
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		int big = am.getMemoryClass();
		Log.i("aa", "free=" + Runtime.getRuntime().freeMemory() + 	";all="
				+ Runtime.getRuntime().totalMemory()+";mem="+big);// ???
		// 监听下载
		webView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				Log.i("aa", "url=" + url);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		webView.loadUrl(url);
	}

	@Override
	public void onBackPressed() {
		// can back or finish
		if (webView != null && webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (webView != null) {
			webView.onResume();
		}
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (webView != null) {
			webView.onPause();// 页码有视频时不调用不停止
		}
		StatService.onPause(this);
	}

	// javascript obj
	class DemoJavaScriptInterface {
//		@android.webkit.JavascriptInterface
		public void clickFromAndroid(final String str) {
			// here must uses Handle or warning:java.lang.Throwable: Warning: A
			// WebView method was called on thread 'WebViewCoreThread'. All
			// WebView methods must be called on the UI thread. Future versions
			// of WebView may not support use on other threads.
			new Handler().post(new Runnable() {// java call javascript function
												// must use Handler
						@Override
						public void run() {
							handler.sendMessage(Message.obtain(handler, 0, str));
						}
					});
		}

		public int getWidth() {
			return getResources().getDisplayMetrics().widthPixels;
		}
	}

	private static class MessageHandler extends Handler {
		private WeakReference<WebViewActivity> tmp;

		public MessageHandler(WebViewActivity activity) {
			tmp = new WeakReference<WebViewActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			WebViewActivity activity = tmp.get();
			switch (msg.what) {
			case 0:
				Toast.makeText(activity, msg.obj.toString(), Toast.LENGTH_SHORT)
						.show();
				activity.webView.loadUrl("javascript:testCallFromJava('"
						+ msg.obj.toString() + "')");
				break;
			}
		}
	}

}
