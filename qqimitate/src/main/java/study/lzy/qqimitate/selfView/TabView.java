package study.lzy.qqimitate.selfView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import study.lzy.qqimitate.R;

// @author: lzy  time: 2016/09/23.


public class TabView extends FrameLayout {

    private ImageView imageWhite;
    private ImageView imageTop;
    private ImageView image;
    private TextView text;

    private int mGrayColor;
    private int mGrayRed;
    private int mGreenColor;
    private int mGrayGreen, mGrayBlue, mGreenRed, mGreenGreen, mGreenBlue;
    private int mTextColor;

    public TabView(Context context) {
        super(context);
        LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.tab, null);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);


        imageWhite = (ImageView) view.findViewById(R.id.image_white);
        imageTop = (ImageView) view.findViewById(R.id.image_top);
        image = (ImageView) view.findViewById(R.id.image);
        text = (TextView) view.findViewById(R.id.text);
        addView(view, params);
        initColor();
        setSelection(false);
    }

    public void onPageScrolled(float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            imageTop.setAlpha((1 - positionOffset));
            image.setColorFilter(getColorChange(1-positionOffset), PorterDuff.Mode.SRC_IN);
            text.setTextColor(getColorChange(1-positionOffset));
            if (positionOffset < 0.2) {
                imageWhite.setVisibility(View.VISIBLE);
                imageWhite.setAlpha(1-5 * positionOffset);
            } else {
                imageWhite.setVisibility(View.GONE);
            }
        }
    }


    public void onPageScrolledNext(float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            image.setColorFilter(getColorChange(positionOffset), PorterDuff.Mode.SRC_IN);
            text.setTextColor(getColorChange(positionOffset));
            imageTop.setAlpha( positionOffset);

            if (positionOffset > 0.8) {
                imageWhite.setVisibility(View.VISIBLE);
                imageWhite.setAlpha(5 * positionOffset - 4);
            } else {
                imageWhite.setVisibility(View.GONE);
            }

        }
    }
    public int  getColorChange(float positionOffset){
        if (positionOffset<0)
            return Color.argb(255, mGrayRed, mGrayGreen, mGrayBlue);
        if (positionOffset>1)
            return Color.argb(255, mGreenRed, mGreenGreen, mGreenBlue);

        int red =mGrayRed+ (int) (positionOffset * (mGreenRed - mGrayRed));
        int green = mGrayGreen +(int) (positionOffset * (mGreenGreen - mGrayGreen) );
        int blue = mGrayBlue +(int) ((positionOffset) * (mGreenBlue - mGrayBlue) );
        Log.d("why ", "#### " + red + "  " + green + "  " + blue);
        return Color.argb(255, red, green, blue);

    }

    public void setSelection(boolean flag) {
        if (flag) {
            image.setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
            imageTop.setAlpha(1f);
            imageWhite.setVisibility(View.VISIBLE);
            mTextColor = mGreenColor;
            text.setTextColor(mGreenColor);
        } else {
            image.setColorFilter(mGrayColor, PorterDuff.Mode.SRC_IN);
            imageTop.setAlpha(0f);
            imageWhite.setVisibility(View.GONE);
            mTextColor = mGrayColor;
            text.setTextColor(mGrayColor);
        }

    }

    public int getTextColor() {
        return mTextColor;
    }

    private int getGrayToGreen(float positionOffset) {
        int red = (int) (positionOffset * (mGreenRed - mGrayRed) + mGrayRed);
        int green = (int) (positionOffset * (mGreenGreen - mGrayGreen) + mGrayGreen);
        int blue = (int) ((positionOffset) * (mGreenBlue - mGrayBlue) + mGrayBlue);
        Log.d("why ", "#### " + red + "  " + green + "  " + blue);
        return Color.argb(255, red, green, blue);
    }

    private int getGreenToGray(float positionOffset) {
        int red = (int) (positionOffset * (mGrayRed - mGreenRed) + mGrayRed);
        int green = (int) (positionOffset * (mGrayGreen - mGreenGreen) + mGrayGreen);
        int blue = (int) (positionOffset * (mGrayBlue - mGreenBlue) + mGrayBlue);
        Log.d("why ", "#### " + red + "  " + green + "  " + blue);
        return Color.argb(255, red, green, blue);
    }

    private void initColor() {
        mGrayColor = getResources().getColor(R.color.gray);
        mTextColor = mGrayColor;
        mGrayRed = Color.red(mGrayColor);
        mGrayGreen = Color.green(mGrayColor);
        mGrayBlue = Color.blue(mGrayColor);
        mGreenColor = getResources().getColor(R.color.green);
        mGreenRed = Color.red(mGreenColor);
        mGreenGreen = Color.green(mGreenColor);
        mGreenBlue = Color.blue(mGreenColor);
    }

    public void setImage(@DrawableRes int image, @DrawableRes int imageTop, @DrawableRes int imageWhite) {
        this.image.setImageResource(image);
        this.imageTop.setImageResource(imageTop);
        if (imageWhite != 0)
            this.imageWhite.setImageResource(imageWhite);
    }

    public void setImage(Drawable image, Drawable imageTop, Drawable imageWhite) {
        this.image.setImageDrawable(image);
        this.imageTop.setImageDrawable(imageTop);
        if (imageWhite != null)
            this.imageWhite.setImageDrawable(imageWhite);
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
