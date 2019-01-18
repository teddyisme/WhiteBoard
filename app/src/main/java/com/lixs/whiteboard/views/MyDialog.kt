package com.lixs.whiteboard.views

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.lixs.whiteboard.R
import com.lixs.whiteboard.base.BaseAdapter
import kotlinx.android.synthetic.main.dialog_my.view.*
import kotlinx.android.synthetic.main.item_bg_color.view.*


class MyDialog : DialogFragment() {
    private var mContext: Context? = null

    private var mListener: DialogListener? = null

    interface DialogListener {
        fun onClickColor(color: Int)
    }

    private var mColors =
        mutableListOf(
            R.color.white,
            R.color.drawBoard1,
            R.color.drawBoard2,
            R.color.drawBoard3,
            R.color.drawBoard4,
            R.color.drawBoard5,
            R.color.drawBoard6,
            R.color.drawBoard7
        )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE) //无标题
        val view = inflater.inflate(R.layout.dialog_my, container)
        view.listView.apply {
            adapter = BaseAdapter(R.layout.item_bg_color, mColors) { view, item, _ ->
                view.viewPie.setBackgroundResource(item)
                view.setOnClickListener {
                    mListener?.onClickColor(item)
                    dismiss()
                }
            }
            layoutManager = GridLayoutManager(mContext, 4)
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        dialog?.window?.setLayout((dm.widthPixels * 0.75).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun setListener(listener: DialogListener): MyDialog {
        this.mListener = listener
        return this
    }

    companion object {
        fun instance(): MyDialog {
            return MyDialog()
        }
    }
}