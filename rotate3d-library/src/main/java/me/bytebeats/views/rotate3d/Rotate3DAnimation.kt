package me.bytebeats.views.rotate3d

import android.graphics.Camera
import android.view.animation.Animation
import android.view.animation.Transformation
import kotlin.math.absoluteValue

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/25 16:25
 * @Version 1.0
 * @Description TO-DO
 */

class Rotate3DAnimation(private val mFromDegree: Float, private val mToDegree: Float) : Animation() {
    var onRotatingListener: OnRotatingListener? = null

    private lateinit var mCamera: Camera
    private var mCenterX: Int = 0
    private var mCenterY: Int = 0
    private var mWidth = 0

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        mWidth = width
        mCenterX = width / 2
        mCenterY = height / 2
        mCamera = Camera()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        val degree = mFromDegree + interpolatedTime * (mToDegree - mFromDegree)
        onRotatingListener?.onRotating(interpolatedTime, degree)
        if (t != null) {
            val matrix = t.matrix
            mCamera.save()
            if (interpolatedTime >= .5F) {
                mCamera.translate(0F, 0F, (interpolatedTime - 1.0F).absoluteValue / .5F * mWidth / 2F)
            } else {
                mCamera.translate(0F, 0F, interpolatedTime / .5F * mWidth / 2F)
            }
            mCamera.rotateY(degree)
            mCamera.getMatrix(matrix)
            matrix.preTranslate(-mCenterX.toFloat(), -mCenterY.toFloat())
            matrix.postTranslate(mCenterX.toFloat(), mCenterY.toFloat())
            mCamera.restore()
        }
    }

    interface OnRotatingListener {
        fun onRotating(progress: Float, degree: Float)
    }
}