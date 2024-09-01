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

/**
 * An object that holds different sets of icons used throughout the application.
 *
 * The `Icons` object contains nested objects for various icon styles and categories, allowing easy access
 * to a comprehensive set of icons for different use cases. Icons are categorized into `Outlined` and `Filled` styles.
 *
 * @property Outlined The set of outlined icons.
 * @property Filled The set of filled icons.
 * @property Default The default set of icons, which is set to the [Filled] icons.
 */
public object Icons {

  /**
   * An object containing outlined style icons.
   *
   * Outlined icons have a distinctive line style, providing a clean and minimalistic look.
   */
  public object Outlined

  /**
   * An object containing filled style icons.
   *
   * Filled icons are solid and filled with color, offering a bold and eye-catching appearance.
   */
  public object Filled

  /**
   * The default set of icons used by the application.
   *
   * This property provides the default style of icons, which is currently set to the [Filled] icons.
   */
  public val Default: Filled = Filled
}
