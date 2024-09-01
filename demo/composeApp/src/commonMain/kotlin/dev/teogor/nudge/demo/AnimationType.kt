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

package dev.teogor.nudge.demo

import dev.teogor.nudge.Animation

// Enum class representing the different animation types
public enum class AnimationType {
  BOUNCE,
  FADE,
  SLIDE,
  ZOOM,
  FLIP,
}

// Extension function to convert AnimationType to Animation
public fun AnimationType.toAnimation(): Animation {
  return when (this) {
    AnimationType.BOUNCE -> Animation.Bounce
    AnimationType.FADE -> Animation.Fade
    AnimationType.SLIDE -> Animation.Slide
    AnimationType.ZOOM -> Animation.Zoom
    AnimationType.FLIP -> Animation.Flip
  }
}

// Extension function to convert Animation to AnimationType
public fun Animation.toAnimationType(): AnimationType {
  return when (this) {
    is Animation.Bounce -> AnimationType.BOUNCE
    is Animation.Fade -> AnimationType.FADE
    is Animation.Slide -> AnimationType.SLIDE
    is Animation.Zoom -> AnimationType.ZOOM
    is Animation.Flip -> AnimationType.FLIP
    else -> throw IllegalArgumentException("Unknown animation type")
  }
}
