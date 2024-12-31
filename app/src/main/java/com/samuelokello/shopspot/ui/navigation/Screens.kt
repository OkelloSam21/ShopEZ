package com.samuelokello.shopspot.ui.navigation



sealed class Screens (val route : String){
    data object Splash: Screens("splash_screen")
    data object AuthDashBoard: Screens("auth_dashboard_screen")
    data object ForgotPassword: Screens("forgot_password_screen")
    data object Register: Screens("register_screen")
    data object Login: Screens("login_screen")
    data object Home : Screens("home")
    data object Cart : Screens("cart_screen")
    data object OrderPlaced: Screens("order_placed_screen")
    data object ProductDetailsScreen: Screens("product_details_screen?product={product}")
    data object Search : Screens("Search")
    data object Favourite : Screens("favourite")
    data object Profile : Screens("profile")
}
