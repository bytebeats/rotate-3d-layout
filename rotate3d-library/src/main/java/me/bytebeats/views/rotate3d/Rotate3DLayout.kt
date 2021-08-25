package me.bytebeats.views.rotate3d

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.core.view.isVisible

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/25 16:50
 * @Version 1.0
 * @Description TO-DO
 */

class Rotate3DLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var mMoonLayout: MoonLayout? = null
    private var mDarkSideLayout: DarkSideLayout? = null
    private var mMoonAnimation: Rotate3DAnimation? = null
    private var mDarkSideAnimation: Rotate3DAnimation? = null

    var animationDuration: Long = 500L
        set(value) {
            field = if (value < 100L) 100L else value
            mMoonAnimation?.duration = field
            mDarkSideAnimation?.duration = field
        }

    init {
        mMoonAnimation = Rotate3DAnimation(0F, 180F).apply {
            duration = animationDuration
            interpolator = LinearInterpolator()
            fillAfter = true
//            setAnimationListener(ThrottleAnimationListener())
        }
        mMoonAnimation?.onRotatingListener = object : Rotate3DAnimation.OnRotatingListener {
            override fun onRotating(progress: Float, degree: Float) {
                if (progress >= .5F && mMoonLayout?.isShown == true) {
                    mMoonLayout?.isVisible = false
                    mDarkSideLayout?.isVisible = true
                }
            }
        }
        mDarkSideAnimation = Rotate3DAnimation(180F, 0F).apply {
            duration = animationDuration
            interpolator = LinearInterpolator()
            fillAfter = true
//            setAnimationListener(ThrottleAnimationListener())
        }
        mDarkSideAnimation?.onRotatingListener = object : Rotate3DAnimation.OnRotatingListener {
            override fun onRotating(progress: Float, degree: Float) {
                if (progress >= .5F && mDarkSideLayout?.isShown == true) {
                    mMoonLayout?.isVisible = true
                    mDarkSideLayout?.isVisible = false
                }
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 2) {
            throw IllegalStateException("%s must have 2 children!!".format(Rotate3DLayout::class.java.name))
        }
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is MoonLayout) {
                if (mMoonLayout == null) {
                    mMoonLayout = child
                } else {
                    throw IllegalStateException(
                        "%s has only 1 %s!!".format(
                            Rotate3DLayout::javaClass.name,
                            MoonLayout::javaClass.name
                        )
                    )
                }
            }
            if (child is DarkSideLayout) {
                if (mDarkSideLayout == null) {
                    mDarkSideLayout = child
                } else {
                    throw IllegalStateException(
                        "%s has only 1 %s!!".format(
                            Rotate3DLayout::javaClass.name,
                            DarkSideLayout::javaClass.name
                        )
                    )
                }
            }
        }
    }

    fun start3dRotate() {
        if (mMoonLayout != null && mDarkSideLayout != null) {
            if (mMoonLayout?.isShown == true) {
                startAnimation(mMoonAnimation)
            } else {
                startAnimation(mDarkSideAnimation)
            }
        }
    }

//    class ThrottleAnimationListener : Animation.AnimationListener {
//        private var isAnimFinished = true
//        override fun onAnimationStart(animation: Animation?) {
//            if (!isAnimFinished) {
//                animation?.cancel()
//            }
//            isAnimFinished = false
//        }
//
//        override fun onAnimationEnd(animation: Animation?) {
//            isAnimFinished = true
//        }
//
//        override fun onAnimationRepeat(animation: Animation?) {
//        }
//    }
}