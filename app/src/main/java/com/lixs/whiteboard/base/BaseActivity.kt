package com.lixs.whiteboard.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
abstract
class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initView(savedInstanceState))
        doSomething()
    }

    protected abstract fun initView(savedInstanceState: Bundle?): Int
    protected abstract fun doSomething()
}