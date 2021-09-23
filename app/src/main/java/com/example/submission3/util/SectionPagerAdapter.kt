package com.example.submission3.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission3.ui.FollowersFragment
import com.example.submission3.ui.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, bundle: Bundle): FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle = bundle

    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}
