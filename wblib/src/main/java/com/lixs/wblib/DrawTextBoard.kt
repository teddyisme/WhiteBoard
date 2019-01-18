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
    private var mBoardListener: DrawBoardListener? = null

    private var mDrawings = mutableListOf<DrawInfo>()
    private var mRemoveDraws = mutableListOf<DrawInfo>()

    private var mDrawType = DrawType.DRAW
    private var mCurrentBoardColor = Color.WHITE

    class DrawInfo(val path: Path, val paint: Paint, val drawType: DrawType) {
        fun draw(mCanvas: Canvas) {
            mCanvas.drawPath(path, paint)
        }
    }

    interface DrawBoardListener {
        fun onTouch()
        fun onUndo(canLeft: Boolean, canRight: Boolean)
        fun onForward(canLeft: Boolean, canRight: Boolean)
        fun onClear()
        fun onOnePathComplate(canLeft: Boolean, canRight: Boolean)
    }

    init {
        setBackgroundResource(R.color.white)
    }

    enum class DrawType {
        DRAW, CLEAR
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mBufferBitmap?.let {
            canvas?.drawBitmap(mBufferBitmap!!, 0f, 0f, null)
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
                mBufferCanvas?.drawPath(mPath!!, mPaint)
                invalidate()
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                saveDrawingPath()
                mPath?.reset()
                mBoardListener?.onOnePathComplate(mDrawings.size > 0, mRemoveDraws.size > 0)
            }
        }
        return true
    }

    /**
     * 初始化二级缓冲画板
     */
    private fun initBuffer() {
        mBufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mBufferCanvas = Canvas(mBufferBitmap!!)
    }

    /**
     * 保存绘画路径
     */
    private fun saveDrawingPath() {
        mPath?.let {
            val cachePath = Path(mPath)
            val cachePaint = Paint(mPaint)
            val drawInfo = DrawInfo(cachePath, cachePaint, mDrawType)
            mDrawings.add(drawInfo)
            mRemoveDraws.clear()
        }
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

    /**
     * 设置画布事件监听
     */
    fun setDrawBoardListener(listener: DrawBoardListener) {
        this.mBoardListener = listener
    }

    /**
     *  清除画布
     */
    fun clearAll() {
        mDrawType = DrawType.CLEAR
        if (mBufferBitmap == null) {
            initBuffer()
        }
        mBufferBitmap?.eraseColor(Color.WHITE)
        mRemoveDraws.clear()
        mDrawings.clear()
        postInvalidate()
        mBoardListener?.onClear()
    }

    /**
     * 撤销一步
     */
    fun backStep() {
        if (mDrawings.size > 0) {
            val info = mDrawings.removeAt(mDrawings.size - 1)
            mRemoveDraws.add(info)
            reDraw()
        }
        mBoardListener?.onUndo(mDrawings.size > 0, mRemoveDraws.size > 0)
    }

    /**
     * 前进一步
     */
    fun forWordStep() {
        if (mRemoveDraws.size > 0) {
            val info = mRemoveDraws.removeAt(mRemoveDraws.size - 1)
            mDrawings.add(info)
            reDraw()
        }
        mBoardListener?.onForward(mDrawings.size > 0, mRemoveDraws.size > 0)
    }

    /**
     * 重新绘画文字
     */
    private fun reDraw() {
        mBufferBitmap?.eraseColor(mCurrentBoardColor)
        for (drawingInfo in mDrawings) {
            drawingInfo.draw(mBufferCanvas!!)
        }
        invalidate()
    }

    /**
     * 设置画板背景颜色
     */
    fun setDrawBoardBgColor(color: Int) {
        mCurrentBoardColor = ContextCompat.getColor(context, color)
        reDraw()
        invalidate()
    }
}