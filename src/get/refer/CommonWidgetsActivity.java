package get.refer;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Typeface;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.baidu.mobstat.StatService;

public class CommonWidgetsActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_widgets);

		TextView mTVText = (TextView) findViewById(R.id.text);

		mTVText.append("1、BackgroundColorSpan 背景色\n");
		SpannableString spanText = new SpannableString(
				"http://www.apkbus.com/forum.php");
		spanText.setSpan(new BackgroundColorSpan(Color.GREEN), 0,
				spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("2、ClickableSpan 文本可点击，有点击事件\n");
		SpannableString ss = new SpannableString("text4: Click here to baidu.");
		ss.setSpan(new StyleSpan(Typeface.BOLD), 0, 6,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new URLSpan("http://www.baidu.com/"), 13, 17,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mTVText.append(ss);
		mTVText.setMovementMethod(LinkMovementMethod.getInstance());

		mTVText.append("\n\n");
		mTVText.append("3、ForegroundColorSpan 文本颜色（前景色）\n");
		spanText = new SpannableString("http://www.apkbus.com/forum.php");
		spanText.setSpan(new ForegroundColorSpan(Color.BLUE), 6,
				spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("4、MaskFilterSpan 修饰效果，如模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)\n");
		spanText = new SpannableString("MaskFilterSpan -- http://orgcent.com");
		int length = spanText.length();
		//模糊(BlurMaskFilter)
		MaskFilterSpan maskFilterSpan = new MaskFilterSpan(new BlurMaskFilter(
				3, Blur.OUTER));
		spanText.setSpan(maskFilterSpan, 0, length - 10,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		//"浮雕(EmbossMaskFilter)
		maskFilterSpan = new MaskFilterSpan(new EmbossMaskFilter(new float[] {
				1, 1, 3 }, 1.5f, 8, 3));
		spanText.setSpan(maskFilterSpan, length - 10, length,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("5、RasterizerSpan 光栅效果\n");
		spanText = new SpannableString("StrikethroughSpan");
		spanText.setSpan(new StrikethroughSpan(), 0, 7,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("6、StrikethroughSpan 删除线（中划线）\n");
		spanText = new SpannableString("StrikethroughSpan");
		spanText.setSpan(new StrikethroughSpan(), 0, 7,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("7、UnderlineSpan 下划线\n");
		spanText = new SpannableString("UnderlineSpan");
		spanText.setSpan(new UnderlineSpan(), 0, spanText.length(),
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("8、AbsoluteSizeSpan 绝对大小（文本字体）\n");
		spanText = new SpannableString("AbsoluteSizeSpan");
		spanText.setSpan(new AbsoluteSizeSpan(20, true), 0, spanText.length(),
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("9、DynamicDrawableSpan 设置图片，基于文本基线或底部对齐。\n");
		DynamicDrawableSpan drawableSpan = new DynamicDrawableSpan(
				DynamicDrawableSpan.ALIGN_BASELINE) {
			@Override
			public Drawable getDrawable() {
				Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
				d.setBounds(0, 0, 50, 50);
				return d;
			}
		};
		DynamicDrawableSpan drawableSpan2 = new DynamicDrawableSpan(
				DynamicDrawableSpan.ALIGN_BOTTOM) {
			@Override
			public Drawable getDrawable() {
				Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
				d.setBounds(0, 0, 50, 50);
				return d;
			}
		};
		spanText.setSpan(drawableSpan, 3, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		spanText.setSpan(drawableSpan2, 7, 8,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("10、ImageSpan 图片\n");
		spanText = new SpannableString("ImageSpan");
		Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
		d.setBounds(0, 0, 50, 50);
		spanText.setSpan(new ImageSpan(d), 3, 4,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("11、RelativeSizeSpan 相对大小（文本字体）\n");
		spanText = new SpannableString("RelativeSizeSpan");
		// "参数proportion:比例大小
		spanText.setSpan(new RelativeSizeSpan(2.5f), 3, 4,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("12、ScaleXSpan 基于x轴缩放\n");
		spanText = new SpannableString("ScaleXSpan -- http://www.apkbus.com/forum.php");
		// 参数proportion:比例大小
		spanText.setSpan(new ScaleXSpan(3.8f), 3, 7,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("13、StyleSpan 字体样式：粗体、斜体等\n");
		spanText = new SpannableString("StyleSpan -- http://www.apkbus.com/forum.php");
		// Typeface.BOLD_ITALIC:粗体+斜体
		spanText.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 3, 7,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("14、SubscriptSpan 下标（数学公式会用到）\n");
		spanText = new SpannableString("SubscriptSpan -- http://www.apkbus.com/forum.php");
		spanText.setSpan(new SubscriptSpan(), 6, 7,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("15、SuperscriptSpan 上标（数学公式会用到）\n");
		spanText = new SpannableString("SuperscriptSpan -- http://www.apkbus.com/forum.php");
		spanText.setSpan(new SuperscriptSpan(), 6, 7,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("16、TextAppearanceSpan 文本外貌（包括字体、大小、样式和颜色）\n");
		spanText = new SpannableString("TextAppearanceSpan -- http://www.apkbus.com/forum.php");
		// 若需自定义TextAppearance，可以在系统样式上进行修改
		spanText.setSpan(new TextAppearanceSpan(this,
				android.R.style.TextAppearance_Medium), 6, 7,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("17、TypefaceSpan 文本字体\n");
		spanText = new SpannableString("TypefaceSpan -- http://www.apkbus.com/forum.php");
		// 若需使用自定义字体，可能要重写类TypefaceSpan
		spanText.setSpan(new TypefaceSpan("monospace"), 3, 10,
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);

		mTVText.append("\n\n");
		mTVText.append("18、URLSpan 文本超链接\n");
		spanText = new SpannableString("URLSpan -- http://www.apkbus.com/forum.php");
		spanText.setSpan(new URLSpan("http://image.baidu.com/channel/index?fm=index&image_id=6644986315#%E7%BE%8E%E5%A5%B3&%E5%85%A8%E9%83%A8&0&0"), 10,
				spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		mTVText.append(spanText);
		mTVText.append("\n");
		// 让URLSpan可以点击
		mTVText.setMovementMethod(new LinkMovementMethod());

		//19、SuggestionSpan
		//相当于占位符，一般用在EditText输入框中。当双击此文本时，会弹出提示框选择一些建议（推荐的）文字，选中的文本将替换此占位符。
		//在输入法上用的较多。
		//PS：API 14新增，暂无示例。
	}
	
	@Override
	public void onResume() {
		super.onResume();
		StatService.onPageStart(this, "test1");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "test1");
	}
}
