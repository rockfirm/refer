package get.refer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * <p>1.ListActivity example</p>
 * <p>2.raw数据库拷贝</p>
 * @author jin
 * @since 2013/7/1
 */
public class JavaApiActivity extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		List<String> list = new ArrayList<String>();
		list.add("Java基础");
		list.add("算法与编程");
		list.add("Java进阶");
		list.add("设计模式");
		list.add("数据库");
		list.add("Java Web");
		list.add("流行框架");
		list.add("Java EE");
		list.add("Java Class");
		list.add("Java Package");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list));
		
		if(getSharedPreferences("setting", 0).getBoolean("first_init_java_db", true)){
			installDb("javamanual.db",R.raw.javamanual);
			installDb("javaapi.db",R.raw.javaapi);
			getSharedPreferences("setting", 0).edit().putBoolean("first_init_java_db", false).commit();
		}
	}
	
	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id){
		Intent intent = new Intent(this, JavaApiList.class);
		intent.putExtra("id", position);
		startActivity(intent);
	}
	
	private void installDb(String str, int id){
		InputStream is = getResources().openRawResource(id);
		byte b[] = new byte[2048];
		int len = 0;
		File dir = new File("/data/data/get.refer/databases/");
		if(!dir.exists()){
			dir.mkdir();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(dir, str));
			while((len = is.read(b)) != 0){
				fos.write(b, 0, len);
			}
			fos.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
