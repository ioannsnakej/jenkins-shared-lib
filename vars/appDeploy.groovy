def prepare(String prjDir, String dockerImage) {
  sh """
    mkdir -p ${prjDir}
    cp -r compose.tmpl nginx ${prjDir}
    cat > ${prjDir}/.env <<EOF
    DB_USER = credentials('db_user')
    DB_NAME = credentials('db_name')
    DB_HOST = credentials('db_host')
    DB_PASS = credentials('db_pass')
    EOF

    cd ${prjDir}
    export DOCKER_IMAGE=${dockerImage}
    envsubst < compose.tmpl > compose.yml
  """
}

def deploy(String prjDir, String dockerImage) {
  sh """
    set -x
    docker pull ${dockerImage}
    cd ${prjDir}
    docker compose down || true
    docker compose up -d
    docker compose ps
  """
}
