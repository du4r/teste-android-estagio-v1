package com.du4r.mybus.views

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.du4r.mybus.R
import com.du4r.mybus.views.fragments.HomeFragment
import com.du4r.mybus.views.fragments.SearchFragment
import com.du4r.mybus.service.RetrofitService
import com.du4r.mybus.viewmodels.MainViewModel
import com.du4r.mybus.viewmodels.MainViewModelFactory
import com.du4r.mybus.views.fragments.ComingFragment
import com.du4r.mybus.views.fragments.StoppagesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var mainFrag: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupFragments(savedInstanceState)
    }

    fun setupFragments(savedInstanceState: Bundle?) {

        mainFrag = findViewById(R.id.fragment_container)
        bottomNav = findViewById(R.id.main_bottom_nav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_bottom_home -> replaceFragment(HomeFragment())
                R.id.menu_bottom_search -> replaceFragment(SearchFragment())
                R.id.menu_stoppages -> replaceFragment(StoppagesFragment())
                R.id.menu_bottom_timer -> replaceFragment(ComingFragment())
            }
            true
        }

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun setupViewModel() {
        vm = ViewModelProvider(
            this, MainViewModelFactory(RetrofitService.getInstance())
        ).get(MainViewModel::class.java)
    }

}