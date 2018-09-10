package com.snappik.tmu

import android.util.Log
import android.view.View
import java.util.*

class Detector(view: View) {

    companion object {
        var cellSize: Int = 6
        var userinput = HashMap<Int, Cell>()
        var keyset = HashMap<Int, Cell>()
    }

    var cells = HashMap<Int, Cell>()
    private var attemp: Long = 0

    init {
        init(view)
    }

    private fun init(view: View) {
        val callHeight = view.height / cellSize
        val cellWidth = view.width / cellSize
        var counter = 0
        for (j in 0 until cellSize) {
            for (i in 0 until cellSize) {
                cells.put(counter, Cell(cellWidth * i, callHeight * j, i * cellWidth + cellWidth, j * callHeight + callHeight))
                counter++
            }
        }
    }

    fun getCell(x: Float, y: Float): MutableMap.MutableEntry<Int, Cell>? {
        for (cell in cells.entries) {
            if (cell.value.x1 < x && x < cell.value.x2 && cell.value.y1 < y && y < cell.value.y2) {
                cell.value.active = true
                return cell
            }
        }
        return null
    }

    fun cleanTouches() {
        cells.forEach { x -> x.value.active = false }
    }

    fun addKey(cell: MutableMap.MutableEntry<Int, Cell>) {
        if (Detector.keyset.size < 30) {
            Detector.keyset.put(cell.key, cell.value)
        }
    }

    fun check(result : MutableMap.MutableEntry<Int, Cell>): Boolean {
        if ((System.currentTimeMillis() - attemp) < 2000) {
            attemp = System.currentTimeMillis()
            if (userinput.keys.containsAll(keyset.keys)) {
                attemp = System.currentTimeMillis();
                userinput = HashMap<Int, Cell>()
                return true
            } else {
                userinput.put(result.key, result.value)
            }
        } else {
            attemp = System.currentTimeMillis();
            userinput = HashMap<Int, Cell>()
            userinput.put(result.key, result.value)
        }
        return false;
    }
}