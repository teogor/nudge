# 🛎️ Nudge - Easy Snackbars for Compose

🛎️ Nudge simplifies snackbars, bringing quick, customizable notifications to your Compose apps!

### Live Demo

Experience Nudge in action and see its features in real-time on our **[Live Demo](https://teogor.dev/nudge)**.

## Installation

To use `Nudge` in your project, you'll need to add the library dependency. Choose the appropriate
method based on your Gradle build script:

### For Gradle Kotlin DSL (`build.gradle.kts`)

Add the following dependency to your `build.gradle.kts` file:

```kts
dependencies {
  implementation("dev.teogor.nudge:nudge:1.0.0-alpha01")
}
```

### For Gradle Groovy DSL (`build.gradle`)

Add the following dependency to your `build.gradle` file:

```gradle
dependencies {
    implementation 'dev.teogor.nudge:nudge:1.0.0-alpha01'
}
```

For a more comprehensive guide on how to include the library in your project, refer to
our [installation documentation](./docs/index.md#adding-nudge-dependencies-manually).

## Basic Usage

### Setup

To start using `Nudge`, you'll need to set up a `NudgeState` and integrate it into your Compose UI.
Here's a step-by-step guide:

#### 1. Create and Remember the NudgeState

Use `rememberNudgeState` to create and remember an instance of `NudgeState`. This should be done at
the top level of your Composable function to ensure the state is preserved across recompositions.

```kotlin
@Composable
fun MyApp() {
  // Create and remember the NudgeState instance
  val nudgeState = rememberNudgeState(
    maxStack = 3,           // Maximum number of nudges that can be stacked
    animation = Animation.Bounce,  // Animation style for the nudge
    placement = Placement.BottomEnd  // Position of the nudge on the screen
  )
}
```

#### 2. Integrate with Scaffold and Display Nudges

Once you have the `NudgeState` instance, integrate it into your Compose UI using the `Scaffold`
and `NudgeContainer`. This ensures the nudges are displayed correctly within your app's layout.

```kotlin
@Composable
fun MyApp() {
  Scaffold(
    snackbarHost = {
      NudgeContainer(
        modifier = Modifier,
        state = nudgeState,
      )
    }
  ) {
    // Your screen content goes here
  }
}
```

### Displaying a Nudge

To display a nudge, use the `display` extension method of `NudgeState`. This method provides a
DSL-style API for configuring and customizing the nudge according to your needs.

> [!WARNING]  
> Make sure to import the `display` extension to access the DSL features:
> ```kotlin
> import dev.teogor.nudge.display
> ```

With the `display` method, you can adjust various aspects of the nudge, such as its title,
description, duration, style, and more. Here’s an example of how to configure a nudge:

```kotlin
nudgeState.display {
  // Set the nudge type and its content
  success {
    setTitle("Operation Successful")
    setDescription("Your changes have been saved.")
  }

  // Configure the nudge duration
  duration(Duration.Short)

  // Add an action button
  button("OK") {
    // Define action to perform on button click
  }

  // Apply a style to the nudge
  style(Style.Default)
}
```

### **Customizing Your Nudge**

To customize your nudge, you can use:

- **Nudge Types:**
  - `success { ... }` for a success nudge
  - `error { ... }` for an error nudge
  - `info { ... }` for an informational nudge
  - `warning { ... }` for a warning nudge
  - `loading { ... }` for a loading nudge
  - `content { dismiss -> ... }` for a custom nudge

- **Duration:**
  - `Duration.Short` for a brief display (4 seconds)
  - `Duration.Medium` for a moderate display (7 seconds)
  - `Duration.Long` for a longer display (10 seconds)
  - `Duration.Persistent` for an indefinite display until manually dismissed
  - `Duration.Custom(duration)` for a specific custom duration

- **Style:**
  - `Style.Default` for the default appearance
  - `Style.Inverse` for an inverted color scheme

Certainly! Here's a dedicated section for the features table with the additional column for issue
links:

## Planned Features and Development Status

Below is a summary of the features planned or in progress for the `Nudge` library, along with their
current development phase and links to related issues or tickets for more details, if available.

| Feature                        | Development Phase | Issue Link                                           |
|--------------------------------|:-----------------:|------------------------------------------------------|
| **Button Action Return Value** |    🗓️ Planned    | [Issue #1](https://github.com/teogor/nudge/issues/1) |

### Legend:

- 🚧 **Under Review**: The feature is under review or in the testing phase.
- 🛠️ **In Progress**: The feature is currently being worked on.
- 🗓️ **Planned**: The feature is planned but not yet started.

Each issue link directs to the corresponding issue or ticket in your issue tracker, providing more
details on the status and discussions around each feature.

## Platform Compatibility

Below is a summary of the platform compatibility for the `Nudge` library, including its core module
and related components:

| Component      | Android | Desktop | Multiplatform |
|----------------|:-------:|:-------:|:-------------:|
| **nudge-core** |    ✅    |    ✅    |       ✅       |

### Legend:

- ✅ **Supported**: The component is fully supported on the platform.
- ❌ **Not Supported**: The component is not supported on the platform.

> [!NOTE]
> Multiplatform support includes `iOS`, `WasmJS`, `JS`, `JVM`, `android`, and `macOS` with
> configurations for `iosX64`, `iosArm64`, `iosSimulatorArm64`, `browser`, `nodejs`, `macosX64`,
> and `macosArm64`.

## Additional Resources

For more detailed documentation and advanced usage, please refer to the [Nudge Documentation](./docs/index.md).

## Contributing

Contributions to Paletteon are welcome! If you have any ideas, bug reports, or feature requests, please open an issue or submit a pull request. For more information, please refer to our [Contributing Guidelines](CONTRIBUTING.md).

## Find this repository useful? :heart:

Support it by joining [stargazers](https://github.com/teogor/nudge/stargazers) for this repository. :star: <br>
Also, [follow me](https://github.com/teogor) on GitHub for my next creations! 🤩

## License

```xml
  Designed and developed by 2024 Teogor (Teodor Grigor)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```