package com.example.dsl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class TestViewModel : ViewModel() {


    val list = MutableLiveData<Result<List<String>>>()

    val data = MutableLiveData<Result<User>>()

    fun getList(requestType: RequestType) {
        viewModelScope.launch {
            list.value = Result.Success(listOf("111","222"),requestType)
//            list.value = Result.Failure(Exception("列表加载失败了"),requestType)
//            list.value = Result.Success(null)
        }
    }


    fun getUser(requestType: RequestType) {
        viewModelScope.launch {
            data.value = Result.Success(User("tom"),requestType)
//            list.value = Result.Failure(Exception("用户获取失败了"),requestType)
        }
    }

}