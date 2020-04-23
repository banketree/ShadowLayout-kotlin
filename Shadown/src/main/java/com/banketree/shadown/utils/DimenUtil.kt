package com.banketree.shadown.utils

import android.content.res.Resources

object DimenUtil {
    fun dp2px(dpValue: Float): Float {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density)
    }
}