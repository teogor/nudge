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
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.teogor.nudge.Animation
import kotlinx.coroutines.delay

@Composable
public fun <T> SwipeableDismissContainer(
  item: T,
  modifier: Modifier,
  animation: Animation,
  onDismiss: (T) -> Unit,
  dismissAnimationDuration: Int = 500,
  enabled: Boolean = false,
  swipeDirections: Set<SwipeToDismissBoxValue> = setOf(SwipeToDismissBoxValue.StartToEnd),
  content: @Composable (T) -> Unit
) {
  var isDismissed by remember { mutableStateOf(false) }
  val state = rememberSwipeToDismissBoxState(
    confirmValueChange = { value ->
      if (enabled && swipeDirections.contains(value)) {
        isDismissed = true
        true
      } else {
        false
      }
    }
  )

  LaunchedEffect(isDismissed) {
    if (isDismissed) {
      delay(dismissAnimationDuration.toLong())
      onDismiss(item)
    }
  }

  AnimatedVisibility(
    visible = !isDismissed,
    exit = shrinkVertically(
      animationSpec = tween(durationMillis = dismissAnimationDuration),
      shrinkTowards = Alignment.Top
    ) + animation.exitTransition
  ) {
    SwipeToDismissBox(
      modifier = modifier,
      state = state,
      backgroundContent = {},
      enableDismissFromStartToEnd = swipeDirections.contains(SwipeToDismissBoxValue.StartToEnd),
      enableDismissFromEndToStart = swipeDirections.contains(SwipeToDismissBoxValue.EndToStart),
      content = { content(item) },
      gesturesEnabled = enabled,
    )
  }
}
