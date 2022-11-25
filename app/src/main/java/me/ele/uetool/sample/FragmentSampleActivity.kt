package me.ele.uetool.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.ele.uetool.sample.ui.fragmentsample.FragmentSampleFragment

class FragmentSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sample_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FragmentSampleFragment.newInstance())
                    .commitNow()
        }
    }

}
