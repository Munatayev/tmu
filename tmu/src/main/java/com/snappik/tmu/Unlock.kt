package com.snappik.tmu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import java.util.*


class Unlock(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init {
        this.setWillNotDraw(false)
    }

    companion object {
        var redRange = 255
        var greenRange = 255
        var blueRange = 255
    }

    private var initFlag = false
    private lateinit var detector: Detector
    private var mode: Mode = Mode.KEYSET
    private lateinit var classToStart : AppCompatActivity

    private fun init(): Boolean {
        detector = Detector(this)
        return ::detector.isInitialized
    }

    override fun onDraw(canvas: Canvas) {
        validateInitialization()
        for (cell in detector.cells.entries) {
            canvas.drawRect(cell.value.x1, cell.value.y1, cell.value.x2, cell.value.y2, getPaint(cell.value.active))
            canvas.drawText(cell.key.toString(), (cell.value.x1 + cell.value.x2) / 2, (cell.value.y1 + cell.value.y2) / 2, getPaint(cell.value.active))
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val width = width / childCount
        val height = height
        val count = childCount
        val eventX = event.x

        val x = event.x
        val y = event.y

        val touchedCell = detector.getCell(x, y)
        if (touchedCell != null && mode == Mode.KEYSET) {
            detector.addKey(touchedCell)
        }
        if (mode == Mode.UNLOCK) {
            val result = detector.check(touchedCell!!)
            if(result)
                context.startActivity(Intent(context, classToStart::class.java))
        }

        if (eventX < width) {
            getChildAt(0).dispatchTouchEvent(event)
        } else if (eventX > width && eventX < 2 * width) {
            val eventY = event.y
            if (eventY < height / 2) {
                for (i in 0 until count) {
                    val child = getChildAt(i)
                    child.dispatchTouchEvent(event)
                }
            } else {
                getChildAt(1).dispatchTouchEvent(event)
            }
        } else if (eventX > 2 * width) {
            getChildAt(2).dispatchTouchEvent(event)
        }
        if (event.action == MotionEvent.ACTION_UP) {
            detector.cleanTouches()
        }

        invalidate()
        return true
    }

    private fun validateInitialization() {
        initFlag = if (!initFlag) init() else true
    }

    private fun generateRundomInRang(min: Int, max: Int): Int {
        return min + Random().nextInt((max - min) + 1)
    }

    private fun generateRundomColor(): Int {
        val r = generateRundomInRang(0, Unlock.redRange)
        val g = generateRundomInRang(0, Unlock.greenRange)
        val b = generateRundomInRang(0, Unlock.blueRange)
        return Color.rgb(r, g, b)
    }

    private fun getPaint(active: Boolean): Paint {
        val paint = Paint()
        paint.textSize = 30f
        if (!active) {
            paint.style = Paint.Style.STROKE
        }
        paint.color = generateRundomColor()
        return paint
    }

    fun mode(mode: Mode){
        this.mode = mode
    }

    fun activityTostart(class_ : AppCompatActivity){
        this.classToStart = class_
    }

    enum class Mode {
        KEYSET, UNLOCK
    }
}