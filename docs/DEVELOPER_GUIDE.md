# Guía del Desarrollador: SDD Flutter Architecture Generator

Esta guía detalla la arquitectura interna, estructura del código fuente Kotlin y flujo de ejecución del plugin para Android Studio e IntelliJ IDEA.

---

## 📐 Estructura del Proyecto Kotlin / Gradle

```text
sdd_flutter_generator/
 ├── build.gradle.kts                   # Configuración de compilación de IntelliJ Platform SDK
 ├── settings.gradle.kts                  # Definición del proyecto Gradle
 ├── src/
 │    └── main/
 │         ├── kotlin/com/nyk/sdd/
 │         │    ├── actions/
 │         │    │    ├── CreateFeatureAction.kt     # AnAction para 'New SDD Flutter Feature...'
 │         │    │    ├── CreateBlocAction.kt        # AnAction para 'New SDD BLoC (logic)...'
 │         │    │    └── BootstrapProjectAction.kt  # AnAction para 'Bootstrap SDD Project Core...'
 │         │    ├── dialogs/
 │         │    │    ├── CreateFeatureDialog.kt     # Diálogo Swing modal para solicitar nombre de Feature
 │         │    │    └── CreateBlocDialog.kt        # Diálogo Swing modal para solicitar nombre de BLoC
 │         │    ├── generators/
 │         │    │    ├── FeatureGenerator.kt        # Creador de estructura data/, logic/ y presentation/ en VFS
 │         │    │    ├── BlocGenerator.kt           # Creador de los 3 archivos BLoC en VFS
 │         │    │    └── ProjectBootstrapper.kt     # Creador de lib/app/, lib/core/ y l10n.yaml en VFS
 │         │    └── templates/
 │         │         └── CodeTemplates.kt           # Colección de plantillas de código Dart
 │         └── resources/
 │              └── META-INF/
 │                   └── plugin.xml             # Manifiesto y registro de acciones del plugin
 └── README.md
```

---

## ⚡ Flujo de Ejecución de una Acción

1. **Invocación desde el Menú (`AnAction`):**
   - El usuario selecciona una opción en el menú contextual (`ProjectViewPopupMenu` o `NewGroup`).
   - Se ejecuta el método `actionPerformed(e: AnActionEvent)` de la acción correspondiente (`CreateFeatureAction`, `CreateBlocAction` o `BootstrapProjectAction`).

2. **Captura de Datos del Usuario (`DialogWrapper`):**
   - Se muestra un diálogo `DialogWrapper` personalizado que valida el nombre del componente ingresado.

3. **Generación de Archivos en el VFS (`Virtual File System`):**
   - El generador correspondiente (`FeatureGenerator`, `BlocGenerator` o `ProjectBootstrapper`) ejecuta un bloque `WriteCommandAction.runWriteCommandAction(project)` para manipular el sistema de archivos virtual de IntelliJ.
   - Las plantillas se leen desde `CodeTemplates.kt`, reemplazando variables de proyecto y convirtiendo nombres a `snake_case` y `PascalCase`.

4. **Notificación al Usuario:**
   - Se despliega una ventana informativa de éxito usando `Messages.showInfoMessage(...)`.

---

## 💡 Mantenimiento de Plantillas de Código

Todas las plantillas de código Dart se gestionan de forma centralizada en `src/main/kotlin/com/nyk/sdd/templates/CodeTemplates.kt`. Si deseas actualizar el tema por defecto, la internacionalización o la estructura de un BLoC, debes modificar los métodos de dicho objeto.

Ejemplo:
```kotlin
fun getBlocTemplate(featureSnake: String, featurePascal: String, projectName: String): String
```
