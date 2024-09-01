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

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import dev.teogor.nudge.ui.NudgeContainer

/**
 * Provides various constants and utility functions for managing nudges within a Compose application.
 *
 * The `Nudge` interface offers access to current nudge state, predefined durations, placements, and animations,
 * which can be used for configuring and displaying nudges.
 */
public interface Nudge {

  public companion object {
    /**
     * Provides the current [NudgeState] in the composition.
     *
     * This property allows access to the current state of nudges, which can be used to manage and display
     * nudges throughout the Compose hierarchy.
     *
     * @return The current [NudgeState].
     */
    public val current: NudgeState
      @Composable
      @ReadOnlyComposable
      get() = LocalNudgeState.current

    // Duration constants
    /**
     * Represents a short duration for displaying a nudge.
     */
    public val short: Duration get() = Duration.Short

    /**
     * Represents a medium duration for displaying a nudge.
     */
    public val medium: Duration get() = Duration.Medium

    /**
     * Represents a long duration for displaying a nudge.
     */
    public val long: Duration get() = Duration.Long

    /**
     * Represents a persistent duration for displaying a nudge indefinitely.
     */
    public val persistent: Duration get() = Duration.Persistent

    /**
     * Creates a custom duration for displaying a nudge.
     *
     * @param duration The custom [kotlin.time.Duration] to use.
     * @return A [Duration] instance with the specified duration.
     */
    public fun custom(duration: kotlin.time.Duration): Duration = Duration.Custom(duration)

    // Placement constants
    /**
     * Represents the placement of a nudge at the top start of the screen.
     */
    public val topStart: Placement get() = Placement.TopStart

    /**
     * Represents the placement of a nudge at the top center of the screen.
     */
    public val topCenter: Placement get() = Placement.TopCenter

    /**
     * Represents the placement of a nudge at the top end of the screen.
     */
    public val topEnd: Placement get() = Placement.TopEnd

    /**
     * Represents the placement of a nudge at the center start of the screen.
     */
    public val centerStart: Placement get() = Placement.CenterStart

    /**
     * Represents the placement of a nudge at the center end of the screen.
     */
    public val centerEnd: Placement get() = Placement.CenterEnd

    /**
     * Represents the placement of a nudge at the bottom start of the screen.
     */
    public val bottomStart: Placement get() = Placement.BottomStart

    /**
     * Represents the placement of a nudge at the bottom center of the screen.
     */
    public val bottomCenter: Placement get() = Placement.BottomCenter

    /**
     * Represents the placement of a nudge at the bottom end of the screen.
     */
    public val bottomEnd: Placement get() = Placement.BottomEnd

    // Animation constants
    /**
     * Represents a bounce animation for a nudge.
     */
    public val bounce: Animation get() = Animation.Bounce

    /**
     * Represents a fade animation for a nudge.
     */
    public val fade: Animation get() = Animation.Fade

    /**
     * Represents a slide animation for a nudge.
     */
    public val slide: Animation get() = Animation.Slide

    /**
     * Represents a zoom animation for a nudge.
     */
    public val zoom: Animation get() = Animation.Zoom

    /**
     * Represents a flip animation for a nudge.
     */
    public val flip: Animation get() = Animation.Flip
  }
}
