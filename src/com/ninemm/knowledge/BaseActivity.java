package com.ninemm.knowledge;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class BaseActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置窗体的标题为空，仅此一句。但是需要在方法setContentView之前声明，原因你懂得。
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MobclickAgent.setDebugMode( true );
	}
	
	//用于UMeng数据统计
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	//用于UMeng数据统计
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
