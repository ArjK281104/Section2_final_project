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
                bat 'mvn clean compile' 
            }
        }

        stage('Run Functional Tests') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    echo 'Running UI and API tests...'
                    bat 'mvn test'
                }
            }
        }

        stage('Run Performance Tests') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    echo 'Running JMeter Load Tests...'
                    // FIX: Added 'jmeter:configure' to generate the required config.json file
                    bat 'mvn jmeter:configure jmeter:jmeter jmeter:results'
                }
            }
        }
    }

    post {
        always {
            echo 'Archiving Artifacts...'
            
            // FIX: Commented out to prevent the "No such DSL method 'allure'" error 
            // since the plugin is not installed on your server.
            
            // allure([
            //     includeProperties: false,
            //     jdk: '',
            //     properties: [],
            //     reportBuildPolicy: 'ALWAYS',
            //     results: [[path: 'target/allure-results'], [path: 'allure-results']]
            // ])
            
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