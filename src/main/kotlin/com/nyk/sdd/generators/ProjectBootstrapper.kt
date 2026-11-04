package com.nyk.sdd.generators

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.nyk.sdd.templates.CodeTemplates

object ProjectBootstrapper {

    fun bootstrapProject(project: Project, rootDir: VirtualFile) {
        val projectName = project.name.lowercase()

        WriteCommandAction.runWriteCommandAction(project) {
            // 1. Root level: l10n.yaml
            createOrUpdateFile(rootDir, "l10n.yaml", CodeTemplates.getL10nYamlTemplate())

            // Get or create lib/
            val libDir = rootDir.findChild("lib") ?: rootDir.createChildDirectory(this, "lib")

            // 2. lib/l10n/
            val l10nDir = libDir.findChild("l10n") ?: libDir.createChildDirectory(this, "l10n")
            createOrUpdateFile(l10nDir, "app_es.arb", CodeTemplates.getAppEsArbTemplate())

            // 3. lib/core/
            val coreDir = libDir.findChild("core") ?: libDir.createChildDirectory(this, "core")
            val utilsDir = coreDir.findChild("utils") ?: coreDir.createChildDirectory(this, "utils")
            coreDir.findChild("widgets") ?: coreDir.createChildDirectory(this, "widgets")

            createOrUpdateFile(utilsDir, "extension_app.dart", CodeTemplates.getExtensionAppTemplate(projectName))
            createOrUpdateFile(utilsDir, "custom_date.dart", CodeTemplates.getCustomDateTemplate())

            // 4. lib/features/
            val featuresDir = libDir.findChild("features") ?: libDir.createChildDirectory(this, "features")

            // 5. lib/app/
            val appDir = libDir.findChild("app") ?: libDir.createChildDirectory(this, "app")
            val themeDir = appDir.findChild("theme") ?: appDir.createChildDirectory(this, "theme")
            val routesDir = appDir.findChild("routes") ?: appDir.createChildDirectory(this, "routes")
            val logicDir = appDir.findChild("logic") ?: appDir.createChildDirectory(this, "logic")

            createOrUpdateFile(themeDir, "app_theme.dart", CodeTemplates.getAppThemeTemplate())
            createOrUpdateFile(logicDir, "theme_bloc.dart", CodeTemplates.getThemeBlocTemplate())
            createOrUpdateFile(logicDir, "theme_event.dart", CodeTemplates.getThemeEventTemplate())
            createOrUpdateFile(logicDir, "theme_state.dart", CodeTemplates.getThemeStateTemplate())
            createOrUpdateFile(routesDir, "app_routes.dart", CodeTemplates.getAppRoutesTemplate(projectName))
            createOrUpdateFile(appDir, "app_bloc_providers.dart", CodeTemplates.getAppBlocProvidersTemplate())
            createOrUpdateFile(appDir, "app_root.dart", CodeTemplates.getAppRootTemplate(projectName))

            // 6. Generate 'home' and 'details' features automatically inside lib/features/
            FeatureGenerator.generateFeature(project, featuresDir, "home")
            FeatureGenerator.generateFeature(project, featuresDir, "details")
        }
    }

    private fun createOrUpdateFile(dir: VirtualFile, fileName: String, content: String) {
        val file = dir.findChild(fileName) ?: dir.createChildData(this, fileName)
        file.setBinaryContent(content.toByteArray(Charsets.UTF_8))
    }
}
