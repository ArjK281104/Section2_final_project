pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling the project...'
                // Changed from 'sh' to 'bat' for Windows execution
                bat 'mvn clean compile' 
            }
        }

        stage('Run Functional Tests') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    echo 'Running UI and API tests...'
                    // Changed from 'sh' to 'bat'
                    bat 'mvn test'
                }
            }
        }

        stage('Run Performance Tests') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    echo 'Running JMeter Load Tests...'
                    // Changed from 'sh' to 'bat'
                    bat 'mvn jmeter:jmeter jmeter:results'
                }
            }
        }
    }

    post {
        always {
            echo 'Generating Reports and Archiving Artifacts...'
            
            // This step requires the Allure Jenkins Plugin to be installed
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results'], [path: 'allure-results']]
            ])
            
            archiveArtifacts artifacts: 'target/jmeter/reports/**', allowEmptyArchive: true
            archiveArtifacts artifacts: 'target/surefire-reports/**', allowEmptyArchive: true
        }
        
        success {
            echo 'Build and Tests completed successfully!'
        }
        
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}