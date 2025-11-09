def login() {
withCredentials([usernamePassword(credentialsId: 'docker_token', usernameVariable: 'DOCKER_USER', passwordVariable: 'docker_token')]) {
    sh "docker login -u ${DOCKER_USER} -p ${DOCKER_TOKEN}"
  }
}

def build(String tag) {
  sh "docker build -t ${tag} ./"
}

def push(String tag) {
  sh "docker push ${tag}"
}
