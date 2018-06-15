# GateKeeper
 Save user credentials easier with AccountManager + Authenticator 

#### Save user credentials
  GateKeeper.login(account: Account, password: String?, authToken: String, userData: Bundle) 
#### Clear saved user credentials
  GateKeeper.logout()

## Usage
    maven{ url 'https://dl.bintray.com/dyoed/maven/' }
    
    implementation 'com.greyblocks:gatekeeper:0.1.2'
    
In your strings.xml, add the following string:

    <string name="account_type">you_app_name</string>

In your Application class, implement the Gate class and provide the activity that you will use for authentication/login.



#### Note: Please do not store plaintext passwords, or do not store password at all.
