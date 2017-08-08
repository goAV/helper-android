### Facebook SDK Library

#### AccountKit

##### How to use.

```java
	boolean isHasCache = FacebookKitHelper.instance().getCurrentAccessToken(this);
        if (!isHasCache) {
            FacebookKitHelper.instance().startSMSRegister(this);
        }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        	super.onActivityResult(requestCode, resultCode, data);
        	FacebookKitHelper.instance().onActivityResult(requestCode, resultCode, data, this);
	}
```

#### Facebook Notify with Firebase

##### How to use
```java
        <service android:name="com.szdmcoffee.facebook.notify.FBFirebaseMessagingService">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

        <service android:name="com.szdmcoffee.facebook.notify.FBFirebaseInstanceIDService">
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
            </intent-filter>
        </service>
```


