## Installation
```sh
npm install https://github.com/redrum1900/react-native-nxdai-rfid --save
```

### Installation (only Android) in settings.gradle
```gradle
...
include ':react-native-nxdai-rfid'
project(':react-native-nxdai-rfid').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-nxdai-rfid/android')
```

* In `android/app/build.gradle`

```gradle
...
dependencies {
    ...
    compile project(':react-native-nxdai-rfid')
}
```

* register module (in MainApplication.java)

```java
......
import com.nxd.rfid.RCTRfidPackage;  // <--- import

......

@Override
protected List<ReactPackage> getPackages() {
   ......
   new RCTRfidPackage(),          // <------ add here
   ......
}

```
```



