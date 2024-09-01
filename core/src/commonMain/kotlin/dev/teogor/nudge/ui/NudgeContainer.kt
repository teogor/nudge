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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import dev.teogor.nudge.NudgeState
import dev.teogor.nudge.rememberNudgePlacement
import dev.teogor.nudge.rememberNudgeState
import dev.teogor.nudge.requireValidState

@Composable
public fun NudgeContainer(
  modifier: Modifier = Modifier,
  state: NudgeState = rememberNudgeState()
) {
  LaunchedEffect(state.placement) {
    println("State placement: ${state.placement}")
  }
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = rememberNudgePlacement(state.placement)
  ) {
    state.requireValidState()

    state.RenderNudges(modifier)
  }
}
