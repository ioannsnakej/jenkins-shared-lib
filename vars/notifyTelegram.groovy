def call(String prefix, String botToken, String chatId) {
  def jobName = env.JOB_NAME ?: "Unknow job"
  def buildNumber = env.BUILD_NUMBER ?: ""
  def buildStatus = currentBuild.currentResult ?: "UNKNOW"
  def buildUrl = env.BUILD_URL ?: ""

  def tmpl = libraryResource 'msgTmpl.md'
  def msg = tmpl
        .replace('${prefix}', prefix)
        .replace('${jobName}', jobName)
        .replace('${buildNumber}', buildNumber)
        .replace('${buildStatus}', buildStatus)
        .replace('${buildUrl}', buildUrl)
  sh """
    curl -v -s -X POST https://api.telegram.org/bot${botToken}/sendMessage \
    -d chat_id=${chatId} \
    -d parse_mode=Markdown \
    -d text="${msg.replace("\"", "\\\"")}"
  """
}
