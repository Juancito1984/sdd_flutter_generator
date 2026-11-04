package com.nyk.sdd.dialogs

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel

class CreateBlocDialog : DialogWrapper(true) {
    private val blocNameField = JBTextField()

    init {
        title = "Nuevo BLoC SDD Mobile"
        init()
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel(BorderLayout(10, 10))
        panel.preferredSize = Dimension(380, 80)

        val label = JBLabel("Nombre del BLoC (ej. user, praises):")
        panel.add(label, BorderLayout.NORTH)
        panel.add(blocNameField, BorderLayout.CENTER)

        return panel
    }

    override fun doValidate(): ValidationInfo? {
        val text = blocNameField.text.trim()
        if (text.isEmpty()) {
            return ValidationInfo("El nombre del BLoC no puede estar vacío", blocNameField)
        }
        if (!text.matches(Regex("^[a-zA-Z0-9_]+$"))) {
            return ValidationInfo("Usa sólo letras, números y guiones bajos (snake_case)", blocNameField)
        }
        return null
    }

    fun getBlocName(): String = blocNameField.text.trim()
}
