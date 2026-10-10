pipeline {
  agent any
  tools { jdk 'Java21'; maven 'Maven3' }
  options { timestamps(); disableConcurrentBuilds() }
  stages {
    stage('Checkout') { steps { checkout scm } }
    stage('Verify Java and Maven') { steps { sh 'java -version'; sh 'mvn -version' } }
    stage('Build and Test') { steps { sh 'mvn -B clean verify' } }
    stage('Build Docker Images') {
      when { branch 'main' }
      steps {
        sh 'docker build -t shopkart-gateway:latest ./shopkart-gateway'
        sh 'docker build -t shopkart-product-service:latest ./product-service'
        sh 'docker build -t shopkart-cart-service:latest ./cart-service'
        sh 'docker build -t shopkart-order-service:latest ./order-service'
      }
    }
  }
  post { always { archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true } }
}
