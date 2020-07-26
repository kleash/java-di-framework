# Custom Java Dependency Injection Framework
This project aims to make a light-weight dependency injection framework using Java.

## Getting started
 - Declare as dependency in pom.xml
 ```
<dependency>
    <groupId>com.github.kleash</groupId>
    <artifactId>java-di-framework</artifactId>
    <version>1.0</version>
</dependency>
 ```
 - Annotate all classes you want to auto instantiate and auto injected with `@Component`.
   - By default, all classes are `singleton`, to declare a class scope to `prototype` use `@Component(type = ClassScope.PROTOTYPE)`
 - Annotate all fields in your `@Component` annotated classes with `@Autowired` to automatically
 inject dependencies into your classes.
 - Invoke `ApplicationContextCreator.getInstance().scan("<<base package name here>>")` from main method for starting dependency injection.

## Design consideration
 - Using reflection for auto-discovering and instantiating classes.
 - Keeping only one instance of `ApplicationContextCreator` by using singleton pattern to make 
 sure only one application context is available throughout the lifecycle. It helps in maintaining
 all instantiated beans at one place.
 - Custom exception handling for various scenarios for ease of understanding.
 - Annotation based markers to give user flexibility of manual or automatic handling of dependencies.
 - Hashmap based data structure is used for easy retrieval of instantiated classes and their object.

## Limitations
 - Cannot handle inner classes for auto bean creation.
 - Doesn't handle interfaces and their implementations.
 - It will not scan classes in internal Jars.
 - Most of the items of `Pending features`

## Changelog

### 1.0
 - Scans all classes in a given package and auto instantiate and wire them together.
 - Multiple class scopes such as `singleton`, `prototype`.
 - Custom exceptions. Few of them are:
   - `BeanCreationException`, if unable to create instance of a class with `@Component` annotation. For example, when constructor
   is not visible.
   - `ContextCreateException`, if unable to initiate classloader and scan passed package.
   - `UnsatisfiedDependencyException`, when class is `autowired` but not declared as `@Component`.
 
## Pending features
 - [ ] Dependency injection of classes with parameterized constructors.
 - [ ] More robust classpath scanning.
 - [ ] Add scanning by class. As of now, it scans all classes in given package.
 - [ ] Detecting and handling cyclic dependencies.
 - [ ] Add a way to allow user to inject dependency manually instead to creating automatically using reflection, for example `@Bean`.
 - [ ] Provide annotations for Post bean creation hook and shutdown hook, for example `@PostConstruct`, `@PreDestroy`.
 - [ ] Add support for constructor based injection for `@Autowired` annotation.
 - [ ] Better logging.
