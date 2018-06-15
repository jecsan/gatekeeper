# GateKeeper
 Save user credentials easier with AccountManager + Authenticator 

#### Save user credentials
``` kotlin
  GateKeeper.login(account: Account, password: String?, authToken: String, userData: Bundle?) 
  ```
#### Clear saved user credentials
``` kotlin
  GateKeeper.logout()
```
## Usage
```groovy
    repositories {
        ..
        maven{
            url 'https://dl.bintray.com/dyoed/maven/'
        }
    }
    
    dependencies{
    ..
      implementation 'com.greyblocks:gatekeeper:0.1.2'
    }
  
   ```
In your strings.xml, add the following string:
```xml
    <string name="account_type">you_app_name</string>
```
In your Application class, implement the Gate class and provide the activity that you will use for authentication/login.



#### Note: Please do not store plaintext passwords, or do not store password at all.
