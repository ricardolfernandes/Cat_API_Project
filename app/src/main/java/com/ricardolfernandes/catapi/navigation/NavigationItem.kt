package com.ricardolfernandes.catapi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, val icon: ImageVector?, var title: String) {
    data object List : NavigationItem("Home", Icons.Rounded.Home, "Cats list")
    data object Details : NavigationItem("Details", Icons.Rounded.Info, "Cat details")
}