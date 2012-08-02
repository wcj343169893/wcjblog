package com.choujone.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.choujone.blog.entity.Category;
import com.choujone.blog.util.HttpDownload;
import com.choujone.blog.util.NetWork;

public class MainActivity extends Activity {
	private ArrayAdapter<Category> adapter;
	private List<Category> categorys = new ArrayList<Category>();
	private Spinner sCategory;
	private TextView tvTitle;
	private TextView tvContent;
	private RadioGroup rgVisible;
	public RadioButton mRadio1, mRadio2;
	private Button btnSubmit;
	public static String category_url = "http://www.choujone.com/blogType?opera=lists";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// String json = HttpDownload.downFile(category_url);
		// Log.d("cate", "-------------" + json);
		// Map<String, String> cate_map = HttpDownload.json2Map(json);
		// for (String key : cate_map.keySet()) {
		// categorys.add(new Category(Long.valueOf(key), cate_map.get(key)));
		// }

		sCategory = (Spinner) findViewById(R.id.spinner1);
		tvTitle = (TextView) findViewById(R.id.editText1);
		tvContent = (TextView) findViewById(R.id.editText2);
		rgVisible = (RadioGroup) findViewById(R.id.radioGroup1);
		mRadio1 = (RadioButton) findViewById(R.id.radio0);
		mRadio2 = (RadioButton) findViewById(R.id.radio1);
		btnSubmit = (Button) findViewById(R.id.button1);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				if(tvTitle.getText()== null || "".equals(tvTitle.getText().toString().trim())){
					showMessage("标题不能为空!");
					return;
				}
				if(tvContent.getText()== null || "".equals(tvContent.getText().toString().trim())){
					showMessage("内容不能为空!");
					return;
				}
				Category choseValue = (Category) sCategory.getSelectedItem();
				String isvisible = rgVisible.getCheckedRadioButtonId() == mRadio1
						.getId()?"0":"1";
				if (NetWork.postData(tvTitle.getText().toString(), choseValue
						.getId().toString(), tvContent.getText().toString(),
						isvisible)) {
					showMessage("发布成功^v^！");
					// 清除title和content内容
					tvTitle.setText("");
					tvContent.setText("");
					tvTitle.setFocusable(true);
				}else{
					showMessage("发布失败~_~！");
				}
			}
		});
		adapter = new ArrayAdapter<Category>(this,
				android.R.layout.simple_spinner_item, categorys);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sCategory.setAdapter(adapter);

		load();

	}
	public void showMessage(String msg){
		Toast.makeText(getApplicationContext(), msg,
				Toast.LENGTH_LONG).show();
	}
	public void load() {
		final Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				// tvTitle.setText("分类:" + (String) msg.obj);
				Map<String, String> cate_map = HttpDownload
						.json2Map((String) msg.obj);
				for (String key : cate_map.keySet()) {
					adapter.add(new Category(Long.valueOf(key), cate_map
							.get(key)));
				}
			};
		};
		new Thread() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				String c = HttpDownload.downFile(category_url);
				// Log.d("cate_", c);
				if (c != null)
					msg.obj = c;
				handler.sendMessage(msg);
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
