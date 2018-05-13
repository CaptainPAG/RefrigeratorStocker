package com.pag.n_satou.refrigeratorstocker

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pag.n_satou.refrigeratorstocker.databinding.FragmentMainBinding

/**
 * Created by N-Satou on 2018/05/12.
 */
class MainFragment: Fragment(), TabProvider {

    companion object {

        private var fragment: MainFragment? = null

        fun getInstance(): MainFragment {
            if (fragment == null) {
                fragment = MainFragment()
            }
            return fragment!!
        }
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onPageSelected() {

    }

    override fun onPageUnSelected() {

    }

}