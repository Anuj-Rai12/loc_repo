package com.example.androidtesting

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityUITestFile::class,
    NextScreenActivityTest::class
)
class SuiteClassFile