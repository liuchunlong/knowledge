package com.ninemm.knowledge;  
  
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.List;  
import java.util.Map;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;  
import android.graphics.Matrix;  
import android.net.Uri;
import android.os.Bundle;  
import android.support.v4.view.PagerAdapter;  
import android.support.v4.view.ViewPager;  
import android.support.v4.view.ViewPager.OnPageChangeListener;  
import android.util.DisplayMetrics;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.animation.Animation;  
import android.view.animation.TranslateAnimation;  
import android.view.ViewGroup;  
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;  
import android.widget.ListView;
import android.widget.TextView;  
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
  
public class SelectPageActivity extends BaseActivity {  
  
    private ViewPager viewPager;//页卡内容  
    private ImageView imageView;// 动画图片  
    private TextView textView1,textView2,textView3;  
    private List<View> views;// Tab页面列表  
    private int offset = 0;// 动画图片偏移量  
    private int currIndex = 0;// 当前页卡编号  
    private int bmpW;// 动画图片宽度  
    private View viewBack,viewList,viewMore;//各个页卡  
    /** 各个分页面的数据---开始 */
    private List<Map<String,String>> lists;//用于listview中adapter的数据传递
    private Button btnRare;	//用于more页面
	private Button btnDog;//用于more页面
	private FeedbackAgent agent;//反馈页面
    /** 各个分页面的数据---结束 */
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.select);  
        InitImageView();  
        InitTextView();  
        InitViewPager();  
    }  
  
    private void InitViewPager() {  
        viewPager=(ViewPager) findViewById(R.id.vPager);  
        views=new ArrayList<View>();  
        LayoutInflater inflater=getLayoutInflater();  
        viewBack=inflater.inflate(R.layout.activity_item, null);  
        viewList=inflater.inflate(R.layout.activity_contents, null);  
        viewMore=inflater.inflate(R.layout.activity_main, null);  
        views.add(viewBack);  
        views.add(viewList);  
        views.add(viewMore);  
        loadView1();
        loadView2();
        loadView3();
        viewPager.setAdapter(new MyViewPagerAdapter(views));  
        viewPager.setCurrentItem(0);  
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());  
    }  
     /** 
      *  初始化头标 
      */  
  
    private void InitTextView() {  
        textView1 = (TextView) findViewById(R.id.text1);  
        textView2 = (TextView) findViewById(R.id.text2);  
        textView3 = (TextView) findViewById(R.id.text3);  
  
        textView1.setOnClickListener(new MyOnClickListener(0));  
        textView2.setOnClickListener(new MyOnClickListener(1));  
        textView3.setOnClickListener(new MyOnClickListener(2));  
    }  
  
    /** 
     2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据 
     3 */  
  
    private void InitImageView() {  
        imageView= (ImageView) findViewById(R.id.cursor);  
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度  
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        int screenW = dm.widthPixels;// 获取分辨率宽度  
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量  
        Matrix matrix = new Matrix();  
        matrix.postTranslate(offset, 0);  
        imageView.setImageMatrix(matrix);// 设置动画初始位置  
    }  
    /**  
		<img src="http://img.my.csdn.net/uploads/201211/10/1352554452_1685.jpg" alt="">  
     *      
     * 头标点击监听 3 */  
    private class MyOnClickListener implements OnClickListener{  
        private int index=0;  
        public MyOnClickListener(int i){  
            index=i;  
        }  
        public void onClick(View v) {  
            viewPager.setCurrentItem(index);              
        }  
          
    }  
      
    public class MyViewPagerAdapter extends PagerAdapter{  
        private List<View> mListViews;  
          
        public MyViewPagerAdapter(List<View> mListViews) {  
            this.mListViews = mListViews;  
        }  
  
        @Override  
        public void destroyItem(ViewGroup container, int position, Object object)   {     
            container.removeView(mListViews.get(position));  
        }  
  
  
        @Override  
        public Object instantiateItem(ViewGroup container, int position) {            
             container.addView(mListViews.get(position), 0);  
             return mListViews.get(position);  
        }  
  
        @Override  
        public int getCount() {           
            return  mListViews.size();  
        }  
          
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {             
            return arg0==arg1;  
        }  
    }  
  
    public class MyOnPageChangeListener implements OnPageChangeListener{  
  
        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量  
        int two = one * 2;// 页卡1 -> 页卡3 偏移量  
        public void onPageScrollStateChanged(int arg0) {}  
  
        public void onPageScrolled(int arg0, float arg1, int arg2) {}  
  
        public void onPageSelected(int target) {  
            /*两种方法，这个是一种，下面还有一种，显然这个比较麻烦 
            Animation animation = null; 
            switch (arg0) { 
            case 0: 
                if (currIndex == 1) { 
                    animation = new TranslateAnimation(one, 0, 0, 0); 
                } else if (currIndex == 2) { 
                    animation = new TranslateAnimation(two, 0, 0, 0); 
                } 
                break; 
            case 1: 
                if (currIndex == 0) { 
                    animation = new TranslateAnimation(offset, one, 0, 0); 
                } else if (currIndex == 2) { 
                    animation = new TranslateAnimation(two, one, 0, 0); 
                } 
                break; 
            case 2: 
                if (currIndex == 0) { 
                    animation = new TranslateAnimation(offset, two, 0, 0); 
                } else if (currIndex == 1) { 
                    animation = new TranslateAnimation(one, two, 0, 0); 
                } 
                break; 
                 
            } 
            */  
            Animation animation = new TranslateAnimation(one*currIndex, one*target, 0, 0);//显然这个比较简洁，只有一行代码。  
            currIndex = target;  
            animation.setFillAfter(true);// True:图片停在动画结束位置  
            animation.setDuration(300);  
            imageView.startAnimation(animation);  
            Toast.makeText(SelectPageActivity.this, "您选择了"+ viewPager.getCurrentItem()+"页卡==="+target, Toast.LENGTH_SHORT).show();  
        }  
          
    } 
    
    /**
     * 下面是页面的加载的方法
     */
    /**  */
    private void loadView1(){
	    String type="dog";
		lists	= showList(type);
		ListView  listView = (ListView) viewList.findViewById(R.id.general_industry_information_other_news_list);
		MyAdapter adapter = new MyAdapter(getApplicationContext(), lists);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,  
				     int position, long id) {
				Intent goMain = new Intent();
				Map<String ,String> map = lists.get(position);
	//			goMain.putExtra("title", map.get("title"));
	//			goMain.putExtra("short", map.get("short"));
	//			goMain.putExtra("detail", map.get("detail"));
	//			goMain.putExtra("web", map.get("web"));
	//			goMain.setClass(ListContentActivity.this, KnowDetailActivity.class);
	//			startActivity(goMain);
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
		String[] string = getResources().getStringArray(arrayId);
		String[] temp	= null;
		Map<String,String> map	= null;
		List<Map<String,String>> list	= new ArrayList<Map<String,String>>();
		for (String string2 : string) {
			map = new HashMap<String, String>();
			temp = string2.split("_");
			map.put("pic", temp[0]);
			map.put("title", temp[1]);
			map.put("web", temp[2]);
			list.add(map);
		}
		return list;
	}
	
	//listView的适配器
	private class MyAdapter extends BaseAdapter{
		private Context	 mContext;
		private List<Map<String,String>> list;
		public MyAdapter(Context c,List<Map<String,String>> list){
			mContext = c;
			this.list = list;
		}

		@Override
		public int getCount(){
			return list.size();
		}

		@Override
		public Object getItem(int position){
			return position;
		}


		@Override
		public long getItemId(int position){
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
    
    
    private void loadView2(){
    	agent	= new FeedbackAgent(getApplicationContext());
    	agent.startFeedbackActivity();
    }
    
    private void loadView3(){
    	FrameLayout frame1	= (FrameLayout)viewMore.findViewById(R.id.btnRare);
		FrameLayout frame2	= (FrameLayout)viewMore.findViewById(R.id.btnDog);
		btnRare = (Button)frame1.findViewById(R.id.btnBg);
		btnDog = (Button)frame2.findViewById(R.id.btnBg);
		((TextView)frame2.findViewById(R.id.txtTitle)).setText("长知识");
		((TextView)frame1.findViewById(R.id.txtTitle)).setText("世界名犬");
		btnDog.setOnClickListener(new btnOnclickMethod());
		btnRare.setOnClickListener(new btnOnclickMethod());
    }
    //按钮点击统一处理方法
    private final class btnOnclickMethod implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v==btnDog){
				//jumpTo("dog");
				agent	= new FeedbackAgent(getApplicationContext());
		    	agent.startFeedbackActivity();
			}else if(v==btnRare){
				agent	= new FeedbackAgent(getApplicationContext());
				agent.startFeedbackActivity();
			}
		}
	}
	
    //页面跳转方法
	private void jumpTo(String string){
		Intent goMain = new Intent();
		goMain.putExtra("type", string);
		goMain.setClass(getApplicationContext(), ListContentActivity.class);
		startActivity(goMain);
		
	}
    
}  
