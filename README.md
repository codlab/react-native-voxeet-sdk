# Voxeet React Native SDK


## SDK 3.0 License agreement

Before using the latest version of the react-native plugin, please review and accept the [Dolby Software License Agreement](https://github.com/voxeet/voxeet-sdk-android/blob/main/LICENSE).

## Documentation

_WIP_

## Getting started

`$ npm install @voxeet/react-native-voxeet-sdk --save`

or 

`$ npm install https://github.com/codlab/react-native-voxeet-sdk --save`

### Mostly automatic installation

`$ react-native link @voxeet/react-native-voxeet-sdk`

**_Note: for iOS & Android, you need to do some [mandatory modification](https://github.com/voxeet/voxeet-uxkit-sdk#mandatory-modification)_ to your project**

### Manual installation

#### Android

**Note: to enable Firebase on Android (for Push Notification), please add also the [react-native-voxeet-firebase](https://github.com/voxeet/react-native-voxeet-firebase) library**

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import io.dolby.sdk.RNVoxeetSdkPackage;` to the imports at the top of the file
  - Add `voxeet` to the list returned by the `getPackages()` method
  - Add a field in the `MainApplication` class named voxeet : `private RNVoxeetSdkPackage voxeet;`
  - in the `onCreate` method, instantiate the `voxeet` field to a new instance of the `RNVoxeetSdkPackage` class : `voxeet = new RNVoxeetSdkPackage(MainApplication.this);`

2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':@voxeet_react-native-voxeet-sdk'
  	project(':@voxeet_react-native-voxeet-sdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/@voxeet/react-native-voxeet-sdk/android')
  	```

3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile (project(':@voxeet_react-native-voxeet-sdk')) {
          transitive = true
      }
  	```

## Mandatory modification

### iOS (react-native >= 0.60)

_WIP_

### Android

**Warning : those modification are not done automatically by `react-native link`. You must set them !**

**Warning : the SDK is only compatible with the Hermes engine, you can use it using the following inside the app/build.gradle file :**

```
project.ext.react = [
    enableHermes: true,  // clean and rebuild if changing
]
```

You must edit those files :
- `app/build.gradle`
- `app/src/main/AndroidManifest.xml`
- `MainApplication`

##### build.gradle

A pickFirst option must be used for the libc++ shared object:

```
android {
  ...
  packagingOptions {
    pickFirst '**/armeabi-v7a/libc++_shared.so'
    pickFirst '**/x86/libc++_shared.so'
    pickFirst '**/arm64-v8a/libc++_shared.so'
    pickFirst '**/x86_64/libc++_shared.so'
  }
  ...
}
```

##### MainApplication

Follow the implementation from the `link` documentation, first step

#### app/src/main/AndroidManifest.xml

After the `permissions` required, add those xml nodes :

```
  <!-- VOXEET PERMISSION - WARNING : THERE MAY BE DUPLICATES - no expected issues -->

  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>

  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  <uses-permission android:name="android.permission.WAKE_LOCK" />
  <uses-permission android:name="android.permission.BLUETOOTH" />
  <uses-permission android:name="android.permission.RECORD_AUDIO" />
  <uses-permission android:name="android.permission.INTERACT_ACROSS_USERS_FULL" />
  <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.CAMERA" />
```


## Usage
```javascript
import { VoxeetSDK, ConferenceUser } from "@voxeet/react-native-voxeet-conferencekit";
...
```

## Configuration

Depending on your Environment, you must configurate your project according to the public documentation

### iOS

_WIP_

### Android

Please go to [Android SDK Sample](https://github.com/voxeet/android-sdk-sample)

## Build locally

To build locally 

```bash
npm run build-library
```

the typescript command line needs local dev resolutions (available in the `package.json`)

```bash
npm i -D @types/react ...
```

## License

```
   Copyright 2020 - Voxeet

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

© Voxeet, 2020