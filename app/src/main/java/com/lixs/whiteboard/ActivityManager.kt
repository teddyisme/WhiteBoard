package com.lixs.whiteboard

import android.app.Activity
import java.lang.ref.WeakReference


/**
 * @description
 * @author  XinSheng
 * @date 2018/5/10
 */
private var sCurrentActivityWeakRef: WeakReference<Activity>? = null

fun getCurrentActivity(): Activity? {
    var currentActivity: Activity? = null
    if (sCurrentActivityWeakRef != null) {
        currentActivity = sCurrentActivityWeakRef?.get()
    }
    return currentActivity
}

fun setCurrentActivity(activity: Activity?) {
    sCurrentActivityWeakRef = WeakReference<Activity>(activity)
}
