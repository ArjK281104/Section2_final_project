pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from repository...'
                checkout([
                    $class: 'GitSCM', 
                    branches: [[name: '*/master']], 
                    extensions: [], 
                    userRemoteConfigs: [[url: 'https://github.com/ArjK281104/Section2_final_project.git']]
                ])
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling the project...'
                bat 'mvn clean compile'
            }
        }

        stage('Run Functional Tests') {
            steps {
                echo 'Running UI and API tests...'
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    bat 'mvn test'
                }
            }
        }
    }

    post {
        always {
            echo 'Archiving test reports...'
            // Archiving functional test reports instead of JMeter
            archiveArtifacts artifacts: 'target/surefire-reports/**, target/cucumber-reports/**', allowEmptyArchive: true
        }
        success {
            echo 'Build completed successfully!'
        }
        unstable {
            echo 'Build is unstable. Some tests may have failed.'
        }
        failure {
            echo 'Build failed during execution.'
        }
    }
}