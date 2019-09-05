node {
  stage('Checkout') {
    // Clean workspace and checkout shared library repository on the jenkins master
    cleanWs()
    checkout scm
  }

  stage('Seed') {
    // seed the jobs
    jobDsl(targets: 'jobs/*.groovy', sandbox: false)
  }
}
