# Unit test robustness 

# Introduction
This project contains a simple, minimal application that integrates with a relational database. 
It has the following specification:

You are administering a service for renting out trucks to customers.
A truck that is not currently in use can be assigned to a new customer
by sending a POST request to the `assignOwner` endpoint.

If a customer is currently set as the owner of at least one vehicle that belongs
to a pre-defined group of premium brands, the customer should be marked as being a premium customer.

If a truck is already being rented out to another customer, it cannot be transferred
directly to a new customer. It has to be returned to the rental service first
(returning a truck is not implemented in this version).

# Preparation
* Clone this branch to your local workspace.
* Install JDK11 or later

# Assignment
When writing unit tests, it is important that they are understandable,
easy to maintain and do not break on refactoring. This exercise explores
different strategies to implement tests for an application with external dependencies.

## Exercise 1
Your task is to improve the unit tests for `VehicleService.java`. In the 
`src.test.java.se.cygni.talang.quality.application` folder you will find fully implemented 
tests that use mocks to replace the database interactions.

* Create a new test class next to the existing one. Reimplement the existing test cases, 
but use fakes and stubs instead of mocks for the database interactions.

### Things to consider
* Try to implement the tests while assuming as little as possible about the implementation.
* Is there any way you could improve the readability and maintainability of the tests?

## Exercise 2
Your task is to further improve the same test suite.

* Refactor the code in `VehicleService.java` so that all business logic is contained in 
a class that has no interactions with out-of-process dependencies.

* Write unit tests for the business logic that cover all requirements.

### Things to consider
* As you perform your refactorings, keep running both test suites from the previous exercise. 
Do the tests still pass? If not, is there anything you could have done to make them pass both 
before and after the refactoring?

## Exercise 3a (optional)
In exercise 2 there are no requirements on performance. For this exercise, you should assume that 
performance is critical.

* Attempt to refactor the application again so that no unnecessary database calls are made in all
situations, while still separating business logic from out-of-process dependencies.  

## Exercise 3b (optional)
Implement an integration test that covers the functionality of `VehicleService.assignOwner`. 
In order to maximise protection from regressions, make sure you test against a real database instance.

This is a free-form exercise for those who want to try their hand at testing the parts that we couldn't 
cover with unit tests. Below are some pointers to help getting started and for working with Spring. 
You may use all or none of them as you see fit.

* Setup database instance in Docker or other container environment
* Trigger database instance creation inside the test with TestContainers
* Annotate the test class with `@SpringBootTest` to make the Spring framework start up your entire
  application before the test runs
  * Adding `@Autowired VehicleService service;` as a class field brings in a fully configured
    service class to test against. The same can be done for any other bean.
  * To do a full component test going through the REST endpoint, look into
    `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)`
* Run the application from the command line with (`./mvnw spring-boot:run`) and run tests against public 
endpoints over the network

### Things to consider

* Integration tests still need to be isolated. How do you keep your tests from affecting 
each other through the shared database? How can you be sure that other (future) tests in the project won't 
affect your tests?

## Running the tests

`./mvnw test` on the command line

You may also want to set up running the tests in you IDE, 
in order to more easily visualise what passes and what fails.