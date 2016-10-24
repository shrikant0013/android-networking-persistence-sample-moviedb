# Android Networking and Persistence samples using The Movie DB API

This is an Android demo application for displaying the latest box office movies using the [The Movie DB API](https://www.themoviedb.org/documentation/api/). 

## Overview
Purpose of this app is to demostrate usage of various networking and persistence libraries in Android

The app does following

 * Demonstrates use of HttpURLConnection and AsyncTask
 ** Follow [Displaying Remote Images (The "Hard" Way)](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#displaying-remote-images-the-hard-way) on our cliffnotes for a step-by-step tutorial.
 * Demonstrates use of Android AsyncHTTPClient third-party library
 ** Follow [Using Android Async Http Client](http://guides.codepath.com/android/Using-Android-Async-Http-Client) on our [cliffnotes](http://guides.codepath.com/) for a step-by-step tutorial.
 * Demonstrates use of [Android AsyncHTTPClient](http://loopj.com/android-async-http/) - For asynchronous network requests
 ** Follow [Consuming APIs with Retrofit](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) on our [cliffnotes](http://guides.codepath.com/) for a step-by-step tutorial.
 * Demonstrates use of Android [Picasso](http://square.github.io/picasso/) - For remote image loading third party library
 ** Follow [Displaying Images with the Picasso Library](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library) on our [cliffnotes](http://guides.codepath.com/) for a step-by-step tutorial.

## Installation

Quick note is that you must **provide your own API key** for The Movie DB API in order to use this demo. To get an API key, you need to [register for an account](https://www.themoviedb.org/account/signup) (or [sign in](https://www.themoviedb.org/login)). Once you have the key, put the key into the `API_KEY` constant in the `./app/src/main/res/values/secrets.xml` file: 

```xml
<resources>
    <string name="api_key">XXXX</string>
</resources>
```

Once you've setup the key and imported the project into Android Studio, you should be all set.
