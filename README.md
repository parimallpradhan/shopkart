# ShopKart — Java 21 Microservices Demo

A learning-friendly online shopping project with the supplied ShopKart-style frontend, a Maven multi-module build, Spring Boot REST services, Docker Compose, a Jenkins pipeline, and starter Kubernetes manifests.

## Architecture

```text
Browser
  |
  v
ShopKart Gateway :8080  (serves index.html and routes API requests)
  |                 |                 |
  v                 v                 v
Product Service    Cart Service      Order Service
:8081              :8082             :8083
```

- **shopkart-gateway**: frontend + single API entry point.
- **product-service**: demo product catalogue and search.
- **cart-service**: add/remove/clear cart items.
- **order-service**: creates demo orders.
- Data is in memory for easy labs; restarting a service clears its data. This is not a production commerce system and has no authentication, payment processing, or database yet.

## Versions / prerequisites

- Java 21 JDK
- Maven 3.9+
- Docker and Docker Compose (for container lab)
- Spring Boot 3.5.6 (configured in root `pom.xml`; compatible with Java 21)

## Run locally with Maven

From this directory, build all modules:

```bash
mvn clean package
```

Open four terminals from the project root and run:

```bash
java -jar product-service/target/product-service-1.0.0.jar
java -jar cart-service/target/cart-service-1.0.0.jar
java -jar order-service/target/order-service-1.0.0.jar
java -jar shopkart-gateway/target/shopkart-gateway-1.0.0.jar
```

Open **http://localhost:8080** in your browser. Keep all four services running. The gateway serves the website and forwards `/api/products`, `/api/cart`, and `/api/orders` to the appropriate microservice.

Health check URLs: `http://localhost:8080/api/health`, `http://localhost:8081/api/products/health`, `http://localhost:8082/api/cart/health`, `http://localhost:8083/api/orders/health`.

## Run with Docker Compose

```bash
mvn clean package
docker compose up --build
```

Open `http://localhost:8080`. Stop with `Ctrl+C`, then `docker compose down`.

## API examples

```bash
curl http://localhost:8080/api/products
curl 'http://localhost:8080/api/products?search=watch'
curl -X POST http://localhost:8080/api/cart -H 'Content-Type: application/json' -d '{"productId":1,"quantity":1}'
curl http://localhost:8080/api/cart
curl -X POST http://localhost:8080/api/orders -H 'Content-Type: application/json' -d '{"items":[{"productId":1,"quantity":1}]}'
```

## Jenkins lab

1. Push the repository to GitHub.
2. Configure Jenkins tools named exactly `Java21` and `Maven3`, or adjust the `tools` section in `Jenkinsfile` to match your Jenkins tool names.
3. Ensure the Jenkins agent can run Docker if you want the image-build stage.
4. Add a Multibranch Pipeline or Pipeline job connected to the repository.
5. The pipeline runs `mvn clean verify`; Docker image build is limited to the `main` branch. Add registry login/tag/push steps only after configuring credentials securely.

## Kubernetes lab

Build the JARs and images first. The manifests assume images named `shopkart-gateway:latest`, `shopkart-product-service:latest`, `shopkart-cart-service:latest`, and `shopkart-order-service:latest` are available to the cluster. For Minikube, build images in its Docker environment or load the images into the cluster.

```bash
kubectl apply -f k8s/shopkart.yaml
kubectl get deployments,pods,services
```

Access using `http://<node-ip>:30080` where NodePort is reachable. For a managed cloud cluster, push images to a registry such as ECR and update the image names. This starter manifest does not include an Ingress resource; you can add one when teaching Ingress and DNS.

## Suggested next improvements

- Add PostgreSQL and Spring Data JPA instead of in-memory data.
- Add validation, global exception handling, tests, and API documentation.
- Add authentication/authorization and secrets management.
- Add readiness/liveness probes, resource requests/limits, HPA, Ingress, and image scanning.
