package com.nurazlib.frameart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.graphics.Path

// Kelas untuk menangani canvas menggambar
class DrawingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint().apply {
        color = 0xFF000000.toInt()  // Warna hitam
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private val paths = mutableListOf<Path>()
    private var currentPath = Path()

    init {
        paths.add(currentPath)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(event.x, event.y)
                invalidate()  // Meminta ulang gambar
            }
            MotionEvent.ACTION_UP -> {
                val newPath = Path(currentPath)
                paths.add(newPath)
                currentPath = Path()  // Reset currentPath setelah selesai menggambar
                paths.add(currentPath)
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {  // Tidak nullable
        super.onDraw(canvas)
        paths.forEach { path ->
            canvas.drawPath(path, paint)
        }
    }

    // Fungsi untuk membersihkan canvas
    fun clearCanvas() {
        paths.clear()
        currentPath.reset()
        invalidate()
    }

    // Fungsi untuk menambah layer (path baru)
    fun addLayer() {
        val newPath = Path()
        paths.add(newPath)
        invalidate()
    }
}
