package czrbt.lzy.mylibrary.utils;
// @author: lzy  time: 2016/11/04.


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

public class AnimationUtils {

    private View view;
    private ObjectAnimator objectAnimator;
    private AnimationSet animationSet;
    public AnimationUtils(View view){
        animationSet=new AnimationSet(true);
        this.view=view;
        view.measure(0,0);
    }
    //属性动画
    public void addObjAnim(PropertyValuesHolder... values){
        if (objectAnimator==null){
            objectAnimator=ObjectAnimator.ofPropertyValuesHolder(view,values);
        }else {
            PropertyValuesHolder[] holders = objectAnimator.getValues();
            PropertyValuesHolder[] newValues=new PropertyValuesHolder[holders.length+values.length];
            System.arraycopy(holders,0,newValues,0,holders.length);
            System.arraycopy(values,0,newValues,holders.length,values.length);
            objectAnimator.setValues(newValues);
        }
    }
    public void addAlphaAnim(float... F){
        addObjAnim(PropertyValuesHolder.ofFloat("alpha", F));
    }
    public void addRotateAnim(float... F){
        addObjAnim(PropertyValuesHolder.ofFloat("alpha", F));
    }
    public void addScaleXAnim(float... F){
        addObjAnim(PropertyValuesHolder.ofFloat("scaleX",F));
    }
    public void addScaleYAnim(float... F){
        addObjAnim(PropertyValuesHolder.ofFloat("scaleY",F));
    }
    public void addTranslationXAnim(float... F){
        addObjAnim(PropertyValuesHolder.ofFloat("translationX",F));
    }
    public void addTranslationYAnim(float... F){
        addObjAnim(PropertyValuesHolder.ofFloat("translationY",F));
    }
    public void startObjAnim(long during){
        if (objectAnimator!=null) {
            objectAnimator.setDuration(during);
            objectAnimator.start();
        }
    }
    public ObjectAnimator getObjectAnimator() {
        return objectAnimator;
    }

    public void addObjListener(Animator.AnimatorListener listener){
        objectAnimator.addListener(listener);
    }
    public void addListener(Animation.AnimationListener listener){
        animationSet.setAnimationListener(listener);
    }

    /**补间动画
     * 淡入淡出                                       from,to
     AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
     * 旋转
     *参数1：从哪个旋转角度开始,参数2：转到什么角度
     *后4个参数用于设置围绕着旋转的圆的圆心在哪里
     *参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
     *参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
     *参数5：确定y轴坐标的类型,参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
     RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
            Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0.5f);
     *收缩
     *参数1：x轴的初始值,参数2：x轴收缩后的值
     *参数3：y轴的初始值,参数4：y轴收缩后的值
     *参数5：确定x轴坐标的类型,参数6：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
     *参数7：确定y轴坐标的类型,参数8：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
     ScaleAnimation scaleAnimation = new ScaleAnimation(
            0, 0.1f,0,0.1f,
            Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0.5f);
     *移动
     *参数1～2：x轴的开始位置
     *参数3～4：y轴的开始位置
     *参数5～6：x轴的结束位置
     *参数7～8：x轴的结束位置
     TranslateAnimation translateAnimation =
     new TranslateAnimation(
            Animation.RELATIVE_TO_SELF,0f,
            Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0f,
            Animation.RELATIVE_TO_SELF,0.5f);
     */
    public void addAnim(Animation a){
        animationSet.addAnimation(a);
    }
    public void startAnim(long during){
        animationSet.setDuration(during);
        view.startAnimation(animationSet);
    }

    //value 动画
    public static void startValueAnim(ValueAnimator.AnimatorUpdateListener updateListener,
                                      TypeEvaluator<? extends PointF> typeEvaluator,
                                      long during,
                                      PointF startPoint, PointF endPoint) {
        ValueAnimator anim = ValueAnimator.ofObject(typeEvaluator, startPoint, endPoint);
        anim.addUpdateListener(updateListener);
        anim.setDuration(during);
        anim.start();
    }
    public static void startValueAnim(ValueAnimator.AnimatorUpdateListener updateListener,
                                      ValueAnimator anim,long during) {
        if (anim!=null) {
            anim.addUpdateListener(updateListener);
            anim.setDuration(during);
            anim.start();
        }
    }
}
