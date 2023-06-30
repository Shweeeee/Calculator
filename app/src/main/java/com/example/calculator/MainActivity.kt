package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.security.KeyStore.TrustedCertificateEntry

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView?=null
    private var lastNumeric:Boolean=false
    private var lastDot: Boolean = false
    private var dotLimit: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }
    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric=true
        if(lastDot)
            dotLimit=true
        lastDot=false

    }
    fun onClear(view: View){
        tvInput?.text=""
        lastNumeric=false
        lastDot=false
        dotLimit=false
    }
    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot &&!dotLimit){
            tvInput?.append(".")
            lastDot=true
            lastNumeric=false
        }
    }
    fun onOperator(view:View) {
        tvInput?.text?.let {
            if (lastNumeric){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                dotLimit=false
            }
        }
    }
    fun onEqual(view: View){
        if(lastNumeric){
            var s = tvInput?.text.toString()
            var negative = false
            if (s[0] == '-') {
                negative = true
                s = s.substring(1)
            }
            var result = 0.0
            var term = ""
            var operation = "+"
            for (i in s) {
                if (i.isDigit() || i == '.') {
                    term += i
                } else {
                    if(!term.contains(".")){
                        term+=".0"
                    }
                    if (negative) {
                        result -= term.toDouble()
                        negative = false
                        operation = i.toString()
                    } else {
                        when (operation) {
                            "-" -> result -= term.toDouble()
                            "+" -> result += term.toDouble()
                            "*" -> result *= term.toDouble()
                            "/" -> result /= term.toDouble()
                        }
                        operation = i.toString()
                    }
                    term = ""
                }
            }

            if (term != "") {
                if(!term.contains(".")){
                    term+=".0"
                }
                when (operation) {
                    "-" -> result -= term.toDouble()
                    "+" -> result += term.toDouble()
                    "*" -> result *= term.toDouble()
                    "/" -> result /= term.toDouble()
                }
            }
            dotLimit=true
            tvInput?.text=removeDotZero(result.toString())
        }
    }
    private fun isNegative(value: String): Boolean{
        return value.startsWith("-")&&(!(value.substring(1).contains("-")))
    }
    private fun removeDotZero(result: String): String{
        var value = result
        if(result.contains(".0")) {
            value = result.substring(0, result.length - 2)
            dotLimit = false
        }
        return value
    }
}