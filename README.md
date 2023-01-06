# Separate schema multi-tenancy using spring boot POC

## Context

Multitenancy refers to the ability of a software application or system to host multiple independent tenants or organizations, with each tenant having the ability to customize or configure the application or system to meet their specific needs.

In a multitenant application or system, each tenant is typically isolated from the others, with their own separate data, configurations, and customizations. This allows different tenants to use the same application or system without interfering with each other.

__Separate schema multi-tenancy__ is a technique for implementing multitenancy in a database or software application, in which each tenant has a separate schema (i.e. a set of tables and other objects) within the database.


This Poc consist of developing a __ToDo App__ based on Separate Schema Tenancy.

## How to start the application

**The commands:**

First you have to git clone the files by entering in your terminal:
```
$ git clone https://github.com/Cherni-Oussama/Separate-schema-multitenancy-with-spring-boot.git
```  
Then start the application:
```
$ docker-compose up -d
```

## Flow

* On startup, the application will configure two `EntityManager`: one for the master and another for tenants.


* The __MasterEntityManager__ will create the Tenant table in the master schema 
to store each tenant and the corresponding schema.


* When new tenant is created:
  * __MasterEntityManager__ will store the `X-TENANT-ID` and its schema.
  * Create new schema for the new tenant.
  * The __TenantEntityManager__ will populate the new schema with Table dedicated to the Tenant (ToDo table in our case)


* When the application received a request:
  * the __Interceptor__ will intercept this request 
and get the actual tenant `X-TENANT-ID` and ask the **TenantContext** to store it.
  * The __ORM__ get the actual tenant from **TenantContext** and execute the request on the corresponding schema.

