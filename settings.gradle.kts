rootProject.name = "payment-service"

includeProject(":common", "common")
includeProject(":domain", "domain")
includeProject(":payment-api", "applications/payment-api")
includeProject(":clients", "adapters/clients")
includeProject(":persistence", "adapters/persistence")

// 프로젝트의 논리적인 경로를 물리적인 경로와 다르게 설정하기 위함
fun includeProject(name: String, projectPath: String? = null) {
    include(name)
    projectPath?.run {
        project(name).projectDir = File(this)
    }
}
