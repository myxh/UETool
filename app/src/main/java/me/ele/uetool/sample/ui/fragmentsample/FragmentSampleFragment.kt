package me.ele.uetool.sample.ui.fragmentsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import me.ele.uetool.sample.R

class FragmentSampleFragment : Fragment() {

    companion object {
        fun newInstance() = FragmentSampleFragment()
    }

    private lateinit var viewModel: FragmentSampleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sample_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentSampleViewModel::class.java)
        // TODO: Use the ViewModel

        childFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentSampleFragment2.newInstance())
                .commitNow()

    }

}
