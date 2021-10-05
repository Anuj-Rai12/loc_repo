package com.example.androidtesting.model

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class MyCalTest {
    private lateinit var myCal: MyCal

    @Before
    fun setUp() {
        myCal = MyCal()
    }

    @Test
    fun testing_Area_radius_feature_file() {
        val radius = myCal.getAreaDoubleValue(2.toDouble())
        assertThat(radius).isEqualTo(12.56)
    }

    @Test
    fun test_for_circle_radius_file() {
        val area = myCal.getCircleDoubleValue(2.1)
        assertThat(area).isEqualTo(13.188)
    }
}