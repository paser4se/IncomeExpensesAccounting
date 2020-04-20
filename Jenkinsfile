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
                sh 'cd iea/iea/'
                sh 'ls -l'
                sh 'mvn clean install'
            }
        }
        stage("Build Images") {
            steps {
                sh 'docker-compose build --parallel'
            }
        }
    }
}