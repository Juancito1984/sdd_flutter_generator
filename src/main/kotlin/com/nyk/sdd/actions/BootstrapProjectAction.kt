package com.nyk.sdd.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.ui.Messages
import com.nyk.sdd.generators.ProjectBootstrapper

class BootstrapProjectAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val rootDir = project.guessProjectDir() ?: return

        val confirm = Messages.showYesNoDialog(
            project,
            "¿Deseas inicializar la infraestructura base SDD Mobile en el proyecto '${project.name}'?\n\nSe creará:\n- l10n.yaml y lib/l10n/app_es.arb\n- lib/core/ (utils, extensions, dates)\n- lib/app/ (theme, root, routes, bloc_providers)",
            "Bootstrap SDD Flutter Core",
            Messages.getQuestionIcon(),
        )

        if (confirm == Messages.YES) {
            try {
                ProjectBootstrapper.bootstrapProject(project, rootDir)
                Messages.showInfoMessage(
                    project,
                    "Infraestructura base SDD Mobile configurada exitosamente.",
                    "Bootstrap Completo",
                )
            } catch (ex: Exception) {
                Messages.showErrorDialog(
                    project,
                    "Error al inicializar el proyecto: ${ex.message}",
                    "Error SDD Bootstrapper",
                )
            }
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.project != null
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT
}
