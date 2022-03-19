package com.example.dsl

import java.lang.Exception

sealed class Result<out T> {

    class Success<T>(val data: T?, val requestType: RequestType = RequestType.REFRESH) : Result<T>()

    class Failure<T>(val exception: Exception, val requestType: RequestType = RequestType.REFRESH) :
        Result<T>()
}