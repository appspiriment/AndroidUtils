# Android Utils Library
[![API](https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17)
[ ![Download](https://api.bintray.com/packages/appspiriment/android/AndroidUtils/images/download.svg?version=0.1.3) ](https://bintray.com/appspiriment/android/AndroidUtils/0.1.3/link)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://opensource.org/licenses/Apache-2.0)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/appspiriment/AndroidUtils/blob/master/LICENSE)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)


Latest Version `0.1.4`

## About Android Utils & Baseclasses Library

> ***This library is optimized for Kotlin. Java users will be unable to use some methods***
> ***Optional values can be skipped in Kotlin. In Java, you have to pass the default value***

This library has two main components: 
* [Android Utils](https://github.com/appspiriment/AndroidUtils#AndroidUtils)
* [Baseclasses](https://github.com/appspiriment/AndroidUtils#BaseClasses)
* [PermissionUtils](https://github.com/appspiriment/AndroidUtils/tree/master/permissionutils)

# AndroidUtils
Android Utils library is a set of commonly used functions which is intented to make coding easier. It utilizes the concept of Extension functions from Kotlin.

## Why use Android Utils Library?
* It brings a bunch of most commonly used methods into a single library, which helps you write clean code, and avoid clutter. The methods are organized into different classess based on purpose. 
* This library provides easier implementation of network/API access using a single entry method. This library implements [Amit Sekhar's Fast Android Networking Library](https://github.com/amitshekhariitbhu/Fast-Android-Networking/blob/master/README.md).

## How to use?
   Add the below line to your app level `build.gradle`. Replace `{latest_version}` with the latest version of the library.

   ```gradle
   implementation 'com.appspiriment.android:androidutils:{latest_version}'
   ```

## What is available?
Utils available:
  * [LogUtils](https://github.com/appspiriment/AndroidUtils#logutils)
  * [UiUtils](https://github.com/appspiriment/AndroidUtils#UiUtils)
  * [DeviceUtils](https://github.com/appspiriment/AndroidUtils#deviceutils)
  * [NetworkUtils](https://github.com/appspiriment/AndroidUtils#networkutils)


        
#### LogUtils
 This util class helps you print logs and stacktrace. This methods print the Log and Stacktrace only on DEBUG mode, hence avoids the requirement of clearing the Log statements on release.
 
* #### Print Log     `printLog(message: Any?, tag: String = "LogTag: ", isError: Boolean = false)`
    This method is the implementation of Log.w method. This method helps your print the log in two levels - warning and error. The advantage of using this method is that this will print the log only in debug mode, and you don't need to take care of the data type in the message part.
    ```
      function name : printLog
      arguments:
            message - You can give the message here. You can give any data type. 
                      The data will be converted to String by the library. 
                      You can even pass null, which prints "NULL"
            tag     - Tag for the Log message. [Optional. Will print "LogTag" if not given]
            isError - will print as Error if true else will print as Warning 
     ```
    Sample Usage in Kotlin:

    ```java
        LogUtils.printLog("Log Message")
        LogUtils.printLog("LogMessage", "LogTag")
        LogUtils.printLog(message= "LogMessage", isError = true)
    ```
    Sample Usage in Java:

    ```java
        LogUtils.Companion.printLog("Log Message")
        LogUtils.Companion.printLog("LogMessage", "LogTag")
        LogUtils.Companion.printLog(message= "LogMessage", isError = true)
    ```
* #### Print Stacktrace     `fun printStacktrace(e: Exception)`
    This method is the implementation of error.printStacktrace. The advantage of using this method is that this will print the stack trace only in debug mode.
    
    ```
      function name : printStacktrace
      arguments:
            error - any error which is a subclass of Android Exception
     ```
    Sample Usage in Kotlin:

    ```java
        LogUtils.printStacktrace(error)
    ```
    
    Sample Usage in java:

    ```java
        LogUtils.Companion.printStacktrace(error)
    ```
 
  ### UiUtils
     This util provide methods to display messages to the user - AlertDialogs and Toasts. These messages are created as extension functions so that you don't need to pass the context as a parameter. You can call it as a function of the context. Hence you cannot make use of these functions in Java.
 
 * #### Show Toast `fun Context.showToast(message: String, isLong: Boolean = false) `
        This method helps to hide the softkeyboard manually. 
     
     ```
        function name : showToast
        arguments:
                message - Message to be displayed
                isLong  - Whether the toast to be displayed Long or Short (Duration) 
     ```
    Sample Usage in Kotlin:

    ```java
        requireContext().showToast(this, "Sample Text") 
    ```
    or
    
    ```java
        context.showToast(this, "Sample Text") 
    ```
    
    
* #### Show Message Dialog `Activity.showMsgDialog(  title: String? = null, message: String? = null, titleRes: Int = -1,        messageRes: Int = -1, viewRes: Int = -1, view: View? = null, positiveButton: String = "OK", negativeButton: String? = null, neutralButton: String? = null, finishActivityOnOk: Boolean = false, finishActivityAffinity: Boolean = false, finishActivityOnCancel: Boolean = false, isCancellable: Boolean = false, positiveClickListen: () -> Unit = {}, negativeClickListen: () -> Unit = {}, neutralClickListen: () -> Unit = {}, onCancelListen: () -> Unit = {}, iconRes: Int = R.mipmap.ic_launcher, isHtmlMessage: Boolean = false)`
        
    This method is a util method to display an Alert Dialog. This is a highly customizable method, in which different combinations are possible.
     
     ```
        function name : showMsgDialog
        arguments:
                title - Title String of the Dialog [Optional]
                titleRes - Resource Id of the String value for the title of Dialog. Either this or `title` could be used [Optional]
                message  - Message String of the Dialog [Optional]
                messageRes - Resource Id of the String value for the message of  Dialog. Either this or `message` could be used [Optional]
                viewRes - Resource id for the custom view for the dialog [Optional]
                view - View object for the custom view for the dialog [Optional]
                positiveButton - Text for the Positive Button  [Optional - will display 'OK' if not given]
                negativeButton - Text for the Negative Button  [Optional - will hide negative button if not given]
                neutralButton - Text for the Neutral Button  [Optional - will hide neutral button if not given]
                finishActivityOnOk - if this is true, the activity will be finished on click of Positive Button [Optional]
                finishActivityOnCancel - if this is true, the activity will be finished on cancelling the dialog box [Optional]
                isCancellable - Whether the dialog is cancellable or not [Optional]
                positiveClickListen - Labmda function to handle the onclick event of Positive button.
                negativeClickListen - Labmda function to handle the onclick event of Negative button.
                neutralClickListen - Labmda function to handle the onclick event of Neutral button.
                onCancelListen - Labmda function to handle the onCancel event. [Optional]
                iconRes - Resource id for Dialog Icon
                isHtmlMessage - If true, the message will be treated as an HTML string.

     ```
    Sample Usage in Kotlin:

    ```java
        requireActivity().showMsg(this, "Sample Title", "Sample Text")
        activityObject.showMsg(this, title = "Sample Title", message = "Sample Text", positiveButton = "YES", negativeButton = "NO")
        this@MainActivity.showMsg(this, "Sample Title", "Sample Text", "YES", "NO")

    ```
     
 * #### Hide Keyboard `fun Activity.hideKeyboard()`
        This method helps to hide the softkeyboard manually. 
     
     ```
        function name : hideKeyboard
     ```
    Sample Usage in Kotlin:

    ```java
        requireActivity().hideKeyboard()
        this@MainActivity.hideKeyboard()
    ```
     Sample Usage in java:

    ```java
        DeviceUtils.Companion.hideKeyboard(this)
    ```
    
 ### NetworkUtils
 
 This util provide methods related to the Network operations. The library makes use of [Amit Shekhar's Fast Android Networking Library](https://github.com/amitshekhariitbhu/Fast-Android-Networking) since it take care of several issues with Volley, and is easier to setup than retrofit. Powerful enough to handle most of the use-cases. Read more on Amit's [Github](https://github.com/amitshekhariitbhu/Fast-Android-Networking).
 
 **This class needs to be initialized using context and could not be used in static context**. It is required for initialization of Android Networking Library, and to enable Dependecy Injection.
 
 * #### Constants: 
    ```java
    val API_REQUEST_STRING = 101
    val API_REQUEST_JSON = 102
    val API_REQUEST_JSON_ARRAY = 103
    ```
 
 * #### Check Network Connection `fun isNetworkConnected() : Boolean`
        This method helps to access the network state. This will check if the device have network connection by mobile network or WiFi. It will not tell you whether internet is available, but only whether the network is connected.
     
     ```
        function name : isNetworkConnected
        arguments: None
        return: Boolean - true or false based on the avilability of network connection
     ```
    Sample Usage:

    ```java
        Java :
                NetworkUtils networkUtils = new NetworkUtils(this);
                boolean networkAvailable = networkUtils.isNetworkConnected();
        Kotlin:
                val isNetworkAvailable = NetworkUtils(this).isNetworkConnected()
    ```


 * #### ApiRequest `fun startApiRequest( url: String,  stringSuccessListenerLambda: (String) -> Unit = {}, jsonSuccessListenerLambda: (JSONObject) -> Unit = {}, jsonArraySuccessListenerLambda: (JSONArray) -> Unit = {}, errorListenLambda: (error: ANError) -> Unit = {}, requestType: Int = API_REQUEST_STRING, connectTimeOutInMillis: Long = 7000, readTimeOutInMillis: Long = 10000, writeTimeOutInMillis: Long = 10000)`
 
      This method starts an asynchronous network call using Fast Android Network Library. You need to handle the result/error in the Success / Error listener. As you can see from the FAN library page, there are mainly three type of success listeners - String. Json and JSON Array objects. (Custom object listener is not implemented in this library). 

**You need to pass the request type if the request is not a String request, else you won't get a result.**

        function name : startApiRequest
        arguments: 
              url  - Url of the api to be called
              stringSuccessListenerLambda - The lambda function to handle string request result. Will get result as string inside the lambda (You should pass this if request type is 'API_REQUEST_STRING' or default)
              jsonSuccessListenerLambda - The lambda function to handle json request result. Will get result as JSON Object inside the lambda (You should pass this if request type is 'API_REQUEST_JSON')
              jsonArraySuccessListenerLambda - The lambda function to handle json array request result. Will get result as JSON array  Object inside the lambda (You should pass this if request type is 'API_REQUEST_JSON_ARRAY')
              errorListenLambda - You should pass an error listener lambda function to handle the error. You will get an error object of type ANError inside the labmda as parameter.
              requestType = Should be one of API_REQUEST_STRING, API_REQUEST_JSON,  API_REQUEST_JSON_ARRAY. 
              connectTimeOutInMillis - Connection timout in milliseconds [Optional]
              readTimeOutInMillis - Read timout in milliseconds [Optional]
              writeTimeOutInMillis - Write timout in milliseconds [Optional]
              


# BaseClasses

## What is this library?
The BaseClasses library provides the user a set of base classes for Activity, Fragmet and ViewModel, which could be used to implement the MVVM Architecture easily. It reduces the clutter and boiler plate code. 

> ***This library is optimized for Kotlin. Java users will be unable to use some methods***
> ***This library uses Koin DI for dependency injection and ViewModel injection***

## How to use?
   Add the below line to your app level `build.gradle`. Replace `{latest_version}` with the latest version of the library.
   
   ```gradle
   implementation 'com.appspiriment.android:androidutils:{latest_version}'
   ```
## What is available?
Baseclasses available:
  * [BaseActivity](https://github.com/appspiriment/AndroidUtils#logutils)
  * [BaseFragment](https://github.com/appspiriment/AndroidUtils#UiUtils)
  * [BaseViewModel](https://github.com/appspiriment/AndroidUtils#deviceutils)
  * [UpdateActivity](https://github.com/appspiriment/AndroidUtils#networkutils)
  
## TODO
* More Util functions
* Java optimization

## CREDITS
[Amit Shekhar](https://github.com/amitshekhariitbhu) - As Fast Android Networking is developed by Amit.

### Copyright (C) 2019 Appspiriment Labs

       ```
       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at
       
       http://www.apache.org/licenses/LICENSE-2.0
       
       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.
       ```
