# GateKeeper
 AccountManager simplified. 
 Initialize GateKeeper
 
 ``` kotlin
  val gateKeeper = GateKeeper(context)
```

maven{url "https://codecodecoffee.jfrog.io/artifactory/codecodecoffee-gradle-dev"}

#### Save user credentials
``` kotlin
  gateKeeper.enter(accountName: String, authToken: String) 
  ```
#### Clear saved user credentials
``` kotlin
  gateKeeper.logout()
```
## Usage
```groovy
    dependencies{
        implementation  'com.codecodecoffee:gatekeeper:v1.0'
    }
   ```
In your strings.xml, add the following string:
```xml
    <string name="account_type">your_app_name</string>
```
#### Optional - For supporting multiple app installs
``` groovy
    debug{
            ....
            resValue "string", "account_type", "debug_account_type_here"
        }
    release{
            ....
            resValue "string", "account_type", "release_account_type_here"
        }
```


In your Application class, implement the Gate class and provide the activity that you will use for authentication/login.
```kotlin
class SampleApp : Application(), Gate {
    override fun getGateClass(): Class<*> {
        return LoginActivity::class.java
    }
}

```


