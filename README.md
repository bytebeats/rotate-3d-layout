# rotate-3d-layout
3D rotate layout.

<br>用于实现3D 翻转效果的 ViewGroup.

### Effect

<img src="/media/rotate-3d.gif" width="360" height="720"/>

### How to use/使用说明

#### 1, layout.xml

```
    <me.bytebeats.views.rotate3d.Rotate3DLayout
        android:id="@+id/rotate_3d_layout"
        android:layout_width="match_parent"
        android:layout_marginStart="50dp"
        android:layout_marginEnd="50dp"
        android:layout_marginTop="20dp"
        android:layout_height="match_parent">

        <me.bytebeats.views.rotate3d.DarkSideLayout
            android:layout_width="match_parent"
            android:visibility="gone"
            android:layout_height="match_parent">

            <LinearLayout
                android:gravity="top|center_horizontal"
                android:orientation="vertical"
                android:layout_gravity="center"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
                
                //...
            </LinearLayout>
        </me.bytebeats.views.rotate3d.DarkSideLayout>

        <me.bytebeats.views.rotate3d.MoonLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:orientation="vertical"
                android:layout_gravity="center"
                android:gravity="top|center_horizontal"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

                //...
            </LinearLayout>

        </me.bytebeats.views.rotate3d.MoonLayout>
    </me.bytebeats.views.rotate3d.Rotate3DLayout>
```
<br>`MoonLayout` is used to display the positive page. `MoonLayout`展示正面 UI.
<br>`DarkSideLayout` is used to display the negative page. `DarkSideLayout`展示反面 UI.

#### 2, 启动翻页

```
        findViewById<Button>(R.id.btn).setOnClickListener {
            rotate3DLayout.start3dRotate()
        }
```

### Under the hood/原理

<br>Rely on `Rotate3DAnimation` to implement this effect. Here is the code:
```
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
```

## MIT License

    Copyright (c) 2021 Chen Pan

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
