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

package dev.teogor.nudge.setup

import dev.teogor.nudge.Duration
import dev.teogor.nudge.NudgeModel
import dev.teogor.nudge.Style
import dev.teogor.nudge.Type
import dev.teogor.nudge.common.NudgeLayout

public class NudgeBuilder internal constructor() {
  private var type: Type? = null
  private var _content: NudgeLayout? = null

  private val titleText: String
    get() = labelingConfig.title.orEmpty()

  private val descriptionText: String?
    get() = labelingConfig.description

  private var duration: Duration = Duration.Medium
  private var style: Style = Style.Default

  public val content: NudgeLayout?
    get() = _content

  private val buttons: MutableList<NudgeButton> = mutableListOf()

  public fun button(title: String, onClick: () -> Unit) {
    buttons.add(NudgeButton(title, onClick))
  }

  public fun button(title: String) {
    buttons.add(NudgeButton(title))
  }

  private var labelingConfig = NudgeLabeling()

  public fun style(style: Style) {
    this.style = style
  }

  public fun content(content: NudgeLayout) {
    type = null
    _content = content
  }

  public fun error(block: NudgeLabeling.() -> Unit) {
    configureNudge(Type.Error, block)
  }

  public fun info(block: NudgeLabeling.() -> Unit) {
    configureNudge(Type.Info, block)
  }

  public fun success(block: NudgeLabeling.() -> Unit) {
    configureNudge(Type.Success, block)
  }

  public fun warning(block: NudgeLabeling.() -> Unit) {
    configureNudge(Type.Warning, block)
  }

  public fun loading(block: NudgeLabeling.() -> Unit) {
    configureNudge(Type.Loading, block)
  }

  private fun configureNudge(type: Type, block: NudgeLabeling.() -> Unit) {
    this.type = type
    labelingConfig.apply(block).also {
      if (it.title == null) {
        throw IllegalArgumentException("Title cannot be null. Please call setTitle()")
      }
    }
  }

  public fun duration(duration: Duration) {
    this.duration = duration
  }

  internal fun build(): NudgeModel {
    return when (type) {
      null -> NudgeModel.Customizable(
        content = content ?: error("Content must be provided for Custom Nudge"),
        duration = duration,
        style = style,
      )

      else -> {
        NudgeModel.Standard(
          type = type!!,
          title = titleText,
          description = descriptionText,
          actions = buttons,
          duration = duration,
          style = style,
        )
      }
    }
  }
}
