package com.nyk.sdd.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.ui.Messages
import com.nyk.sdd.dialogs.CreateFeatureDialog
import com.nyk.sdd.generators.FeatureGenerator

class CreateFeatureAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val selectedFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
            ?: e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)?.firstOrNull()
            ?: project.guessProjectDir()
            ?: return

        val targetDir = if (selectedFile.isDirectory) selectedFile else (selectedFile.parent ?: project.guessProjectDir() ?: return)

        val dialog = CreateFeatureDialog()
        if (dialog.showAndGet()) {
            val featureName = dialog.getFeatureName()
            try {
                FeatureGenerator.generateFeature(project, targetDir, featureName)
                Messages.showInfoMessage(
                    project,
                    "Feature '$featureName' generado exitosamente con arquitectura SDD Mobile.",
                    "SDD Feature Creado",
                )
            } catch (ex: Exception) {
                Messages.showErrorDialog(
                    project,
                    "Error al generar el feature: ${ex.message}",
                    "Error SDD Generator",
                )
            }
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.project != null
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT
}
