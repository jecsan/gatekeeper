# GateKeeper
 Easier account management. 

#### Save user credentials
``` kotlin
  GateKeeper.enter(accountName: String, password: String?, authToken: String, userData: Bundle?) 
  ```
#### Clear saved user credentials
``` kotlin
  GateKeeper.logout()
```
## Usage
```groovy
    dependencies{
    
      implementation 'com.greyblocks:gatekeeper:0.1.3'
      
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


#### Note: Please do not store plaintext passwords, or do not store password at all.
