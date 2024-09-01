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

package dev.teogor.nudge.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.teogor.nudge.Icons
import dev.teogor.nudge.Icons.Filled

public val Icons.Filled.Warning: ImageVector
    get() {
        if (_warning != null) {
            return _warning!!
        }
        _warning = Builder(name = "Warning", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF1C274C)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(3.0f, 10.418f)
                curveTo(3.0f, 7.2188f, 3.0f, 5.6211f, 3.3789f, 5.082f)
                curveTo(3.7539f, 4.543f, 5.2578f, 4.0313f, 8.2656f, 3.0f)
                lineTo(8.8359f, 2.8047f)
                curveTo(10.4063f, 2.2695f, 11.1875f, 2.0f, 12.0f, 2.0f)
                curveTo(12.8125f, 2.0f, 13.5938f, 2.2695f, 15.1641f, 2.8047f)
                lineTo(15.7344f, 3.0f)
                curveTo(18.7422f, 4.0313f, 20.2461f, 4.543f, 20.6211f, 5.082f)
                curveTo(21.0f, 5.6211f, 21.0f, 7.2188f, 21.0f, 10.418f)
                lineTo(21.0f, 11.9922f)
                curveTo(21.0f, 17.6289f, 16.7617f, 20.3672f, 14.1016f, 21.5273f)
                curveTo(13.3789f, 21.8438f, 13.0195f, 22.0f, 12.0f, 22.0f)
                curveTo(10.9805f, 22.0f, 10.6211f, 21.8438f, 9.8984f, 21.5273f)
                curveTo(7.2383f, 20.3672f, 3.0f, 17.6289f, 3.0f, 11.9922f)
                close()
                moveTo(12.0f, 7.25f)
                curveTo(12.4141f, 7.25f, 12.75f, 7.5859f, 12.75f, 8.0f)
                lineTo(12.75f, 12.0f)
                curveTo(12.75f, 12.4141f, 12.4141f, 12.75f, 12.0f, 12.75f)
                curveTo(11.5859f, 12.75f, 11.25f, 12.4141f, 11.25f, 12.0f)
                lineTo(11.25f, 8.0f)
                curveTo(11.25f, 7.5859f, 11.5859f, 7.25f, 12.0f, 7.25f)
                close()
                moveTo(12.0f, 16.0f)
                curveTo(12.5508f, 16.0f, 13.0f, 15.5508f, 13.0f, 15.0f)
                curveTo(13.0f, 14.4492f, 12.5508f, 14.0f, 12.0f, 14.0f)
                curveTo(11.4492f, 14.0f, 11.0f, 14.4492f, 11.0f, 15.0f)
                curveTo(11.0f, 15.5508f, 11.4492f, 16.0f, 12.0f, 16.0f)
                close()
                moveTo(12.0f, 16.0f)
            }
        }
        .build()
        return _warning!!
    }

private var _warning: ImageVector? = null
