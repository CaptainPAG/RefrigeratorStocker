package com.pag.n_satou.refrigeratorstocker

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.google.firebase.FirebaseApp
import com.pag.n_satou.refrigeratorstocker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                adapter.changeSelect(position)
            }
        } )

    }

    private class ViewPagerAdapter(manager: FragmentManager): FragmentStatePagerAdapter(manager) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> MainFragment.getInstance()
                1 -> PhotoFragment.getInstance()
                else -> MainFragment()
            }
        }

        override fun getCount(): Int = 2

        fun changeSelect(position: Int) {
            for (i in 0 .. count) {
                val fragment = getItem(i) as TabProvider
                if (position == i) {
                    fragment.onPageSelected()
                } else {
                    fragment.onPageUnSelected()
                }
            }
        }
    }
}
