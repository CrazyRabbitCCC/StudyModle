package cn.sleepycoder.designexample.stickyheader;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.sleepycoder.designexample.R;

/**
 * Created by Mark on 16/5/7.
 */
public class BannerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(position==0){
            imageView.setImageResource(R.drawable.mv0);
        }else if(position==1){
            imageView.setImageResource(R.drawable.mv1);
        }else{
            imageView.setImageResource(R.drawable.mv2);
        }
        container.addView(imageView);
        return imageView;
    }
}
