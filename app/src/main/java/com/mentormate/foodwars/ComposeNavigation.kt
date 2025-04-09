import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mentormate.foodwars.ui.details.FoodDetailsScreen
import com.mentormate.foodwars.ui.login.LoginScreen
import com.mentormate.foodwars.ui.main.MainScreen
import com.mentormate.foodwars.ui.registration.RegistrationScreen

//@Composable
//fun FoodWarsNavGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        startDestination = "main_screen"
//    ) {
//        composable("main_screen") {
//            MainScreen(navController = navController)
//        }
//        composable("food_details/{foodId}", arguments = listOf(
//            navArgument("foodId") { type = NavType.LongType }
//        )) { backStackEntry ->
//            val foodId = backStackEntry.arguments?.getLong("foodId") ?: 0L
//            FoodDetailsScreen(foodId = foodId, navController = navController)
//        }
//        composable("history_screen") {
//            HistoryScreen(navController = navController)
//        }
//        composable("profile_screen") {
//            ProfileScreen(navController = navController)
//        }
//        composable("registration_screen") {
//            RegistrationScreen(navController = navController)
//        }
//        composable("motivation_screen") {
//            MotivationScreen(navController = navController)
//        }
//        composable("login_screen") {
//            LoginScreen(navController = navController)
//        }
//    }
//}
