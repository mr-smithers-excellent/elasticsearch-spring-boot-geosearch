#!groovy​

node {
    def mvnHome = tool 'M3'

    stage('Checkout') {
        // checkout code from GitHub
        checkout scm

        sh "${mvnHome}/bin/mvn -B verify"
    }

    stage('Build') {
        sh "${mvnHome}/bin/mvn -B build -DskipTests=true"
    }

    stage('Unit Tests') {
        sh "${mvnHome}/bin/mvn -B test"
    }
}