/*
 * Copyright 2024 Sheldon Okware
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.loki.selecta

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.loki.selecta.utils.Position
import com.loki.selecta.utils.SelectaDefaults
import com.loki.selecta.utils.SelectaItemColors
import com.loki.selecta.utils.SelectaItemPadding
import com.loki.selecta.utils.SelectaItemShape


/**
 * Creates and remembers a [SelectaLazyGridState] for managing the selection state of a grid of items.
 *
 * @param list The list of items to manage the selection state for.
 * @param lazyGridState Optional [LazyGridState] to control the scrolling behavior of the grid. If not provided, a default [LazyGridState] will be used.
 * @return An instance of [SelectaLazyGridState] initialized with the provided list and optional [lazyGridState].
 */
@Composable
fun <T> rememberSelectaLazyGridState(
    list: List<T>,
    lazyGridState: LazyGridState = rememberLazyGridState()
): SelectaLazyGridState<T> {
    return remember(list) {
        SelectaState(
            l = list,
            lg = lazyGridState
        )
    }
}

@Composable
fun <T> LazyVerticalGridSelecta(
    modifier: Modifier = Modifier,
    selectaState: SelectaLazyGridState<T>,
    selectaIcon: ImageVector = Icons.Filled.CheckCircle,
    selectaItemColors: SelectaItemColors = SelectaDefaults.colors(),
    selectaShape: SelectaItemShape = SelectaDefaults.shape(),
    selectaPadding: SelectaItemPadding = SelectaDefaults.padding(),
    selectaPosition: Position,
    columns: GridCells = GridCells.Fixed(2),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(0.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    userScrollEnabled: Boolean = true,
    itemContent: @Composable LazyGridItemScope.(index: Int, item: T) -> Unit
) {

    val selectedItems = remember { mutableStateListOf<T>() }
    val isPressedList = remember { mutableStateListOf<Boolean>() }
    var isActive by remember { mutableStateOf(false) }

    repeat(selectaState.list.size) {
        isPressedList.add(false)
    }

    if (isActive) {
        BackHandler {
            isPressedList.clear()
            selectedItems.clear()
            isActive = false
            selectaState.selectedItems(emptyList())
        }
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = columns,
        state = selectaState.lazyGridState,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        userScrollEnabled = userScrollEnabled
    ) {

        itemsIndexed(selectaState.list) { index, item ->
            val isSelected = isPressedList[index]
            SelectaGridItemContainer(
                isPressed = isSelected,
                onLongPressed = {
                    isActive = true
                    isPressedList[index] = true
                    selectedItems.add(selectaState.list[index])
                    selectaState.selectedItems(selectedItems.toList())
                },
                onTap = {
                    if (isPressedList.contains(true)) {
                        if (isPressedList[index]) {
                            // Deselect the item
                            isPressedList[index] = false
                            selectedItems.remove(selectaState.list[index])
                        } else {
                            // Select the item
                            isPressedList[index] = true
                            selectedItems.add(selectaState.list[index])
                        }
                        selectaState.selectedItems(selectedItems.toList())
                    }
                },
                modifier = Modifier,
                selectedIcon = selectaIcon,
                selectaItemColors = selectaItemColors,
                selectaShape = selectaShape,
                selectaItemPadding = selectaPadding,
                position = selectaPosition
            ) {
                itemContent(index, item)
            }
        }
    }
}


@Composable
internal fun SelectaGridItemContainer(
    modifier: Modifier = Modifier,
    isPressed: Boolean,
    onLongPressed: () -> Unit,
    onTap: () -> Unit,
    selectedIcon: ImageVector,
    selectaItemColors: SelectaItemColors,
    selectaShape: SelectaItemShape,
    selectaItemPadding: SelectaItemPadding,
    position: Position,
    content: @Composable () -> Unit
) {


    val itemPadding by animateDpAsState(
        targetValue = if (isPressed) selectaItemPadding.selectedContainerPadding else selectaItemPadding.unselectedContainerPadding,
        animationSpec = tween(easing = EaseInOut),
        label = "item padding"
    )

    val itemBackground by animateColorAsState(
        targetValue = if (isPressed) selectaItemColors.selectedContainerColor else selectaItemColors.unselectedContainerColor,
        animationSpec = tween(easing = EaseInOut),
        label = "item background"
    )

    Box(
        modifier = modifier
            .clip(selectaShape.containerShape)
            .background(color = itemBackground, shape = selectaShape.containerShape)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPressed() },
                    onTap = { onTap() }
                )
            }
    ) {

        val alignment = when (position) {
            Position.TOPSTART -> Alignment.TopStart
            Position.TOPEND -> Alignment.TopEnd
            else -> Alignment.TopEnd
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(itemPadding)
        ) {
            content()
        }

        if (isPressed) {
            Icon(
                imageVector = selectedIcon,
                contentDescription = "check icon",
                modifier = Modifier
                    .align(alignment)
                    .padding(selectaItemPadding.iconPadding),
                tint = selectaItemColors.iconColor
            )
        }
    }
}