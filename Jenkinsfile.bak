 
 node {
       stage('pulling code from git'){
        checkout scm
       }
  stage('Build Image'){
        buildFile: 'build.gradle', tasks: 'clean artifactoryPublish'
  }
  
}



        