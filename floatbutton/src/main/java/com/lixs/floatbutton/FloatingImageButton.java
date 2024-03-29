package com.lixs.floatbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageButton;

/**
 * FloatingActionButton 不具备的功能
 */
public class FloatingImageButton extends AppCompatImageButton {
    Paint mPaint;

    /**
     * 不做绘画操作
     */
    static final int DRAW_NULL = 0;
    /**
     * 画圆环
     */
    static final int DRAW_RING = 1;
    /**
     * 画圆
     */
    static final int DRAW_CIRCLE = 2;
    /**
     * 画圆和圆环
     */
    static final int DRAW_CIRCLE_AND_RING = 3;
    int mDrawType;

    public FloatingImageButton(Context context) {
        this(context, null);
    }

    public FloatingImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatingImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //消除锯齿
    }

    /**
     * 在图片中间画圆环(按钮小圆环)
     *
     * @param color 圆的颜色
     */
    public void drawMiniRing(int color) {
        drawRing(DimenUtils.dp2pxInt(1), color);
    }

    /**
     * 在图片中间画圆环(按钮大圆环)
     *
     * @param color 圆的颜色
     */
    public void drawLargeRing(int color) {
        drawRing(DimenUtils.dp2pxInt(3), color);
    }

    /**
     * 在图片中间画圆环
     *
     * @param size  圆的半径
     * @param color 圆的颜色
     */
    public void drawRing(int size, int color) {
        mPaint.setColor(color);
        mPaint.setStrokeWidth(size);
        mPaint.setStyle(Paint.Style.FILL); //绘制空心圆
        mDrawType = DRAW_RING;
        postInvalidate();
    }


    /**
     * 在图片中间画圆
     * @param size  圆的半径
     * */
//    public void drawCircle(int size){
//        drawCircle(size, OperationUtils.getInstance().mCurrentColor);
//    }

    /**
     * 在图片中间画圆
     *
     * @param size  圆的半径
     * @param color 圆的颜色
     */
    public void drawCircle(int size, int color) {
        mPaint.setColor(color);
        mPaint.setStrokeWidth(size);
        mPaint.setStyle(Paint.Style.FILL); //绘制实心圆
        mDrawType = DRAW_CIRCLE;
        postInvalidate();
    }

    /**
     * 在图片中间画圆和外圈圆环
     *
     * @param size  圆的半径
     * @param color 圆的颜色
     */
    public void drawCircleAndRing(int size, int color) {
        mPaint.setColor(color);
        mPaint.setStrokeWidth(size);
        mPaint.setStyle(Paint.Style.FILL); //绘制实心圆
        mDrawType = DRAW_CIRCLE_AND_RING;
        postInvalidate();
    }

    /**
     * 清除绘画
     */
    public void clearDraw() {
        mDrawType = DRAW_NULL;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        if (mDrawType == DRAW_RING) {
            canvas.drawCircle(mPaint.getStrokeWidth() / 2, mPaint.getStrokeWidth() / 2, mPaint.getStrokeWidth() / 2, mPaint);
        } else if (mDrawType == DRAW_CIRCLE) {
            canvas.drawCircle(center, center, mPaint.getStrokeWidth(), mPaint);
        } else if (mDrawType == DRAW_CIRCLE_AND_RING) {
            canvas.drawCircle(center, center, mPaint.getStrokeWidth(), mPaint);
            mPaint.setStrokeWidth(DimenUtils.dp2pxInt(1));
            mPaint.setStyle(Paint.Style.STROKE); //绘制空心圆
            canvas.drawCircle(center, center, center - DimenUtils.dp2pxInt(1), mPaint);
        }


    }
}
