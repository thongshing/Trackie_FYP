package com.example.monthandyearpicker

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import java.util.*

internal class MonthViewAdapter(private val _context: Context) : BaseAdapter() {
    private var _minMonth = 0
    private var _maxMonth = 0
    private var _activatedMonth = 0
    private var _colors: HashMap<String, Int>? = null
    private var mOnDaySelectedListener: OnDaySelectedListener? = null

   init {
       setRange()
   }

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(i: Int): Any = Unit

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup): View {
        val v : MonthView
        if (convertView != null) {
            v = convertView as MonthView
        }else{
            v = MonthView(_context)
            v.setColors(_colors!!)
            val params = AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT)
            v.layoutParams = params
            v.background = ContextCompat.getDrawable(_context, R.drawable.month_ripplr)
            v.setMonthParams(_activatedMonth, _minMonth, _maxMonth)
            v.reuse()
            v.invalidate()
        }
        return v
    }

    private val mOnDayClickListener: MonthView.OnMonthClickListener = object :
        MonthView.OnMonthClickListener {
        override fun onMonthClick(view: MonthView?, month: Int) {
            Log.d("MonthViewAdapter", "onDayClick $month")
            if (isCalendarInRange(month)) {
                Log.d("MonthViewAdapter", "day not null && Calender in range $month")
                setSelectedMonth(month)
                if (mOnDaySelectedListener != null) {
                    mOnDaySelectedListener!!.onDaySelected(this@MonthViewAdapter, month)
                }
            }
        }
    }

    fun isCalendarInRange(value: Int): Boolean {
        return value in _minMonth.._maxMonth
    }

    /**
     * Updates the selected day and related parameters.
     *
     * @param month The day to highlight
     */
    fun setSelectedMonth(month: Int) {
        Log.d("MonthViewAdapter", "setSelectedMonth : $month")
        _activatedMonth = month
        notifyDataSetChanged()
    }

    /* set min and max date and years*/
    private fun setRange() {
        _minMonth = Calendar.JANUARY
        _maxMonth = Calendar.DECEMBER
        _activatedMonth = Calendar.AUGUST
        notifyDataSetInvalidated()
    }

    /**
     * Sets the listener to call when the user selects a day.
     *
     * @param listener The listener to call.
     */
    fun setOnDaySelectedListener(listener: OnDaySelectedListener?) {
        mOnDaySelectedListener = listener
    }

    interface OnDaySelectedListener {
        fun onDaySelected(view: MonthViewAdapter?, month: Int)
    }

    fun setMaxMonth(maxMonth: Int) {
        _maxMonth =
            if (maxMonth <= Calendar.DECEMBER && maxMonth >= Calendar.JANUARY) {
                maxMonth
            } else {
                throw IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER")
            }
    }

    fun setMinMonth(minMonth: Int) {
        _minMonth =
            if (minMonth >= Calendar.JANUARY && minMonth <= Calendar.DECEMBER) {
                minMonth
            } else {
                throw IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER")
            }
    }

    fun setActivatedMonth(activatedMonth: Int) {
        _activatedMonth =
            if (activatedMonth >= Calendar.JANUARY && activatedMonth <= Calendar.DECEMBER) {
                activatedMonth
            } else {
                throw IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER")
            }
    }

    fun setColors(map: HashMap<String, Int>?) {
        _colors = map
    }

}