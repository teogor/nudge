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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.teogor.paletteon.ktx.surfaceColorAtElevation
import dev.teogor.nudge.Icons
import dev.teogor.nudge.icons.filled.Error
import dev.teogor.nudge.icons.filled.Info
import dev.teogor.nudge.icons.filled.Success
import dev.teogor.nudge.icons.filled.Warning
import dev.teogor.nudge.icons.outlined.Error
import dev.teogor.nudge.icons.outlined.Info
import dev.teogor.nudge.icons.outlined.Success
import dev.teogor.nudge.icons.outlined.Warning
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

public fun LazyGridScope.iconGalleryLayout(
  viewModel: IconGalleryViewModel,
  icons: List<ImageVector>,
) {
  item(span = { GridItemSpan(maxLineSpan) }) {
    Text(
      text = "Icons",
      fontSize = 20.sp,
      modifier = Modifier.fillMaxWidth()
    )
  }

  item(span = { GridItemSpan(maxLineSpan) }) {
    val selectedTab by viewModel.selectedTab.collectAsState()

    // TabRow for switching between Filled and Outlined icons
    TabRow(
      selectedTabIndex = selectedTab.ordinal,
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ) {
      IconType.entries.forEach { tab ->
        Tab(
          selected = selectedTab == tab,
          onClick = { viewModel.onTabSelected(tab) },
          text = { Text(tab.name) }
        )
      }
    }
  }

  items(icons) { icon ->
    IconItem(icon)
  }
}

@Composable
private fun IconItem(icon: ImageVector) {
  val tonalElevation = 1.dp
  val color = MaterialTheme.colorScheme.surface
  val absoluteElevation = LocalAbsoluteTonalElevation.current + tonalElevation
  val colorScheme = MaterialTheme.colorScheme

  val shape = remember { RoundedCornerShape(10.dp) }
  Column(
    modifier = Modifier
      .background(
        color = surfaceColorAtElevation(
          color = color,
          elevation = absoluteElevation
        ),
        shape = shape
      )
      .padding(vertical = 6.dp, horizontal = 4.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    Text(
      text = icon.name,
      fontSize = 12.sp,
      color = colorScheme.onSurface,
    )
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Icon(
        modifier = Modifier
          .clip(shape)
          .background(
            color = colorScheme.primaryContainer,
            shape = shape
          )
          .clickable { }
          .size(40.dp)
          .padding(8.dp),
        imageVector = icon,
        contentDescription = null,
        tint = colorScheme.onPrimaryContainer
      )
      Icon(
        modifier = Modifier
          .clip(shape)
          .background(
            color = colorScheme.onPrimary,
            shape = shape
          )
          .clickable { }
          .size(40.dp)
          .padding(8.dp),
        imageVector = icon,
        contentDescription = null,
        tint = colorScheme.primary
      )
    }
  }
}

public enum class IconType(public val displayName: String) {
  Filled("Filled"),
  Outlined("Outlined")
}

public class IconGalleryViewModel : ViewModel() {
  private val _selectedTab = MutableStateFlow(IconType.Filled)
  public val selectedTab: StateFlow<IconType> = _selectedTab.asStateFlow()

  private val _icons = MutableStateFlow(getIcons(IconType.Filled))
  public val icons: StateFlow<List<ImageVector>> = _icons

  public fun onTabSelected(tab: IconType) {
    viewModelScope.launch {
      _selectedTab.value = tab
      _icons.value = getIcons(tab)
    }
  }

  private fun getIcons(iconType: IconType): List<ImageVector> {
    return when (iconType) {
      IconType.Filled -> listOf(
        Icons.Filled.Success,
        Icons.Filled.Warning,
        Icons.Filled.Info,
        Icons.Filled.Error,
      )
      IconType.Outlined -> listOf(
        Icons.Outlined.Success,
        Icons.Outlined.Warning,
        Icons.Outlined.Info,
        Icons.Outlined.Error,
      )
    }
  }
}
