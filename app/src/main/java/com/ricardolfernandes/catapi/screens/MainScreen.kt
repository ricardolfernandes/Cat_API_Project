package com.ricardolfernandes.catapi.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ricardolfernandes.catapi.R
import com.ricardolfernandes.catapi.navigation.BottomNavigationBar
import com.ricardolfernandes.catapi.navigation.NavigationItem
import com.ricardolfernandes.catapi.navigation.NavigationRoutes

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val currentRoute = currentRoute(navController)
    Scaffold(
        topBar = {
            if (currentRoute == NavigationItem.List.route || currentRoute == NavigationItem.Favourites.route) {
                MyTopAppBar(
                    title = currentRoute,
                    showBackButton = false,
                    onBackClick = {  }
                )
            }
        },
        bottomBar = {
            if (currentRoute == NavigationItem.List.route || currentRoute == NavigationItem.Favourites.route) {
                BottomAppBar(modifier = Modifier) {
                    BottomNavigationBar(navController = navController)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                PaddingValues(
                    0.dp,
                    innerPadding.calculateTopPadding(),
                    0.dp,
                    innerPadding.calculateBottomPadding()
                )
            )
        ) {
            NavigationRoutes(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title:String,
    showBackButton: Boolean,
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}