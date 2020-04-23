package com.banketree.shadowlayout_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.banketree.shadowlayout_demo.demo1.Test2Activity
import com.banketree.shadowlayout_demo.demo1.TestActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test1_tv.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }

        test2_tv.setOnClickListener {
//            startActivity(Intent(this, Test2Activity::class.java))
        }
    }
}
