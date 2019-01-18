package com.lixs.whiteboard.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.lixs.floatbutton.FloatingActionsMenu
import com.lixs.wblib.DimenUtils
import com.lixs.wblib.DrawTextBoard
import com.lixs.whiteboard.R
import com.lixs.whiteboard.base.BaseActivity
import com.lixs.whiteboard.modle.DrawEraserSize
import com.lixs.whiteboard.modle.DrawPenSize
import com.lixs.whiteboard.views.SelectColorDialog
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 画板页面
 */
class MainActivity : BaseActivity(), View.OnClickListener {
    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun doSomething() {
        initFloatButtons()
    }

    private var mCurrentSize = DrawPenSize.BIG.size
    private var mCurrentEraserSize = DrawPenSize.BIG.size
    private var mCurrentColor = -1
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btSizeLarge -> {
                resetButtons()
                mCurrentSize = DrawPenSize.LARGE.size
                fabMenuSize.collapse()
                drawBoard.setPaintSize(mCurrentSize)
                drawRing(fabMenuSize)
            }
            R.id.btSizeBig -> {
                resetButtons()
                mCurrentSize = DrawPenSize.BIG.size
                fabMenuSize.collapse()
                drawBoard.setPaintSize(mCurrentSize)
                drawRing(fabMenuSize)
            }
            R.id.btSizeMiddle -> {
                resetButtons()
                mCurrentSize = DrawPenSize.MIDDLE.size
                fabMenuSize.collapse()
                drawBoard.setPaintSize(mCurrentSize)
                drawRing(fabMenuSize)
            }
            R.id.btSizeMini -> {
                resetButtons()
                mCurrentSize = DrawPenSize.SMALL.size
                fabMenuSize.collapse()
                drawBoard.setPaintSize(mCurrentSize)
                drawRing(fabMenuSize)
            }
            R.id.btSizeRed -> {
                resetButtons()
                mCurrentColor = ContextCompat.getColor(this, R.color.red)
                fabMenuColor.collapse()
                drawBoard.setPaintColor(mCurrentColor)
                drawRing(fabMenuColor)
                drawBoard.setPaintSize(mCurrentSize)
                drawRing(fabMenuSize)
            }
            R.id.btSizeGreen -> {
                resetButtons()
                mCurrentColor = ContextCompat.getColor(this, R.color.green)
                fabMenuColor.collapse()
                drawBoard.setPaintColor(mCurrentColor)
                drawRing(fabMenuColor)
                drawBoard.setPaintSize(mCurrentSize)
                drawRing(fabMenuSize)
            }
            R.id.btSizeBlue -> {
                resetButtons()
                mCurrentColor = ContextCompat.getColor(this, R.color.blue)
                fabMenuColor.collapse()
                drawBoard.setPaintColor(mCurrentColor)
                drawRing(fabMenuColor)
                drawBoard.setPaintSize(mCurrentSize)
                drawRing(fabMenuSize)
            }
            R.id.btSizeBlack -> {
                resetButtons()
                mCurrentColor = ContextCompat.getColor(this, R.color.black)
                fabMenuColor.collapse()
                drawBoard.setPaintColor(mCurrentColor)
                drawRing(fabMenuColor)
                drawBoard.setPaintSize(mCurrentSize)
                drawRing(fabMenuSize)
            }
            R.id.btEraserLarge -> {
                resetButtons()
                mCurrentEraserSize = DrawEraserSize.LARGE.size
                fabMenuEraser.collapse()
                drawBoard.setPaintEraser(mCurrentEraserSize)
                drawRing(fabMenuEraser)
            }
            R.id.btEraserBig -> {
                resetButtons()
                mCurrentEraserSize = DrawEraserSize.BIG.size
                fabMenuEraser.collapse()
                drawBoard.setPaintEraser(mCurrentEraserSize)
                drawRing(fabMenuEraser)
            }
            R.id.btEraserMiddle -> {
                resetButtons()
                mCurrentEraserSize = DrawEraserSize.MIDDLE.size
                fabMenuEraser.collapse()
                drawBoard.setPaintEraser(mCurrentEraserSize)
                drawRing(fabMenuEraser)
            }
            R.id.btEraserMini -> {
                resetButtons()
                mCurrentEraserSize = DrawEraserSize.SMALL.size
                fabMenuEraser.collapse()
                drawBoard.setPaintEraser(mCurrentEraserSize)
                drawRing(fabMenuEraser)
            }
            R.id.btReset -> {
                AlertDialog.Builder(this)
                    .setMessage("确定清空画布吗?")
                    .setTitle("提示")
                    .setPositiveButton("確定") { _, _ ->
                        drawBoard.clearAll()
                    }
                    .setNeutralButton("取消", null)
                    .create()
                    .show()
            }
            R.id.btLeftBack -> {
                drawBoard.backStep()
            }
            R.id.btRightBack -> {
                drawBoard.forWordStep()
            }
            //选择背景
            R.id.btSelectBg -> {
                SelectColorDialog.instance()
                    .setListener(object : SelectColorDialog.DialogListener {
                        override fun onClickColor(color: Int) {
                            drawBoard.setDrawBoardBgColor(color)
                        }
                    })
                    .showNow(supportFragmentManager, "")
            }
        }

    }

    /**
     * 還原按鈕的狀態
     */
    private fun resetButtons() {
        fabMenuEraser.clearDraw()
        fabMenuColor.clearDraw()
        fabMenuSize.clearDraw()
    }

    /**
     * 按钮左上角画小圆点
     */
    private fun drawRing(menu: FloatingActionsMenu) {
        menu.drawRing(ContextCompat.getColor(this, R.color.gray))
    }

    /**
     * 初始化展开按钮
     */
    private fun initFloatButtons() {
        //笔触粗细
        btSizeLarge.drawCircle(
            DrawPenSize.LARGE.size.toInt(), ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        btSizeBig.drawCircle(
            DrawPenSize.BIG.size.toInt(), ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        btSizeMiddle.drawCircle(
            DrawPenSize.MIDDLE.size.toInt(), ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        btSizeMini.drawCircle(
            DrawPenSize.SMALL.size.toInt(), ContextCompat.getColor(
                this,
                R.color.black
            )
        )

        //笔触颜色
        btSizeRed.drawCircle(DimenUtils.dp2pxInt(20f), ContextCompat.getColor(this, R.color.red))
        btSizeGreen.drawCircle(DimenUtils.dp2pxInt(20f), ContextCompat.getColor(this, R.color.green))
        btSizeBlue.drawCircle(DimenUtils.dp2pxInt(20f), ContextCompat.getColor(this, R.color.blue))
        btSizeBlack.drawCircle(DimenUtils.dp2pxInt(20f), ContextCompat.getColor(this, R.color.black))
        //橡皮尺寸
        btEraserLarge.drawCircle(
            DrawEraserSize.LARGE.size.toInt(), ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        btEraserBig.drawCircle(
            DrawEraserSize.BIG.size.toInt(), ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        btEraserMiddle.drawCircle(
            DrawEraserSize.MIDDLE.size.toInt(), ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        btEraserMini.drawCircle(
            DrawEraserSize.SMALL.size.toInt(), ContextCompat.getColor(
                this,
                R.color.black
            )
        )

        initButtonsListeners()
    }

    /**
     * 初始化按钮的监听事件
     */
    private fun initButtonsListeners() {
        drawBoard.setPaintColor(ContextCompat.getColor(this, R.color.black))
        drawRing(fabMenuSize)

        btSizeLarge.setOnClickListener(this)
        btSizeBig.setOnClickListener(this)
        btSizeMiddle.setOnClickListener(this)
        btSizeMini.setOnClickListener(this)

        btSizeRed.setOnClickListener(this)
        btSizeGreen.setOnClickListener(this)
        btSizeBlue.setOnClickListener(this)
        btSizeBlack.setOnClickListener(this)

        btEraserLarge.setOnClickListener(this)
        btEraserBig.setOnClickListener(this)
        btEraserMiddle.setOnClickListener(this)
        btEraserMini.setOnClickListener(this)

        btSelectBg.setOnClickListener(this)

        btReset.setOnClickListener(this)
        btLeftBack.setOnClickListener(this)
        btRightBack.setOnClickListener(this)
        //重新监听按钮的点击
        fabMenuColor.setOnFloatingActionsMenuClickListener {
            if (fabMenuColor.isExpanded) {
                fabMenuColor.collapse()
            } else {
                fabMenuColor.expand()
            }
            fabMenuSize.collapse()
            fabMenuEraser.collapse()
        }
        fabMenuSize.setOnFloatingActionsMenuClickListener {
            if (fabMenuSize.isExpanded) {
                fabMenuSize.collapse()
            } else {
                fabMenuSize.expand()
            }
            fabMenuColor.collapse()
            fabMenuEraser.collapse()
        }
        fabMenuEraser.setOnFloatingActionsMenuClickListener {
            if (fabMenuEraser.isExpanded) {
                fabMenuEraser.collapse()
            } else {
                fabMenuEraser.expand()
            }
            fabMenuSize.collapse()
            fabMenuColor.collapse()
        }
        drawBoard.setDrawBoardListener(object : DrawTextBoard.DrawBoardListener {
            override fun onOnePathComplate(canLeft: Boolean, canRight: Boolean) {
                checkRedoBtStatus(canLeft, canRight)
            }

            override fun onUndo(canLeft: Boolean, canRight: Boolean) {
                checkRedoBtStatus(canLeft, canRight)
            }

            override fun onForward(canLeft: Boolean, canRight: Boolean) {
                checkRedoBtStatus(canLeft, canRight)
            }


            override fun onClear() {
                checkRedoBtStatus(false, false)
            }

            override fun onTouch() {
                fabMenuSize.collapse()
                fabMenuColor.collapse()
                fabMenuEraser.collapse()
            }
        })
    }

    /**
     * 设置返回和前进按钮的状态
     */
    private fun checkRedoBtStatus(canLeft: Boolean, canRight: Boolean) {
        if (canLeft) {
            btLeftBackIv.setBackgroundResource(R.drawable.ico_left_back)
        } else {
            btLeftBackIv.setBackgroundResource(R.drawable.ico_left_back_disadble)
        }
        if (canRight) {
            btRightBackIv.setBackgroundResource(R.drawable.ico_right_back)
        } else {
            btRightBackIv.setBackgroundResource(R.drawable.ico_right_back_disadble)
        }
    }
}
