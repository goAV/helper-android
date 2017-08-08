### Google Pay Library


#### How to use.

```java
	GooglePayHelper payHelper = new GooglePayHelper(this,this);
	...
	payHelper.requestPayFor("skuId","extraData");



	@Override
	public void onActivityResult(int requestCode,int responseCode,Intent data){
	...
	payHelper.onActivityResult(requestCode,responseCode,data);
	}

	@Override
	public void onDestroy(){
	...
	payHelper.onDestyoy();
	}
```

#### enjoy
