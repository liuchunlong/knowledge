package com.ninemm.knowledge;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button btnRare;
	private Button btnDog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FrameLayout frame1	= (FrameLayout)findViewById(R.id.btnRare);
		FrameLayout frame2	= (FrameLayout)findViewById(R.id.btnDog);
		btnRare = (Button)frame1.findViewById(R.id.btnBg);
		btnDog = (Button)frame2.findViewById(R.id.btnBg);
		((TextView)frame2.findViewById(R.id.txtTitle)).setText("长知识");
		((TextView)frame1.findViewById(R.id.txtTitle)).setText("世界名犬");
		btnDog.setOnClickListener(new btnOnclickMethod());
		btnRare.setOnClickListener(new btnOnclickMethod());
		
	}

	private final class btnOnclickMethod implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v==btnDog){
				jumpTo("dog");
			}else if(v==btnRare){
				jumpTo("rare");
			}
		}
	}
	
	private void jumpTo(String string){
		Intent goMain = new Intent();
		goMain.putExtra("type", string);
		goMain.setClass(MainActivity.this, ListContentActivity.class);
		startActivity(goMain);
		
	}
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	*/
}
