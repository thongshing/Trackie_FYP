package com.example.monthandyearpicker

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import java.text.DateFormatSymbols
import java.util.*


internal open class MonthPickerView(var _context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(
        _context, attrs, defStyleAttr
    ) {
    var _yearView: YearPickerView
    var _monthList: ListView
    var _monthViewAdapter: MonthViewAdapter
    var _month: TextView
    var _year: TextView
    var _title: TextView
    var _headerFontColorSelected: Int
    var _headerFontColorNormal: Int
    var _showMonthOnly = false
    var month = 0
    var year = 0
    var _onYearChanged: MonthPickerDialog.OnYearChangedListener? = null
    var _onMonthChanged: MonthPickerDialog.OnMonthChangedListener? = null
    var _onDateSet: OnDateSet? = null
    var _onCancel: OnCancel? = null
    private val _monthNames: Array<String>

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        _context = context
    }

    fun init(year: Int, month: Int) {
        this.year = year
        this.month = month
    }

    fun setMaxMonth(maxMonth: Int) {
        if (maxMonth <= Calendar.DECEMBER && maxMonth >= Calendar.JANUARY) {
            _monthViewAdapter.setMaxMonth(maxMonth)
        } else {
            throw IllegalArgumentException(
                "Month out of range please send months between " +
                        "Calendar.JANUARY, Calendar.DECEMBER"
            )
        }
    }

    fun setMinMonth(minMonth: Int) {
        if (minMonth >= Calendar.JANUARY && minMonth <= Calendar.DECEMBER) {
            _monthViewAdapter.setMinMonth(minMonth)
        } else {
            throw IllegalArgumentException(
                "Month out of range please send months between" +
                        " Calendar.JANUARY, Calendar.DECEMBER"
            )
        }
    }

    fun setMinYear(minYear: Int) {
        _yearView.setMinYear(minYear)
    }

    fun setMaxYear(maxYear: Int) {
        _yearView.setMaxYear(maxYear)
    }

    fun showMonthOnly() {
        _showMonthOnly = true
        _year.visibility = GONE
    }

    fun showYearOnly() {
        _monthList.visibility = GONE
        _yearView.visibility = VISIBLE
        _month.visibility = GONE
        _year.setTextColor(_headerFontColorSelected)
    }

    fun setActivatedMonth(activatedMonth: Int) {
        if (activatedMonth >= Calendar.JANUARY && activatedMonth <= Calendar.DECEMBER) {
            _monthViewAdapter.setActivatedMonth(activatedMonth)
            _month.text = _monthNames[activatedMonth]
        } else {
            throw IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER")
        }
    }

    fun setActivatedYear(activatedYear: Int) {
        _yearView.setActivatedYear(activatedYear)
        _year.text = activatedYear.toString()
    }

    protected fun setMonthRange(minMonth: Int, maxMonth: Int) {
        if (minMonth < maxMonth) {
            setMinMonth(minMonth)
            setMaxYear(maxMonth)
        } else {
            throw IllegalArgumentException("maximum month is less then minimum month")
        }
    }

    protected fun setYearRange(minYear: Int, maxYear: Int) {
        if (minYear < maxYear) {
            setMinYear(minYear)
            setMaxYear(maxYear)
        } else {
            throw IllegalArgumentException("maximum year is less then minimum year")
        }
    }

    protected fun setMonthYearRange(minMonth: Int, maxMonth: Int, minYear: Int, maxYear: Int) {
        setMonthRange(minMonth, maxMonth)
        setYearRange(minYear, maxYear)
    }

    fun setTitle(dialogTitle: String?) {
        if (dialogTitle != null && dialogTitle.trim { it <= ' ' }.isNotEmpty()) {
            _title.text = dialogTitle
            _title.visibility = VISIBLE
        } else {
            _title.visibility = GONE
        }
    }

    fun setOnMonthChangedListener(onMonthChangedListener: MonthPickerDialog.OnMonthChangedListener?) {
        if (onMonthChangedListener != null) {
            _onMonthChanged = onMonthChangedListener
        }
    }

    fun setOnYearChangedListener(onYearChangedListener: MonthPickerDialog.OnYearChangedListener?) {
        if (onYearChangedListener != null) {
            _onYearChanged = onYearChangedListener
        }
    }

    fun setOnDateListener(onDateSet: OnDateSet) {
        _onDateSet = onDateSet
    }

    fun setOnCancelListener(onCancel: OnCancel) {
        _onCancel = onCancel
    }

    interface OnDateSet {
        fun onDateSet()
    }

    interface OnCancel {
        fun onCancel()
    }

    var configChangeListener: MonthPickerDialog.OnConfigChangeListener? = null
    fun setOnConfigurationChanged(configChangeListener: MonthPickerDialog.OnConfigChangeListener?) {
        this.configChangeListener = configChangeListener
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        configChangeListener!!.onConfigChange()
        super.onConfigurationChanged(newConfig)
    }

    companion object {
        var _minYear = 1900
        var _maxYear = Calendar.getInstance()[Calendar.YEAR]
    }

    init {
        val mInflater = _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mInflater.inflate(R.layout.month_picker_view, this)
        _monthNames = DateFormatSymbols(Locale.getDefault()).shortMonths
        val a = _context.obtainStyledAttributes(
            attrs, R.styleable.monthPickerDialog,
            defStyleAttr, 0
        )

        var headerBgColor = a.getColor(R.styleable.monthPickerDialog_headerBgColor, 0)
        _headerFontColorNormal = a.getColor(R.styleable.monthPickerDialog_headerFontColorNormal, 0)
        _headerFontColorSelected =
            a.getColor(R.styleable.monthPickerDialog_headerFontColorSelected, 0)
        var monthBgColor = a.getColor(R.styleable.monthPickerDialog_monthBgColor, 0)
        var monthBgSelectedColor = a.getColor(R.styleable.monthPickerDialog_monthBgSelectedColor, 0)
        var monthFontColorNormal = a.getColor(R.styleable.monthPickerDialog_monthFontColorNormal, 0)
        var monthFontColorSelected =
            a.getColor(R.styleable.monthPickerDialog_monthFontColorSelected, 0)
        var monthFontColorDisabled =
            a.getColor(R.styleable.monthPickerDialog_monthFontColorDisabled, 0)
        var headerTitleColor = a.getColor(R.styleable.monthPickerDialog_headerTitleColor, 0)
        val actionButtonColor = a.getColor(R.styleable.monthPickerDialog_dialogActionButtonColor, 0)
        if (monthFontColorNormal == 0) {
            monthFontColorNormal = ContextCompat.getColor(context, R.color.fontBlackEnable)
        }
        if (monthFontColorSelected == 0) {
            monthFontColorSelected = ContextCompat.getColor(context,R.color.fontWhiteEnable)
        }
        if (monthFontColorDisabled == 0) {
            monthFontColorDisabled = ContextCompat.getColor(context,R.color.fontBlackDisable)
        }
        if (_headerFontColorNormal == 0) {
            _headerFontColorNormal = ContextCompat.getColor(context,R.color.fontWhiteDisable)
        }
        if (_headerFontColorSelected == 0) {
            _headerFontColorSelected = ContextCompat.getColor(context,R.color.fontWhiteEnable)
        }
        if (headerTitleColor == 0) {
            headerTitleColor = ContextCompat.getColor(context,R.color.fontWhiteEnable)
        }
        if (monthBgColor == 0) {
            monthBgColor = ContextCompat.getColor(context,R.color.fontWhiteEnable)
        }
        if (headerBgColor == 0) {
            headerBgColor = android.R.attr.colorAccent
            val outValue = TypedValue()
            _context.theme.resolveAttribute(headerBgColor, outValue, true)
            headerBgColor = outValue.data
        }
        if (monthBgSelectedColor == 0) {
            monthBgSelectedColor = android.R.attr.colorAccent
            val outValue = TypedValue()
            _context.theme.resolveAttribute(monthBgSelectedColor, outValue, true)
            monthBgSelectedColor = outValue.data
        }
        val map: HashMap<String, Int> = HashMap<String, Int>()
        if (monthBgColor != 0) map["monthBgColor"] = monthBgColor
        if (monthBgSelectedColor != 0) map["monthBgSelectedColor"] = monthBgSelectedColor
        if (monthFontColorNormal != 0) map["monthFontColorNormal"] = monthFontColorNormal
        if (monthFontColorSelected != 0) map["monthFontColorSelected"] = monthFontColorSelected
        if (monthFontColorDisabled != 0) map["monthFontColorDisabled"] = monthFontColorDisabled
        a.recycle()
        _monthList = findViewById<View>(R.id.listview) as ListView
        _yearView = findViewById<View>(R.id.yearView) as YearPickerView
        _month = findViewById<View>(R.id.month) as TextView
        _year = findViewById<View>(R.id.year) as TextView
        _title = findViewById<View>(R.id.title) as TextView
        val _pickerBg = findViewById<View>(R.id.picker_view) as RelativeLayout
        val _header = findViewById<View>(R.id.header) as LinearLayout
        val _actionBtnLay = findViewById<View>(R.id.action_btn_lay) as RelativeLayout
        val ok = findViewById<View>(R.id.ok_action) as TextView
        val cancel = findViewById<View>(R.id.cancel_action) as TextView
        if (actionButtonColor != 0) {
            ok.setTextColor(actionButtonColor)
            cancel.setTextColor(actionButtonColor)
        } else {
            ok.setTextColor(headerBgColor)
            cancel.setTextColor(headerBgColor)
        }
        if (_headerFontColorSelected != 0) _month.setTextColor(_headerFontColorSelected)
        if (_headerFontColorNormal != 0) _year.setTextColor(_headerFontColorNormal)
        if (headerTitleColor != 0) _title.setTextColor(headerTitleColor)
        if (headerBgColor != 0) _header.setBackgroundColor(headerBgColor)
        if (monthBgColor != 0) _pickerBg.setBackgroundColor(monthBgColor)
        if (monthBgColor != 0) _actionBtnLay.setBackgroundColor(monthBgColor)
        ok.setOnClickListener { _onDateSet!!.onDateSet() }
        cancel.setOnClickListener { _onCancel!!.onCancel() }
        _monthViewAdapter = MonthViewAdapter(_context)
        _monthViewAdapter.setColors(map)
        _monthViewAdapter.setOnDaySelectedListener(object : MonthViewAdapter.OnDaySelectedListener {
            override fun onDaySelected(view: MonthViewAdapter?, month: Int) {
                Log.d("----------------", "MonthPickerDialogStyle selected month = $month")
                this@MonthPickerView.month = month
                _month.text = _monthNames[month]
                if (!_showMonthOnly) {
                    _monthList.visibility = GONE
                    _yearView.visibility = VISIBLE
                    _month.setTextColor(_headerFontColorNormal)
                    _year.setTextColor(_headerFontColorSelected)
                }
                if (_onMonthChanged != null) {
                    _onMonthChanged!!.onMonthChanged(month)
                }
            }
        })
        _monthList.adapter = _monthViewAdapter
        _yearView.setRange(_minYear, _maxYear)
        _yearView.setColors(map)
        _yearView.setYear(Calendar.getInstance()[Calendar.YEAR])
        _yearView.setOnYearSelectedListener(object : YearPickerView.OnYearSelectedListener {
            override fun onYearChanged(view: YearPickerView?, year: Int) {
                Log.d("----------------", "selected year = $year")
                this@MonthPickerView.year = year
                _year.text = year.toString()
                _year.setTextColor(_headerFontColorSelected)
                _month.setTextColor(_headerFontColorNormal)
                if (_onYearChanged != null) {
                    _onYearChanged!!.onYearChanged(year)
                }
            }
        })
        _month.setOnClickListener {
            if (_monthList.visibility == GONE) {
                _yearView.visibility = GONE
                _monthList.visibility = VISIBLE
                _year.setTextColor(_headerFontColorNormal)
                _month.setTextColor(_headerFontColorSelected)
            }
        }
        _year.setOnClickListener {
            if (_yearView.visibility == GONE) {
                _monthList.visibility = GONE
                _yearView.visibility = VISIBLE
                _year.setTextColor(_headerFontColorSelected)
                _month.setTextColor(_headerFontColorNormal)
            }
        }
    }
}