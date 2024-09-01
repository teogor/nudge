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

public val Icons.Filled.Info: ImageVector
    get() {
        if (_info != null) {
            return _info!!
        }
        _info = Builder(name = "Info", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
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
                moveTo(12.0f, 17.75f)
                curveTo(12.4141f, 17.75f, 12.75f, 17.4141f, 12.75f, 17.0f)
                lineTo(12.75f, 11.0f)
                curveTo(12.75f, 10.5859f, 12.4141f, 10.25f, 12.0f, 10.25f)
                curveTo(11.5859f, 10.25f, 11.25f, 10.5859f, 11.25f, 11.0f)
                lineTo(11.25f, 17.0f)
                curveTo(11.25f, 17.4141f, 11.5859f, 17.75f, 12.0f, 17.75f)
                close()
                moveTo(12.0f, 7.0f)
                curveTo(12.5508f, 7.0f, 13.0f, 7.4492f, 13.0f, 8.0f)
                curveTo(13.0f, 8.5508f, 12.5508f, 9.0f, 12.0f, 9.0f)
                curveTo(11.4492f, 9.0f, 11.0f, 8.5508f, 11.0f, 8.0f)
                curveTo(11.0f, 7.4492f, 11.4492f, 7.0f, 12.0f, 7.0f)
                close()
                moveTo(12.0f, 7.0f)
            }
        }
        .build()
        return _info!!
    }

private var _info: ImageVector? = null
