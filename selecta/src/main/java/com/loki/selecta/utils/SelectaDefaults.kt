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
package com.loki.selecta.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SelectaItemPadding(
    val selectedContainerPadding: Dp,
    val unselectedContainerPadding: Dp,
    val iconPadding: Dp
)

data class SelectaItemColors(
    val selectedContainerColor: Color,
    val unselectedContainerColor: Color,
    val iconColor: Color
)

data class SelectaItemShape(
    val containerShape: Shape
)

object SelectaDefaults {

    @Composable
    fun colors(
        selectedContainerColor: Color = MaterialTheme.colorScheme.primary.copy(.1f),
        unselectedContainerColor: Color = MaterialTheme.colorScheme.background,
        iconColor: Color = MaterialTheme.colorScheme.primary
    ) = SelectaItemColors(selectedContainerColor, unselectedContainerColor, iconColor)

    @Composable
    fun shape(
        containerShape: Shape = RoundedCornerShape(8.dp)
    ) = SelectaItemShape(containerShape)

    @Composable
    fun padding(
        selectedContainerPadding: Dp = 8.dp,
        unselectedContainerPadding: Dp = 8.dp,
        iconPadding: Dp = 0.dp
    ) = SelectaItemPadding(selectedContainerPadding, unselectedContainerPadding, iconPadding)
}