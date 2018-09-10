package com.snappik.tmu

class Cell(x1: Int, y1: Int, x2: Int, y2: Int, touched: Boolean = false) {
    var x1: Float = x1.toFloat()
    var y1: Float = y1.toFloat()
    var x2: Float = x2.toFloat()
    var y2: Float = y2.toFloat()
    var active: Boolean = touched
}