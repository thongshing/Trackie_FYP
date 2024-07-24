package com.example.trackie_fyp.graph

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class CustomComposeMarkerView(
    private val context: Context,
    private val content: @Composable (Entry) -> Unit
) : IMarker {

    private var markerBitmap: Bitmap? = null
    private val paint = Paint().apply {
        isAntiAlias = true
    }
    private var offset = MPPointF()

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            val composeView = ComposeView(context).apply {
                setContent {
                    content(it)
                }
            }

            composeView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    composeView.viewTreeObserver.addOnGlobalLayoutListener {
                        val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                        composeView.measure(measureSpec, measureSpec)
                        composeView.layout(0, 0, composeView.measuredWidth, composeView.measuredHeight)
                        markerBitmap = Bitmap.createBitmap(
                            composeView.measuredWidth,
                            composeView.measuredHeight,
                            Bitmap.Config.ARGB_8888
                        )
                        val canvas = Canvas(markerBitmap!!)
                        composeView.draw(canvas)

                        offset = MPPointF(-(composeView.width / 2f), -composeView.height.toFloat())
                    }
                }

                override fun onViewDetachedFromWindow(v: View) {
                    // Clean up
                }
            })
        }
    }

    override fun getOffset(): MPPointF {
        return offset
    }

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        return offset
    }

    override fun draw(canvas: Canvas, posX: Float, posY: Float) {
        markerBitmap?.let {
            canvas.drawBitmap(it, posX + offset.x, posY + offset.y, paint)
        }
    }
}