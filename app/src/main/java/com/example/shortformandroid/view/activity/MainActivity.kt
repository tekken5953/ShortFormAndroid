package com.example.shortformandroid.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shortformandroid.R
import com.example.shortformandroid.databinding.ActivityMainBinding
import com.example.shortformandroid.view.fragment.HomeFragment
import com.example.shortformandroid.view.fragment.ReelsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)  {
            changeFragment(HomeFragment())
        }

        binding.mainBottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    changeFragment(HomeFragment())
                }
                R.id.bottom_reels -> {
                    changeFragment(ReelsFragment())
                }

                else -> { return@setOnItemSelectedListener false }
            }

            true
        }
    }

    fun changeFragment(frag: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, frag)
        if (!supportFragmentManager.isStateSaved) transaction.commit()
        else transaction.show(frag)
    }
}