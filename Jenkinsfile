node('master') {
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
                            targets: 'jobs/*.groovy',
                    )
                } catch (def e) {
                    timeout(50) {
                        echo "Looks like we might have to approve some DSL scripts. Please check at ${JENKINS_URL}/scriptApproval"
                        input "DSL Scripts approved? Then 'continue', otherwise 'abort'."
                        jobDsl(
                                targets: 'jobs/*.groovy',
                        )
                    }
                }
            }
        }
    }
}
