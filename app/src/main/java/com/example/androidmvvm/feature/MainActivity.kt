package com.example.androidmvvm.feature

import android.os.Bundle
import com.example.androidmvvm.databinding.ActivityMainBinding
import com.example.androidmvvm.platform.BaseActivity

class MainActivity : BaseActivity() {

    var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }
}