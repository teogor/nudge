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

package dev.teogor.nudge.utils

import dev.teogor.paletteon.dynamiccolor.ContrastCurve
import dev.teogor.paletteon.dynamiccolor.DynamicColor
import dev.teogor.paletteon.scheme.DynamicScheme

internal fun outlineVariantInverse(): DynamicColor = DynamicColor(
  name = "outline_variant",
  palette = { scheme -> scheme.neutralVariantPalette },
  tone = { scheme -> if (!scheme.isDark) 30.0 else 80.0 },
  isBackground = false,
  background = { scheme -> highestSurface(scheme) },
  secondBackground = null,
  contrastCurve = ContrastCurve(1.0, 1.0, 3.0, 4.5),
  toneDeltaPair = null,
)

internal fun highestSurface(scheme: DynamicScheme): DynamicColor {
  return if (scheme.isDark) surfaceBright() else surfaceDim()
}

internal fun surfaceDim(): DynamicColor = DynamicColor(
  name = "surface_dim",
  palette = { scheme -> scheme.neutralPalette },
  tone = { scheme ->
    if (scheme.isDark) 6.0
    else ContrastCurve(87.0, 87.0, 80.0, 75.0).get(scheme.contrastLevel)
  },
  isBackground = true,
  background = null,
  secondBackground = null,
  contrastCurve = null,
  toneDeltaPair = null,
)

internal fun surfaceBright(): DynamicColor = DynamicColor(
  name = "surface_bright",
  palette = { scheme -> scheme.neutralPalette },
  tone = { scheme ->
    if (scheme.isDark) ContrastCurve(24.0, 24.0, 29.0, 34.0).get(scheme.contrastLevel)
    else 98.0
  },
  isBackground = true,
  background = null,
  secondBackground = null,
  contrastCurve = null,
  toneDeltaPair = null,
)
