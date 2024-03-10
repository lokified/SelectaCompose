## SelectaCompose
This is an android library that provides a composable for creating selectable item lists in Jetpack Compose.

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p><br>

## Download
[![](https://jitpack.io/v/lokified/SelectaCompose.svg)](https://jitpack.io/#lokified/SelectaCompose)


### Usage

### Gradle
Add the codes below to your root `build.gradle` file:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### Groovy
```groovy
dependencies {
    implementation 'com.github.lokified:SelectaCompose:tag'
}
```

#### Kotlin
```kotlin
dependencies {
    implementation ("com.github.lokified:SelectaCompose:tag")
}
```

#### Example

For `LazyColumn` use `LazyColumnSelecta` which takes all parameters except `LazyListState` where you are required to create a `SelectaListState`. 
This is created as shown below. You can also provide your own `LazyListState`.

```kotlin
val selectaListState = rememberSelectaListState(
   list = // list goes here,
   selectedItems = {
      // returns selected list
   }
)


LazyColumnSelecta(
   selectaState = selectaListState,
   verticalArrangement = Arrangement.spacedBy(8.dp),
   contentPadding = PaddingValues(16.dp),
   selectaPosition = Position.START
) {  index, item ->
   // your composable goes here
}
```

For `LazyVerticalGrid` use `LazyVerticalGridSelecta` which takes all parameters except `LazyGridState` where you are required to create a `SelectaLazyGridState`. 
This is created as shown below. You can also provide your own `LazyGridState`.

```kotlin
val selectaLazyGridState = rememberSelectaLazyGridState(
   list = // list goes here,
   selectedItems = {
      // returns selected list
   }
)


LazyVerticalGridSelecta(
   selectaState = selectaLazyGridState,
   contentPadding = PaddingValues(16.dp),
   horizontalArrangement = Arrangement.spacedBy(8.dp),
   verticalArrangement = Arrangement.spacedBy(8.dp),
   position = Position.TOPSTART
) {  index, item ->
   // your composable goes here
}
```

#### Styles

To style the selecta container you can override the following in the composables parameters:
 * selectaItemColors - This changes the color of the container and icon.
   ```kotlin
     selectaItemColors: SelectaItemColors = SelectaDefaults.colors(
         selectedContainerColor = ,
         unselectedContainerColor = ,
         iconColor = 
     )  
   ```
 * selectaShape - This changes the shape of the container.
   ```kotlin
     selectaShape: SelectaItemShape = SelectaDefaults.colors(
         containerShape = 
     )
   ```
 * selectaPadding - This changes container padding and icon.
   ```kotlin
     selectaPadding: SelectaItemPadding = SelectaDefaults.colors(
          selectedContainerPadding = ,
          unselectedContainerPadding = ,
          iconPadding = 
     )  
   ```

   ## Contributing

   Open an issue or contact [Twitter](https://twitter.com/_sheldonO)

   ## License

   Copyright 2024 Sheldon Okware

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
