package com.nyk.sdd.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.ui.Messages
import com.nyk.sdd.dialogs.CreateBlocDialog
import com.nyk.sdd.generators.BlocGenerator

class CreateBlocAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val selectedFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
            ?: e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)?.firstOrNull()
            ?: project.guessProjectDir()
            ?: return

        val targetDir = if (selectedFile.isDirectory) selectedFile else (selectedFile.parent ?: project.guessProjectDir() ?: return)

        val dialog = CreateBlocDialog()
        if (dialog.showAndGet()) {
            val blocName = dialog.getBlocName()
            try {
                BlocGenerator.generateBloc(project, targetDir, blocName)
                Messages.showInfoMessage(
                    project,
                    "Archivos BLoC ('$blocName') creados exitosamente.",
                    "SDD BLoC Creado"
                )
            } catch (ex: Exception) {
                Messages.showErrorDialog(
                    project,
                    "Error al generar el BLoC: ${ex.message}",
                    "Error SDD Generator"
                )
            }
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.project != null
    }
}
