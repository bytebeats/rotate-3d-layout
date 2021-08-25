package me.bytebeats.views.rotate_3d.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import me.bytebeats.views.rotate3d.Rotate3DLayout

class MainActivity : AppCompatActivity() {
    private val rotate3DLayout by lazy { findViewById<Rotate3DLayout>(R.id.rotate_3d_layout) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn).setOnClickListener {
            rotate3DLayout.start3dRotate()
        }
    }
}