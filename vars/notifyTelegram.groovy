def call(String prefix, String botToken, String chatId) {
  def jobName = env.JOB_NAME ?: "Unknow job"
  def buildStatus = currentBuild.currentResult ?: "UNKNOW"
  def buildUrl = env.BUILD_URL ?: ""

  def tmpl = libraryResource 'msgTmpl.md'
  def msg = tmpl
        .replace('${jobName}', jobName)
        .replace('${buildStatus}', buildStatus)
        .replace('${buildUrl}', buildUrl)
  ssh """
    curl -s -X POST https://api.telegram.org/bot${botToken}/sendMessage \
    -d chat_id=${chatId} \
    -d parse_mode=Markdown \
    -d text="${msg.replace("\"", "\\\"")}"
  """
}
