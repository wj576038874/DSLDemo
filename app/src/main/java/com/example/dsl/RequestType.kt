package com.example.dsl

sealed class RequestType {
    object REFRESH : RequestType()
    object LOADMORE : RequestType()
}