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


https://github.com/lokified/SelectaCompose/assets/87479198/ea0103ce-4000-45c9-b3d6-3f8466d635b3

```kotlin
val selectaListState = rememberSelectaListState(
   list = // list goes here,
)


LazyColumnSelecta(
   selectaState = selectaListState,
   verticalArrangement = Arrangement.spacedBy(8.dp),
   contentPadding = PaddingValues(16.dp),
   selectaPosition = Position.START
) {  index, item ->
   // your composable goes here
}

// to get states use;
selectaListState.selectedItems // returns a list of selected items.
selectaListState.selectedCount // returns the number of selected items.
```

For `LazyVerticalGrid` use `LazyVerticalGridSelecta` which takes all parameters except `LazyGridState` where you are required to create a `SelectaLazyGridState`. 
This is created as shown below. You can also provide your own `LazyGridState`.

https://github.com/lokified/SelectaCompose/assets/87479198/e1106650-8ec2-41b8-b285-cfa59ea178c3


```kotlin
val selectaLazyGridState = rememberSelectaLazyGridState(
   list = // list goes here
)


LazyVerticalGridSelecta(
   selectaState = selectaLazyGridState,
   contentPadding = PaddingValues(16.dp),
   horizontalArrangement = Arrangement.spacedBy(8.dp),
   verticalArrangement = Arrangement.spacedBy(8.dp),
   selectaPosition = Position.TOPSTART
) {  index, item ->
   // your composable goes here
}

// to get states use;
selectaLazyGridState.selectedItems // returns a list of selected items.
selectaLazyGridState.selectedCount // returns the number of selected items.
```
#### Clickable Item
To implement a clickabble item don't add clickable modifier to your parent item composable. Instead use `SelectaItem` as shown below. Usable in all Selecta composables.
```kotlin
LazyColumnSelecta(
  selectaState = selectaListState
) { _, item ->
  SelectaItem (
      onClick = {
        // implement your clickable here
      }
  ) {        
    // place your composable item here
  }
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
   Want to contribute? fork  the repo and open pull request.

   Have an issue? contact [Twitter](https://twitter.com/_sheldonO)

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
