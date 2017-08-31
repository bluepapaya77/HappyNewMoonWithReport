# Happy New Moon with Report

[![Join the chat at https://gitter.im/HappyNewMoonWithReport/Lobby](https://badges.gitter.im/HappyNewMoonWithReport/Lobby.svg)](https://gitter.im/HappyNewMoonWithReport/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
Run and Test Web Assembly modules (*.wasm) in Java.

## The basic use case is:

```java
Wasm wasm = new Wasm("your Web Assembly Module.wasm");
wasm.exports().yourFunction();

Wasm wasm = new Wasm("HelloWorld.wasm");
System.out.println(wasm.exports().HelloWorld()); 
```

## For Testing the Web Assembly Module
```java
@Test
public void testHelloWorld throws Exception {
	Wasm wasm = new Wasm("HelloWorld.wasm");
	assertEquals("Hello World", wasm.exports().HelloWorld());
}	
```

This project has nothing to do with reporting or fireworks.  
