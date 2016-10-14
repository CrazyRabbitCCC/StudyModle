package study.lzy.qqimitate.selfView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
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
    private BadgeView badgeView;

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

        badgeView = new BadgeView(context,view);
        badgeView.setText("1");

    }

    public void setBadgeView(String text) {
        if (TextUtils.isEmpty(text))
            badgeView.setLittlePoint();
        else
        if (badgeView.isLittlePoint())
            badgeView=badgeView.getNewView();
            badgeView.setText(text);
        badgeView.show();

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

//    public void onPageScrolled( float positionOffset, int positionOffsetPixels) {
//        if (positionOffset > 0) {
//            if (positionOffset < 0.5) {
//                //  滑动到一半前，上一页的边框保持绿色不变，下一页的边框由灰色变为绿色
//                image.setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
//                //   上一页的内容保持由实心变为透明，下一页的内容保持透明
//                imageTop.setAlpha((1 - 2 * positionOffset));
//                //文字颜色变化
//                mTextColor=mGreenColor;
//                text.setTextColor(mGreenColor);
//
//            } else {
//                //滑动到一半后，上一页的边框由lvse变为灰色，，下一页边框保持绿色不变
//                image.setColorFilter(getGreenToGray(positionOffset), PorterDuff.Mode.SRC_IN);
//                imageTop.setAlpha(0f);//上一页的内容保持透明，下一页的内容由透明变为实心绿色
//                mTextColor=getGreenToGray(positionOffset);
//                text.setTextColor(getGreenToGray(positionOffset));
//            }
//        }
//    }
//
//
//    public void onPageScrolledNext( float positionOffset, int positionOffsetPixels) {
//        if (positionOffset > 0) {
//            if (positionOffset < 0.5) {
//                image.setColorFilter(getGrayToGreen(positionOffset), PorterDuff.Mode.SRC_IN);
//                imageTop.setAlpha(0f);
//                mTextColor=getGrayToGreen(positionOffset);
//
//                text.setTextColor(getGrayToGreen(positionOffset));
//
//
//            } else {
//                //滑动到一半后，上一页的边框由lvse变为灰色，，下一页边框保持绿色不变
//                image.setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
//                //上一页的内容保持透明，下一页的内容由透明变为实心绿色
//                imageTop.setAlpha(2 * positionOffset - 1);
//                mTextColor=mGreenColor;
//                text.setTextColor(mGreenColor);
//
//                if (positionOffset > 0.8) {
//                    imageWhite.setVisibility(View.VISIBLE);
//                    imageWhite.setAlpha(10 * positionOffset - 8);
//                } else {
//                    imageWhite.setVisibility(View.GONE);
//                }
//            }
//        }
//    }

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

    /**
     * 偏移量在 0——0.5区间 ，左边一项颜色不变，右边一项颜色从灰色变为绿色，根据两点式算出变化函数
     *
     * @param positionOffset
     * @return
     */
//    private int getGrayToGreen(float positionOffset) {
//        int red = (int) (positionOffset * (mGreenRed - mGrayRed) * 2 + mGrayRed);
//        int green = (int) (positionOffset * (mGreenGreen - mGrayGreen) * 2 + mGrayGreen);
//        int blue = (int) ((positionOffset) * (mGreenBlue - mGrayBlue) * 2 + mGrayBlue);
//        Log.d("why ", "#### " + red + "  " + green + "  " + blue);
//        return Color.argb(255, red, green, blue);
//    }
    private int getGrayToGreen(float positionOffset) {
        int red = (int) (positionOffset * (mGreenRed - mGrayRed) + mGrayRed);
        int green = (int) (positionOffset * (mGreenGreen - mGrayGreen) + mGrayGreen);
        int blue = (int) ((positionOffset) * (mGreenBlue - mGrayBlue) + mGrayBlue);
        Log.d("why ", "#### " + red + "  " + green + "  " + blue);
        return Color.argb(255, red, green, blue);
    }

    /**
     * 0-1
     *
     * @param positionOffset
     * @return
     */
    private int getGreenToGray(float positionOffset) {
        int red = (int) (positionOffset * (mGrayRed - mGreenRed) + mGrayRed);
        int green = (int) (positionOffset * (mGrayGreen - mGreenGreen) + mGrayGreen);
        int blue = (int) (positionOffset * (mGrayBlue - mGreenBlue) + mGrayBlue);
        Log.d("why ", "#### " + red + "  " + green + "  " + blue);
        return Color.argb(255, red, green, blue);
    }

//    /**
//     * 偏移量在 0.5--1 区间，颜色从绿色变成灰色，根据两点式算出变化函数
//     *
//     * @param positionOffset
//     * @return
//     */
//    private int getGreenToGray(float positionOffset) {
//        int red = (int) (positionOffset * (mGrayRed - mGreenRed) * 2 + 2 * mGreenRed - mGrayRed);
//        int green = (int) (positionOffset * (mGrayGreen - mGreenGreen) * 2 + 2 * mGreenGreen - mGrayGreen);
//        int blue = (int) (positionOffset * (mGrayBlue - mGreenBlue) * 2 + 2 * mGreenBlue - mGrayBlue);
//        Log.d("why ", "#### " + red + "  " + green + "  " + blue);
//        return Color.argb(255, red, green, blue);
//    }

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
