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
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.loki.selecta.utils.Position
import com.loki.selecta.utils.SelectaDefaults
import com.loki.selecta.utils.SelectaItemColors
import com.loki.selecta.utils.SelectaItemPadding
import com.loki.selecta.utils.SelectaItemShape

data class SelectaListState <T>(
    val list: List<T>,
    val lazyListState: LazyListState,
    val selectedItems: (List<T>) -> Unit,
    val selectedCount: (Int) -> Unit,
)

@Composable
fun <T> rememberSelectaListState(
    list: List<T>,
    selectedItems: (List<T>) -> Unit,
    selectedCount: (Int) -> Unit
): SelectaListState<T> {
    val lazyListState = rememberLazyListState()

    return remember(list, selectedItems, selectedCount) {
        SelectaListState(
            list = list,
            lazyListState = lazyListState,
            selectedItems = selectedItems,
            selectedCount = selectedCount
        )
    }
}

@Composable
fun <T> LazyColumnSelecta(
    modifier: Modifier = Modifier,
    selectaState: SelectaListState<T>,
    selectaItemColors: SelectaItemColors = SelectaDefaults.colors(),
    selectaShape: SelectaItemShape = SelectaDefaults.shape(),
    selectaPadding: SelectaItemPadding = SelectaDefaults.padding(),
    selectaPosition: Position,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    userScrollEnabled: Boolean = true,
    itemContent: @Composable LazyItemScope.(T) -> Unit
) {

    val selectedItems = remember { mutableStateListOf<T>() }
    val isPressedList = remember { mutableStateListOf<Boolean>() }
    var isActive by remember { mutableStateOf(false) }

    repeat(selectaState.list.size) {
        isPressedList.add(false)
    }

    if (isPressedList.toList().isNotEmpty()) {
        BackHandler {
            isPressedList.clear()
            selectedItems.clear()
            isActive = false
            selectaState.selectedItems(emptyList())
            selectaState.selectedCount(selectedItems.size)
        }
    }

    LazyColumn(
        modifier = modifier,
        state = selectaState.lazyListState,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        userScrollEnabled = userScrollEnabled
    ) {

        itemsIndexed(selectaState.list) { index, item ->
            val isSelected = isPressedList[index]
            SelectaListItemContainer(
                isPressed = isSelected,
                isActive = isActive,
                onLongPressed = {
                    isActive = true
                    isPressedList[index] = true
                    selectedItems.add(selectaState.list[index])
                    selectaState.selectedItems(emptyList())
                    selectaState.selectedCount(selectedItems.size)
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
                         selectaState.selectedItems(emptyList())
                        selectaState.selectedCount(selectedItems.size)
                    }

                    if (selectedItems.toList().isEmpty()) {
                        isActive = false
                    }
                },
                onCheckedChange = {
                    if (isPressedList[index]) {
                        // Deselect the item
                        isPressedList[index] = it
                        selectedItems.remove(selectaState.list[index])
                    } else {
                        // Select the item
                        isPressedList[index] = it
                        selectedItems.add(selectaState.list[index])
                    }
                    if (selectedItems.toList().isEmpty()) {
                        isActive = false
                    }
                    selectaState.selectedItems(emptyList())
                    selectaState.selectedCount(selectedItems.size)
                },
                modifier = Modifier,
                selectaItemColors = selectaItemColors,
                selectaShape = selectaShape,
                selectaItemPadding = selectaPadding,
                selectaPosition = selectaPosition
            ) {
                itemContent(item)
            }
        }
    }
}

@Composable
private fun SelectaListItemContainer(
    modifier: Modifier = Modifier,
    isPressed: Boolean,
    isActive: Boolean,
    onLongPressed: () -> Unit,
    onTap: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    selectaItemColors: SelectaItemColors,
    selectaShape: SelectaItemShape,
    selectaItemPadding: SelectaItemPadding,
    selectaPosition: Position,
    content: @Composable () -> Unit
) {

    val itemWeight by animateFloatAsState(
        targetValue = if (isPressed) .9f else 1f,
        animationSpec = tween(easing = EaseInCubic),
        label = "item weight"
    )

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
            .fillMaxWidth()
            .clip(selectaShape.containerShape)
            .background(color = itemBackground, shape = selectaShape.containerShape)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPressed() },
                    onTap = { onTap() }
                )
            }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isActive && selectaPosition == Position.START) {
                SelectaCheckbox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.1f),
                    isPressed = isPressed,
                    onCheckedChange = onCheckedChange,
                    iconPadding = selectaItemPadding.iconPadding
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(itemPadding)
                    .weight(itemWeight)
            ) {
                content()
            }

            if (isActive && selectaPosition == Position.END) {
                SelectaCheckbox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.1f),
                    isPressed = isPressed,
                    onCheckedChange = onCheckedChange,
                    iconPadding = selectaItemPadding.iconPadding
                )
            }
        }
    }
}

@Composable
private fun SelectaCheckbox(
    modifier: Modifier = Modifier,
    isPressed: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    iconPadding: Dp
) {
    Box(
        modifier = modifier
            .padding(iconPadding)
    ) {
        Checkbox(
            checked = isPressed,
            onCheckedChange = onCheckedChange
        )
    }
}