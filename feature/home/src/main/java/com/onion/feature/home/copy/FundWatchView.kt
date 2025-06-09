package com.onion.feature.home.copy

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onion.feature.home.R

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: FundWatchView
 * Author: admin by 张琦
 * Date: 2025/3/30 22:34
 * Description:
 */
class FundWatchView(var ctx: Context, attrs: AttributeSet): View(ctx,attrs) {

    //静态弧度
    private var mStaticArcPaint: Paint = Paint()
    //静态刻度
    private var mStaticScalePaint: Paint = Paint()
    //小圆点
    private var mRoundPaint: Paint = Paint()
    private var mCirclePaint: Paint = Paint()
    private var mCenterTextPaint: Paint = Paint()
    //进度条
    private var mProgressPaint: Paint = Paint()
    private var mTestProgressPaint: Paint = Paint()

    private var mStokeWidth = 5.dp
    private var mHorMargin = 10.dp

    private var mCircleRectF = RectF()

    //进度
    private var mProgress = 0

    private var mWidth = 0
    private var mHeight = 0

    private var mCenterX = 0
    private var mCenterY = 0

    private val mPathAnimator = ValueAnimator.ofFloat(0F,1F)
    private var mPer = 0F
    private var mAnimFinish = true
    private var mType = FundHomeConstant.TabInfo.INCOME
    private var mStatus = FundHomeConstant.TabInfo.HIGH
    private var mValuePer: Double = 0.0
    private var mSweepAngel = 180F

    private var mRadius = 0F
    private var mStartAngel = 180F
    private var mScaleMargin = mStartAngel / 6

    init {
        /**
         * 静态的弧度
         */
        mStaticArcPaint.color = ctx.getColor(R.color.color_E8EAEE)
        mStaticArcPaint.strokeWidth = mStokeWidth.toFloat()
        mStaticArcPaint.isAntiAlias = true
        mStaticArcPaint.style = Paint.Style.STROKE
        mStaticArcPaint.strokeCap = Paint.Cap.ROUND

        /**
         * 刻度
         */
        mStaticScalePaint.color = ctx.getColor(R.color.color_E8EAEE)
        mStaticScalePaint.strokeWidth = 1.dp.toFloat()
        mStaticScalePaint.isAntiAlias = true
        mStaticScalePaint.style = Paint.Style.STROKE
        mStaticScalePaint.strokeCap = Paint.Cap.BUTT
        /**
         * 小圆点
         */
        mRoundPaint.color = Color.WHITE
        mRoundPaint.isAntiAlias = true
        /**
         * 进度条
         */
        mProgressPaint.apply {
            strokeWidth = mStokeWidth.toFloat()
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
        }

        mTestProgressPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        /**
         * 小圆点
         */
        mCirclePaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.WHITE
        }
        mCenterTextPaint.apply {
            color = Color.BLACK
            textSize = 12F.sp
            isAntiAlias = true
        }
    }

    fun setData(): FundWatchView {
        return this
    }

    fun start(){

        /**
         * 动画
         */
        mPathAnimator.interpolator = LinearInterpolator()
        mPathAnimator.duration = 1000
        mPathAnimator.repeatCount = 0
        mPathAnimator.interpolator = DecelerateInterpolator()
        mPathAnimator.addUpdateListener {
            mPer = it.animatedValue as Float

            invalidate()
        }
        mPathAnimator.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                mAnimFinish = true
            }
        })

        startAnim()
    }

    fun startAnim(){
        if(!mPathAnimator.isRunning && mAnimFinish){
            mAnimFinish = false
            mPathAnimator.start()
        }
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        mCenterX = w / 2
        mCenterY = h / 2

        mCircleRectF.left = getPreciseLeft()
        mCircleRectF.top = getPreciseTop()
        mCircleRectF.right = getPreciseRight()
        mCircleRectF.bottom = getPreciseBottom()

        mRadius = ((mCircleRectF.right - mCircleRectF.left) / 2F)
//        mRadius = mCircleRectF.left + ((mCircleRectF.right - mCircleRectF.left) / 2F)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * 计算颜色
         */
        when(mType){
            FundHomeConstant.TabInfo.INCOME,
            FundHomeConstant.TabInfo.DOWN, -> {
//                mProgressPaint.shader = SweepGradient(mCenterX.toFloat(),getPreciseBottom(),
//                    intArrayOf(Color.parseColor("#FE8327"),Color.parseColor("#FF1A0C")), floatArrayOf(0.5F,1F)
//                )
                mProgressPaint.shader = SweepGradient(mCenterX.toFloat(),getPreciseBottom(),
                    intArrayOf(Color.parseColor("#27DBFE"),Color.parseColor("#19EE97")
                        ,Color.parseColor("#FFC158"),Color.parseColor("#FF2D00")), floatArrayOf(0.5F,0.65F,0.8F,0.9F)
                )
            }
            FundHomeConstant.TabInfo.POP -> {
                mProgressPaint.shader = SweepGradient(mCenterX.toFloat(),getPreciseBottom(),
                    intArrayOf(Color.parseColor("#27DBFE"),Color.parseColor("#19EE97")
                        ,Color.parseColor("#FFC158"),Color.parseColor("#FF2D00")), floatArrayOf(0.5F,0.65F,0.8F,0.9F)
                )
            }
        }
        when(mStatus){
            FundHomeConstant.TabInfo.HIGH -> {
                mSweepAngel = 180F
                val textWidth = mCenterTextPaint.measureText("高")
                canvas?.drawText("高",mCenterX - textWidth / 2,mCenterY + 12F.sp,mCenterTextPaint)
            }
            FundHomeConstant.TabInfo.MIDDLE -> {
                mSweepAngel = 95F
                val textWidth = mCenterTextPaint.measureText("中")
                canvas?.drawText("中",mCenterX - textWidth / 2,mCenterY + 12F.sp,mCenterTextPaint)
            }
            FundHomeConstant.TabInfo.LOW -> {
                mSweepAngel = 0F
                val textWidth = mCenterTextPaint.measureText("低")
                canvas?.drawText("低",mCenterX - textWidth / 2,mCenterY + 12F.sp,mCenterTextPaint)
            }
            0 -> {
                mSweepAngel = 0F
                val textWidth = mCenterTextPaint.measureText("低")
                canvas?.drawText("低",mCenterX - textWidth / 2,mCenterY + 12F.sp,mCenterTextPaint)
            }
        }
        mSweepAngel = (180 * mValuePer).toFloat()

        canvas?.let {
            drawStaticArc(it)
            drawStaticScale(it)
            drawProgress(it)
        }
    }

    private fun drawProgress(canvas: Canvas) {
        val starAngle = mStartAngel
        val sweepAngle = mSweepAngel * mPer

        canvas.drawArc(mCircleRectF,starAngle,sweepAngle,false,mProgressPaint)
        /**
         * 画白色小圆点
         */
        val startX = mRadius * cos(Math.toRadians((starAngle + sweepAngle).toDouble())).toFloat()
        val radius = mRadius
        val startY = radius * sin(Math.toRadians(((starAngle + sweepAngle).toDouble()))).toFloat()

        canvas.drawCircle(mCenterX + startX,startY + (getPreciseBottom() - getPreciseTop()) / 2 + getPreciseTop(),2.dp.toFloat(),mCirclePaint)
    }

    /**
     * 画静态刻度尺
     */
    private fun drawStaticArc(canvas: Canvas) {
        canvas.drawArc(mCircleRectF,mStartAngel,mStartAngel,false,mStaticArcPaint)
    }
    /**
     * 刻度
     */
    private fun drawStaticScale(canvas: Canvas){
        for(index in 0 until 7){
            val radius = mRadius - mStokeWidth
            val startX = radius * cos(Math.toRadians((mStartAngel + index * mScaleMargin).toDouble())).toFloat()
            val startY = radius * sin(Math.toRadians((mStartAngel + index * mScaleMargin).toDouble())).toFloat()

            val stopX = (radius - 3.dp) * cos(Math.toRadians((mStartAngel + index * mScaleMargin).toDouble())).toFloat()
            val stopY = (radius - 3.dp) * sin(Math.toRadians((mStartAngel + index * mScaleMargin).toDouble())).toFloat()

            canvas.drawLine(mCenterX + startX,startY + (getPreciseBottom() - getPreciseTop()) / 2 + getPreciseTop(),mCenterX + stopX,stopY + (getPreciseBottom() - getPreciseTop()) / 2 + getPreciseTop(),mStaticScalePaint)
        }
    }
    private fun getPreciseLeft(): Float {
        return mCenterX - (mHeight / 2F) - mStokeWidth
    }

    private fun getPreciseTop(): Float {
        return 0F + mStokeWidth
    }

    private fun getPreciseRight(): Float {
        return mCenterX + (mHeight / 2F) + mStokeWidth
    }

    private fun getPreciseBottom(): Float {
        return mHeight + mStokeWidth * 3 + 0F
    }