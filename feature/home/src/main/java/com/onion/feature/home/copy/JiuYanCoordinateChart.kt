package com.onion.feature.home.copy

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.DashPathEffect
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import java.text.SimpleDateFormat
import java.util.Random

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: JiuYanCoordinateChart
 * Author: admin by 张琦
 * Date: 2025/3/30 22:07
 * Description:
 */
class JiuYanCoordinateChart(var ctx: Context, var attrs: AttributeSet) : FrameLayout(ctx, attrs) {
    var TAG = "JiuYanCoordinateChart"

    private val <T : Number> T.correctSp
        get() = sp2px(toFloat()).toFloat()

    private val <T : Number> T.correctDp
        get() = dp2px(toFloat()).toFloat()

    private var mMatrix: Matrix = Matrix()

    private var mMaxDay = 90
    private var mFormat = SimpleDateFormat("yyyy-MM-dd")
    /**
     * 手势控制器
     */
    private var mJiuYanGestureListener: JiuYanGestureListener = JiuYanGestureListener()
    private var mJiuYanGestureDetector: GestureDetector = GestureDetector(ctx,mJiuYanGestureListener)
    /**
     * fling控制器
     */
    private var mJiuYanScroller: Scroller = Scroller(ctx)

    /**
     * 动画类
     */
    private var mLineAnimator = ValueAnimator.ofFloat(0F, 1F)
    private var mLineAnimFinish = true

    private var mBlockAnimator = ValueAnimator.ofFloat(0F, 1F)
    private var mBlockAnimFinish = true
    /**
     * 坐标的笔
     */
    private var mCoordinatePaint = Paint()

    private var mCoordinateTestPaint = Paint()

    /**
     * 虚线paint
     */
    private var mDottedPaint = Paint()

    /**
     * 阴影的画笔
     */
    private var mShadowPaint = Paint()
    private var mShadowMargin = 15.correctDp

    /**
     * 实线paint
     */
    private var mSolidPaint = Paint()
    /**
     * 左边的坐标
     */
    private var mStartCoordinateList: ArrayList<Coordinate> = arrayListOf()

    /**
     * 右边的坐标
     */
    private var mEndCoordinateList = arrayListOf<Coordinate>()

    /**
     * 下面的日期坐标
     */
    private var mBottomCoordinateList = arrayListOf<Coordinate>()

    private var mWidth = 0
    private var mHeight = 0

    /**
     * 上面的距离
     */
    private var mTopHeight = 20.correctDp

    /**
     * 下面的距离 画日期
     */
    private var mBottomHeight = 20.correctDp

    /**
     * 左右边的边距
     */
    private var mStartMargin = 0.correctDp
    private var mEndMargin = 0.correctDp

    /**
     * 实线开始的坐标
     */
    private var mStartSolidX = 0F
    private var mEndSolidX = 0F
    /**
     * 字左边和右边的margin
     */
    private var mStartTextMargin = 5.correctDp
    private var mEndTextMargin = 5.correctDp

    /**
     * 最大的字的长度
     */
    private var mStartMaxTextWidth = 0F
    private var mEndMaxTextWidth = 0F

    /**
     * 实线
     */
    private var mSolidLinePath = Path()
    private var mSolidCenterLinePath = Path()
    private var mSolidCenterPaint = Paint()

    /**
     * 虚线
     */
    private var mDottedLinePath = Path()

// 线的有关对象
    /**
     * 折线图的paint 多个折线图
     */
    private var mLineWrapperList = arrayListOf<LineWrapper>()
    private var mLineTranslate = 0F
    private var mLineVisibleRectF = RectF()
    private var mDateVisibleRectF = RectF()
    private var mScaleX: Float = 1F
    private var mScaleY: Float = 1F
    private var mScaleCenterX = 0F
    private var mScaleCenterY = 0F
    /**
     * 块状图
     */
    private var mBlockList = arrayListOf<Block>()
    private var mBlockPaint = Paint()
    /**
     * 日期
     */
    private var mDatePaint: Paint = Paint()
    private var mStartDate: Long = 0L
    private var mEndDate: Long = 0L
    private var mDateList: ArrayList<JiuYanDate> = arrayListOf()
    init {
        mJiuYanGestureDetector.setOnDoubleTapListener(JiuYanDoubleListener())
        mCoordinatePaint.apply {
            color = Color.parseColor("#63677e")
            isAntiAlias = true
            isDither = true
            style = Paint.Style.FILL
            textAlign = Paint.Align.RIGHT
            textSize = 8F.correctSp
        }

        mCoordinateTestPaint.apply {
            color = Color.parseColor("#ff00ff")
            isAntiAlias = true
            isDither = true
            style = Paint.Style.FILL
            textAlign = Paint.Align.LEFT
            textSize = 28F.correctSp
        }

        mShadowPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            isDither = true
            maskFilter = BlurMaskFilter(20F,BlurMaskFilter.Blur.NORMAL)
        }
        mDottedPaint.apply {
            color = Color.parseColor("#63677e")
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            pathEffect = DashPathEffect(floatArrayOf(3F, 3F), 0F)
            strokeWidth = 0.5.correctDp
        }

        mSolidPaint.apply {
            color = Color.parseColor("#63677e")
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            strokeWidth = 0.7.correctDp
        }

        mSolidCenterPaint.apply {
            color = Color.parseColor("#666666")
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            strokeWidth = 1.correctDp
        }

        mBlockPaint.apply {
            color = Color.parseColor("#e45398")
            isAntiAlias = true
            isDither = true
            style = Paint.Style.FILL
        }

        mDatePaint.apply {
            color = Color.parseColor("#63677e")
            isAntiAlias = true
            isDither = true
            style = Paint.Style.FILL
            textAlign = Paint.Align.LEFT
            textSize = 8F.correctSp
        }
        setWillNotDraw(false)

        postDelayed({
            /**
             * 模拟数据
             */
            setCoordinate(
                arrayListOf(
                    Coordinate("4800", 4800F),
                    Coordinate("4300", 4300F),
                    Coordinate("3800", 3800F),
                    Coordinate("3300", 3300F),
                    Coordinate("2800", 2800F),
                ),
                arrayListOf(
                    Coordinate("100", 100F),
                    Coordinate("75", 75F),
                    Coordinate("50", 50F),
                    Coordinate("25", 25F),
                    Coordinate("0", 0F),
                    Coordinate("-25", -25F),
                    Coordinate("-50", -50F),
                    Coordinate("-75", -75F),
                    Coordinate("-100", -100F),
                ),
            )

            val lineList = arrayListOf<Line>()
            for (index in 0..365) {
                if(index % 2 == 0){
                    lineList.add(Line(Random().nextInt(30).toFloat()))
                }else{
                    lineList.add(Line(-Random().nextInt(30).toFloat()))
                }
            }
            val lineList2 = arrayListOf<Line>()
            for (index in 0..365) {
                if(index % 2 == 0){
                    lineList2.add(Line(Random().nextInt(30).toFloat()))
                }else{
                    lineList2.add(Line(-Random().nextInt(30).toFloat()))
                }
            }

            val blockList = arrayListOf<Block>()
            for(index in 0..365){
                blockList.add(Block((Random().nextInt(1000) + 3300).toFloat()))
            }
            blockList.last().value = 4800F
            setLineData(
                arrayListOf(
                    LineWrapper(lineList, Paint().apply {
                        color = Color.parseColor("#5f8afd")
                        style = Paint.Style.STROKE
                        isAntiAlias = true
                        isDither = true
                        pathEffect = CornerPathEffect(25.correctDp)
                        strokeWidth = 1.correctDp
                    }),
                )
            )
            setBlockData(blockList)
            setDate(1665998136000,1697534136000)
            start()
        }, 1000)
    }

    /**
     * 设置数据
     */
    fun setCoordinate(
        startCoordinateList: ArrayList<Coordinate>,
        endCoordinateList: ArrayList<Coordinate>
    ): JiuYanCoordinateChart {
        mStartCoordinateList = startCoordinateList
        mEndCoordinateList = endCoordinateList
        return this
    }

    fun setLineData(lineWrapperList: ArrayList<LineWrapper>): JiuYanCoordinateChart {
        mLineWrapperList.clear()
        mLineWrapperList.addAll(lineWrapperList)

        return this
    }

    fun setBlockData(blockList: ArrayList<Block>): JiuYanCoordinateChart{
        mBlockList.clear()
        mBlockList.addAll(blockList)
        return this
    }
    fun setDate(startDate: Long,endDate: Long): JiuYanCoordinateChart{
        mDateList.clear()
        mStartDate = startDate
        mEndDate = endDate
        return this
    }

    fun start() {
        if (ArrayUtil.empty(mStartCoordinateList) || ArrayUtil.empty(mEndCoordinateList)) {
            return
        }

        setPath()
        startAnim()

        invalidate()
    }

    /**
     * 开始动画
     */
    private fun startAnim() {
        mLineWrapperList.forEach {
            it.dstPath.reset()
        }

        if (!mLineAnimator.isRunning && mLineAnimFinish) {
            mLineAnimFinish = false
            mLineAnimator.start()
        }

        if (!mBlockAnimator.isRunning && mBlockAnimFinish) {
            mBlockAnimFinish = false
            mBlockAnimator.start()
        }
    }
    /**
     * 构建出path
     */
    private fun setPath() {
        if (mHeight == 0) {
            return
        }

        /**
         * 算出最大的左边坐标字的宽度
         */
        val mStartMaxCoordinate = mStartCoordinateList.maxByOrNull {
            mCoordinatePaint.measureText(it.title)
        }

        mStartMaxTextWidth = mCoordinatePaint.measureText(mStartMaxCoordinate?.title)

        /**
         * 算出最大的右边坐标字的宽度
         */
        val mEndMaxCoordinate = mEndCoordinateList.maxByOrNull {
            mCoordinatePaint.measureText(it.title)
        }

        mEndMaxTextWidth = mCoordinatePaint.measureText(mEndMaxCoordinate?.title)

        mSolidLinePath.reset()
        mDottedLinePath.reset()
        /**
         * 实线的path
         */
        mSolidLinePath.moveTo(getStartSolidX(), getStartSolidY())
        mSolidLinePath.lineTo(getStartSolidX(), getBottomSolidY())
        mSolidLinePath.lineTo(getEndSolidX(), getBottomSolidY())
        mSolidLinePath.lineTo(getEndSolidX(), getStartSolidY())
        mSolidCenterLinePath.moveTo(getStartSolidX(), getChartCenterY())
        mSolidCenterLinePath.lineTo(getEndSolidX(), getChartCenterY())

        mEndCoordinateList.forEachIndexed { index, coordinate ->

            /**
             * 文字画的坐标
             */
            if (coordinate.point == null) {
                coordinate.point = Point()
            }

            val half =
                (mCoordinatePaint.fontMetrics.descent - mCoordinatePaint.fontMetrics.ascent) / 4

            coordinate.point?.x = (getEndSolidX() + mStartTextMargin).toInt()
            coordinate.point?.y = (getLineHeight() * index + getStartSolidY() + half).toInt()

            /**
             * 虚线的path
             */
            val dottedY = (coordinate.point?.y)?.minus(half)
            if (index != mEndCoordinateList.size - 1 && index != mEndCoordinateList.size / 2) {
                mDottedLinePath.moveTo(getStartSolidX(), dottedY!!)
                mDottedLinePath.lineTo(getEndSolidX(), dottedY)

                if(index == mEndCoordinateList.size / 2){
                    //预留实线的位置
                }
            }

            if (index == 0) {

            }
        }
        mStartCoordinateList.forEachIndexed { index, coordinate ->
            /**
             * 文字画的坐标
             */
            if (coordinate.point == null) {
                coordinate.point = Point()
            }

            val half =
                (mCoordinatePaint.fontMetrics.descent - mCoordinatePaint.fontMetrics.ascent) / 4

            coordinate.point?.x = (getStartSolidX() - mStartTextMargin).toInt()
            coordinate.point?.y = (getStartLineHeight() * index + getStartSolidY() + half).toInt()
        }

        /**
         * 线的数据
         */
        mLineWrapperList.forEachIndexed { index, lineWrapper ->
            val path = lineWrapper.path
            val dstPath = lineWrapper.dstPath
            lineWrapper.lineList.forEachIndexed { lineIndex, line ->

                val x = getStartSolidX() + (lineIndex * getLineBlockWidth()) + (getLineBlockWidth() / 2)
                val y = getPercent(line.value)
                if (lineIndex == 0) {
                    path.moveTo(x, y)
                    dstPath.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }

                line.pointF.x = x
                line.pointF.y = y

            }

            lineWrapper.pathMeasure.setPath(path, false)
        }

        /**
         * 线的动画
         */
        mLineAnimator.interpolator = LinearInterpolator()
        mLineAnimator.duration = getLineAnimDuration()
        mLineAnimator.repeatCount = 0
        mLineAnimator.addUpdateListener {
            val value = it.animatedValue as Float

            mLineWrapperList.forEachIndexed { index, lineWrapper ->
                val length = lineWrapper.pathMeasure.length * value
                lineWrapper.dstPath.reset()
                lineWrapper.pathMeasure.getSegment(0F, length, lineWrapper.dstPath, true)
                /**
                 * 闭合path  暂时不需要 没有渐变
                 */
                Log.d(
                    TAG,
                    "addUpdateListener: ${value} ${length} ${lineWrapper.pathMeasure.length}"
                )
            }
            invalidate()
        }

        mLineAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                mLineAnimFinish = true
            }
        })
        /**
         * 块的数据
         */
        val lineBlockWidth = getLineBlockWidth()
        val blockWidth = getBlockWidth()
        val centerValue = mStartCoordinateList.get(mStartCoordinateList.size / 2).value
        mBlockList.forEachIndexed { index, block ->
            val rectF = block.rectF

            rectF.left = getStartSolidX() + (lineBlockWidth / 4) + (index * lineBlockWidth)
            rectF.right = rectF.left + blockWidth
            /**
             * 是否高于基准线
             */
            if(block.value >= centerValue){
                block.direction = 1

                rectF.bottom = getChartCenterY()
                rectF.top = getStartSolidY() + getBlockPercent(block.value)
                block.height = getChartCenterY() - rectF.top
            }else{
                block.direction = 0

                rectF.top = getChartCenterY()
                rectF.bottom = getStartSolidY() + getBlockPercent(block.value)
                block.height = rectF.bottom - getChartCenterY()
            }
        }
        mBlockList.forEachIndexed { index, block ->
//            Log.d(TAG, "setPath: ${block.direction} ${block.rectF.top} ${block.rectF.bottom} ")

        }
        /**
         * 块的动画
         */
        mBlockAnimator.interpolator = LinearInterpolator()
        mBlockAnimator.duration = getBlockAnimDuration()
        mBlockAnimator.repeatCount = 0
        mBlockAnimator.addUpdateListener {
            val value = it.animatedValue as Float

            mBlockList.forEachIndexed { index, block ->

                if(block.direction == 1){
                    //正值
                    val height = block.height * value
                    block.rectF.top = getChartCenterY() - height
                }else{
                    val height = block.height * value
                    block.rectF.bottom = getChartCenterY() + height
                }
            }

            invalidate()
        }
        mBlockAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                mBlockAnimFinish = true
            }
        })

        /**
         * 日期参数的初始化
         */
        val centerDate = mEndDate - mStartDate
        val dateSize = (mBlockList.size / 20)
        val every = centerDate / dateSize
        val dateBlockWidth = getDateBlockWidth()
        for(index in 0 until dateSize){
            when(index){
                0 -> {
                    val str = mFormat.format(mStartDate)
                    mDateList.add(JiuYanDate(mStartDate,str,getStartSolidX()))
                }
                dateSize - 1 -> {
                    val str = mFormat.format(mEndDate)
                    val strWidth = mDatePaint.measureText(str)
                    val x = getChartMaxWidth() - strWidth + getStartSolidX()
                    Log.d(TAG, "setPath: zhangqi ${getChartMaxWidth()}")
                    mDateList.add(JiuYanDate(mEndDate,str,x))
                }
                else -> {
                    val str = mFormat.format(mStartDate + (every * index))
                    val strWidth = mDatePaint.measureText(str)
                    val x = (index * dateBlockWidth) + (dateBlockWidth / 2) - (strWidth / 2) + getStartSolidX()
                    val jiuYanDate = JiuYanDate(mStartDate + (every * index),str,x)
                    mDateList.add(jiuYanDate)

                }
            }
        }

        /**
         * 画笔设置
         */
        mShadowPaint.shader = LinearGradient(
            0F,getChartCenterY() - mShadowMargin,0F, getChartCenterY() + mShadowMargin, intArrayOf(
                Color.parseColor("#00000000"),
                Color.parseColor("#000000"),
                Color.parseColor("#00000000"),
            ), null, Shader.TileMode.CLAMP
        )
        resetScaleCenter()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        /**
         * 宽和高
         */
        mWidth = w
        mHeight = h
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //onLayout
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.let {
            drawDate(it)
            drawRect(it)
            drawCoordinate(it)
            drawLine(it)
        }
    }
    /**
     * 画日期
     */
    fun drawDate(canvas: Canvas){
        canvas.save()
        canvas.translate(-mLineTranslate,0F)

        /**
         * 设置显示区域
         */
        mDateVisibleRectF.left = (getStartSolidX() + mLineTranslate)
        mDateVisibleRectF.right = (getEndSolidX() + mLineTranslate)
        mDateVisibleRectF.top = getBottomSolidY()
        mDateVisibleRectF.bottom = getBottomSolidY() + mBottomHeight
        canvas.clipRect(mDateVisibleRectF)

        mDateList.forEach {
            Log.d(TAG, "drawDate: ${it.x} ${getBottomSolidY()}")
            canvas.drawText(it.dateStr, it.x,getBottomSolidY() + mBottomHeight,mDatePaint)
        }

        canvas.restore()
    }

    /**
     * 画每日格子
     */
    private fun drawRect(canvas: Canvas) {
        canvas.save()
        canvas.translate(-mLineTranslate,0F)
        canvas.scale(mScaleX,mScaleY)
        /**
         * 设置显示区域
         */
        mLineVisibleRectF.left = (getStartSolidX() + mLineTranslate)
        mLineVisibleRectF.right = (getEndSolidX() + mLineTranslate)
        mLineVisibleRectF.top = getStartSolidY()
        mLineVisibleRectF.bottom = getBottomSolidY()
        canvas.clipRect(mLineVisibleRectF)

        mBlockList.forEachIndexed { index, block ->
            Log.d(TAG, "setPath: zhangqi1  ${block.rectF.right - block.rectF.left}")

            canvas.drawRect(block.rectF,mBlockPaint)
            if(index == mBlockList.size - 1){
            }
        }

        canvas.restore()
    }
    /**
     * 画折线图的折线
     */
    private fun drawLine(canvas: Canvas) {
        canvas.save()
        canvas.translate(-mLineTranslate, 0F)
        canvas.scale(mScaleX,mScaleY,mScaleCenterX,mScaleCenterY)
        /**
         * 设置显示区域
         */
        mLineVisibleRectF.left = (getStartSolidX() + mLineTranslate)
        mLineVisibleRectF.right = (getEndSolidX() + mLineTranslate)
        mLineVisibleRectF.top = getStartSolidY()
        mLineVisibleRectF.bottom = getBottomSolidY()
        canvas.clipRect(mLineVisibleRectF)

        mLineWrapperList.forEachIndexed { index, lineWrapper ->
            canvas.drawPath(lineWrapper.dstPath, lineWrapper.paint)
        }

        canvas.restore()
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)

    }
    /**
     * 画虚线和坐标
     */
    private fun drawCoordinate(canvas: Canvas) {
        /**
         * 画渐变阴影
         */
        val shadowRect = RectF()
        shadowRect.left = getStartSolidX()
        shadowRect.right = getEndSolidX()
        shadowRect.top = getChartCenterY() - mShadowMargin
        shadowRect.bottom = getChartCenterY() + mShadowMargin
        canvas.drawRect(shadowRect,mShadowPaint)

        /**
         * 画实线
         */
        canvas.drawPath(mSolidLinePath, mSolidPaint)
        canvas.drawPath(mSolidCenterLinePath,mSolidCenterPaint)
        /**
         * 画虚线
         */
        canvas.drawPath(mDottedLinePath, mDottedPaint)
        /**
         * 画右边文字
         */
        mCoordinatePaint.textAlign = Paint.Align.RIGHT
        mStartCoordinateList.forEachIndexed { index, coordinate ->
            canvas.drawText(
                coordinate.title,
                coordinate.point?.x?.toFloat() ?: 0F,
                coordinate.point?.y?.toFloat() ?: 0F,
                mCoordinatePaint
            )
        }
        /**
         * 画右边文字
         */
        mCoordinatePaint.textAlign = Paint.Align.LEFT
        mEndCoordinateList.forEachIndexed { index, coordinate ->
            canvas.drawText(
                coordinate.title,
                coordinate.point?.x?.toFloat() ?: 0F,
                coordinate.point?.y?.toFloat() ?: 0F,
                mCoordinatePaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mJiuYanGestureDetector.onTouchEvent(event)
    }

    /**
     * 一系列坐标方法
     */
    /**
     * 得到实线开始的坐标X
     */
    private fun getStartSolidX(): Float {
        mStartSolidX = mStartTextMargin + mStartMaxTextWidth + mEndTextMargin
        return mStartSolidX
    }
    /**
     * 得到实线开始的坐标Y
     */
    private fun getStartSolidY(): Float {
        return mTopHeight
    }

    /**
     * 实线底部最大
     */
    private fun getBottomSolidY(): Float {
        return mHeight - mBottomHeight
    }

    /**
     * 实线的最右边
     */
    private fun getEndSolidX(): Float {
        mEndSolidX = mWidth - (mStartTextMargin + mEndMaxTextWidth + mEndTextMargin)
        return mEndSolidX
    }

    /**
     * 每行的高度
     */
    private fun getLineHeight(): Float {
        if (ArrayUtil.empty(mEndCoordinateList)) {
            return 0F
        }

        return (getBottomSolidY() - getStartSolidY()) / (mEndCoordinateList.size - 1)
    }
    /**
     * 左边每行的高度
     */
    private fun getStartLineHeight(): Float {
        if (ArrayUtil.empty(mStartCoordinateList)) {
            return 0F
        }

        return (getBottomSolidY() - getStartSolidY()) / (mStartCoordinateList.size - 1)
    }

    /**
     * 图的最中心位置
     */
    private fun getChartCenterY(): Float {
        return getStartSolidY() + (getBottomSolidY() - getStartSolidY()) / 2F
    }

    /**
     * 得到每个线之间的width
     */
    private fun getLineBlockWidth(): Float {
        val width = getEndSolidX() - getStartSolidX()
        val lineBlockWidth = width * 1F / mMaxDay
        return lineBlockWidth
    }

    /**
     * 得到块状图
     */
    private fun getBlockWidth(): Float{
        val lineBlockWidth = getLineBlockWidth()
        return lineBlockWidth / 2
    }
    /**
     * 得到日期的width
     */
    private fun getDateBlockWidth(): Float {
        return getChartMaxWidth() / (mBlockList.size / 20)
    }

    /**
     * 得到maxX
     */
    private fun getChartMaxWidth(): Float {
        return getLineBlockWidth() * mLineWrapperList[0].lineList.size
    }

    /**
     * s算出位置
     */
    fun getPercent(value: Float): Float {
        val first = mEndCoordinateList.first().value
        val last = mEndCoordinateList.last().value

        val v = (getBottomSolidY() - getStartSolidY()) * (value - first) / (last - first)

        Log.d(
            TAG,
            "getPercent: ${value} ${first} ${last} ${getStartSolidY()} ${getStartSolidY() + v}"
        )
        return getStartSolidY() + v
    }
    /**
     * s算出位置
     */
    fun getBlockPercent(value: Float): Float {
        val first = mStartCoordinateList.first().value
        val last = mStartCoordinateList.last().value

        val v = (getBottomSolidY() - getStartSolidY()) * (value - first) / (last - first)

        Log.d(
            TAG,
            "getPercent: ${value} ${first} ${last} ${v}}"
        )
        return v
    }

    /**
     * 线动画时间
     */
    fun getLineAnimDuration(): Long{
        return (mLineWrapperList[0].lineList.size / 90F * 1000).toLong()
    }

    fun getBlockAnimDuration(): Long{
        return 1000L
    }
    fun translateLineX(x: Float) {
        val oneScreen = getEndSolidX() - getStartSolidX()
        Log.d(TAG, "translateLineX: ${mLineTranslate} ${x} ${getChartMaxWidth()} ${oneScreen}")
        mLineTranslate += x

        if(mLineTranslate <= 0){
            mLineTranslate = 0F
        }

        if(mLineTranslate >= (getChartMaxWidth() - oneScreen)){
            mLineTranslate = (getChartMaxWidth() - oneScreen)
        }
        invalidate()
    }

    fun scaleXY(){
        val maxScale = 5F
        mScaleX = mScaleX * 1.2F
        mScaleY = mScaleY * 1.2F

        if(mScaleX > maxScale){
            mScaleX = 1F
        }

        if(mScaleY > maxScale){
            mScaleY = 1F
        }

        postInvalidate()
    }

    override fun computeScroll() {

        if(mJiuYanScroller.computeScrollOffset()){
            Log.d(TAG, "computeScroll: ${mJiuYanScroller.startX} ${mJiuYanScroller.currX}")
            translateLineX(mJiuYanScroller.currX.toFloat())
            postInvalidate()
        }

        super.computeScroll()
    }
    fun resetScaleCenter(){
        mScaleCenterX = (getEndSolidX() - getStartSolidX()) / 2
        mScaleCenterY = getChartCenterY()
    }

    class Coordinate(
        var title: String = "",
        var value: Float = 0F,
        var direction: Paint.Align = Paint.Align.LEFT,
        var point: Point? = null, //画的地方
    ) {

    }

    /**
     * 线数据的包装类
     */
    class LineWrapper(
        var lineList: ArrayList<Line> = arrayListOf(),
        var paint: Paint = Paint(),
        var path: Path = Path(),
        var dstPath: Path = Path(),
        var pathMeasure: PathMeasure = PathMeasure()
    ) {

    }
    class Line(
        var value: Float = 0F,
        var pointF: PointF = PointF()
    ) {

    }

    class Block(
        var value: Float = 0F,
        var direction: Int = 0, //1 正值 0 负值
        var rectF: RectF = RectF(),
        var height: Float = 0F
    ){

    }

    class JiuYanDate(
        var date: Long = 0L,
        var dateStr: String = "",
        var x: Float = 0F
    ){

    }
    inner class JiuYanDoubleListener: GestureDetector.OnDoubleTapListener{
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            Log.d(TAG, "onDoubleTap: ")
//            scaleXY()
            return true
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            return true
        }

    }
    inner class JiuYanGestureListener: GestureDetector.OnGestureListener{
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onShowPress(e: MotionEvent) {
            //onShowPress
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            Log.d("TAG", "onScroll: ${distanceX}")
            translateLineX(distanceX)
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            startAnim()
        }
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.d(TAG, "onFling: ${e2.getRawX()}")
            val startX = e1?.rawX?.toInt() ?: 0
            val startY = e1?.rawY?.toInt() ?: 0

//            mJiuYanScroller.fling(scrollX.toInt(),0,-velocityX.toInt(),0,0,
//                getChartMaxWidth().toInt(),0,0)
            return true
        }

    }

    fun dp2px(dpValue: Float): Int {
        val scale = ctx
            .getResources()
            .displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val fontScale = ctx
            .getResources()
            .displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}