package study.lzy.qqimitate.selfView;
// @author: lzy  time: 2016/09/22.


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import study.lzy.qqimitate.R;

public class TabLayout extends LinearLayout{
    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    class TabView extends LinearLayout{
        private Tab mTab;
        private TextView mTextView;
        private ImageView mIconView;

        private ImageView imageWhite;
        private ImageView imageTop;
        private ImageView image;
        private TextView text;

        private View mCustomView;
        private TextView mCustomTextView;
        private ImageView mCustomIconView;
        public TabView(Context context) {
            super(context);
            LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.tab, null);

            imageWhite = (ImageView) findViewById(R.id.image_white);
            imageTop = (ImageView) findViewById(R.id.image_top);
            image = (ImageView) findViewById(R.id.image);
            text = (TextView) findViewById(R.id.text);

        }


    }



    public class Tab{
        private Object mTag;
        private Drawable mIcon;
        private CharSequence mText;
        private int position;

        private Drawable imageWhite;
        private Drawable imageTop;
        private Drawable image;

        private TabLayout mParent;
        private TabView mView;


        public Object getTag() {
            return mTag;
        }

        public void setTag(Object mTag) {
            this.mTag = mTag;
        }

        public Drawable getIcon() {
            return mIcon;
        }

        public void setIcon(Drawable mIcon) {
            this.mIcon = mIcon;
        }

        public CharSequence getText() {
            return mText;
        }

        public void setText(CharSequence mText) {
            this.mText = mText;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public Drawable getImageWhite() {
            return imageWhite;
        }

        public void setImageWhite(Drawable imageWhite) {
            this.imageWhite = imageWhite;
        }

        public Drawable getImageTop() {
            return imageTop;
        }

        public void setImageTop(Drawable imageTop) {
            this.imageTop = imageTop;
        }

        public Drawable getImage() {
            return image;
        }

        public void setImage(Drawable image) {
            this.image = image;
        }

        public TabLayout getParent() {
            return mParent;
        }

        public void setParent(TabLayout mParent) {
            this.mParent = mParent;
        }

        public TabView getView() {
            return mView;
        }

        public void setView(TabView mView) {
            this.mView = mView;
        }
    }

    public  interface TabSet{
        Tab getTab(int position);
    }
}
