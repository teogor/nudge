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

package dev.teogor.nudge.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import dev.teogor.nudge.Animation
import dev.teogor.nudge.NudgeModel
import dev.teogor.nudge.RemovalReason
import dev.teogor.nudge.common.Constant
import dev.teogor.nudge.utils.calculateNudgeMetrics
import dev.teogor.nudge.utils.nudgeVisibilityModifier
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun NudgeStack(
  stack: ImmutableList<NudgeModel>,
  maxStack: Int,
  animation: Animation,
  onRemoveNudge: (RemovalReason) -> Unit,
  newNudgeHosted: Boolean,
  removedNudgeHosted: Boolean,
  modifier: Modifier = Modifier,
) {
  val nudgeCount = stack.size
  Box(contentAlignment = Alignment.BottomCenter, modifier = modifier) {
    stack.forEachIndexed { index, nudge ->
      val metrics = calculateNudgeMetrics(
        index = index,
        nudgeCount = nudgeCount,
        animation = animation
      )

      AnimatedVisibility(
        label = "Nudge_$index",
        visible = nudgeVisibilityModifier(
          index = index,
          nudgeCount = nudgeCount,
          maxStack = maxStack,
          newNudgeHosted = newNudgeHosted,
          removedNudgeHosted = removedNudgeHosted,
        ),
        enter = slideIn(
          initialOffset = { IntOffset(0, Constant.Y_TARGET_ENTER) },
          animationSpec = animation.enterAnimationSpec,
        ) + animation.enterTransition,
        exit = slideOut(
          targetOffset = { IntOffset(0, Constant.Y_TARGET_EXIT) },
          animationSpec = animation.exitAnimationSpec,
        ) + animation.exitTransition,
        modifier = Modifier
          .padding(bottom = metrics.paddingAnimation)
          .clip(NudgeCardDefaults.shape)
      ) {
        SwipeableDismissContainer(
          modifier = Modifier,
          item = nudge,
          animation = animation,
          enabled = index == stack.lastIndex,
          onDismiss = {
            onRemoveNudge(RemovalReason.UserAction)
          },
          swipeDirections = setOf(
            SwipeToDismissBoxValue.StartToEnd, SwipeToDismissBoxValue.EndToStart
          ),
        ) { nudge ->
          NudgeContent(
            model = nudge,
            draggableModifier = Modifier,
            scaleAnimation = metrics.scaleAnimation,
            nudgeDataSize = nudgeCount,
            index = index,
            maxStack = maxStack,
            onRemoveNudge = onRemoveNudge,
          )
        }
      }
    }
  }
}
