import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rtl.petkinfe.R
import com.rtl.petkinfe.navigation.PetkinScreens

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Calendar,
        BottomNavItem.Home,
        BottomNavItem.User
    )

    // 네비게이션 상태 추적
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.White, contentColor = Color.Gray) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(id = item.title), fontSize = 10.sp) },
                selected = currentRoute == item.screenRoute,
                onClick = {
                    // 현재 화면과 다른 경우에만 이동
                    if (currentRoute != item.screenRoute) {
                        navController.navigate(item.screenRoute) {
                            popUpTo(navController.graph.startDestinationRoute!!) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}



sealed class BottomNavItem(val title: Int, val icon: Int, val screenRoute: String) {
    object Calendar : BottomNavItem(R.string.text_calendar, R.drawable.ic_calendar, PetkinScreens.CalendarScreen.name)
    object Home : BottomNavItem(R.string.text_home, R.drawable.ic_home, PetkinScreens.HomeScreen.name)
    object User : BottomNavItem(R.string.text_user, R.drawable.ic_user, PetkinScreens.MyPageScreen.name)
}
