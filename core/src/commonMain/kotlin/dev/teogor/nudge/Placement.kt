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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

public enum class Placement {
  TopStart,
  TopCenter,
  TopEnd,
  CenterStart,
  CenterEnd,
  BottomStart,
  BottomCenter,
  BottomEnd
}

@Composable
public fun rememberNudgePlacement(
  placement: Placement = Placement.BottomEnd
): Alignment {
  return remember(placement) {
    when (placement) {
      Placement.TopStart -> Alignment.TopStart
      Placement.TopCenter -> Alignment.TopCenter
      Placement.TopEnd -> Alignment.TopEnd
      Placement.CenterStart -> Alignment.CenterStart
      Placement.CenterEnd -> Alignment.CenterEnd
      Placement.BottomStart -> Alignment.BottomStart
      Placement.BottomCenter -> Alignment.BottomCenter
      Placement.BottomEnd -> Alignment.BottomEnd
    }
  }
}
