node {
  stage('Checkout') {
    // checkout shared library repository on the jenkins master
    checkout scm
  }

  stage('Seed') {
    // seed the jobs
    jobDsl(targets: 'jobs/*.groovy', sandbox: false)
  }
}
