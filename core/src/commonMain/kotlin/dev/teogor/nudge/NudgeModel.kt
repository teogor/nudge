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

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import dev.teogor.nudge.common.NudgeLayout
import dev.teogor.nudge.setup.NudgeButton

@Stable
public sealed interface NudgeModel {
  public val duration: Duration
  public val style: Style

  @Immutable
  public data class Standard(
    val type: Type,
    val title: String,
    val description: String? = null,
    val actions: List<NudgeButton> = emptyList(),
    override val duration: Duration = Duration.Medium,
    override val style: Style = Style.Default,
  ) : NudgeModel

  @Immutable
  public data class Customizable(
    override val duration: Duration = Duration.Medium,
    override val style: Style = Style.Default,
    val content: NudgeLayout,
  ) : NudgeModel
}
