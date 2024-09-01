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

package dev.teogor.nudge.demo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.teogor.nudge.Animation
import dev.teogor.nudge.Placement
import dev.teogor.nudge.ProvideNudgeState
import dev.teogor.nudge.rememberNudgeState
import dev.teogor.nudge.ui.NudgeContainer
import dev.teogor.paletteon.PaletteonDynamicTheme
import dev.teogor.paletteon.ui.components.PaletteonThemedSurface
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
public fun App() {
  PaletteonDynamicTheme(
    seedColor = Color.Green,
    useDarkTheme = isSystemInDarkTheme(),
    animate = true,
  ) {
    PaletteonThemedSurface {
      var placement by remember { mutableStateOf(Placement.BottomEnd) }
      var animation by remember { mutableStateOf<Animation>(Animation.Bounce) }
      val nudgeState = rememberNudgeState(
        maxStack = 3,
        animation = animation,
        placement = placement
      )

      ProvideNudgeState(state = nudgeState) {
        Scaffold(
          snackbarHost = {
            NudgeContainer(
              modifier = Modifier.fillMaxWidth(1f),
              state = nudgeState,
            )
          },
        ) {
          DemoScreen(
            nudgeState = nudgeState,
            placement = placement,
            onPlacementChange = { placement = it },
            animation = animation,
            onAnimationChange = { animation = it },
          )
        }
      }
    }
  }
}
