package com.snappik.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.snappik.tmu.Unlock
import kotlinx.android.synthetic.main.key_activity.*

class KeyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.key_activity)

        mfl2.mode(Unlock.Mode.KEYSET)
    }
}