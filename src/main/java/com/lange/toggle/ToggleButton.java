package com.lange.toggle;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义滑动开关
 */
public class ToggleButton extends View {
    private Bitmap btn_open_bg;
    private Bitmap btn_close_bg;
    private Bitmap btn_slide_bg;
    private float leftOrRightPadding = 0;//设置触摸按钮左右边距
    public enum ToggleButtonState{
        Open,Close;
    }
    //开关状态变量
    private ToggleButtonState mCurrentState = ToggleButtonState.Close;
    /**
     * 在Java代码中直接new一个CustomView实例的时候，会调用该构造函数
     * @param context
     */
    public ToggleButton(Context context) {
        this(context,null);
    }

    /**
     * 在xml中引用CustomView标签的时候，会调用2参数的构造函数。
     * 这种方式通常是我们需要自定义View的属性的时候，使用2参数的构造函数。
     */
    public ToggleButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载资源
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ToggleButton);
        //设置xml中的背景图
        int imgOpenBgId = a.getResourceId(R.styleable.ToggleButton_btn_open_bg,R.drawable.toggle_open_background);
        setToggleButtonOpenBackgroundResId(context,imgOpenBgId);
        int imgCloseBgId = a.getResourceId(R.styleable.ToggleButton_btn_close_bg,R.drawable.toggle_close_background);
        setToggleButtonCloseBackgroundResId(context,imgCloseBgId);
        //设置xml中的按钮图
        int imgSlideId = a.getResourceId(R.styleable.ToggleButton_btn_slide_bg,R.drawable.toggle_slide_background);
        setToggleButtonSlideResId(context,imgSlideId);
        //设置左右padding,自动转成px
        leftOrRightPadding = a.getDimensionPixelSize(R.styleable.ToggleButton_btn_left_right_padding,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置view的大小区域
        setMeasuredDimension(btn_open_bg.getWidth(),btn_open_bg.getHeight());
    }

    private float currentX;//记录滑动的x位置
    private boolean isSliding = false;//是否正在滑动
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentX = event.getX();
                isSliding = true;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                isSliding = true;
                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                if(event.getX()>btn_open_bg.getWidth()/2){
                    if(mCurrentState == ToggleButtonState.Close) {
                        //打开状态
                        mCurrentState = ToggleButtonState.Open;
                        if (stateChangeListener != null) {
                            stateChangeListener.onStateChange(mCurrentState);
                        }
                    }
                }else {
                    if(mCurrentState == ToggleButtonState.Open){
                        //关闭状态
                        mCurrentState = ToggleButtonState.Close;
                        if(stateChangeListener!=null){
                            stateChangeListener.onStateChange(mCurrentState);
                        }
                    }
                }
                /*if(mCurrentState == ToggleButtonState.Close) {
                    //打开状态
                    mCurrentState = ToggleButtonState.Open;
                }else{
                    //关闭状态
                    mCurrentState = ToggleButtonState.Close;
                }
                if (stateChangeListener != null) {
                    stateChangeListener.onStateChange(mCurrentState);
                }*/
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置背景
        canvas.drawBitmap(mCurrentState== ToggleButtonState.Open?btn_open_bg:btn_close_bg,0,0,null);
        int top = (btn_open_bg.getHeight()-btn_slide_bg.getHeight())/2;
        if(isSliding){
            //设置滑动开关
            float left = currentX-btn_slide_bg.getWidth()/2;
            if(left<leftOrRightPadding)left = leftOrRightPadding;
            if(left>btn_open_bg.getWidth()-btn_slide_bg.getWidth()-leftOrRightPadding){
                left = btn_open_bg.getWidth()-btn_slide_bg.getWidth()-leftOrRightPadding;
            }
            canvas.drawBitmap(btn_slide_bg,left,top,null);
        }else{
            int left = (int)leftOrRightPadding;
            if(mCurrentState== ToggleButtonState.Open){
                left = btn_open_bg.getWidth()-btn_slide_bg.getWidth()-left;
            }
            canvas.drawBitmap(btn_slide_bg,left,top,null);
        }
    }

    /**
     * 代码设置开关的背景图片
     * @param resId
     */
    @SuppressLint("NewApi")
    public void setToggleButtonOpenBackgroundResId(Context context, int resId){
       // btn_bg = BitmapFactory.decodeResource(getResources(),resId);
        Drawable vectorDrawable = context.getDrawable(resId);
        btn_open_bg = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(btn_open_bg);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
    }
    /**
     * 代码设置开关的背景图片
     * @param resId
     */
    @SuppressLint("NewApi")
    public void setToggleButtonCloseBackgroundResId(Context context, int resId){
       // btn_bg = BitmapFactory.decodeResource(getResources(),resId);
        Drawable vectorDrawable = context.getDrawable(resId);
        btn_close_bg = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(btn_close_bg);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
    }

    /**
     * 代码设置开关的按钮图片
     * @param resId
     */
    @SuppressLint("NewApi")
    public void setToggleButtonSlideResId(Context context,int resId){
        //btn_slide_bg = BitmapFactory.decodeResource(getResources(),resId);
        Drawable vectorDrawable = context.getDrawable(resId);
        btn_slide_bg = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(btn_slide_bg);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
    }

    /**
     * 代码设置开关状态
     * @param state
     */
    public void setToggleButtonState(ToggleButtonState state){
        this.mCurrentState = state;
    }

    /**
     * 设置位移动画
     * @param v
     * @param fromX
     * @param toX
     */
    private void setTranslationAnim(View v,int fromX,int toX){
        ObjectAnimator oa = ObjectAnimator.ofFloat(v,"translationX",fromX,toX,0,0);
        oa.setDuration(200);
        oa.start();
    }

    /**
     * 设置监听
     */
    private OnToggleStateChangeListener stateChangeListener;
    public interface OnToggleStateChangeListener{
        void onStateChange(ToggleButtonState state);
    }
    public void setOnToggleStateChangeListener(OnToggleStateChangeListener stateChangeListener){
        this.stateChangeListener = stateChangeListener;
    }
}
