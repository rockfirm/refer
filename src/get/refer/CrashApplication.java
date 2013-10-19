package get.refer;

import android.app.Application;
import android.content.Intent;

public class CrashApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());
		
//		startService(new Intent(getApplicationContext(), AlarmService.class));
		startService(new Intent(getApplicationContext(), TestServerWithActivity.class));
	}
}
