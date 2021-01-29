package com.guosen.common.base
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class BasePageAdapter : FragmentPagerAdapter {
    private var mFragments:MutableList<BaseFragment> = arrayListOf()
    constructor(fm:FragmentManager):super(fm){
    }
    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
       return view == `object`
    }
    override fun getCount(): Int {
        return if (mFragments == null) 0 else mFragments.size
    }
    fun setData(fragments:MutableList<BaseFragment>){
        mFragments = fragments
    }

}