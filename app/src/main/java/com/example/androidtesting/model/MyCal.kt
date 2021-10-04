package com.example.androidtesting.model

import kotlin.math.pow

class MyCal : MyInterFace {
    private val pi = 3.14
    override fun getAreaDoubleValue(radius: Double): Double {
        return pi * (radius.pow(2.toDouble()))
    }

    override fun getCircleDoubleValue(radius: Double): Double {
        return 2 * (pi * radius)
    }
}