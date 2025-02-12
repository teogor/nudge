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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.teogor.nudge.InternalNudgeApi
import dev.teogor.nudge.NudgeModel

@Composable
@InternalNudgeApi
internal fun CustomizableNudgeCard(
  model: NudgeModel.Customizable,
  scaleAnimation: Float,
  onActionClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  NudgeCard(
    style = model.style,
    scaleAnimation = scaleAnimation,
    content = {
      Box(
        modifier = Modifier.fillMaxWidth()
          .wrapContentHeight()
          .clip(RoundedCornerShape(16.dp))
          .padding(16.dp),
      ) {
        model.content.invoke(this, onActionClicked)
      }
    },
    modifier = modifier,
  )
}
