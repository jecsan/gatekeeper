# GateKeeper
Save user credentials easier with AccountManager + Authenticator 


## Usage
    maven{ url 'https://dl.bintray.com/dyoed/maven/' }
    
    implementation 'com.greyblocks:gatekeeper:0.1.1'
    
In your strings.xml, add the following string:

    <string name="account_type">you_app_name</string>

In your Application class, implement the Gate class and provide the activity that you will use for authentication/login.
