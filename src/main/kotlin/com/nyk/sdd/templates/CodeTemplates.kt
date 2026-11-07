package com.nyk.sdd.templates

@Suppress("UNUSED_PARAMETER")
object CodeTemplates {

    fun toPascalCase(text: String): String {
        return text.split("_", "-", " ")
            .asSequence()
            .filter { it.isNotEmpty() }
            .joinToString("") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
    }

    fun toSnakeCase(text: String): String {
        return text.replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase()
    }

    private const val DOLLAR = "$"

    // --- FEATURE TEMPLATES ---

    fun getBlocEventTemplate(featureSnake: String, featurePascal: String, projectName: String): String = """
part of '${featureSnake}_bloc.dart';

sealed class ${featurePascal}Event {}
""".trimIndent()

    fun getBlocStateTemplate(featureSnake: String, featurePascal: String, projectName: String): String = """
part of '${featureSnake}_bloc.dart';

class ${featurePascal}State {
  const ${featurePascal}State();
}
""".trimIndent()

    fun getBlocTemplate(featureSnake: String, featurePascal: String, projectName: String): String = """
import 'package:flutter_bloc/flutter_bloc.dart';

part '${featureSnake}_event.dart';
part '${featureSnake}_state.dart';

class ${featurePascal}Bloc extends Bloc<${featurePascal}Event, ${featurePascal}State> {
  ${featurePascal}Bloc() : super(const ${featurePascal}State());
}
""".trimIndent()

    fun getModelTemplate(featureSnake: String, featurePascal: String, projectName: String): String = """
class ${featurePascal}Model {
  ${featurePascal}Model();
}
""".trimIndent()

    fun getRepositoryContractTemplate(featureSnake: String, featurePascal: String, projectName: String): String = """
abstract class ${featurePascal}Repository {
}
""".trimIndent()

    fun getRepositoryImplTemplate(featureSnake: String, featurePascal: String, projectName: String): String = """
import '../contracts/${featureSnake}_repository.dart';

class ${featurePascal}RepositoryImpl implements ${featurePascal}Repository {
  ${featurePascal}RepositoryImpl();
}
""".trimIndent()

    fun getServicesTemplate(featureSnake: String, featurePascal: String, projectName: String): String = """
class ${featurePascal}Services {
  ${featurePascal}Services();
}
""".trimIndent()

    fun getScreenTemplate(featureSnake: String, featurePascal: String, projectName: String): String = """
import 'package:flutter/material.dart';

class ${featurePascal}Screen extends StatelessWidget {
  const ${featurePascal}Screen({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold();
  }
}
""".trimIndent()

    // --- LIB/APP/LOGIC ---

    fun getThemeBlocTemplate(): String = """
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

part 'theme_event.dart';
part 'theme_state.dart';

class ThemeBloc extends Bloc<ThemeEvent, ThemeState> {
  ThemeBloc() : super(ThemeState(ThemeMode.system)) {
    on<ThemeChanged>(_onThemeChanged);
    on<ToggleThemeRequested>(_onToggleThemeRequested);
  }

  void _onThemeChanged(ThemeChanged event, Emitter<ThemeState> emit) {
    emit(ThemeState(event.themeMode));
  }

  void _onToggleThemeRequested(
    ToggleThemeRequested event,
    Emitter<ThemeState> emit,
  ) {
    final newMode = state.themeMode == ThemeMode.dark
        ? ThemeMode.light
        : ThemeMode.dark;

    emit(ThemeState(newMode));
  }
}
""".trimIndent()

    fun getThemeEventTemplate(): String = """
part of 'theme_bloc.dart';

sealed class ThemeEvent {}

class ThemeChanged extends ThemeEvent {
  final ThemeMode themeMode;

  ThemeChanged(this.themeMode);
}

class ToggleThemeRequested extends ThemeEvent {}
""".trimIndent()

    fun getThemeStateTemplate(): String = """
part of 'theme_bloc.dart';

class ThemeState {
  final ThemeMode themeMode;

  ThemeState(this.themeMode);
}
""".trimIndent()

    // --- LIB/APP/THEME ---

    fun getAppThemeTemplate(): String = """
import 'package:flutter/material.dart';

class AppTheme {
  // Colores principales
  static const Color primaryColor = Color(0xff216971);
  static const Color secondaryColor = Color(0xffBE9E34);
  static const Color errorColor = Color(0xffB03A2E);

  // --- LIGHT THEME ---
  static ThemeData get lightTheme {
    return ThemeData(
      useMaterial3: true,
      brightness: Brightness.light,
      primaryColor: primaryColor,
      scaffoldBackgroundColor: const Color(0xffF4F7F6),

      colorScheme: ColorScheme.fromSeed(
        seedColor: primaryColor,
        primary: primaryColor,
        secondary: secondaryColor,
        surface: Colors.white,
        error: errorColor,
        onPrimary: Colors.white,
        onSecondary: Colors.white,
        onSurface: Colors.black87,
      ),

      appBarTheme: const AppBarTheme(
        centerTitle: true,
        backgroundColor: primaryColor,
        foregroundColor: Colors.white,
        elevation: 0,
        titleTextStyle: TextStyle(
          fontSize: 20,
          fontWeight: FontWeight.bold,
          letterSpacing: 1.2,
        ),
      ),

      cardTheme: CardThemeData(
        color: Colors.white,
        elevation: 2,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
        margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
      ),

      inputDecorationTheme: InputDecorationTheme(
        filled: true,
        fillColor: Colors.grey[100],
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: BorderSide.none,
        ),
        enabledBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: BorderSide(color: Colors.grey[300]!, width: 1),
        ),
        focusedBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: const BorderSide(color: primaryColor, width: 2),
        ),
        labelStyle: const TextStyle(color: primaryColor),
        prefixIconColor: primaryColor,
      ),

      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          backgroundColor: primaryColor,
          foregroundColor: Colors.white,
          minimumSize: const Size.fromHeight(50),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
          ),
          elevation: 2,
        ),
      ),

      floatingActionButtonTheme: const FloatingActionButtonThemeData(
        backgroundColor: secondaryColor,
        foregroundColor: Colors.white,
      ),

      dialogTheme: DialogThemeData(
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
        elevation: 10,
        backgroundColor: Colors.white,
      ),
    );
  }

  // --- DARK THEME ---
  static ThemeData get darkTheme {
    return ThemeData(
      useMaterial3: true,
      brightness: Brightness.dark,
      primaryColor: primaryColor,
      scaffoldBackgroundColor: const Color(0xff121212),

      colorScheme: ColorScheme.fromSeed(
        seedColor: primaryColor,
        brightness: Brightness.dark,
        primary: primaryColor,
        secondary: secondaryColor,
        surface: const Color(0xff1E1E1E),
        error: errorColor,
        onPrimary: Colors.white,
        onSecondary: Colors.white,
        onSurface: Colors.white70,
      ),

      appBarTheme: const AppBarTheme(
        centerTitle: true,
        backgroundColor: Color(0xff1A1A1A),
        foregroundColor: Colors.white,
        elevation: 0,
        titleTextStyle: TextStyle(
          fontSize: 20,
          fontWeight: FontWeight.bold,
          letterSpacing: 1.2,
        ),
      ),

      cardTheme: CardThemeData(
        color: const Color(0xff1E1E1E),
        elevation: 4,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
        margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
      ),

      inputDecorationTheme: InputDecorationTheme(
        filled: true,
        fillColor: const Color(0xff2A2A2A),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: BorderSide.none,
        ),
        enabledBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: const BorderSide(color: Colors.white10),
        ),
        focusedBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: const BorderSide(color: secondaryColor, width: 2),
        ),
        labelStyle: const TextStyle(color: Colors.white70),
        prefixIconColor: secondaryColor,
      ),

      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          backgroundColor: primaryColor,
          foregroundColor: Colors.white,
          minimumSize: const Size.fromHeight(50),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
          ),
          elevation: 2,
        ),
      ),

      floatingActionButtonTheme: const FloatingActionButtonThemeData(
        backgroundColor: secondaryColor,
        foregroundColor: Colors.white,
      ),

      dialogTheme: DialogThemeData(
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
        elevation: 10,
        backgroundColor: const Color(0xff1E1E1E),
      ),
    );
  }
}
""".trimIndent()

    // --- CORE & APP BOOTSTRAPPER TEMPLATES ---

    fun getExtensionAppTemplate(projectName: String): String = """
import 'package:flutter/material.dart';
import 'package:$projectName/l10n/app_localizations.dart';

extension AppExtension on BuildContext {
  AppLocalizations get idioma => AppLocalizations.of(this);
  ThemeData get theme => Theme.of(this);
  ColorScheme get colorScheme => Theme.of(this).colorScheme;
}
""".trimIndent()

    fun getCustomDateTemplate(): String = """
import 'package:intl/intl.dart';

class CustomDate {
  static String longDate(
    DateTime date, {
    String? title,
    String format = "EEEE d 'de' MMMM 'de' yyyy",
  }) {
    if (title != null) {
      final now = DateFormat(format, 'es').format(date);

      return '${DOLLAR}title: ${DOLLAR}now';
    }

    return DateFormat(format, 'es').format(date);
  }

  static String shortDate(
    DateTime date, {
    String? title,
    String format = 'EEEE dd/MM/yyyy',
  }) {
    if (title != null) {
      final now = DateFormat(format, 'es').format(date);

      return '${DOLLAR}title: ${DOLLAR}now';
    }

    return DateFormat(format, 'es').format(date);
  }
}
""".trimIndent()

    fun getAppRootTemplate(projectName: String): String = """
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:$projectName/l10n/app_localizations.dart';
import 'logic/theme_bloc.dart';
import 'app_bloc_providers.dart';
import 'routes/app_routes.dart';
import 'theme/app_theme.dart';

class AppRoot extends StatelessWidget {
  const AppRoot({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: AppBlocProviders.blocProviders,
      child: BlocBuilder<ThemeBloc, ThemeState>(
        builder: (context, state) {
          return MaterialApp(
            title: '$projectName',
            debugShowCheckedModeBanner: false,
            theme: AppTheme.lightTheme,
            darkTheme: AppTheme.darkTheme,
            themeMode: state.themeMode,
            routes: AppRoutes.routes,
            localizationsDelegates: AppLocalizations.localizationsDelegates,
            supportedLocales: AppLocalizations.supportedLocales,
          );
        },
      ),
    );
  }
}
""".trimIndent()

    fun getAppBlocProvidersTemplate(): String = """
import 'package:flutter_bloc/flutter_bloc.dart';

import 'logic/theme_bloc.dart';

class AppBlocProviders {
  static final blocProviders = [
    BlocProvider<ThemeBloc>(create: (context) => ThemeBloc()),
  ];
}
""".trimIndent()

    fun getAppRoutesTemplate(projectName: String): String = """
import 'package:flutter/material.dart';
import 'package:$projectName/features/details/presentation/details_screen.dart';
import 'package:$projectName/features/home/presentation/home_screen.dart';

class AppRoutes {
  static const String initial = '/';
  static const String home = '/home';
  static const String details = '/details';

  static Map<String, WidgetBuilder> get routes => {
    initial: (context) => const HomeScreen(),
    home: (context) => const HomeScreen(),
    details: (context) => const DetailsScreen(),
  };
}
""".trimIndent()

    fun getL10nYamlTemplate(): String = """
arb-dir: lib/l10n
template-arb-file: app_es.arb
output-localization-file: app_localizations.dart
nullable-getter: false
""".trimIndent()

    fun getAppEsArbTemplate(): String = """
{
  "guardar": "Guardar",
  "editar": "Editar",
  "cancelar": "Cancelar",
  "titulo": "Título",
  "error": "Ocurrió un error",
  "agregar": "Agregar",
  "noHayItemParaMostrar": "No hay elementos para mostrar"
}
""".trimIndent()
}
