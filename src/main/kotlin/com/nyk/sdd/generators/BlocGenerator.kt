package com.nyk.sdd.generators

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.nyk.sdd.templates.CodeTemplates

object BlocGenerator {

    fun generateBloc(project: Project, targetDir: VirtualFile, blocName: String) {
        val blocSnake = CodeTemplates.toSnakeCase(blocName)
        val blocPascal = CodeTemplates.toPascalCase(blocName)
        val projectName = project.name.lowercase()

        WriteCommandAction.runWriteCommandAction(project) {
            createFile(targetDir, "${blocSnake}_event.dart", CodeTemplates.getBlocEventTemplate(blocSnake, blocPascal, projectName))
            createFile(targetDir, "${blocSnake}_state.dart", CodeTemplates.getBlocStateTemplate(blocSnake, blocPascal, projectName))
            createFile(targetDir, "${blocSnake}_bloc.dart", CodeTemplates.getBlocTemplate(blocSnake, blocPascal, projectName))
        }
    }

    private fun createFile(dir: VirtualFile, fileName: String, content: String) {
        val file = dir.findChild(fileName) ?: dir.createChildData(this, fileName)
        file.setBinaryContent(content.toByteArray(Charsets.UTF_8))
    }
}
