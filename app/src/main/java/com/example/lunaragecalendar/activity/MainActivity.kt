/* Lunar Age Calendar - Main Activity
* Kim Hyun
* Main(First) Page Activity
* Data Binding
* */
package com.example.lunaragecalendar.activity

import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lunaragecalendar.R
import com.example.lunaragecalendar.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.info = vModel

        vModel.date.observe(this, Observer {
            binding.textViewDate.text = "${it.year}年 ${it.monthValue}月 ${it.dayOfMonth}日"
        })
        vModel.lunAge.observe(this, Observer {
            binding.textViewAge.text = "月齢: ${it.toString()}"
            val res : Resources = resources
            val resID : Int = res.getIdentifier("moon_${it.toInt()}","drawable", packageName)
            binding.imageViewMoon.setImageDrawable(res.getDrawable(resID, res.newTheme()))
        })
        binding.ButtonCalendar.setOnClickListener(View.OnClickListener {
            vModel.onCalendarClick(this)
        })
    }
}