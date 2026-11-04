package com.nyk.sdd.dialogs

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel

class CreateFeatureDialog : DialogWrapper(true) {
    private val featureNameField = JBTextField()

    init {
        title = "Nuevo Feature SDD Mobile"
        init()
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel(BorderLayout(10, 10))
        panel.preferredSize = Dimension(380, 80)

        val label = JBLabel("Nombre del Feature (ej. praises, user_profile):")
        panel.add(label, BorderLayout.NORTH)
        panel.add(featureNameField, BorderLayout.CENTER)

        return panel
    }

    override fun doValidate(): ValidationInfo? {
        val text = featureNameField.text.trim()
        if (text.isEmpty()) {
            return ValidationInfo("El nombre del feature no puede estar vacío", featureNameField)
        }
        if (!text.matches(Regex("^[a-zA-Z0-9_]+$"))) {
            return ValidationInfo("Usa sólo letras, números y guiones bajos (snake_case)", featureNameField)
        }
        return null
    }

    fun getFeatureName(): String = featureNameField.text.trim()
}
