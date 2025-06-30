package com.hseun.lendy_v2.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hseun.lendy_v2.R
import com.hseun.lendy_v2.ui.theme.Main
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.noRippleClickable

sealed class BottomNavItem(
    val title: Int,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val screenRoute: String
) {
    data object OpenLoan : BottomNavItem(
        R.string.open_loan,
        R.drawable.nav_open_loan_unselected,
        R.drawable.nav_open_loan_selected,
        NavigationRoutes.OPEN_LOAN
    )

    data object Home : BottomNavItem(
        R.string.home,
        R.drawable.nav_home_unselected,
        R.drawable.nav_home_selected,
        NavigationRoutes.HOME
    )

    data object MyPage : BottomNavItem(
        R.string.my_page,
        R.drawable.nav_my_unselected,
        R.drawable.nav_my_selected,
        NavigationRoutes.MY_PAGE
    )
}

private val navItems = listOf<BottomNavItem>(
    BottomNavItem.OpenLoan,
    BottomNavItem.Home,
    BottomNavItem.MyPage
)

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Main)
            .padding(
                start = 60.dp,
                end = 60.dp,
                top = 12.dp,
                bottom = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { navItem ->
            Icon(
                modifier = modifier
                    .size(40.dp)
                    .noRippleClickable {
                        navController.navigate(navItem.screenRoute) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                painter = painterResource(id = if (navItem.screenRoute == currentRoute) navItem.selectedIcon else navItem.unselectedIcon),
                contentDescription = stringResource(id = navItem.title),
                tint = White
            )
        }
    }
}