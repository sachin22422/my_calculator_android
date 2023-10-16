package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var tvInput: TextView? = null
    var lastNumeric: Boolean= false
    var lastDecimal: Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tv_Input) // Initialize tvInput with the actual TextView
    }

    fun OnDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric=true
    }
    fun onClear(view: View){
        tvInput?.text=""
    }
    fun OnDecimal(view: View){
        if(lastNumeric && !lastDecimal){
            tvInput?.append(".")
            lastDecimal=true
            lastNumeric=false
        }
    }

    fun OnOperator(view: View){
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDecimal=false
            }
        }
    }


    private fun isOperatorAdded(value:String):Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("x") || value.contains("+") || value.contains("-")
        }
    }

    fun OnEqual(view: View){
        if(lastNumeric){
            var tvValue =tvInput?.text.toString()
            var prefix=""
            try {
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue=tvValue.split("-")
                    var firstoperand=splitValue[0]
                    var secondoperand=splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstoperand=prefix+firstoperand
                    }
                    tvInput?.text=removeZeroAfterDot((firstoperand.toDouble()-secondoperand.toDouble()).toString())
                }
                 else if(tvValue.contains("+")){
                    val splitValue=tvValue.split("+")
                    var firstoperand=splitValue[0]
                    var secondoperand=splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstoperand=prefix+firstoperand
                    }
                    tvInput?.text=removeZeroAfterDot((firstoperand.toDouble()+secondoperand.toDouble()).toString())
                }
                else if(tvValue.contains("x")){
                    val splitValue=tvValue.split("x")
                    var firstoperand=splitValue[0]
                    var secondoperand=splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstoperand=prefix+firstoperand
                    }
                    tvInput?.text=removeZeroAfterDot((firstoperand.toDouble()*secondoperand.toDouble()).toString())
                }
                else if(tvValue.contains("/")){
                    val splitValue=tvValue.split("/")
                    var firstoperand=splitValue[0]
                    var secondoperand=splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstoperand=prefix+firstoperand
                    }
                    tvInput?.text=removeZeroAfterDot((firstoperand.toDouble()/secondoperand.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                    e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result:String):String{
        var value =result
        if(result.contains(".0")){
            value=result.substring(0,result.length-2)
        }
        return value
    }
}