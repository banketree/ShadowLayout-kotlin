package com.banketree.shadown

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import com.banketree.shadown.utils.DimenUtil.dp2px
import kotlin.math.abs


/**
 * @author caihaifei
 * @time 2019/9/10 16:39
 * @description
 * 阴影布局
 * WebBlog:http://www.enjoytoday.cn
 */
class ShadowLayout : FrameLayout {
    companion object {
        //默认阴影半径
        val SHADOW_DEFAULT_RADIUS = dp2px(5f)

        //阴影最大偏移量
        val SHADOW_MAX_OFFSET = dp2px(20f)

        //阴影最大模糊半径
        val SHADOW_MAX_BLUR = dp2px(20f)

        //默认模糊半径
        val SHADOW_DEFAULT_BLUR_RADIUS = dp2px(5f)
    }


    //阴影颜色
    private var shadowColor = Color.parseColor("#333333")

    //阴影类型,0:默认为单边 1:单边 2:邻边 3:四边所有
    private var shadowType = 0

    //阴影半径
    private var shadowRadius = 0f

    //模糊度半径
    private var blurRadius =
        SHADOW_DEFAULT_BLUR_RADIUS

    //水平位移
    private var xOffset = dp2px(10f)


    //竖直方向位移
    private var yOffset = dp2px(10f)

    //背景色
    private var bgColor = Color.WHITE

    //是否有点击效果
    private var hasEffect = false

    var localLeft = 0
    var localRight: Int = 0
    var localTop: Int = 0
    var localBottom: Int = 0

    //代理方式
    private var shadow: IShadow = ShadowConfig(this)

    private var mWidthMode = 0f
    private var mHeightMode = 0f
    private var mPaint = Paint()
    private var locationPaint = Paint()

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null) //取消硬件加速

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout)
        shadowColor =
            typedArray.getColor(R.styleable.ShadowLayout_shadowColor, Color.BLUE)
        blurRadius = typedArray.getDimension(
            R.styleable.ShadowLayout_blurRadius,
            SHADOW_DEFAULT_BLUR_RADIUS
        )
        shadowRadius = typedArray.getDimension(R.styleable.ShadowLayout_shadowRadius, 0f)
        hasEffect = typedArray.getBoolean(R.styleable.ShadowLayout_hasEffect, false)
        xOffset = typedArray.getDimension(R.styleable.ShadowLayout_xOffset, dp2px(10f))
        yOffset = typedArray.getDimension(R.styleable.ShadowLayout_yOffset, dp2px(10f))
        bgColor =
            typedArray.getColor(R.styleable.ShadowLayout_bgColor, Color.WHITE)
        typedArray.recycle()

        if (shadowRadius < 0) {
            shadowRadius = -shadowRadius
        }
        if (blurRadius < 0) {
            blurRadius = -blurRadius
        }

        blurRadius =
            SHADOW_MAX_BLUR.coerceAtMost(blurRadius)

        if (abs(xOffset) > SHADOW_MAX_OFFSET) {
            xOffset =
                xOffset / Math.abs(xOffset) * SHADOW_MAX_OFFSET
        }

        if (abs(yOffset) > SHADOW_MAX_OFFSET) {
            yOffset =
                yOffset / abs(yOffset) * SHADOW_MAX_OFFSET
        }

        setBackgroundColor(Color.parseColor("#00ffffff"))
        init()
    }

    private fun init() {
        if (xOffset > 0) { //水平偏移量为正数，右侧有阴影，阴影长度为blurRadius+|xOffset|
            localRight = (blurRadius + abs(xOffset)).toInt()
        } else if (xOffset == 0f) { //水平偏移为0,水平间距为blurRadius
            localLeft = blurRadius.toInt()
            localRight = blurRadius.toInt()
        } else { //水平偏移为负数,左侧有阴影，阴影长度为blurRadius+|xOffset|
            localLeft = (blurRadius + abs(xOffset)).toInt()
        }
        if (yOffset > 0) { //竖直偏移量为正数，底部有阴影，阴影长度为blurRadius+|yOffset|
            localBottom = (blurRadius + abs(yOffset)).toInt()
        } else if (yOffset == 0f) { //竖直偏移量为0，竖直间距为blurRadius
            localTop = blurRadius.toInt()
            localBottom = blurRadius.toInt()
        } else { //竖直偏移量为负数，顶部有阴影，阴影长度为blurRadius+|yOffset|
            localTop = (blurRadius + abs(yOffset)).toInt()
        }
        setPadding(localLeft, localTop, localRight, localBottom)
    }

    /**
     * 获取阴影设置
     * @return 返回阴影设置配置
     */
    fun getShadowConfig(): IShadow? {
        return shadow
    }


    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        super.onLayout(changed, l, t, r, b)
    }


    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }


    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas) //放在super前是后景，相反是前景，前景会覆盖子布局
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    //绘制背景色(在子view底部)
    private fun drawBackground(canvas: Canvas) {
        mWidthMode = measuredWidth.toFloat()
        mHeightMode = measuredHeight.toFloat()
        var startX = 0f
        var startY = 0f
        var endX = 0f
        var endY = 0f
        if (xOffset == 0f) {
            startX = localRight.toFloat()
            endX = mWidthMode - blurRadius
        } else {
            startX = localRight + blurRadius
            endX = mWidthMode - localLeft - blurRadius
        }
        if (yOffset == 0f) {
            startY = localBottom.toFloat()
            endY = mHeightMode - blurRadius
        } else {
            startY = localBottom + blurRadius
            endY = mHeightMode - localTop - blurRadius
        }
        //        mPaint.setShadowLayer(blurRadius,0,0,shadowColor);
        if (blurRadius > 0) {
            mPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
        }
        mPaint.color = shadowColor
        mPaint.isAntiAlias = true
        val shadowRect = RectF(startX, startY, endX, endY)
        val locationRectF = RectF(
            localLeft.toFloat(),
            localTop.toFloat(), mWidthMode - localRight, mHeightMode - localBottom
        )
        if (shadowRadius == 0f) { //不是圆角
            canvas.drawRect(shadowRect, mPaint)
        } else { //圆角，角度为shadowRadius
            canvas.drawRoundRect(shadowRect, shadowRadius, shadowRadius, mPaint)
        }
        locationPaint.color = bgColor
        locationPaint.isAntiAlias = true
        if (shadowRadius == 0f) { //不是圆角
            canvas.drawRect(locationRectF, locationPaint)
        } else { //圆角，角度为shadowRadius
            canvas.drawRoundRect(locationRectF, shadowRadius, shadowRadius, locationPaint)
        }
    }

    /**
     * 阴影配置
     */
    inner class ShadowConfig constructor(
        private val shadow: ShadowLayout
    ) : IShadow {

        private fun getLocalResources(): Resources {
            return if (context == null) {
                Resources.getSystem()
            } else {
                context.resources
            }
        }

        override fun setShadowRadius(radius: Float): IShadow? {
            return setShadowRadius(TypedValue.COMPLEX_UNIT_DIP, radius)
        }

        override fun setShadowRadius(unit: Int, radius: Float): IShadow? {
            shadow.shadowRadius =
                abs(TypedValue.applyDimension(unit, radius, getLocalResources().displayMetrics))
            return this
        }

        override fun setShadowColor(color: Int): IShadow? {
            shadow.shadowColor = color
            return this
        }

        override fun setShadowColorRes(colorRes: Int): IShadow? {
            shadow.shadowColor = shadow.resources.getColor(colorRes)
            return this
        }

        override fun setBlurRadius(radius: Float): IShadow? {
            return setBlurRadius(TypedValue.COMPLEX_UNIT_DIP, radius)
        }

        override fun setBlurRadius(unit: Int, radius: Float): IShadow? {
            shadow.blurRadius = Math.min(
                SHADOW_MAX_BLUR, abs(
                    TypedValue.applyDimension(unit, radius, getLocalResources().displayMetrics)
                )
            )
            return this
        }

        override fun setXOffset(offset: Float): IShadow? {
            return setXOffset(TypedValue.COMPLEX_UNIT_DIP, offset)
        }

        override fun setXOffset(unit: Int, offset: Float): IShadow? {
            var x = TypedValue.applyDimension(unit, offset, getLocalResources().displayMetrics)
            if (abs(x) > SHADOW_MAX_OFFSET) {
                x = x / Math.abs(x) * SHADOW_MAX_OFFSET
            }
            shadow.xOffset = x
            return this
        }

        override fun setYOffset(offset: Float): IShadow? {
            return setYOffset(TypedValue.COMPLEX_UNIT_DIP, offset)
        }

        override fun setYOffset(unit: Int, offset: Float): IShadow? {
            var y = TypedValue.applyDimension(unit, offset, getLocalResources().displayMetrics)
            if (abs(y) > SHADOW_MAX_OFFSET) {
                y = y / abs(y) * SHADOW_MAX_OFFSET
            }
            shadow.yOffset = y
            return this
        }

        override fun updateUI() {
            shadow.init()
            shadow.requestLayout()
            shadow.postInvalidate()
        }
    }
}
