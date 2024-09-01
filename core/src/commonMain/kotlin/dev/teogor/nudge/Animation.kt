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

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset

// Define the Animation interface
public sealed interface Animation {
  public val paddingAnimationSpec: AnimationSpec<Dp>
  public val scaleAnimationSpec: AnimationSpec<Float>
  public val enterAnimationSpec: FiniteAnimationSpec<IntOffset>
  public val exitAnimationSpec: FiniteAnimationSpec<IntOffset>
  public val enterTransition: EnterTransition
  public val exitTransition: ExitTransition

  // Bounce Animation
  public data object Bounce : Animation {
    override val paddingAnimationSpec: AnimationSpec<Dp> = tween(durationMillis = 300)
    override val scaleAnimationSpec: AnimationSpec<Float> =
      spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessMedium)
    override val enterAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(durationMillis = 500)
    override val exitAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(durationMillis = 300)
    override val enterTransition: EnterTransition =
      slideInVertically(initialOffsetY = { it }) + expandVertically(expandFrom = Alignment.Bottom)
    override val exitTransition: ExitTransition =
      slideOutVertically(targetOffsetY = { it }) + shrinkVertically(shrinkTowards = Alignment.Top)
  }

  // Fade Animation
  public data object Fade : Animation {
    override val paddingAnimationSpec: AnimationSpec<Dp> = tween(durationMillis = 300)
    override val scaleAnimationSpec: AnimationSpec<Float> = tween(durationMillis = 300)
    override val enterAnimationSpec: FiniteAnimationSpec<IntOffset> =spring(stiffness = Spring.StiffnessMediumLow)
    override val exitAnimationSpec: FiniteAnimationSpec<IntOffset> = spring(stiffness = Spring.StiffnessMediumLow)
    override val enterTransition: EnterTransition = fadeIn() + scaleIn()
    override val exitTransition: ExitTransition = fadeOut() + scaleOut()
  }

  // Slide Animation
  public data object Slide : Animation {
    override val paddingAnimationSpec: AnimationSpec<Dp> = tween(durationMillis = 300)
    override val scaleAnimationSpec: AnimationSpec<Float> = tween(durationMillis = 300)
    override val enterAnimationSpec: FiniteAnimationSpec<IntOffset> =
      spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
      )
    override val exitAnimationSpec: FiniteAnimationSpec<IntOffset> =spring(
      stiffness = Spring.StiffnessMediumLow,
      visibilityThreshold = IntOffset.VisibilityThreshold
    )
    override val enterTransition: EnterTransition =
      slideInHorizontally(initialOffsetX = { it }) + fadeIn()
    override val exitTransition: ExitTransition =
      slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
  }

  // Zoom Animation
  public data object Zoom : Animation {
    override val paddingAnimationSpec: AnimationSpec<Dp> = tween(durationMillis = 300)
    override val scaleAnimationSpec: AnimationSpec<Float> = tween(durationMillis = 300)
    override val enterAnimationSpec: FiniteAnimationSpec<IntOffset> = spring(stiffness = Spring.StiffnessMediumLow)
    override val exitAnimationSpec: FiniteAnimationSpec<IntOffset> = spring(stiffness = Spring.StiffnessMediumLow)
    override val enterTransition: EnterTransition = scaleIn(initialScale = 0.5f) + fadeIn()
    override val exitTransition: ExitTransition = scaleOut(targetScale = 0.5f) + fadeOut()
  }

  // Flip Animation
  public data object Flip : Animation {
    override val paddingAnimationSpec: AnimationSpec<Dp> = tween(durationMillis = 300)
    override val scaleAnimationSpec: AnimationSpec<Float> = tween(durationMillis = 300)
    override val enterAnimationSpec: FiniteAnimationSpec<IntOffset> = flipEnterAnimationSpec()
    override val exitAnimationSpec: FiniteAnimationSpec<IntOffset> = flipExitAnimationSpec()
    override val enterTransition: EnterTransition = flipIn()
    override val exitTransition: ExitTransition = flipOut()
  }

  // Helper functions for Flip and Rotate
  public fun flipEnterAnimationSpec(): FiniteAnimationSpec<IntOffset> {
    return keyframes {
      durationMillis = 500
      IntOffset(0, 0) at 0 using LinearOutSlowInEasing
      IntOffset(0, -100) at 250 using FastOutSlowInEasing
      IntOffset(0, 0) at 500 using LinearOutSlowInEasing
    }
  }

  public fun flipExitAnimationSpec(): FiniteAnimationSpec<IntOffset> {
    return keyframes {
      durationMillis = 500
      IntOffset(0, 0) at 0 using LinearOutSlowInEasing
      IntOffset(0, 100) at 250 using FastOutSlowInEasing
      IntOffset(0, 0) at 500 using LinearOutSlowInEasing
    }
  }

  public fun flipIn(): EnterTransition {
    return scaleIn(initialScale = 0.5f) + fadeIn()
  }

  public fun flipOut(): ExitTransition {
    return scaleOut(targetScale = 0.5f) + fadeOut()
  }
}
