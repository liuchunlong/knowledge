package com.ninemm.knowledge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class ListContentActivity extends BaseActivity {

	private final static String TAG="listview";
	private List<Map<String,String>> lists;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置窗体的标题为空，仅此一句。但是需要在方法setContentView之前声明，原因你懂得。
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		MobclickAgent.setDebugMode( true );
		setContentView(R.layout.activity_contents);
//		String type	= getIntent().getStringExtra("type");
		String type="dog";
		Log.i(TAG,"type==="+type);
		lists	= showList(type);
		ListView  listView = (ListView) findViewById(R.id.general_industry_information_other_news_list);
		MyAdapter adapter = new MyAdapter(getApplicationContext(), lists);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,  
				     int position, long id) {
				Intent goMain = new Intent();
				Map<String ,String> map = lists.get(position);
//				goMain.putExtra("title", map.get("title"));
//				goMain.putExtra("short", map.get("short"));
//				goMain.putExtra("detail", map.get("detail"));
//				goMain.putExtra("web", map.get("web"));
//				goMain.setClass(ListContentActivity.this, KnowDetailActivity.class);
//				startActivity(goMain);
				Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(map.get("web")));  
		        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
		        startActivity(it);  
		        MobclickAgent.onEvent(getApplicationContext(), "dog_onclick");
			}
		});
	}
	
	/** 循环读取数组 */
	private List<Map<String,String>> showList(String type){
		int arrayId = getResources().getIdentifier("com.ninemm.knowledge:array/"+type, null, null);
		Log.i(TAG, "arrayId==="+arrayId);
		String[] string = getResources().getStringArray(arrayId);
		String[] temp	= null;
		Map<String,String> map	= null;
		List<Map<String,String>> list	= new ArrayList<Map<String,String>>();
		Log.i(TAG,"循环读取数组");
		for (String string2 : string) {
			Log.i(TAG,"循环读取数组======="+string2);
			map = new HashMap<String, String>();
			temp = string2.split("_");
			map.put("pic", temp[0]);
			map.put("title", temp[1]);
			map.put("web", temp[2]);
			list.add(map);
		}
		return list;
	}
	
	private class MyAdapter extends BaseAdapter{
		private Context	 mContext;
		private List<Map<String,String>> list;
		public MyAdapter(Context c,List<Map<String,String>> list)
		{
			mContext = c;
			this.list = list;
		}

		@Override
		public int getCount()
		{
			return list.size();
		}

		@Override
		public Object getItem(int position)
		{
			return position;
		}


		@Override
		public long getItemId(int position)
		{
			return position;
		}


		@Override
		public View getView(final int position, View convertView, ViewGroup parent){
			TextView title = null ;
//			TextView intro = null ;
			ImageView newsPic = null;
			if (convertView == null){
				convertView = View.inflate(mContext,R.layout.activity_item, null);
			}
			title = (TextView)convertView.findViewById(R.id.info_title);
			newsPic = (ImageView)convertView.findViewById(R.id.general_industry_information_news_pic);
//			intro = (TextView)convertView.findViewById(R.id.info_short);
			title.setText(list.get(position).get("title"));
//			intro.setText(list.get(position).get("short"));
			convertView.setTag(list.get(position).get("detail"));
			int id = getResources().getIdentifier("com.ninemm.knowledge:drawable/"+list.get(position).get("pic"), null, null);
			newsPic.setImageResource(id);
			return convertView;
		}
/**
 * 		//首个条目，不接受点击事件
		@Override
		public boolean isEnabled(int position) {
			// TODO Auto-generated method stub
			boolean r = true;
			if(position==0){
				System.out.print("posiotion:"+position);
				r = false;
			}
			return r;
		}
		
 */

	}
	
	
}
