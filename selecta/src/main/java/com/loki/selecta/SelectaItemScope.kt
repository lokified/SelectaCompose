package com.loki.selecta

import androidx.compose.runtime.Composable

interface SelectaItemScope {

    @Composable
    fun SelectaItem(onClick: () -> Unit, content: @Composable () -> Unit)
}

internal class SelectaItemScopeImpl: SelectaItemScope {

    private var onClickHandler: (() -> Unit)? = null

    @Composable
    override fun SelectaItem(onClick: () -> Unit, content: @Composable () -> Unit) {
        onClickHandler = onClick
        content()
    }

    fun performClickAction() {
        onClickHandler?.invoke()
    }
}