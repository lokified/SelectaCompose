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
package com.loki.selectacompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.loki.selecta.LazyColumnSelecta
import com.loki.selecta.LazyVerticalGridSelecta
import com.loki.selecta.rememberSelectaLazyGridState
import com.loki.selecta.rememberSelectaListState
import com.loki.selecta.utils.Position
import com.loki.selectacompose.ui.theme.SelectaComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SelectaComposeTheme {

                val selectaListState = rememberSelectaListState(
                    list = imageText,
                    selectedItems = {
                        Log.i("selected ***", it.toString())
                    }
                )

                val selectaLazyGridState = rememberSelectaLazyGridState(
                    list = imageModels,
                    selectedItems = {
                        Log.i("selected ***", it.toString())
                    }
                )

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { },
                            actions = {
                                Text(text = "${selectaListState.selectedCount} selected")
                            }
                        )
                    }
                ) { paddingValues ->

                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        LazyColumnSelecta(
                            selectaState = selectaListState,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(16.dp),
                            selectaPosition = Position.START
                        ) {  index, item ->

                            Column {
                                Text(text = item.text1 ?: "")
                                Text(text = item.text2 ?: "")
                            }
                        }


//                        LazyVerticalGridSelecta(
//                            selectaState = selectaLazyGridState,
//                            contentPadding = PaddingValues(16.dp),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp),
//                            verticalArrangement = Arrangement.spacedBy(8.dp),
//                            position = Position.TOPSTART
//                        ) { index, item ->
//
//                            Column {
//                                Image(
//                                    painter = painterResource(id = item.id ?: 0),
//                                    contentDescription = null,
//                                    modifier = Modifier.height(150.dp),
//                                    contentScale = ContentScale.Crop
//                                )
//                                Text(text = "tester")
//                            }
//                        }
                    }
                }
            }
        }
    }
}

val imageText = listOf(
    ImageModel(text1 = "test1", text2 = "testing list 1 is fun"),
    ImageModel(text1 = "test2", text2 = "testing list 2 is fun"),
    ImageModel(text1 = "test3", text2 = "testing list 3 is fun"),
    ImageModel(text1 = "test4", text2 = "testing list 4 is fun"),
    ImageModel(text1 = "test5", text2 = "testing list 5 is fun"),
    ImageModel(text1 = "test6", text2 = "testing list 6 is fun"),
    ImageModel(text1 = "test7", text2 = "testing list 7 is fun"),
    ImageModel(text1 = "test8", text2 = "testing list 8 is fun"),
    ImageModel(text1 = "test9", text2 = "testing list 9 is fun"),
    ImageModel(text1 = "test10", text2 = "testing list 10 is fun"),
    ImageModel(text1 = "test11", text2 = "testing list 11 is fun"),
    ImageModel(text1 = "test12", text2 = "testing list 12 is fun"),
    ImageModel(text1 = "test13", text2 = "testing list 13 is fun"),
)

val imageModels = listOf(
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
    ImageModel(R.drawable.droid),
)

data class ImageModel(
    @DrawableRes
    val id: Int? = null,
    val text1: String? = null,
    val text2: String? = null
)