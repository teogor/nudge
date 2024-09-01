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

import kotlin.time.Duration.Companion.seconds


public sealed interface Duration {

  public fun toMillis(): kotlin.Long {
    return when (this) {
      is Short -> 4.seconds
      is Medium -> 7.seconds
      is Long -> 10.seconds
      is Persistent -> kotlin.Long.MAX_VALUE.seconds
      is Custom -> duration
    }.inWholeMilliseconds
  }

  public data object Short : Duration
  public data object Medium : Duration
  public data object Long : Duration
  public data object Persistent : Duration
  public data class Custom(val duration: kotlin.time.Duration) : Duration
}
