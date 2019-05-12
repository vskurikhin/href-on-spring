pipeline {
  agent {
    kubernetes {
      //cloud 'kubernetes'
      label 'href'
      yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: docker
    image: docker:18.05-dind
    command: ['cat']
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
    - name: repository
      mountPath: /root/.m2
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
  - name: repository
    persistentVolumeClaim:
      claimName: jnlp-slave-pvc
"""
    }
  }
  stages {

    stage('Build Docker image href-locations') {
      steps {
        git 'https://github.com/vskurikhin/href-on-spring.git'
        container('docker') {
          script {
            def pom = readMavenPom file: './rest-locations-service/pom.xml'  
            def locationImage = docker.build('docker.io/vskurikhin/href-locations:' + pom.version, '-f ./rest-locations-service/Dockerfile.k8s .')
          }
        }
      }
    }

    stage('Build Docker image href-departments') {
      steps {
        git 'https://github.com/vskurikhin/href-on-spring.git'
        container('docker') {
          script {
            def pom = readMavenPom file: './rest-departments-service/pom.xml'  
            def locationImage = docker.build('docker.io/vskurikhin/href-departments:' + pom.version, '-f ./rest-departments-service/Dockerfile.k8s .')
          }
        }
      }
    }

    stage('Build Docker image href-employees') {
      steps {
        git 'https://github.com/vskurikhin/href-on-spring.git'
        container('docker') {
          script {
            def pom = readMavenPom file: './rest-employees-service/pom.xml'  
            def locationImage = docker.build('docker.io/vskurikhin/href-employees:' + pom.version, '-f ./rest-employees-service/Dockerfile.k8s .')
          }
        }
      }
    }
  }
}
