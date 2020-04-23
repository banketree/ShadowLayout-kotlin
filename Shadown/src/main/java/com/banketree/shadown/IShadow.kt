package com.banketree.shadown

import androidx.annotation.ColorRes

/**
 * Created by Android Studio.
 * User: caihaifei
 * Date: 2019/9/10
 * Time: 10:26
 * WebBlog:http://www.enjoytoday.cn
 * 阴影抽象接口
 */
interface IShadow {

    //设置阴影半径
    fun setShadowRadius(radius: Float): IShadow?

    //添加单位设置
    fun setShadowRadius(unit: Int, radius: Float): IShadow?

    //设置应用颜色
    fun setShadowColor(color: Int): IShadow?

    //设置阴影颜色资源文件id
    fun setShadowColorRes(@ColorRes colorRes: Int): IShadow?

    /**
     * 设置模糊半径
     * @param radius
     */
    fun setBlurRadius(radius: Float): IShadow?

    /**
     *
     * @param unit @[android.util.TypedValue.TYPE_DIMENSION]
     * @param radius 模糊半径
     */
    fun setBlurRadius(unit: Int, radius: Float): IShadow?


    /**
     * 设置水平方向的偏移量
     * @param offset x轴偏移
     */
    fun setXOffset(offset: Float): IShadow?


    /**
     * 设置x方向的偏移量,设置单位
     * @param unit @[android.util.TypedValue.TYPE_DIMENSION]
     * @param offset x轴偏移
     */
    fun setXOffset(unit: Int, offset: Float): IShadow?

    /**
     * 设置竖直方向的偏移量
     * @param offset y轴偏移
     */
    fun setYOffset(offset: Float): IShadow?

    /**
     * 设置竖直方向的偏移量,带单位
     * @param unit @[android.util.TypedValue.TYPE_DIMENSION]
     * @param offset  y轴偏移
     */
    fun setYOffset(unit: Int, offset: Float): IShadow?

    /**
     * 更新绘制
     */
    fun updateUI()
}