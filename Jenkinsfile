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
      mountPath: /root/.m2/repository
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
    stage('Build Docker image 1') {
      steps {
        git 'https://github.com/vskurikhin/href-on-spring.git'
        container('docker') {
          script {
            def hrefImage = docker.build('docker.io/vskurikhin/href')
          }
        }
      }
    }

    stage('Build Docker image 2') {
      steps {
        container('docker') {
          script {
            def locationImage = docker.build('docker.io/vskurikhin/href-location', '-f ./rest-locations-service/Dockerfile .')
          }
        }
      }
    }
  }
}
