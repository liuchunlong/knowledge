package com.ninemm.knowledge;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class KnowDetailActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		TextView text	= (TextView)findViewById(R.id.know_detail);
		TextView textWeb	= (TextView)findViewById(R.id.know_detail_web);
		String title	= getIntent().getStringExtra("title");
		String shor		= getIntent().getStringExtra("short");
		String detail	= getIntent().getStringExtra("detail");
		String web	= getIntent().getStringExtra("web");
		
		String string	= title+"\n\n"+shor+"¡£\n\n"+detail;
		text.setText(string);
		textWeb.setText(web);
	}
}
