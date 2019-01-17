package com.lixs.whiteboard

import com.lixs.wblib.DimenUtils

/**
 * 画笔大小属性
 * @param size 粗细 dp
 */
enum class DrawPenSize(val size: Float) {
    LARGE(DimenUtils.dp2px(8f)),
    BIG(DimenUtils.dp2px(6f)),
    MIDDLE(DimenUtils.dp2px(4f)),
    SMALL(DimenUtils.dp2px(2f)), ;

    companion object {

    }
}
/**
 * 橡皮大小属性
 * @param size 粗细 dp
 */
enum class DrawEraserSize(val size: Float) {
    LARGE(DimenUtils.dp2px(12f)),
    BIG(DimenUtils.dp2px(8f)),
    MIDDLE(DimenUtils.dp2px(4f)),
    SMALL(DimenUtils.dp2px(2f)), ;

    companion object {

    }
}