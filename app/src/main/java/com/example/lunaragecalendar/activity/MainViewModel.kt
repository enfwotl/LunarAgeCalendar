/* Lunar Age Calendar - Main ViewModel
* Kim Hyun
* ViewModel for Main Activity Page
* Data & Logic
* */

package com.example.lunaragecalendar.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lunaragecalendar.api.RetrofitClient
import com.example.lunaragecalendar.api.RetrofitService
import com.example.lunaragecalendar.api.XmlResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDate
import java.util.*

class MainViewModel() : ViewModel() {
    private var _date = MutableLiveData<LocalDate>()
    private var _lunAge = MutableLiveData<Double>()
    private lateinit var retrofit: Retrofit
    private lateinit var retroService: RetrofitService
    private lateinit var calendar: Calendar


    val date: LiveData<LocalDate>
        get() = _date
    val lunAge: LiveData<Double>
        get() = _lunAge

    init {
        _date.value = LocalDate.now()
        _lunAge.value = 0.0
        init()
        getLunarAge()
    }

    private fun init(){
        retrofit = RetrofitClient.getInstance()
        retroService = retrofit.create(RetrofitService::class.java)
        calendar = Calendar.getInstance()
    }

    fun getLunarAge() {
        var monthString : String = date.value?.monthValue.toString()
        var dateString : String = date.value?.dayOfMonth.toString()
        if (monthString.toInt() < 10) monthString = "0" + date.value?.monthValue
        if (dateString.toInt() < 10) dateString = "0" + date.value?.dayOfMonth
        val call: Call<XmlResponse> = retroService.getInfo(RetrofitClient.getServiceKey(), date.value?.year.toString(), monthString, dateString)
        call.enqueue(object: Callback<XmlResponse> {
            override fun onResponse(call: Call<XmlResponse>, response: Response<XmlResponse>) {
                var result = response.body()?.body?.items?.item
                if(result?.lunAge != null) _lunAge.value = result.lunAge!!
            }

            override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                Log.d("Error", "$t")
            }

        })
    }

    fun onButtonClick(mode: Int) {
        if (mode == 1) dateChange(date.value?.plusDays(1))
        else if (mode == -1) dateChange(date.value?.minusDays(1))
    }

    fun onCalendarClick(activity: Activity) {
        val calendarListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            dateChange(LocalDate.of(year, month+1, day))
        }

        DatePickerDialog(activity, calendarListener, date.value?.year!!, date.value?.monthValue!!-1, date.value?.dayOfMonth!!).show()
    }

    fun dateChange(newDate: LocalDate?) {
        if(newDate != null) _date.value = newDate!!
        getLunarAge()
    }
}