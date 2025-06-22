//package com.onion.feature.home.copy
//
//import android.content.Context
//import android.text.Editable
//import android.text.InputFilter
//import android.text.Spanned
//import android.text.TextUtils
//import android.text.TextWatcher
//import android.text.method.DigitsKeyListener
//import android.util.AttributeSet
//import android.util.Log
//import androidx.appcompat.widget.AppCompatEditText
//import java.math.BigDecimal
//import java.math.RoundingMode
//import java.util.regex.Pattern
//import kotlin.math.pow
//
///**
// * Copyright (C), 2023-2025 Meta
// * FileName: NumberEditText
// * Author: admin by 张琦
// * Date: 2025/3/30 22:25
// * Description:
// */
//class NumberEditText(ctx: Context, attrs: AttributeSet): AppCompatEditText(ctx, attrs),
//    TextWatcher {
//
//    companion object{
//
//        const val TAG = "NumberEditText"
//
//        fun isNumber(str: String?): Boolean{
//            if(TextUtils.isEmpty(str)){
//                return false
//            }
//
//            str?.forEach {
//                if(!Character.isDigit(it)){
//                    return false
//                }
//            }
//
//            return true
//        }
//
//        /**
//         * 是否是空或者0
//         */
//        fun isEmptyOrZero(value: String? = null): Boolean{
//            if(TextUtils.isEmpty(value)){
//                return true
//            }
//            try {
//                val compare = BigDecimal(value).compareTo(BigDecimal(0))
//                return compare == 0
//            }catch (e: Exception){
//                return false
//            }
//        }
//        /**
//         * 是否是空或者0
//         */
//        fun isEmptyOrZero(value: BigDecimal? = null): Boolean{
//            if(value == null){
//                return true
//            }
//            try {
//                val compare = value.compareTo(BigDecimal(0))
//                return compare == 0
//            }catch (e: Exception){
//                return false
//            }
//        }
//
//        /**
//         * 是否小于等于0
//         * @equalZero true 包括0 false 不包括0
//         */
//        fun smallerZero(value: String? = null,equalZero: Boolean = true): Boolean{
//            if(TextUtils.isEmpty(value)){
//                return true
//            }
//
//            try {
//                val compare = BigDecimal(value).compareTo(BigDecimal.ZERO)
//                if(compare == -1 || (equalZero && compare == 0)){
//                    return true
//                }
//            }catch (e: Exception){
//                Log.d(TAG, "smallerZero: ")
//            }
//
//            return false
//        }
//
//    }
//    init {
//        keyListener = DigitsKeyListener.getInstance("-0123456789.")
//        addTextChangedListener(this)
//        filters = arrayOf(NumberFilter())
//
//    }
//
//    private var mRegex = "^[-]?[1-9]\\d{0,11}([.])?(\\d{1,2})?$"
//    private var mPattern: Pattern? = null
//
//    private var mPrefix: Int = 12
//    private var mSuffix: Int = 2
//
//    /**
//     * 老的位数限制
//     */
//    private var mOldPrefix: Int = 12
//    private var mOldSuffix: Int = 2
//    private var mSupportNegative = false
//
//    fun supportNegative(supportNegative: Boolean): NumberEditText {
//        mSupportNegative = supportNegative
//        if(mSupportNegative){
//            keyListener = DigitsKeyListener.getInstance("-0123456789.")
//        }else{
//            keyListener = DigitsKeyListener.getInstance("0123456789.")
//        }
//        return this
//    }
//    fun setPrefix(prefix: Int = 12): NumberEditText {
//        mPrefix = prefix
//        mOldPrefix = prefix
//        return this
//    }
//
//    fun setSuffix(suffix: Int = 2): NumberEditText {
//        mSuffix = suffix
//        mOldSuffix = suffix
//        return this
//    }
//
//    fun setAmount(amount: Any?): NumberEditText {
//        var bigDecimal: BigDecimal? = null
//        when(amount){
//            is BigDecimal -> {
//                bigDecimal = amount
//            }
//            is String -> {
//                bigDecimal = BigDecimal(amount)
//            }
//            is Double -> {
//                bigDecimal = BigDecimal(amount)
//            }
//        }
//        bigDecimal?.abs()
//        if(bigDecimal?.compareTo(BigDecimal(10.0.pow(12))) == -1){
//            //小于
//            mPrefix = 12
//        }else{
//            var str: String = bigDecimal?.toPlainString() ?: "0"
//
//            if (str.contains(".")) {
//                val arr = str.split(".")
//                str = arr[0]
//            }
//            mPrefix = str.length
//        }
//        return this
//    }
//
//    override fun setText(text: CharSequence?, type: BufferType?) {
//        super.setText(text, type)
//
////        val number = BigDecimal(text.toString())
////        number.abs()
//        /**
//         * 如果金额超过当前的prefix 那就需要扩容正则
//         */
//
//    }
//    /**
//     * 这是正则
//     */
//    fun pattern(){
//        var negative = "[-]?"
//        if(!mSupportNegative){
//            negative = ""
//        }
//        if(mSuffix == 0){
//            mRegex = "^${negative}\\d{0,${mPrefix}}?$"
//        }else{
//            mRegex = "^${negative}\\d{0,${mPrefix}}([.])?(\\d{1,${mSuffix}})?$"
//        }
//
//        mPattern = Pattern.compile(mRegex)
//    }
//
//    override fun toString(): String {
//        val current = text.toString()
//
//        if(TextUtils.isEmpty(current)){
//            return current
//        }
//
//        if(current.startsWith(".")){
//            return "0${current}"
//        }
//
//        if(current.endsWith(".")){
//            return current.substring(0,current.length - 1)
//        }
//
//        return super.toString()
//    }
//    fun toStringZero(): String{
//        val current = text.toString()
//
//        if(TextUtils.isEmpty(current)){
//            return "0"
//        }
//
//        if(current.startsWith(".")){
//            return "0${current}"
//        }
//
//        if(current.endsWith(".")){
//            return current.substring(0,current.length - 1)
//        }
//
//        return current
//    }
//
//    /**
//     * 计算是否超出最大值
//     */
//    fun exceedMax(value: String? = null): Boolean{
//        if(mPattern == null || TextUtils.isEmpty(value)){
//            return false
//        }
//        val max = BigDecimal(getMax())
//        val compare = BigDecimal(value).compareTo(max)
//        when(compare){
//            -1 -> {
//                return false
//            }
//            0 -> {
//                return false
//            }
//            1 -> {
//                return true
//            }
//        }
//        return true
//    }
//
//    fun getMax(): String{
//        val sb = StringBuilder()
//        for(index in 0 until mPrefix){
//            sb.append("9")
//        }
//
//        sb.append(".")
//        for(index in 0 until mSuffix){
//            sb.append("9")
//        }
//        return sb.toString()
//    }
//    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        //beforeTextChanged
//    }
//
//    override fun onTextChanged(
//        text: CharSequence?,
//        start: Int,
//        lengthBefore: Int,
//        lengthAfter: Int
//    ) {
//        super.onTextChanged(text, start, lengthBefore, lengthAfter)
//    }
//
//    override fun afterTextChanged(s: Editable?) {
//        val amount = s?.toString()
//        if(mSupportNegative){
//            if(amount?.startsWith("-00") == true){
//                setText("")
//            }
//        }
//
//        if(amount?.startsWith("00") == true){
//            setText("")
//        }
//        if(amount?.contains(".") == false){
//            val max = if(mSupportNegative) mPrefix + 1 else mPrefix
//            if(amount.length > max){
//                setText("")
//            }
//        }
//
//        if(amount?.length == 1 && amount == "."){
//            setText("")
//        }
//    }
//
//    fun getNumber(): Double{
//        val value = this.text.toString()
//
//        if(TextUtils.isEmpty(value)){
//            return 0.0
//        }
//
//        if(value == "."){
//            return 0.0
//        }
//
//        return value.toDouble()
//    }
//    fun getScaleNumber(newScale:Int = 2): BigDecimal{
//        val value = this.text.toString()
//
//        if(TextUtils.isEmpty(value)){
//            return BigDecimal("0")
//        }
//
//        if(value == "."){
//            return BigDecimal("0")
//        }
//
//        return BigDecimal(value).setScale(newScale, RoundingMode.HALF_UP)
//    }
//
//    inner class NumberFilter: InputFilter {
//        private val sb = StringBuilder()
//
//        override fun filter(
//            source: CharSequence?,
//            start: Int,
//            end: Int,
//            dest: Spanned?,
//            dstart: Int,
//            dend: Int
//        ): CharSequence {
//            try {
//                val input = source?.toString() ?: ""
//                val before = dest?.toString() ?: ""
//
//                if(TextUtils.isEmpty(before)){
//                    val matcher = mPattern?.matcher(input)
//                    if(matcher?.matches() == true){
//                        return input
//                    }else{
//                        return ""
//                    }
//                }else{
//                    val after = insert(before, input, dstart)
//
//                    if(TextUtils.isEmpty(input) && after.length > dstart){
//                        //删除，此时判断 这地方有时候会报错
//                        val first = after[dstart]
//                        if(".".equals("$first")){
//
//                            var max = if(mSupportNegative) mPrefix + 1 else mPrefix
//
//                            if(after.contains(".")){
//                                max += 1
//                            }
//                            if(after.length > max){
//                                return "."
//                            }else{
//                                return input
//                            }
//                        }
//
//                    }
//                    val matcher = mPattern?.matcher(after)
//                    if(matcher?.matches() == true){
//
//                        if(!after.contains(".")){
//                            val max = if(mSupportNegative) mPrefix + 1 else mPrefix
//                            if(after.length > max){
//                                return ""
//                            }
//                        }
//
//                        return input
//                    }else{
//                        return ""
//                    }
//                }
//            }catch (e: Exception){
//                return source ?: ""
//            }
//        }
//
//        private fun insert(source: String, insert: String, start: Int): String {
//            sb.clear()
//            sb.append(source)
//            sb.insert(start, insert)
//            return sb.toString()
//        }
//
//    }
//
//}