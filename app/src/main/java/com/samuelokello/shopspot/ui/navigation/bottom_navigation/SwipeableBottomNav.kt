package com.samuelokello.shopspot.ui.navigation.bottom_navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun SwipeableBottomNav(
    selectedIndex: Int,
    onNavigate: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true
) {
    val density = LocalDensity.current
    val bottomBarHeight = 120.dp
    val heightInPx = with(density) { bottomBarHeight.toPx() }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { heightInPx.toInt() },
        exit = slideOutVertically { heightInPx.toInt() }
    ) {
        NavigationBar(
            modifier = modifier.height(bottomBarHeight),
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            val items = BottomNavigationItem().bottomNavigationItems()
            val coroutineScope = rememberCoroutineScope()
            
            Box(
                modifier = Modifier.draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        // Handle drag gesture
                        if (delta.absoluteValue > 50) {
                            // Determine swipe direction
                            val direction = if (delta > 0) -1 else 1
                            val newIndex = (selectedIndex + direction).coerceIn(0, items.size - 1)
                            
                            if (newIndex != selectedIndex) {
                                coroutineScope.launch {
                                    onNavigate(newIndex, items[newIndex].route)
                                }
                            }
                        }
                    }
                )
            ) {
                Row {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = index == selectedIndex,
                            onClick = { 
                                onNavigate(index, item.route)
                            },
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(text = item.label) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeAbleBottomNav(
    selectedIndex: Int,
    onNavigate: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true
) {
    val items = BottomNavigationItem().bottomNavigationItems()
    val scope = rememberCoroutineScope()


    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = modifier
                .fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == selectedIndex,
                    onClick = {
                        scope.launch {
//                            swipeableState.animateTo(index)
                            onNavigate(index, item.route)
                        }
                    },
                    icon = { Icon(item.icon, contentDescription = null) },
                    label = { Text(text = item.label) }
                )
            }
        }
    }
}