package com.example.androidmvvm.core.mapper

import com.example.androidmvvm.core.extension.defaultEmpty

abstract class Mapper<Input, Output> {
    abstract fun map(input: Input): Output

    fun mapList(inputs: List<Input>?): List<Output> {
        return inputs?.map { input -> map(input) }.defaultEmpty()
    }
}