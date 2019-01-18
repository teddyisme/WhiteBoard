package com.lixs.whiteboard.base


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class BaseAdapter<T>(private val layoutResourceId: Int, var items: MutableList<T>,
                     private var animationsLocked: Boolean? = true, val init: (View, T, Int) -> Unit) :
        RecyclerView.Adapter<BaseAdapter.ViewHolder<T>>() {

    private var lastAnimatedPosition = -1
    private var delayEnterAnimation = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(layoutResourceId, parent, false)
        return ViewHolder(view, init)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bindData(items[position], position)
    }

    override fun getItemCount() = items.size

    class ViewHolder<in T>(view: View, val init: (View, T, Int) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindData(item: T, position: Int) {
            with(item) {
                init(itemView, item, position)
            }
        }
    }

    fun refreshItems(adds: MutableList<T>) {
        items = adds
        notifyDataSetChanged()
    }

    fun addItems(adds: MutableList<T>, position: Int = items.size) {
        items.addAll(position, adds)
        notifyDataSetChanged()
    }

    fun removeItems(po: Int) {
        items.removeAt(po)
        notifyItemRemoved(po)
        notifyItemRangeChanged(po, items.size)
    }

    fun changeItem(po: Int, item11: T) {
        items[po] = item11
        notifyItemChanged(po)
    }

}