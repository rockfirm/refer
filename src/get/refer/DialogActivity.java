package get.refer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;


public class DialogActivity extends Activity {

	@Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        new AlertDialog.Builder(this)
        .setTitle(getIntent().getStringExtra("title"))
        .setMessage(getIntent().getStringExtra("message"))
        .setPositiveButton(android.R.string.ok, mOkListener)
        .setCancelable(false)
        .show();
    }

    private final OnClickListener mOkListener = new OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
        	DialogActivity.this.finish();
        }
    };
}
