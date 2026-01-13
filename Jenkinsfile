// Jenkins Declarative Pipeline
// Usage notes:
// - This pipeline runs Maven build and tests inside a Maven Docker image.
// - To enable Docker build & push, configure these environment variables in Jenkins (Credentials or Pipeline env):
//     DOCKER_REGISTRY (e.g. registry.hub.docker.com/youruser)
//     DOCKER_USERNAME
//     DOCKER_PASSWORD
//   and set them as environment variables in the job or use Jenkins credentials binding.
// - The pipeline archives the produced artifact (target/*.jar) and publishes test results.

pipeline {
  agent any

  options {
    timeout(time: 60, unit: 'MINUTES')
    timestamps()
    ansiColor('xterm')
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      agent {
        docker {
          image 'maven:3.8.8-openjdk-17'
          args '-v /root/.m2:/root/.m2'
        }
      }
      steps {
        echo "Running tests and packaging with Maven"
        sh './mvnw -B -U clean test'
        sh './mvnw -B -U -DskipTests=false package'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
          archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
      }
    }

    stage('Docker Build & Push') {
      when {
        expression { return env.DOCKER_REGISTRY && env.DOCKER_USERNAME && env.DOCKER_PASSWORD }
      }
      steps {
        script {
          def imageName = "${env.DOCKER_REGISTRY}/${env.JOB_NAME}:${env.BUILD_NUMBER}"
          echo "Docker image: ${imageName}"

          // Build image (assumes docker is available on the agent)
          sh "docker build -t ${imageName} ."

          // Login and push
          sh "echo ${env.DOCKER_PASSWORD} | docker login ${env.DOCKER_REGISTRY} -u ${env.DOCKER_USERNAME} --password-stdin"
          sh "docker push ${imageName}"
        }
      }
    }

    stage('Cleanup') {
      steps {
        echo 'Cleanup stage (placeholder)'
      }
    }
  }

  post {
    success {
      echo "Pipeline succeeded: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
    }
    failure {
      echo "Pipeline failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
    }
  }
}

