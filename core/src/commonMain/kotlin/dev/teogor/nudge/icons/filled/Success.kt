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

public val Icons.Filled.Success: ImageVector
    get() {
        if (_success != null) {
            return _success!!
        }
        _success = Builder(name = "Success", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF1C274C)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(22.0f, 12.0f)
                curveTo(22.0f, 17.5234f, 17.5234f, 22.0f, 12.0f, 22.0f)
                curveTo(6.4766f, 22.0f, 2.0f, 17.5234f, 2.0f, 12.0f)
                curveTo(2.0f, 6.4766f, 6.4766f, 2.0f, 12.0f, 2.0f)
                curveTo(17.5234f, 2.0f, 22.0f, 6.4766f, 22.0f, 12.0f)
                close()
                moveTo(16.0313f, 8.9688f)
                curveTo(16.3242f, 9.2617f, 16.3242f, 9.7383f, 16.0313f, 10.0313f)
                lineTo(11.0313f, 15.0313f)
                curveTo(10.7383f, 15.3242f, 10.2617f, 15.3242f, 9.9688f, 15.0313f)
                lineTo(7.9688f, 13.0313f)
                curveTo(7.6758f, 12.7383f, 7.6758f, 12.2617f, 7.9688f, 11.9688f)
                curveTo(8.2617f, 11.6758f, 8.7383f, 11.6758f, 9.0313f, 11.9688f)
                lineTo(10.5f, 13.4375f)
                lineTo(14.9688f, 8.9688f)
                curveTo(15.2617f, 8.6758f, 15.7383f, 8.6758f, 16.0313f, 8.9688f)
                close()
                moveTo(16.0313f, 8.9688f)
            }
        }
        .build()
        return _success!!
    }

private var _success: ImageVector? = null
