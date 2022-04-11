# Anilist

An anime explorer based on MyAnimeList anime listing via https://jikan.moe/ .

This project is developed as submission
for [Dicoding](https://www.dicoding.com/)'s [Menjadi Android Developer Expert](https://www.dicoding.com/academies/165) course
and [#JuaraAndroid](https://rsvp.withgoogle.com/events/juara-android-22) event.

## Features

- Browse anime
- Sort anime
- Browse anime details
- Search anime
- Mark anime as favorite
- Offline browsing/caching

## Screenshot

TO DO

## What I use in and learned from this project

- [Jetpack navigation](https://developer.android.com/guide/navigation)
- Single activity architecture
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- [Flow](https://developer.android.com/kotlin/flow)
- [Splash screen](https://developer.android.com/guide/topics/ui/splash-screen)
- MVVM architecture with use cases
- Clean architecture (data, domain, ui)
- [Room database](https://developer.android.com/training/data-storage/room) for database
- [Lottie](https://github.com/airbnb/lottie-android) for image animation
- [Dagger2 & Hilt](https://dagger.dev/hilt/) for dependency injection
- [Retrofit](https://square.github.io/retrofit/) for networking
- [Moshi](https://github.com/square/moshi) for serialization
- Modular gradle project. Core for core logic, app for UI, and favorite for favorite-related feature
- Dynamic features for favorite module

## Note

You cannot use Hilt in a DF (dynamic feature). The workaround for DF is by injecting the
dependencies using Dagger manually.

Reference used: https://github.com/kido1611/Hilt-On-Dynamic-Feature-Module

## How to run this code

1. Clone this repository to your machine
2. Open your Android Studio
3. Import this repository to your Android Studio
4. Build and run the application to your emulator or Android devices

## License

Licensed under [MIT License](LICENSE)
