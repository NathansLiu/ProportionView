package com.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.myapplication.R;

/**
 * 作者: Nathans'Liu
 * 邮箱: a1053128464@qq.com
 * 时间: 2017/11/29 15:08
 * 描述:
 */

public class ProportionView2 extends View {
    public int mWide, mHigh;
    private int initialValue = 0;
    private double mDouble, viewWide;
    private Paint mPaint1, mPaint2, mPaint3,mPaint4;
    private String leftString, centreString, rightString;
    public int textSize, textColor, viewbackground1, viewbackground2;
    /**
     * 画笔,文本绘制范围
     */
    private Rect mBound;

    public ProportionView2(Context context) {
        super(context);
    }

    public ProportionView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取xml值
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProportionView_);
        textSize = array.getInt(R.styleable.ProportionView__textSize_, 20);
        textColor = array.getColor(R.styleable.ProportionView__textColor_, getResources().getColor(R.color.colorPrimary));
        viewbackground1 = array.getColor(R.styleable.ProportionView__progressBackground, getResources().getColor(R.color.colorHuangPears));
        viewbackground2 = array.getColor(R.styleable.ProportionView__backgroundColor_, getResources().getColor(R.color.colorFleshcolor));
        mPaint1 = new Paint();
        mPaint2 = new Paint();
        mPaint3 = new Paint();
        mPaint4= new Paint();
        mPaint3.setTextSize(textSize);//设置字体大小
        mPaint3.setColor(textColor);//设置字体颜色
        mPaint4.setColor(getResources().getColor(R.color.colorHuangPears));

        mPaint3.setTextAlign(Paint.Align.CENTER);
        mPaint1.setColor(viewbackground2);//整体背景色
        mPaint2.setColor(viewbackground1);//进度条黄色
        mBound = new Rect();

        array.recycle();//注意回收！！！
    }
    public void leftTextView(String s) {
        this.leftString = s;
    }

    public void rightTextView(String s) {
        this.rightString = s;
    }

    public void proportionTextView(double d) {
        this.mDouble = (int) d;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(viewbackground2);//背景色
            /*
         * 控件宽度/2 - 文字宽度/2
		 */
        float startX = getWidth() / 2 - mBound.width() / 2;
/*
         * 控件高度/2 + 文字高度/2,绘制文字从文字左下角开始,因此"+"
		 */
        // float startY = getHeight() / 2 + mBound.height() / 2;

        Paint.FontMetricsInt fm = mPaint3.getFontMetricsInt();

        // int startY = getHeight() / 2 - fm.descent + (fm.descent - fm.ascent)
        // / 2;
        int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
        //由于计算不精细需要判断100时情况
        if(mDouble >= 100){
            viewWide = mWide;//总长度
            canvas.drawRect(0, 0, mWide, mHigh, mPaint4);//绘制初始进度条 若100则满状态
        }else{
            viewWide = mWide / 100 * mDouble;//总长度
            canvas.drawRect(0, 0, (float) viewWide, mHigh, mPaint4);//绘制初始进度条 若不等100
        }

        canvas.drawRect(0, 0, initialValue, mHigh, mPaint2);//绘制初始进度条
        canvas.drawText(leftString, 50, startY, mPaint3);//左边文字
        canvas.drawText("虚框长："+initialValue + ""+"/"+mDouble+""+"%", mWide / 2, startY, mPaint3);//中间文字
        canvas.drawText("已选择", mWide - 100, startY, mPaint3);//右边文字

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (initialValue <= viewWide) {
                    initialValue = initialValue + 5;
                }else{
                    //当长度绘制完毕 改变画笔mPaint2为中空
                    mPaint2.setStyle(Paint.Style.STROKE);
                }
            }
        }, 0);
        postInvalidate();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWide = w;
        mHigh = h;
        Log.d("长度", viewWide + "");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = onMeasureR(0, widthMeasureSpec);
        int height = onMeasureR(1, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
    /**
     * 计算控件宽高
     *
     * @param
     * @param oldMeasure
     * @author Ruffian
     * 摘自 http://blog.csdn.net/u014702653/article/details/51985821
     */
    public int onMeasureR(int attr, int oldMeasure) {
        String demo = "Nathans'Liu";
        int newSize = 0;
        int mode = MeasureSpec.getMode(oldMeasure);
        int oldSize = MeasureSpec.getSize(oldMeasure);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                newSize = oldSize;
                break;
            case MeasureSpec.AT_MOST:
                float value;

                if (attr == 0) {

                    // value = mBound.width();
                    value = mPaint3.measureText(demo);

                    // 控件的宽度 + getPaddingLeft() + getPaddingRight()
                    newSize = (int) (getPaddingLeft() + value + getPaddingRight());

                } else if (attr == 1) {

                    // value = mBound.height();
                    Paint.FontMetrics fontMetrics = mPaint3.getFontMetrics();
                    value = Math.abs((fontMetrics.bottom - fontMetrics.top));
                    // 控件的高度 + getPaddingTop() + getPaddingBottom()
                    newSize = (int) (getPaddingTop() + value + getPaddingBottom());

                }

                break;
        }

        return newSize;
    }
}
