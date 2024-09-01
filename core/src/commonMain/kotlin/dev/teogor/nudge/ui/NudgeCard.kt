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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.teogor.nudge.Style
import dev.teogor.nudge.utils.outlineVariantInverse
import dev.teogor.paletteon.PaletteonTheme

@Composable
public fun NudgeCard(
  style: Style,
  scaleAnimation: Float,
  content: @Composable () -> Unit,
  modifier: Modifier = Modifier,
) {
  Card(
    shape = NudgeCardDefaults.shape,
    modifier = Modifier
      .padding(start = 16.dp, end = 16.dp)
      .wrapContentHeight()
      .scale(scaleAnimation)
      .then(modifier),
    border = NudgeCardDefaults.borderForStyle(style),
    colors = NudgeCardDefaults.customCardColors(style),
    elevation = NudgeCardDefaults.elevationValues(),
  ) {
    content.invoke()
  }
}

internal object NudgeCardDefaults {

  public val shape: Shape
    @Composable
    get() = RoundedCornerShape(16.dp)

  @Composable
  public fun elevationValues(
    defaultElevation: Dp = 4.dp,
    pressedElevation: Dp = 6.dp,
    focusedElevation: Dp = 6.dp,
    hoveredElevation: Dp = 6.dp,
    draggedElevation: Dp = 8.dp,
    disabledElevation: Dp = 2.dp
  ): CardElevation = CardDefaults.elevatedCardElevation(
    defaultElevation = defaultElevation,
    pressedElevation = pressedElevation,
    focusedElevation = focusedElevation,
    hoveredElevation = hoveredElevation,
    draggedElevation = draggedElevation,
    disabledElevation = disabledElevation
  )

  @Composable
  public fun colorSchemeForStyle(style: Style): CardColors {
    val defaultColors = CardDefaults.cardColors()
    return when (style) {
      Style.Inverse -> defaultColors.copy(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = contentColorFor(MaterialTheme.colorScheme.surface)
      )

      Style.Default -> defaultColors.copy(
        containerColor = MaterialTheme.colorScheme.inverseSurface,
        contentColor = contentColorFor(MaterialTheme.colorScheme.inverseSurface)
      )
    }
  }

  @Composable
  public fun customCardColors(
    style: Style,
    containerColor: Color = Color.Unspecified,
    contentColor: Color = Color.Unspecified,
    disabledContainerColor: Color = Color.Unspecified,
    disabledContentColor: Color = Color.Unspecified
  ): CardColors {
    val defaultColors = colorSchemeForStyle(style)
    return CardColors(
      containerColor = containerColor.takeIf { it != Color.Unspecified }
        ?: defaultColors.containerColor,
      contentColor = contentColor.takeIf { it != Color.Unspecified } ?: defaultColors.contentColor,
      disabledContainerColor = disabledContainerColor.takeIf { it != Color.Unspecified }
        ?: defaultColors.disabledContainerColor,
      disabledContentColor = disabledContentColor.takeIf { it != Color.Unspecified }
        ?: defaultColors.disabledContentColor
    )
  }

  /**
   * Creates a [BorderStroke] for the outlined card based on the style.
   *
   * @param style The style of the card.
   */
  @Composable
  public fun borderForStyle(style: Style): BorderStroke {
    val theme = PaletteonTheme.current

    val color = when (style) {
      Style.Inverse -> theme.getColor { outlineVariantInverse() }
      Style.Default -> MaterialTheme.colorScheme.outlineVariant
    }
    return remember(color) { BorderStroke(1.0.dp, color) }
  }
}
