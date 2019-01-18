package com.lixs.whiteboard

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.lixs.whiteboard.utils.setCurrentActivity
import java.util.*


/**
 * @description
 * @author  XinSheng
 * @date 2018/9/20
 */
class WBApp : Application() {
    /**
     * 维护Activity的List
     */
    private val mActivates = Collections.synchronizedList(LinkedList<Activity>())

    companion object {
        lateinit var appInstance: WBApp
    }

    init {

    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        initLifecycle()
    }

    private fun initLifecycle() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity?) {
            }

            override fun onActivityResumed(p0: Activity?) {
                setCurrentActivity(p0)
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivityDestroyed(p0: Activity?) {
                if (null == p0 && mActivates.isEmpty()) {
                    return
                }
                if (mActivates.contains(p0)) {
                    mActivates.remove(p0)
                }
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
                if (null == mActivates) {
                    return
                }
                mActivates.add(p0)
            }
        })
    }

    fun finishAllActivity() {
        if (mActivates == null || mActivates.isEmpty()) {
            return
        }
        for (activity in mActivates) {
            activity.finish()
        }
    }

}