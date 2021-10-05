package com.example.androidtesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidtesting.liveDataTestCase.getOrAwaitValue
import com.example.androidtesting.model.MyInterFace
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MyViewModelTest {
    private lateinit var myViewModel: MyViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val cal = Mockito.mock(MyInterFace::class.java)
        Mockito.`when`(cal.getAreaDoubleValue(2.toDouble())).thenReturn(23.toDouble())
        myViewModel = MyViewModel(cal)
    }

    @Test
    fun viewModel_Function_testing() {
        myViewModel.getCalculate(2.toDouble())
        val read = myViewModel.area.value
        assertThat(read).isEqualTo(23.toDouble().toString())
    }


    @Test
    fun testing_some_data() {
        myViewModel.getCalculate(2.toDouble())
        val data=myViewModel.area.getOrAwaitValue()
        assertThat(data).isEqualTo(23.toDouble().toString())
    }
}