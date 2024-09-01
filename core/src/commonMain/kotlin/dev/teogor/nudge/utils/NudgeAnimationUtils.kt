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

package dev.teogor.nudge.utils

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.teogor.nudge.Animation
import dev.teogor.nudge.common.Constant
import dev.teogor.nudge.common.NudgeMetrics
import kotlin.math.abs
import kotlin.math.ln

@Composable
internal fun calculateScaleAnimation(
  index: Int,
  nudgeCount: Int,
  scaleDecrement: Float
): Float {
  // Calculate the multiplier based on the position in the list
  val multiplier = abs(index.toFloat() - nudgeCount.toFloat() + 1)
  return 1f - (multiplier * scaleDecrement).coerceAtLeast(0f)
}

@Composable
internal fun calculatePaddingAnimation(
  index: Int,
  nudgeCount: Int,
  paddingIncrement: Int
): Dp {
  // Calculate padding based on the position in the list
  val multiplier = abs(index.toFloat() - nudgeCount.toFloat())
  val paddingMultiplier = (ln(multiplier + 1) / ln(2.0)).times(paddingIncrement)
  return (paddingMultiplier + paddingIncrement).dp
}

@Composable
internal fun calculateNudgeMetrics(
  index: Int,
  nudgeCount: Int,
  animation: Animation
): NudgeMetrics {
  val scaleAnimation by animateFloatAsState(
    targetValue = calculateScaleAnimation(
      index = index,
      nudgeCount = nudgeCount,
      scaleDecrement = Constant.SCALE_DECREMENT
    ),
    animationSpec = animation.scaleAnimationSpec
  )
  val initialPos by animateFloatAsState(0f, animationSpec = animation.scaleAnimationSpec)
  val paddingAnimation by animateDpAsState(
    targetValue = calculatePaddingAnimation(
      index = index,
      nudgeCount = nudgeCount,
      paddingIncrement = Constant.PADDING_INCREMENT
    ),
    animationSpec = animation.paddingAnimationSpec
  )
  return NudgeMetrics(scaleAnimation, paddingAnimation, initialPos)
}

@Composable
internal fun nudgeVisibilityModifier(
  index: Int,
  nudgeCount: Int,
  maxStack: Int,
  newNudgeHosted: Boolean,
  removedNudgeHosted: Boolean
): Boolean {
  // Determine visibility based on the position and flags
  if (nudgeCount.dec() == index) return newNudgeHosted
  if (index == maxStack) return removedNudgeHosted
  if (index >= maxStack && !newNudgeHosted) return false
  return true
}
