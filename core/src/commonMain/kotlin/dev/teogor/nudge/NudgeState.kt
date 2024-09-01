/*
 * Copyright 2024 Teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.nudge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import dev.teogor.nudge.common.NudgeLayout
import dev.teogor.nudge.coroutines.createScopeWithContext
import dev.teogor.nudge.coroutines.rememberCoroutineScope
import dev.teogor.nudge.setup.NudgeBuilder
import dev.teogor.nudge.setup.NudgeButton
import dev.teogor.nudge.ui.NudgeStack
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import dev.teogor.nudge.Duration as NudgeDuration

/**
 * Creates and remembers an instance of [NudgeState] with the specified parameters.
 *
 * This function initializes the state management for nudges, including settings for stack size, animation type,
 * and placement on the screen.
 *
 * @param maxStack The maximum number of nudges to display at once.
 * @param animation The animation to use for displaying nudges.
 * @param placement The placement of nudges on the screen.
 * @return A remembered instance of [NudgeState].
 */
@Composable
public fun rememberNudgeState(
  maxStack: Int = 3,
  animation: Animation = Animation.Bounce,
  placement: Placement = Placement.BottomEnd,
): NudgeState {
  val coroutineScope = rememberCoroutineScope(supervised = true)
  return rememberSaveable(maxStack, animation, placement, saver = NudgeState.Saver) {
    NudgeStateImpl(maxStack, animation, placement, coroutineScope)
  }
}

/**
 * Defines the state for managing and displaying nudges.
 *
 * It includes configurations for the maximum stack size, animation, placement, and the coroutine scope used
 * for managing nudge-related coroutines.
 */
@Immutable
public interface NudgeState {
  /**
   * The maximum number of nudges that can be displayed at once.
   */
  public val maxStack: Int

  /**
   * The animation to use for displaying nudges.
   */
  public val animation: Animation

  /**
   * The placement of the nudges on the screen.
   */
  public val placement: Placement

  /**
   * The [CoroutineScope] used for managing nudge-related coroutines.
   */
  public val coroutineScope: CoroutineScope

  /**
   * Displays a new nudge with the provided [NudgeModel].
   *
   * @param model The [NudgeModel] to be displayed.
   */
  @InternalNudgeApi
  public fun display(model: NudgeModel)

  /**
   * Renders the current list of nudges in the UI.
   *
   * @param modifier [Modifier] to apply to the rendered nudges.
   */
  @Composable
  public fun RenderNudges(modifier: Modifier)

  public companion object {
    // TODO: Implement saver for animation
    internal val Saver: Saver<NudgeState, *> =
      listSaver(
        save = {
          listOf(
            it.maxStack,
            // it.animation,
            it.placement.ordinal,
          )
        },
        restore = {
          NudgeState(
            maxStack = it[0],
            animation = Animation.Bounce, // Placeholder, needs actual implementation
            placement = Placement.entries[it[1]],
            coroutineScope = createScopeWithContext(supervised = true)
          )
        }
      )
  }
}

/**
 * Creates a new instance of [NudgeState] with the specified parameters.
 *
 * @param maxStack The maximum number of nudges to display at once.
 * @param animation The animation to use for displaying nudges.
 * @param placement The placement of nudges on the screen.
 * @param coroutineScope The [CoroutineScope] to use for managing nudge-related coroutines.
 * @return A new [NudgeState] instance.
 */
public fun NudgeState(
  maxStack: Int,
  animation: Animation,
  placement: Placement,
  coroutineScope: CoroutineScope,
): NudgeState {
  return NudgeStateImpl(maxStack, animation, placement, coroutineScope)
}

/**
 * Implementation of [NudgeState] that manages the display and lifecycle of nudges.
 *
 * @param maxStack The maximum number of nudges that can be displayed at once.
 * @param animation The animation to use for displaying nudges.
 * @param placement The placement of the nudges on the screen.
 * @param coroutineScope The [CoroutineScope] used for managing nudge-related coroutines.
 */
@Stable
private class NudgeStateImpl(
  override val maxStack: Int,
  override val animation: Animation,
  override val placement: Placement,
  override val coroutineScope: CoroutineScope
) : NudgeState {

  // Holds the current list of NudgeModel instances being displayed
  var currentNudgeModels by mutableStateOf<List<NudgeModel>>(emptyList())

  // Flags to track when a new nudge is being displayed or removed for animations
  val isNewNudgeDisplayed = MutableStateFlow(false)
  val isNudgeRemoved = MutableStateFlow(false)

  @OptIn(InternalNudgeApi::class)
  override fun display(model: NudgeModel) {
    isNewNudgeDisplayed.value = false
    isNudgeRemoved.value = false

    // Add the new nudge to the current list
    currentNudgeModels = currentNudgeModels.updateNudgeModels {
      add(model)
    }

    coroutineScope.launch {
      // Simulate delay for animation or processing
      delay(100)
      isNewNudgeDisplayed.value = true

      // Simulate delay for displaying the nudge
      delay(400)

      // Remove the oldest nudge if the stack size exceeds maxStack
      currentNudgeModels = currentNudgeModels.updateNudgeModels {
        if (size > maxStack) removeFirstOrNull()
      }
      isNudgeRemoved.value = true

      // Final delay before completing the update
      delay(100)
      isNudgeRemoved.value = false
    }
  }

  @Composable
  override fun RenderNudges(modifier: Modifier) {
    val newNudgeHosted by isNewNudgeDisplayed.collectAsState()
    val removedNudgeHosted by isNudgeRemoved.collectAsState()

    LaunchedEffect(currentNudgeModels) {
      handleNudgeDisplay()
    }

    if (currentNudgeModels.isNotEmpty()) {
      NudgeStack(
        stack = currentNudgeModels.toImmutableList(),
        onRemoveNudge = { reason -> handleNudgeRemoval(reason) },
        newNudgeHosted = newNudgeHosted,
        removedNudgeHosted = removedNudgeHosted,
        maxStack = maxStack,
        animation = animation,
        modifier = modifier,
      )
    }
  }

  private suspend fun handleNudgeDisplay() {
    val data = currentNudgeModels
    data.lastOrNull()?.let { currentNudge ->
      delay(currentNudge.duration.toMillis())
      updateNudgeState { nudgeList ->
        nudgeList.remove(currentNudge)
      }
    }
  }

  private fun handleNudgeRemoval(reason: RemovalReason) {
    coroutineScope.launch {
      updateNudgeState(
        immediateUpdate = reason == RemovalReason.UserAction,
      ) { nudgeList ->
        nudgeList.removeLastOrNull()
      }
    }
  }

  private fun updateNudgeState(
    immediateUpdate: Boolean = false,
    update: (MutableList<NudgeModel>) -> Unit,
  ) {
    isNewNudgeDisplayed.value = false
    coroutineScope.launch {
      if (!immediateUpdate) {
        delay(500)
      }
      currentNudgeModels = currentNudgeModels.toMutableList().apply(update)
      isNewNudgeDisplayed.value = true
    }
  }
}

private fun List<NudgeModel>.updateNudgeModels(
  action: MutableList<NudgeModel>.() -> Unit,
) = this.toMutableList().apply(action)

/**
 * Provides a DSL for creating and displaying nudges.
 *
 * @param content A lambda function for configuring the nudge using [NudgeBuilder].
 */
@NudgeDsl
public fun NudgeState.display(
  content: NudgeBuilder.() -> Unit
) {
  requireValidState()
  val builder = NudgeBuilder().apply(content)
  display(builder.build())
}

/**
 * Displays a success nudge with the specified parameters.
 *
 * @param title The title of the nudge.
 * @param description An optional description of the nudge.
 * @param actions A list of [NudgeButton] actions associated with the nudge.
 * @param duration The duration for which the nudge should be displayed.
 * @param style The style of the nudge.
 */
public fun NudgeState.success(
  title: String,
  description: String? = null,
  actions: List<NudgeButton> = emptyList(),
  duration: NudgeDuration = NudgeDuration.Persistent,
  style: Style = Style.Default
) {
  enqueueNudge(
    type = Type.Success,
    title = title,
    description = description,
    actions = actions,
    duration = duration,
    style = style
  )
}

/**
 * Displays an info nudge with the specified parameters.
 *
 * @param title The title of the nudge.
 * @param description An optional description of the nudge.
 * @param actions A list of [NudgeButton] actions associated with the nudge.
 * @param duration The duration for which the nudge should be displayed.
 * @param style The style of the nudge.
 */
public fun NudgeState.info(
  title: String,
  description: String? = null,
  actions: List<NudgeButton> = emptyList(),
  duration: NudgeDuration = NudgeDuration.Persistent,
  style: Style = Style.Default
) {
  enqueueNudge(
    type = Type.Info,
    title = title,
    description = description,
    actions = actions,
    duration = duration,
    style = style
  )
}

/**
 * Displays a warning nudge with the specified parameters.
 *
 * @param title The title of the nudge.
 * @param description An optional description of the nudge.
 * @param actions A list of [NudgeButton] actions associated with the nudge.
 * @param duration The duration for which the nudge should be displayed.
 * @param style The style of the nudge.
 */
public fun NudgeState.warning(
  title: String,
  description: String? = null,
  actions: List<NudgeButton> = emptyList(),
  duration: NudgeDuration = NudgeDuration.Persistent,
  style: Style = Style.Default
) {
  enqueueNudge(
    type = Type.Warning,
    title = title,
    description = description,
    actions = actions,
    duration = duration,
    style = style
  )
}

/**
 * Displays an error nudge with the specified parameters.
 *
 * @param title The title of the nudge.
 * @param description An optional description of the nudge.
 * @param actions A list of [NudgeButton] actions associated with the nudge.
 * @param duration The duration for which the nudge should be displayed.
 * @param style The style of the nudge.
 */
public fun NudgeState.error(
  title: String,
  description: String? = null,
  actions: List<NudgeButton> = emptyList(),
  duration: NudgeDuration = NudgeDuration.Persistent,
  style: Style = Style.Default
) {
  enqueueNudge(
    type = Type.Error,
    title = title,
    description = description,
    actions = actions,
    duration = duration,
    style = style
  )
}

/**
 * Displays a loading nudge with the specified parameters.
 *
 * @param title The title of the nudge.
 * @param description An optional description of the nudge.
 * @param actions A list of [NudgeButton] actions associated with the nudge.
 * @param duration The duration for which the nudge should be displayed.
 * @param style The style of the nudge.
 */
public fun NudgeState.loading(
  title: String,
  description: String? = null,
  actions: List<NudgeButton> = emptyList(),
  duration: NudgeDuration = NudgeDuration.Persistent,
  style: Style = Style.Default
) {
  enqueueNudge(
    type = Type.Loading,
    title = title,
    description = description,
    actions = actions,
    duration = duration,
    style = style
  )
}

/**
 * Enqueues a new nudge with the specified parameters.
 *
 * @param type The type of the nudge.
 * @param title The title of the nudge.
 * @param description An optional description of the nudge.
 * @param actions A list of [NudgeButton] actions associated with the nudge.
 * @param duration The duration for which the nudge should be displayed.
 * @param style The style of the nudge.
 */
private fun NudgeState.enqueueNudge(
  type: Type,
  title: String,
  description: String? = null,
  actions: List<NudgeButton> = emptyList(),
  duration: NudgeDuration = NudgeDuration.Persistent,
  style: Style = Style.Default
) {
  display(
    NudgeModel.Standard(
      type = type,
      title = title,
      description = description,
      actions = actions,
      duration = duration,
      style = style
    )
  )
}

/**
 * Displays a customizable nudge with the specified parameters.
 *
 * @param duration The duration for which the nudge should be displayed.
 * @param style The style of the nudge.
 * @param content The [NudgeLayout] to display within the nudge.
 */
public fun NudgeState.custom(
  duration: NudgeDuration = NudgeDuration.Persistent,
  style: Style = Style.Default,
  content: NudgeLayout,
) {
  display(
    NudgeModel.Customizable(
      duration = duration,
      style = style,
      content = content,
    )
  )
}

/**
 * Validates the current [NudgeState] to ensure that it is correctly configured.
 *
 * Throws an [InvalidNudgeStateException] if the configuration is invalid.
 */
@InternalNudgeApi
public fun NudgeState.requireValidState() {
  require(maxStack > 0) {
    throw InvalidNudgeStateException("maxStack must be greater than 0")
  }
}

private class InvalidNudgeStateException(message: String) : Exception(message)

/**
 * Provides the current [NudgeState] to the composition.
 *
 * @see ProvideNudgeState
 */
internal val LocalNudgeState: ProvidableCompositionLocal<NudgeState> = staticCompositionLocalOf {
  error("LocalNudgeState is not provided")
}

/**
 * Provides a [NudgeState] to the Compose hierarchy.
 *
 * @param state The [NudgeState] to provide.
 * @param content The content that will have access to the provided [NudgeState].
 */
@Composable
public fun ProvideNudgeState(
  state: NudgeState,
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(LocalNudgeState provides state) {
    content()
  }
}
