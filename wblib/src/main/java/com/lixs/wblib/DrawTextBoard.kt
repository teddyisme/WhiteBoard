package com.lixs.wblib

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

class DrawTextBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var mPaint: Paint = getPaint()
    private var mPath: Path? = null
    private var mLastX: Float = 0f
    private var mLastY: Float = 0f
    private var mBufferBitmap: Bitmap? = null
    private var mBufferCanvas: Canvas? = null
    private var mBoardListener: DrawBoardListner? = null

    interface DrawBoardListner {
        fun onTouch()
    }

    init {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mBufferBitmap?.let {
            canvas?.drawBitmap(mBufferBitmap, 0f, 0f, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) {
            return false
        }
        val action = event.action and MotionEvent.ACTION_MASK
        val x = event.x
        val y = event.y
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = x
                mLastY = y
                if (mPath == null) {
                    mPath = Path()
                }
                mPath?.moveTo(x, y)
                mBoardListener?.onTouch()
            }
            MotionEvent.ACTION_MOVE -> {
                mPath?.quadTo(mLastX, mLastY, (x + mLastX) / 2, (y + mLastY) / 2)
                if (mBufferBitmap == null) {
                    initBuffer()
                }
                mBufferCanvas?.drawPath(mPath, mPaint)
                invalidate()
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                saveDrawingPath()
                mPath?.reset()
            }
        }
        return true
    }

    /**
     * 初始化二级缓冲画板
     */
    private fun initBuffer() {
        mBufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mBufferCanvas = Canvas(mBufferBitmap)
    }

    /**
     * TODO:保存绘画路径
     */
    private fun saveDrawingPath() {

    }

    private fun getPaint(): Paint {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.isSubpixelText = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = DimenUtils.dp2px(6f)
        paint.isFilterBitmap = true
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = ContextCompat.getColor(context, R.color.colorAccent)
        return paint
    }

    /**
     * 设置画笔粗细
     */
    fun setPaintSize(size: Float) {
        mPaint.strokeWidth = size
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
    }

    /**
     * 设置画笔粗细
     */
    fun setPaintColor(color: Int) {
        mPaint.color = color
    }

    /**
     * 设置橡皮
     */
    fun setPaintEraser(size: Float) {
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        mPaint.strokeWidth = size
    }

    fun setDrawBoardListener(listener: DrawBoardListner) {
        this.mBoardListener = listener
    }
}