package com.nyk.sdd.generators

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.nyk.sdd.templates.CodeTemplates

object FeatureGenerator {

    fun generateFeature(project: Project, targetDir: VirtualFile, featureName: String) {
        val featureSnake = CodeTemplates.toSnakeCase(featureName)
        val featurePascal = CodeTemplates.toPascalCase(featureName)
        val projectName = project.name.lowercase()

        WriteCommandAction.runWriteCommandAction(project) {
            val featureDir = targetDir.findChild(featureSnake) ?: targetDir.createChildDirectory(this, featureSnake)

            // 1. Data Layer
            val dataDir = featureDir.findChild("data") ?: featureDir.createChildDirectory(this, "data")
            val modelsDir = dataDir.findChild("models") ?: dataDir.createChildDirectory(this, "models")
            val reposDir = dataDir.findChild("repositories") ?: dataDir.createChildDirectory(this, "repositories")
            val contractsDir = reposDir.findChild("contracts") ?: reposDir.createChildDirectory(this, "contracts")
            val implDir = reposDir.findChild("implement") ?: reposDir.createChildDirectory(this, "implement")
            val servicesDir = dataDir.findChild("services") ?: dataDir.createChildDirectory(this, "services")

            createFile(modelsDir, "${featureSnake}_model.dart", CodeTemplates.getModelTemplate(featureSnake, featurePascal, projectName))
            createFile(contractsDir, "${featureSnake}_repository.dart", CodeTemplates.getRepositoryContractTemplate(featureSnake, featurePascal, projectName))
            createFile(implDir, "${featureSnake}_repository_impl.dart", CodeTemplates.getRepositoryImplTemplate(featureSnake, featurePascal, projectName))
            createFile(servicesDir, "${featureSnake}_services.dart", CodeTemplates.getServicesTemplate(featureSnake, featurePascal, projectName))

            // 2. Logic Layer (BLoC)
            val logicDir = featureDir.findChild("logic") ?: featureDir.createChildDirectory(this, "logic")
            createFile(logicDir, "${featureSnake}_event.dart", CodeTemplates.getBlocEventTemplate(featureSnake, featurePascal, projectName))
            createFile(logicDir, "${featureSnake}_state.dart", CodeTemplates.getBlocStateTemplate(featureSnake, featurePascal, projectName))
            createFile(logicDir, "${featureSnake}_bloc.dart", CodeTemplates.getBlocTemplate(featureSnake, featurePascal, projectName))

            // 3. Presentation Layer
            val presentationDir = featureDir.findChild("presentation") ?: featureDir.createChildDirectory(this, "presentation")
            presentationDir.findChild("widgets") ?: presentationDir.createChildDirectory(this, "widgets")
            createFile(presentationDir, "${featureSnake}_screen.dart", CodeTemplates.getScreenTemplate(featureSnake, featurePascal, projectName))
        }
    }

    private fun createFile(dir: VirtualFile, fileName: String, content: String) {
        val file = dir.findChild(fileName) ?: dir.createChildData(this, fileName)
        file.setBinaryContent(content.toByteArray(Charsets.UTF_8))
    }
}
