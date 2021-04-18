# GateKeeper
 Easier account management. 
 Get gatekeeper instance:
 
 ``` kotlin
  val gateKeeper = GateKeeper(context)
```

maven{url "https://codecodecoffee.jfrog.io/artifactory/codecodecoffee-gradle-dev"}

#### Save user credentials
``` kotlin
  gateKeeper.enter(accountName: String, authToken: String, userData: Bundle?) 
  ```
#### Clear saved user credentials
``` kotlin
  gateKeeper.logout()
```
## Usage
```groovy
    dependencies{
    
    implementation  'com.codecodecoffee:gatekeeper:1.0.0-alpha4@aar'
      
    }
  
   ```
In your strings.xml, add the following string:
```xml
    <string name="account_type">you_app_name</string>
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


#### Optional - Saving account details using @UserAccount and @Serializable(kotlinx-serialization)
Setup kotlinx-serialization:
1. https://github.com/Kotlin/kotlinx.serialization
2. Create a class for your account details;
```kotlin
@UserAccount
@Serializable
data class MyAccount(val id:Int, var name:String)

//Save
gateKeeper.saveAccount(MyAccount(1,"Jeff"))

//Get
val account:MyAccount = gateKeeper.getAccount()

```
