# ShopEZ
ShopEZ is a simple, intuitive Android shopping app built with Jetpack Compose. It demonstrates modern Android development practices and provides a smooth shopping experience for users.

Note:
The API has some endpoints that does nothing i.e adding item to cart, registering a user and many more. So some screens have UI implementation but no logic
Contributions are welcomed, because the app is far away from being good 😂

To Login - use :
- Username: johnd
- Password: m38rmF$
Demo
## Features

- Browse a list of products
- Add products to cart
- View and modify cart contents
- Checkout process
- Order confirmation

## Screenshots
<p float="left">
 <image src = "images/ScreenShot_2.jpg" height = "350px">
 <image src = "images/ScreenShot_3.jpg" height = "350.px">
 <image src = "images/product_details.jpg" height = "350.px">
 <image src = "images/ScreenShot_4.jpg" height = "350.px">
 <image src = "images/ScrrenShot_1.jpg" height = "350.px">
 <image src = "images/ScreenShot_5.jpg" height = "350.px">
</p>
 


## Tech Stack.
Kotlin - Kotlin is a programming language that can run on JVM. Google has announced Kotlin as one of its officially supported programming languages in Android Studio; and the Android community is migrating at a pace from Java to Kotlin.

Jetpack components:

* -Jetpack Compose - Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.
* - Android KTX - Android KTX is a set of Kotlin extensions that are included with Android Jetpack and other Android libraries. KTX extensions provide concise, idiomatic Kotlin to Jetpack, Android platform, and other APIs.
* - AndroidX - Major improvement to the original Android Support Library, which is no longer maintained.
* - Lifecycle - Lifecycle-aware components perform actions in response to a change in the lifecycle status of another component, such as activities and fragments. These components help you produce better-organized, and often lighter-weight code, that is easier to maintain.
* - ViewModel -The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
* - Room database - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
* - Preferences DataStore - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.
* - Kotlin Coroutines - A concurrency design pattern that you can use on Android to simplify code that executes asynchronously.

* Retrofit - Retrofit is a REST client for Java/ Kotlin and Android by Square inc under Apache 2.0 license. Its a simple network library that is used for network transactions. By using this library we can seamlessly capture JSON response from web service/web API.
* GSON - JSON Parser,used to parse requests on the data layer for Entities and understands Kotlin non-nullable and default parameters.
* Kotlin Flow - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
* Coil- An image loading library for Android backed by Kotlin Coroutines.

## Setup Instructions

Clone the repository:
Copygit clone https://github.com/yourusername/ShopEZ.git

Open the project in Android Studio Arctic Fox or later.
Sync the project with Gradle files.
Run the app on an emulator or physical device.

## Project Structure

ui/products: Contains composables and ViewModel for the product listing screen.
ui/checkout: Contains composables for the checkout screen.
ui/navigation: Handles app navigation.
data: Contains data models and repository.
utils: Utility classes and functions.

## Usage

Launch the app to view the product list.
Click on "Add to Cart" for desired products.
Navigate to the cart using the bottom navigation.
Adjust quantities or remove items in the cart.
Proceed to checkout and place the order.
View the order confirmation screen.

## API Integration
This app uses a fake store API for demonstration purposes. In a production environment, replace the API calls with your actual backend service.
Contributing
Contributions are welcome! Please feel free to submit a Pull Request.


## Acknowledgements

Jetpack Compose Documentation
Android Developer Guides
Fake Store API
