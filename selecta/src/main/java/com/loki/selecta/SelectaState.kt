package com.loki.selecta

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.mutableStateOf

interface BaseSelectaState<T> {
    val list: List<T>
    val selectedItems: List<T>
    val selectedCount: Int
    fun updateSelectedList(updateList: List<T>)
}

interface SelectaListState<T> : BaseSelectaState<T> {
    val lazyListState: LazyListState
}

interface SelectaLazyGridState<T> : BaseSelectaState<T> {
    val lazyGridState: LazyGridState
}

class SelectaState<T>() : SelectaListState<T>, SelectaLazyGridState<T> {

    private var l: List<T> = emptyList()
    private var ls: LazyListState? = null
    private var lg: LazyGridState? = null

    constructor(l: List<T>, ls: LazyListState) : this() {
        this.l = l
        this.ls = ls
        this.lg = null
    }

    constructor(l: List<T>, lg: LazyGridState) : this() {
        this.l = l
        this.ls = null
        this.lg = lg
    }

    private var updatedSelectedList = mutableStateOf<List<T>>(emptyList())

    override val list: List<T>
        get() = l

    override val lazyListState: LazyListState
        get() = ls!!

    override val lazyGridState: LazyGridState
        get() = lg!!

    override val selectedItems: List<T>
        get() = updatedSelectedList.value

    override val selectedCount: Int
        get() = updatedSelectedList.value.size

    override fun updateSelectedList(updateList: List<T>) {
        updatedSelectedList.value = updateList
    }
}