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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.teogor.nudge.InternalNudgeApi
import dev.teogor.nudge.NudgeModel
import dev.teogor.nudge.Style
import dev.teogor.nudge.Type
import dev.teogor.paletteon.ktx.harmonize
import dev.teogor.paletteon.onSuccess
import dev.teogor.paletteon.onSuccessContainer
import dev.teogor.paletteon.onWarning
import dev.teogor.paletteon.onWarningContainer
import dev.teogor.paletteon.success
import dev.teogor.paletteon.successContainer
import dev.teogor.paletteon.warning
import dev.teogor.paletteon.warningContainer


@Composable
@InternalNudgeApi
internal fun StandardNudgeCard(
  model: NudgeModel.Standard,
  scaleAnimation: Float,
  onActionClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  NudgeCard(
    style = model.style,
    scaleAnimation = scaleAnimation,
    content = {
      val style = LocalTextStyle.current
      val textColor = style.color.takeOrElse { LocalContentColor.current }

      Row(
        modifier = Modifier
          .background(Color.Unspecified)
          .fillMaxWidth()
          .wrapContentHeight()
          .clip(RoundedCornerShape(16.dp))
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        if (model.type == Type.Loading) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(
                color = MaterialTheme.colorScheme.warningContainer
              )
              .padding(8.dp),
          ) {
            CircularProgressIndicator(
              color = MaterialTheme.colorScheme.onWarningContainer,
              strokeWidth = 3.dp,
            )
          }
        } else {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(
                color = when (model.style) {
                  Style.Default -> when (model.type) {
                    Type.Info -> MaterialTheme.colorScheme.primaryContainer
                    Type.Warning -> MaterialTheme.colorScheme.warningContainer
                    Type.Error -> MaterialTheme.colorScheme.errorContainer
                    Type.Success -> MaterialTheme.colorScheme.successContainer
                    Type.Loading -> Color.Unspecified
                  }

                  Style.Inverse -> when (model.type) {
                    Type.Info -> MaterialTheme.colorScheme.primary
                    Type.Warning -> MaterialTheme.colorScheme.warning
                    Type.Error -> MaterialTheme.colorScheme.error
                    Type.Success -> MaterialTheme.colorScheme.success
                    Type.Loading -> Color.Unspecified
                  }
                }
              )
              .padding(8.dp),
          ) {
            Icon(
              imageVector = model.type.icon,
              tint = when (model.style) {
                Style.Default -> when (model.type) {
                  Type.Info -> MaterialTheme.colorScheme.onPrimaryContainer
                  Type.Warning -> MaterialTheme.colorScheme.onWarningContainer
                  Type.Error -> MaterialTheme.colorScheme.onErrorContainer
                  Type.Success -> MaterialTheme.colorScheme.onSuccessContainer
                  Type.Loading -> Color.Unspecified
                }

                Style.Inverse -> when (model.type) {
                  Type.Info -> MaterialTheme.colorScheme.onPrimary
                  Type.Warning -> MaterialTheme.colorScheme.onWarning
                  Type.Error -> MaterialTheme.colorScheme.onError
                  Type.Success -> MaterialTheme.colorScheme.onSuccess
                  Type.Loading -> Color.Unspecified
                }
              },
              contentDescription = when (model.type) {
                Type.Info -> "Info"
                Type.Warning -> "Warning"
                Type.Error -> "Error"
                Type.Success -> "Success"
                Type.Loading -> "Loading"
              },
            )
          }
        }

        Column(
          modifier = Modifier.weight(1f),
        ) {
          Text(
            text = model.title,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
            ),
          )
          if (model.description.isNullOrEmpty().not()) {
            Text(
              text = model.description.orEmpty(),
              maxLines = 2,
              overflow = TextOverflow.Ellipsis,
              style = MaterialTheme.typography.labelSmall,
              color = textColor.harmonize(MaterialTheme.colorScheme.primary),
            )
          }
        }

        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          model.actions.forEach { actionProperties ->
            Button(
              onClick = {
                onActionClicked()
                actionProperties.action?.invoke()
              },
              colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = contentColorFor(MaterialTheme.colorScheme.tertiaryContainer),
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(
                  alpha = .12f,
                ),
                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(
                  alpha = .38f,
                ),
              )
            ) {
              Text(
                actionProperties.title.orEmpty(),
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                ),
              )
            }
          }
        }
      }
    },
    modifier = modifier,
  )
}
