# Dynamic Language Resource Loading with Google Mobile Ads SDK Example
This repository contains a sample Android application demonstrating an issue related to dynamic language resource loading in conjunction with the initialization of the Google Mobile Ads SDK. The primary focus is on maintaining the application's selected locale across fragments within the MainActivity after the SDK initialization.

Problem Description
The application supports dynamic language switching and uses the feature delivery mechanism to download additional language resources as needed. However, upon initializing the Google Mobile Ads SDK within the MainActivity, the application's locale resets to the default language (English) when navigating between fragments or recreating them via the bottom navigation view. This behavior disrupts the user experience, especially for those requiring the app in their native language.

Initializing the Google Mobile Ads SDK in the Application class's onCreate() method resolves the locale issue but introduces complications in dynamically handling user consent for ads, a requirement for GDPR compliance.

Objective
The objective of this sample is to illustrate the challenge and seek potential solutions or workarounds that allow:

Dynamic language resource management to coexist with Google Mobile Ads SDK initialization.
Preservation of the user-selected locale in fragments within the MainActivity without necessitating SDK initialization in the Application class.
Efficient handling of user consent for ad personalization in compliance with GDPR.
Structure
MainActivity: Demonstrates the SDK initialization and the dynamic language switching logic.
LanguageHelper: Utility class for managing language settings.
MyApplication: Contains the alternative SDK initialization approach.
How to Use
Clone the repository.
Open the project in Android Studio.
Run the application on a device or emulator.
Observe the locale behavior when interacting with the app's fragments and after SDK initialization.
Seeking Solutions
This repository is part of an effort to find solutions to the described issue. Developers, contributors, and anyone with insights into resolving the locale reset problem are welcome to suggest improvements, contribute code, or share their experiences.
