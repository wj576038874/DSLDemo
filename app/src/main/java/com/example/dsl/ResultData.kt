package com.example.dsl

import android.util.Log


class ResultData<T> {

//    var blockDataIsNull: (() -> Unit)? = null

    /**
     * 如果泛型是对象，那么当对象为null时，调用此方法
     * 如果反省是集合，那么当集合为null或者集合为null时，调用此方法
     */
    var blockDataIsNullOrEmptyBlock: (() -> Unit)? = null

//    var blockDataIsEmptyBlock: (() -> Unit)? = null

    /**
     * 如果泛型是对象，那么当对象不为null时调用此方法
     * 如果泛型是集合，那么当集合不为null且集合不会空的时候调用此方法
     */
    var blockDataIsOkBlock: ((T) -> Unit)? = null

}

//fun <T> ResultData<T>.dataIsNull(block: () -> Unit): ResultData<T> {
//    return apply {
//        blockDataIsNull = block
//    }
//}

fun <T> ResultData<T>.dataIsNullOrEmpty(block: () -> Unit): ResultData<T> {
    return apply {
        blockDataIsNullOrEmptyBlock = block
    }
}

fun <T> ResultData<T>.dataIsOk(block: (T) -> Unit): ResultData<T> {
    return apply {
        blockDataIsOkBlock = block
    }
}

//fun <T> ResultData<List<T>>.dataIsEmpty(block: () -> Unit): ResultData<List<T>> {
//    return apply {
//        blockDataIsEmptyBlock = block
//    }
//}


/**
 * 内联函数加如crossinline 以防调用方 加入了return 破坏代码逻辑执行
 * 如果加了crossinline 那么调用execute的lambda中 就不可以写return存在语法错误 就不会导致语句被破坏
 * 如果不加execute，那么调用execute的lambda就可以加如return，那么execute方法就只能执行到
 * dslBlock.invoke(it)//return 下面代码直接会被return了 不会继续执行，因为内联了，调用方加了return，会被直接写到
 * dslBlock.invoke(it) == return
 */
inline fun <T> Result.Success<T>.execute(crossinline dslBlock: ResultData<T>.() -> Unit) {
    ResultData<T>().also {
        Log.e("asd", "开始执行")
        dslBlock.invoke(it)//return
        Log.e("asd", "被终止执行")
        when (data) {
            null -> {
                it.blockDataIsNullOrEmptyBlock?.invoke()
            }
            else -> {
                when (data) {
                    is Collection<*> -> {
                        when {
                            data.isEmpty() -> it.blockDataIsNullOrEmptyBlock?.invoke()
                            else -> it.blockDataIsOkBlock?.invoke(data)
                        }
                    }
                    else -> {
                        it.blockDataIsOkBlock?.invoke(data)
                    }
                }
            }
        }
    }
}

//fun <T> Result.Success<List<T>>.execute2(dslBlock: ResultData<List<T>>.() -> Unit) {
//    ResultData<List<T>>().also {
//        dslBlock.invoke(it)
//        when {
//            data.isNullOrEmpty() -> it.blockDataIsNullOrEmptyBlock?.invoke()
//            else -> it.blockDataIsOkBlock?.invoke(data)
//        }
//    }
//}