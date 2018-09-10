package com.snappik.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.snappik.tmu.Unlock
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        mfl.mode(Unlock.Mode.UNLOCK)
        mfl.activityTostart(SecondActivity())
        button.setOnClickListener { Log.d(TAG, "Button clicked!") }
        editText.setOnClickListener { Log.d(TAG, "Edit text view clicked!") }
        button2.setOnClickListener {startActivity(Intent(this, KeyActivity::class.java))}
    }

    companion object {
        private var TAG = "TMU TEST"
    }
}
