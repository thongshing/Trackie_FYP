import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trackie_fyp.AppNavigation
import com.example.trackie_fyp.DatabaseHelper
import com.example.trackie_fyp.ui.theme.Trackie_FYPTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(userId: Int) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AppNavigation(navController = navController, dbHelper = dbHelper, userId = userId)
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, "home"),
        BottomNavItem("Report", Icons.Filled.AutoGraph, "report"),
        BottomNavItem("Scan", Icons.Filled.CropFree, "scan"),
        BottomNavItem("Budget", Icons.Filled.AttachMoney, "budgetStatus"),
        BottomNavItem("Settings", Icons.Filled.Settings, "settings")
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Gray
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    if (item.route == "scan") {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color.LightGray, shape = CircleShape)
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(item.icon, contentDescription = item.label, tint = Color.Black)
                        }
                    } else {
                        Icon(item.icon, contentDescription = item.label)
                    }
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Trackie_FYPTheme {
        MainScreen(userId = 1) // Provide a sample userId
    }
}