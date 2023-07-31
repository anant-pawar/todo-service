![Build Deploy Workflow](https://github.com/anant-pawar/todo-service/actions/workflows/build-deploy.yaml/badge.svg)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=todo-service&metric=coverage)](https://sonarcloud.io/summary/new_code?id=todo-service)

# Todo Service
 A simple service for creating and managing todo items.

# Local : Build and Run
* Build :  `./mvnw clean install`
* Run : `java -jar target\todo-service-0.0.1-SNAPSHOT.jar`

# Technology Stack
Few key language and frameworks
* Java 17
* Springboot
* H2 DB

# Service documentation :
* Service documentation built and hosted using GitHub Actions and Pages : [Todo Service API](https://anant-pawar.github.io/todo-service)

# Quality and Coverage : 
* Integrated with SonarCloud using GitHub Actions to generate quality and coverage report : [Quality and Coverage Report](https://sonarcloud.io/project/information?id=todo-service) 

# Build and Deployment :
* Integrated with GitHub Actions to build and deploy service
  * [Build and Deployment Workflow](https://github.com/anant-pawar/todo-service/actions)
  * Deployed on [Render](https://render.com/) and service accessible at : https://todo-service-2xuo.onrender.com
