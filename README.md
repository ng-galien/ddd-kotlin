# DDD Core library for Kotlin

![build workflow](https://github.com/ng-galien/ddd-kotlin/actions/workflows/build.yml/badge.svg?branch=main)
![release workflow](https://github.com/ng-galien/ddd-kotlin/actions/workflows/release-drafter.yml/badge.svg)
![publish workflow](https://github.com/ng-galien/ddd-kotlin/actions/workflows/publish.yml/badge.svg)

Core library for DDD in Kotlin.  
Implements strategic classes and interfaces to build a DDD application.  

> Copilot is the main contributor of this project.

## ValueObject

A value object is an object that represents a value, which does not have an identity.

## Entity

An entity is an object that is not defined by its attributes, but rather by a thread of continuity and its identity.

## Aggregate

An aggregate is a cluster of associated objects that we treat as a unit for the purpose of data changes.

## Repository

A repository is an object that encapsulates the set of objects persisted in a data store and the operations performed over them, providing a more object-oriented view of the persistence layer.

## DomainEvent

A domain event is an event that occurs in the domain and that is important to the domain.
