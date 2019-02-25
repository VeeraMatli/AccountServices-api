node {
       stage('pulling code from git'){
        checkout scm
       }
  stage('Build Image'){
        sh '/opt/gradle/gradle-5.2.1/bin/gradle build -x test'
  }
  
  stage('Build Image'){
        sh 'sudo docker build -t veeramatli/accountservices-api:${BUILD_NUMBER} .'
        sh 'docker tag veeramatli/accountservices-api:${BUILD_NUMBER} veeramatli/accountservices-api:latest'
  }
  stage('Push Image'){
        sh 'docker login -u veeramatli -p Ap28dc6655'
        sh 'docker push veeramatli/accountservices-api:latest'
  }
 
}




