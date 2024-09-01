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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.teogor.nudge.InternalNudgeApi
import dev.teogor.nudge.NudgeModel
import dev.teogor.nudge.RemovalReason

@Composable
@InternalNudgeApi
internal fun NudgeContent(
  model: NudgeModel,
  draggableModifier: Modifier,
  scaleAnimation: Float,
  nudgeDataSize: Int,
  index: Int,
  maxStack: Int,
  onRemoveNudge: (RemovalReason) -> Unit,
) {
  val nudgeScale = if (nudgeDataSize - index > maxStack) {
    0f
  } else {
    scaleAnimation.coerceAtLeast(0f)
  }

  when (model) {
    is NudgeModel.Customizable -> CustomizableNudgeCard(
      model = model,
      scaleAnimation = nudgeScale,
      onActionClicked = { onRemoveNudge(RemovalReason.UserAction) },
      modifier = draggableModifier,
    )

    is NudgeModel.Standard -> StandardNudgeCard(
      model = model,
      scaleAnimation = nudgeScale,
      onActionClicked = { onRemoveNudge(RemovalReason.UserAction) },
      modifier = draggableModifier,
    )
  }
}
