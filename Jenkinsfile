pipeline {
    agent none
    
    stages {
        stage('prepare') {
            agent any
            steps {
                sh 'pwd && ls -l'
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'maven:latest'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'cd iea/'
                sh 'cp ./src/main/docker/Dockerfile.jvm ./Dockerfile'
                sh 'mvn clean package'
            }
        }
        stage("Build Images") {
            steps {
                sh 'docker-compose build --parallel'
            }
        }
    }
}