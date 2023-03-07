pipeline {
    agent any
    tools {
        maven 'MAVEN3'
        jdk 'JDK8'
   }

    stages {
        stage('fetching the repo') {
            steps {
                git branch: 'vp-rem', url: 'https://github.com/devopshydclub/vprofile-repo.git'
            }
        }
        stage('BUILD') {
            steps {
                sh 'mvn install -DskipTests'
            }
            post {
                success {
                    echo 'Now archiving the artifacts'
                    archiveArtifacts artifacts: '**/target/*.war'
                 }
            }
        }
        stage('TEST') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }
        stage('Sonar Analysis') {
            environment {
                scannerHome = tool 'sonarq4.8'
            }
            steps {

            }
        }
        }
}
