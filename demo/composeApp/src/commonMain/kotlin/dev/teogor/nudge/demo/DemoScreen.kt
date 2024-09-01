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

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.teogor.nudge.Animation
import dev.teogor.nudge.Duration
import dev.teogor.nudge.NudgeState
import dev.teogor.nudge.Placement
import dev.teogor.nudge.Style
import dev.teogor.nudge.common.DismissAction
import dev.teogor.nudge.display

@Composable
public fun DemoScreen(
  nudgeState: NudgeState,
  placement: Placement,
  onPlacementChange: (Placement) -> Unit,
  animation: Animation,
  onAnimationChange: (Animation) -> Unit,
) {
  val scrollState = rememberLazyGridState()
  val iconGalleryViewModel = viewModel { IconGalleryViewModel() }
  val icons by iconGalleryViewModel.icons.collectAsState()

  LazyVerticalGrid(
    state = scrollState,
    columns = GridCells.Adaptive(minSize = 200.dp),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    modifier = Modifier
      .fillMaxWidth()
  ) {
    playgrounds(
      nudgeState = nudgeState,
      placement = placement,
      onPlacementChange = onPlacementChange,
      animation = animation,
      onAnimationChange = onAnimationChange
    )

    divider()

    iconGalleryLayout(iconGalleryViewModel, icons)
  }
}

private fun LazyGridScope.playgrounds(
  nudgeState: NudgeState,
  placement: Placement,
  onPlacementChange: (Placement) -> Unit,
  animation: Animation,
  onAnimationChange: (Animation) -> Unit,
) = item(
  span = {
    GridItemSpan(maxLineSpan)
  }
) {
  Column(
    modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    val placementOptions = remember { Placement.entries }
    val selectedPlacementIndex by remember(placement) {
      mutableStateOf(placementOptions.indexOf(placement))
    }
    SingleChoiceSegmentedButtonRow(
      modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
      placementOptions.forEachIndexed { index, currentPlacement ->
        SegmentedButton(
          shape = SegmentedButtonDefaults.itemShape(index = index, count = placementOptions.size),
          selected = selectedPlacementIndex == index,
          onClick = { onPlacementChange(currentPlacement) },
        ) {
          Text(currentPlacement.name)
        }
      }
    }

    val animationOptions = remember { AnimationType.entries }
    val selectedAnimationIndex by remember(animation.toAnimationType()) {
      mutableStateOf(animationOptions.indexOf(animation.toAnimationType()))
    }
    SingleChoiceSegmentedButtonRow(
      modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
      animationOptions.forEachIndexed { index, currentAnimation ->
        SegmentedButton(
          shape = SegmentedButtonDefaults.itemShape(index = index, count = animationOptions.size),
          selected = selectedAnimationIndex == index,
          onClick = { onAnimationChange(currentAnimation.toAnimation()) },
        ) {
          Text(currentAnimation.name)
        }
      }
    }

    Button(
      onClick = {
        nudgeState.display {
          success {
            setTitle("Success!")
            setDescription("This is a success message.")
          }
          duration(Duration.Short)
          button("OK")
          style(Style.entries.random())
        }
      }
    ) {
      Text("Show Success Nudge")
    }

    // Button for Warning Nudge
    Button(
      onClick = {
        nudgeState.display {
          warning {
            setTitle("Warning!")
            setDescription("This is a warning message.")
          }
          duration(Duration.Short)
          button("OK")
          style(Style.entries.random())
        }
      }
    ) {
      Text("Show Warning Nudge")
    }

    // Button for Info Nudge
    Button(
      onClick = {
        nudgeState.display {
          info {
            setTitle("Info!")
            setDescription("This is an informational message.")
          }
          duration(Duration.Short)
          button("OK")
          style(Style.entries.random())
        }
      }
    ) {
      Text("Show Info Nudge")
    }

    // Button for Error Nudge
    Button(
      onClick = {
        nudgeState.display {
          error {
            setTitle("Error!")
            setDescription("This is an error message.")
          }
          duration(Duration.Short)
          button("OK")
          style(Style.entries.random())
        }
      }
    ) {
      Text("Show Error Nudge")
    }

    // Button for Loading Nudge
    Button(
      onClick = {
        nudgeState.display {
          loading {
            setTitle("Loading!")
            setDescription("Please wait, loading...")
          }
          duration(Duration.Persistent)
          button("Dismiss")
          style(Style.entries.random())
        }
      }
    ) {
      Text("Show Loading Nudge")
    }

    // Button for Custom Nudge
    Button(
      onClick = {
        nudgeState.display {
          content { dismiss ->
            CustomizableNudge(dismiss)
          }
          duration(Duration.Persistent)
          button("Dismiss")
          style(Style.entries.random())
        }
      }
    ) {
      Text("Show Custom Nudge")
    }
  }
}
// Define a data class to represent each customizable nudge entry
private data class NudgeOption(val description: String, val buttonText: String)

// Create a list of possible nudge options
private val nudgeOptions = listOf(
  NudgeOption("This is a custom nudge with a message.", "Dismiss"),
  NudgeOption("Here's a unique custom notification.", "Got It"),
  NudgeOption("Don't miss this important update!", "Okay"),
  NudgeOption("Here's some info you might need.", "Understood"),
  NudgeOption("Check out this special announcement!", "See More"),
  NudgeOption("You have a new alert!", "Alert")
)

@Composable
private fun BoxScope.CustomizableNudge(dismiss: DismissAction) {
  // Randomly select a nudge option
  val nudgeOption = remember { nudgeOptions.random() }

  // Display the text and button according to the selected nudge option
  Text(
    text = nudgeOption.description,
    modifier = Modifier.align(Alignment.CenterStart)
  )
  Button(
    onClick = dismiss,
    modifier = Modifier.align(Alignment.CenterEnd)
  ) {
    Text(nudgeOption.buttonText)
  }
}

private fun LazyGridScope.divider() = item(
  span = {
    GridItemSpan(maxLineSpan)
  }
) {
  HorizontalDivider(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 4.dp, vertical = 6.dp),
  )
}
