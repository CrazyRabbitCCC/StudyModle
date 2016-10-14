package cn.sleepycoder.designexample.stickyheader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

import cn.sleepycoder.designexample.R;

/**
 * 滚动广告栏
 */
public class BannerView extends RelativeLayout implements OnPageChangeListener{
	
	private static final long Duration = 5000;	//默认间隔时间
	private Context mContext;
	private ViewPager mViewPager;				//
	private LinearLayout pointBar;				//小点指示条
	private boolean isAutoPlay;					//是否自动播放
	private int marginRight = 20;				//指示条右外边距
	private int marginButtom = 20;				//指示条下外边距
	private static final int MaxPointCount = 5;	//指示小点的最大数目
	private int pointCount = 0;					//指示小点的实际数目
	private ImageView[]  points;				//小点控件实例
//	private int selectPointResId,unSelectPointResId;
	private Bitmap pointSelected,pointUnselected;	//选中 和 未选中的小点图像对象
	private OnPageChangeListener mPagerChangeListener;		//用户接口
	private Handler mHandler;	
	private MyRunnable mRunnable;							//自动播放
	private static final String  selectPointColor = "#FFFFFF"; 		//选中小点的颜色
	private static final String  unselectPointColor = "#55898989";  //未选中小点的颜色
	private int pointsLocation = 0;
	/**
	 * api 20以下 注释掉此构造方法
	 */
	@SuppressLint("NewApi")
	public BannerView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
		pointsLocation = a.getInt(R.styleable.BannerView_pointsLocation,0);
		a.recycle();
		init(context);
	}

	public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
		pointsLocation = a.getInt(R.styleable.BannerView_pointsLocation,0);
		a.recycle();
		init(context);

	}

	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
		pointsLocation = a.getInt(R.styleable.BannerView_pointsLocation,0);
		a.recycle();
		init(context);
	}

	public BannerView(Context context) {
		super(context);
		init(context);
	}
	/**
	 * 初始化控件
	 * @param context
	 */
	private void init(Context context){
		mContext = context;
		mHandler = new Handler();
		//viewpager
		mViewPager = new WrapViewPager(mContext);
		mViewPager.setOnPageChangeListener(this);
		//指示条
		pointBar = new LinearLayout(mContext);
		pointBar.setOrientation(LinearLayout.HORIZONTAL);  
		//获取指示小点的图片
		pointSelected = creatPointBitmap(true);
		pointUnselected = creatPointBitmap(false);
		//最低宽高
		setMinimumHeight(80);
		setMinimumWidth(100);
		//viewpager 布满父控件
		LayoutParams lp  = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mViewPager.setId(R.id.scrapped_view);
		addView(mViewPager, lp);
		//指示条  位于父控件右下方   也可以设置为靠下居中
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.rightMargin = marginRight;
		lp.bottomMargin = marginButtom;

		if(pointsLocation==0) {
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}else {
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		}
		lp.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.scrapped_view);
		addView(pointBar, lp);
	}
	
	class MyRunnable implements Runnable {
		
		public void run() {
			if(mViewPager.getAdapter()==null || mViewPager.getAdapter().getCount()<1){
				return;
			}
			int position = mViewPager.getCurrentItem()+1;
			mRunnable = null;
			mViewPager.setCurrentItem( position % mViewPager.getAdapter().getCount());
		}
	}
	
	/**
	 * 设置viewpager的adapter  对外开放的接口
	 * @param adapter	适配器
	 * @param isAutoPlay  是否自动播放
	 */
	public void setViewAdapter(PagerAdapter adapter,boolean isAutoPlay){
		if(adapter==null){
			return;
		}
		this.isAutoPlay = isAutoPlay;
		pointCount = adapter.getCount()>MaxPointCount? MaxPointCount:adapter.getCount();
		if(pointCount==0){
			return;
		}
		points = new ImageView[pointCount];
		addPointsView();
		mViewPager.setAdapter(adapter);
		//开始自动播放
		if(isAutoPlay){
			if(mRunnable!=null){
				mHandler.removeCallbacks(mRunnable);
			}
			mRunnable = new MyRunnable();
			mHandler.postDelayed(mRunnable, Duration);
		}
	}
	
	public void setViewAdapter(PagerAdapter adapter){
		setViewAdapter(adapter,true);
	}
	/**
	 * 设置页面切换的监听事件
	 * @param listener
	 */
	public void setOnPageChangeListener(OnPageChangeListener listener){
		mPagerChangeListener = listener;
	}
	/**
	 * 获得viewpager对象  满足各种自定义设置
	 * @return
	 */
	public ViewPager getViewPager(){
		return mViewPager;
	}
	/**
	 * 手动设置选中页面
	 * @param position
	 */
	public void setSelectionPager(int position){
		mViewPager.setCurrentItem(position);
		setSelectionPoint(position);
	}
	
	/**
	 * 设置指示点的图片
	 * @param position
	 */
	private void setSelectionPoint(int position){
		if(pointCount<1 || position<0){
			return;
		}
		int p = position % pointCount;
		for(int i=0;i<pointCount;i++){
			if(i==p){
				points[i].setImageBitmap(pointSelected);
				//points[i].setImageResource(selectPointResId);
			}else{
				points[i].setImageBitmap(pointUnselected);
				//points[i].setImageResource(unSelectPointResId);
			}
		}
	}
	
	/**
	 * 添加指示点
	 */
	private void addPointsView(){
		//清空已有
		pointBar.removeAllViews();
		LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
        pointParams.setMargins(8, 8,  8, 8);  
        ImageView point = null;
        for(int i=0;i<pointCount;i++){
        	point = new ImageView(mContext);
        	point.setScaleType(ScaleType.CENTER_INSIDE);  
            point.setLayoutParams(pointParams);
            points[i] = point;
            pointBar.addView(point);
        }
        setSelectionPoint(0);
	}
	
	/**
	 * 生成指示点的图片  （可以重写 根据 resId 返回图片）
	 * @param isSelected
	 * @return
	 */
	private Bitmap creatPointBitmap(boolean isSelected){
		Bitmap bmp = Bitmap.createBitmap(21, 21, Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setStyle(Style.FILL);
		if(isSelected){
			p.setColor(Color.parseColor(selectPointColor));
		}else{
			p.setColor(Color.parseColor(unselectPointColor));
		}
		canvas.drawCircle(10, 10, 10, p);
		return bmp;
	}

	@Override
	public void onPageScrollStateChanged(int position) {
		if(mPagerChangeListener!=null){
			mPagerChangeListener.onPageScrollStateChanged(position);
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if(mPagerChangeListener!=null){
			mPagerChangeListener.onPageScrolled(arg0, arg1, arg2);
		}
	}

	@Override
	public void onPageSelected(int position) {
		setSelectionPoint(position);
		if(mPagerChangeListener!=null){
			mPagerChangeListener.onPageSelected(position);
		}
		//定时播放下一页
		if(isAutoPlay){
			if(mRunnable!=null){
				mHandler.removeCallbacks(mRunnable); 
			}
			mRunnable = new MyRunnable();
			mHandler.postDelayed(mRunnable, Duration);
		}
		
	}	
}
