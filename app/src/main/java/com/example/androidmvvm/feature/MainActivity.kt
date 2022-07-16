package com.example.androidmvvm.feature

import android.os.Bundle
import com.example.androidmvvm.R
import com.example.androidmvvm.core.platform.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}