#!groovy​

node {
    def mvnHome = tool 'M3'

    stage('Preparation') {
        // checkout code from GitHub
        checkout scm

        sh "${mvnHome}/bin/mvn -B verify"
    }
}