package me.bytebeats.views.rotate3d

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/25 16:41
 * @Version 1.0
 * @Description TO-DO
 */

class DarkSideLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var mCamera: Camera = Camera()

    init {
        visibility = View.GONE
    }

    override fun dispatchDraw(canvas: Canvas?) {
        mCamera.save()
        canvas?.save()
        mCamera.rotateY(180F)
        val matrix = Matrix()
        mCamera.getMatrix(matrix)
        matrix.preTranslate(-width / 2F, -height / 2F)
        matrix.postTranslate(width / 2F, height / 2F)
        canvas?.setMatrix(matrix)
        mCamera.restore()
        super.dispatchDraw(canvas)
        canvas?.restore()
    }
}