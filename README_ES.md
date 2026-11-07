# SDD Flutter Architecture Generator 🚀 (Español)

Plugin oficial para **Android Studio** e **IntelliJ IDEA** creado por el **Ing. Juan Pablo Gosalvez** (`com.nyk`).

[English Version](README_EN.md) | **Versión en Español**

Este plugin automatiza la generación de proyectos y módulos en **Flutter** siguiendo la metodología de arquitectura **SDD Mobile (Spec-Driven Development Mobile)** con **BLoC puro**, separación estricta de capas (`data`, `logic`, `presentation`), repositorios con contratos e implementaciones, e integración nativa con **Material 3** y temas claro/oscuro.

---

## 👨‍💻 Información del Desarrollador y Marca

- **Autor:** Ing. Juan Pablo Gosalvez
- **Email:** [ingejpablo@gmail.com](mailto:ingejpablo@gmail.com)
- **Plugin ID:** `com.nyk.sdd_flutter_generator`
- **Licencia:** MIT

---

## ✨ Funcionalidades Principales

El plugin añade un grupo de opciones en el IDE denominado **`SDD Flutter Generator`** (disponible al hacer clic derecho en la vista de proyecto y dentro del menú **New**):

### 1. 📂 `New SDD Flutter Feature...`
Genera la estructura modular completa para un nuevo feature con las tres capas fundamentales (clases vacías e integradas sin código innecesario):

```text
lib/features/[nombre_feature]/
 ├── data/
 │    ├── models/
 │    │    └── [nombre_feature]_model.dart
 │    ├── repositories/
 │    │    ├── contracts/
 │    │    │    └── [nombre_feature]_repository.dart
 │    │    └── implement/
 │    │         └── [nombre_feature]_repository_impl.dart
 │    └── services/
 │         └── [nombre_feature]_services.dart
 ├── logic/
 │    ├── [nombre_feature]_bloc.dart
 │    ├── [nombre_feature]_event.dart
 │    └── [nombre_feature]_state.dart
 └── presentation/
      ├── [nombre_feature]_screen.dart
      └── widgets/
```

### 2. ⚡ `New SDD BLoC (logic)...`
Genera únicamente el trío de archivos de estado BLoC puro vinculados nativamente mediante `part` y `part of` dentro de la carpeta seleccionada (ej. `logic/`):

- **`[nombre]_bloc.dart`**: Extiende de `Bloc<[Nombre]Event, [Nombre]State>`.
- **`[nombre]_event.dart`**: Declara `sealed class [Nombre]Event {}`.
- **`[nombre]_state.dart`**: Declara `class [Nombre]State { const [Nombre]State(); }`.

### 3. 🏗️ `Bootstrap SDD Project Core...`
Inicializa en un proyecto Flutter recién creado toda la infraestructura de producción, creando automáticamente las carpetas `home` y `details` dentro de `features/` e integrando las rutas nombradas en `app_routes.dart`:

```text
├── l10n.yaml
├── lib/
 │   ├── l10n/
 │   │    └── app_es.arb
 │   ├── core/
 │   │    ├── utils/
 │   │    │    ├── extension_app.dart (context.idioma, context.theme, context.colorScheme)
 │   │    │    └── custom_date.dart (longDate, shortDate en español)
 │   │    └── widgets/
 │   ├── app/
 │   │    ├── theme/
 │   │    │    └── app_theme.dart (Material 3: lightTheme, darkTheme)
 │   │    ├── logic/
 │   │    │    ├── theme_bloc.dart (Gestión dinámica de ThemeMode)
 │   │    │    ├── theme_event.dart
 │   │    │    └── theme_state.dart
 │   │    ├── routes/
 │   │    │    └── app_routes.dart (Rutas nombradas /, /home, /details)
 │   │    ├── app_bloc_providers.dart (ThemeBloc provider)
 │   │    └── app_root.dart
 │   └── features/
 │        ├── home/ (data/, logic/, presentation/)
 │        └── details/ (data/, logic/, presentation/)
```

---

## 🌐 Configuración de Internacionalización (l10n) en Flutter

Para que la localización generada por **Bootstrap SDD Project Core** funcione correctamente, asegúrate de incluir las siguientes dependencias en el archivo `pubspec.yaml` de tu proyecto Flutter:

```yaml
dependencies:
  flutter:
    sdk: flutter
  flutter_localizations:
    sdk: flutter
  flutter_bloc: ^9.1.1
  intl: ^0.20.3

flutter:
  generate: true
```

*(Nota: No se requiere la librería `nested`, ya que `flutter_bloc` maneja internamente la lista de proveedores de estado).*

Posteriormente, ejecuta en la terminal de tu proyecto Flutter el siguiente comando para generar las clases de traducción (`AppLocalizations`):

```bash
flutter gen-l10n
```

---

## 🛠️ Requisitos e Instalación del Plugin

### Requisitos de Compatibilidad
- **IDE:** Android Studio (2024.1 / Build 241 o superior, incluyendo 2025/2026/Canary) e IntelliJ IDEA.
- **Flutter SDK:** Flutter 3.13.0 o superior.
- **Gestión de Estado:** `flutter_bloc` 8.1+ / 9.0+.

### Pasos de Instalación desde Archivo Zip

1. Ubica el archivo del plugin compilado:
   - `build/distributions/sdd_flutter_generator-1.0.0.zip`
2. En Android Studio / IntelliJ IDEA, ve a **Settings** (o **Preferences**) ➔ **Plugins**.
3. Haz clic en el ícono del engranaje (**⚙️**) arriba a la derecha.
4. Selecciona **Install Plugin from Disk...**.
5. Selecciona el archivo **`sdd_flutter_generator-1.0.0.zip`**.
6. Haz clic en **Apply** y reinicia el IDE (**Restart IDE**).

---

## 💻 Compilación para Desarrolladores

Para modificar o compilar el código fuente de este plugin:

```bash
# Clonar el repositorio
git clone https://github.com/ingejpablo/sdd_flutter_generator.git
cd sdd_flutter_generator

# Compilar y ejecutar pruebas
./gradlew test

# Compilar el paquete final (.zip)
./gradlew buildPlugin
```

El paquete instalable estará disponible en `build/distributions/sdd_flutter_generator-1.0.0.zip`.
