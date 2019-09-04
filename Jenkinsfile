pipeline {
    options { ... }

    parameters { ... }

    agent { ... }

    stages {
        stage('Test') {
            when {
                changeRequest()
            }
            steps {
                sh 'gradle clean test'
            }
            post {
                always {
                    archiveArtifacts allowEmptyArchive: true, artifacts: '**/build/reports/tests/**/*.*'
                    junit allowEmptyResults: true, testResults: '**/build/test-results/test/*.xml'
                }
            }
        }
        stage('Run DSL') {
            when {
                anyOf {
                    branch 'master'
                }
            }
            steps {
                script {
                    try {
                        jobDsl(
                                failOnMissingPlugin: true,
                                removedConfigFilesAction: 'DELETE',
                                removedJobAction: 'DELETE',
                                removedViewAction: 'DELETE',
                                targets: 'jobs/**/*.groovy',
                                unstableOnDeprecation: true
                        )
                    } catch (def e) {
                        timeout(50) {
                            echo "Looks like we might have to approve some DSL scripts. Please check at ${JENKINS_URL}/scriptApproval"
                            input "DSL Scripts approved? Then 'continue', otherwise 'abort'."
                            jobDsl(
                                    failOnMissingPlugin: true,
                                    removedConfigFilesAction: 'DELETE',
                                    removedJobAction: 'DELETE',
                                    removedViewAction: 'DELETE',
                                    targets: 'jobs/**/*.groovy',
                                    unstableOnDeprecation: true
                            )
                        }
                    }
                }
            }
        }
    }
    post {
        cleanup {
            cleanWs()
        }
    }
}
