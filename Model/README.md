### Model Library

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

