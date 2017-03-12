# spring-security-explained

[![Build Status](https://travis-ci.org/maxxhuang/spring-security-explained.svg?branch=master)](https://travis-ci.org/maxxhuang/spring-security-explained)

A live tutorial explaining the basic architecture of Spring Security.

Check out the code, follow each unit represented by a Java class, read the content in JavaDoc
and run the live example, you should have a grasp of how Spring Security works.

The official reference tries to cover everything about Spring Security.
There is nothing wrong about it. It is the reference after all. However beginners
usually take longer than they should to learn the basics by reading the reference.
I was one of them.

This tutorial is intended to be a complement to the official Spring Security
Reference. It only covers the essential parts of Spring Security and focuses on
explaining the correlation among the major components and how each component takes part
in the authentication and authorization flow.


## How it works

The tutorial is embedded in Java classes as JavaDoc. Choose any tool you prefer as long as it can properly display JavaDoc.

### Read the tutorial in IDE

Most modern IDEs come with the tool that can parse and display the JavaDoc. Pick one of the topic (Java class) you are interested in, and bring up the JavaDoc tool.

This is how it looks like 

in IntelliJ.
![](image/tutorial-in-intellij.png?raw=true)

in Eclipse
![](image/tutorial-in-eclipse.png?raw=true)
 

### Read the tutorial in browser

Run the maven command to generate JavaDoc in HTML files.

```
$ mvn javadoc:javadoc
```

And open index.html in your favorite browser, then choose the topic from the left panel.

![](image/tutorial-in-browser.png?raw=true)

