package com.example.wxcamping.view.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.wxcamping.R
import com.example.wxcamping.view.adapter.ViewPagerAdapter
import com.example.wxcamping.view.fragment.FxFragment
import com.example.wxcamping.view.fragment.TxlFragment
import com.example.wxcamping.view.fragment.WoFragment
import com.example.wxcamping.view.fragment.WxFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewActivity : AppCompatActivity() {
    lateinit var viewPager2: ViewPager2
    lateinit var navigationView: BottomNavigationView
    lateinit var fragmentList: List<Fragment>
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var constraintlayou: ConstraintLayout
    lateinit var mainview_title: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        constraintlayou = findViewById(R.id.top)
        window.statusBarColor = Color.parseColor("#ECECEC")
        viewPager2 = findViewById(R.id.viewpager)
        navigationView = findViewById(R.id.nav)
        mainview_title = findViewById(R.id.mainview_title)
        initFragmentList()
        viewPagerAdapter = ViewPagerAdapter(this, fragmentList)
        viewPager2.adapter = viewPagerAdapter
        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.wx -> viewPager2.currentItem = 0
                R.id.txl -> viewPager2.currentItem = 1
                R.id.fx -> viewPager2.currentItem = 2
                R.id.wo -> viewPager2.currentItem = 3


            }
            true
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    3 -> {
                        constraintlayou.visibility = View.GONE
                        navigationView.setSelectedItemId(R.id.wo)
                    }

                    2 -> {
                        constraintlayou.visibility = View.VISIBLE
                        mainview_title.text = "发现"
                        navigationView.setSelectedItemId(R.id.fx)
                    }

                    1 -> {
                        constraintlayou.visibility = View.VISIBLE
                        mainview_title.text = "通讯录"
                        navigationView.setSelectedItemId(R.id.txl)
                    }

                    0 -> {
                        constraintlayou.visibility = View.VISIBLE
                        mainview_title.text = "微信"
                        navigationView.setSelectedItemId(R.id.wx)
                    }
                }
            }
        })
    }

    fun initFragmentList() {
        fragmentList = listOf(WxFragment(), TxlFragment(), FxFragment(), WoFragment())
    }
}