### Model Library

```gradle
com.github.goAV.helper-android:ModelParser:v0.2
com.github.goAV.helper-android:MVP:v0.2
```

#### Retrofit

```java
        Impl impl = GoRetrofitSource.create(new GoRetrofitIMPL() {
            @Override
            public String callHostAddress() {
                return "host";
            }
        },Impl.class);

        ...

        //impl.
```

#### Okhttp
1. [cache](./ModelParser/src/main/java/com/goav/parser/okhttp/cache)
2. [cookie](./ModelParser/src/main/java/com/goav/parser/okhttp/cookie)

