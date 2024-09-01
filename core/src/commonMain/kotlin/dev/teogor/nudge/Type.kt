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

package dev.teogor.nudge

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import dev.teogor.nudge.icons.filled.Error
import dev.teogor.nudge.icons.filled.Info
import dev.teogor.nudge.icons.filled.Success
import dev.teogor.nudge.icons.filled.Warning
import dev.teogor.nudge.icons.outlined.Success

@Stable
public enum class Type(
  internal val icon: ImageVector
) {
  Info(Icons.Filled.Info),
  Warning(Icons.Filled.Warning),
  Error(Icons.Filled.Error),
  Success(Icons.Filled.Success),
  Loading(Icons.Outlined.Success),
}
