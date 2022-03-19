package com.example.dsl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var testViewModel: TestViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn1).setOnClickListener {
            testViewModel.getList(RequestType.LOADMORE)
        }

        findViewById<View>(R.id.btn2).setOnClickListener {
            testViewModel.getUser(RequestType.LOADMORE)
        }

        findViewById<View>(R.id.btn3).setOnClickListener(this)

        testViewModel = ViewModelProvider(this)[TestViewModel::class.java]



        testViewModel.list.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    result.execute {
                        dataIsNullOrEmpty {
                            Log.e("asd", "list dataIsNullOrEmpty${result.requestType}")
                        }
                        dataIsOk {
                            Log.e("asd", "$it${result.requestType}")
                        }
                        return@execute
                    }
                }
                is Result.Failure -> {
                    Log.e("asd","${ result.exception.message}${result.requestType}")
                }
            }
        }

        testViewModel.data.observe(this){result->
            when(result){
                is Result.Success->{
                    result.execute {
                        dataIsOk {
                            Log.e("asd", "$it${result.requestType}")
                        }
                        dataIsNullOrEmpty {
                            Log.e("asd", "user dataIsNullOrEmpty${result.requestType}")
                        }
                    }
                }
                is Result.Failure->{
                    Log.e("asd", "${ result.exception.message}${result.requestType}")
                }
            }
        }


        runRunnable {
            let {

            }
            return
        }

    }

    inline fun runRunnable( block:String.()->Unit){
//        val runnable = Runnable {
//            block()
//        }
        block("")
    }

    override fun onClick(p0: View?) {
        val result  = Result.Success(listOf<String>())
        result.execute {
            dataIsNullOrEmpty {
                Log.e("asd" , "执行了1")
            }
            dataIsOk {
                Log.e("asd" , "执行了2")
            }
//            return
        }
    }
}