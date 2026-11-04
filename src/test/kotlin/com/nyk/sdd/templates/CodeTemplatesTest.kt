package com.nyk.sdd.templates

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CodeTemplatesTest {

    @Test
    fun testToPascalCase() {
        assertEquals("UserProfile", CodeTemplates.toPascalCase("user_profile"))
        assertEquals("User", CodeTemplates.toPascalCase("user"))
        assertEquals("UserDetail", CodeTemplates.toPascalCase("user-detail"))
    }

    @Test
    fun testToSnakeCase() {
        assertEquals("user_profile", CodeTemplates.toSnakeCase("UserProfile"))
        assertEquals("user", CodeTemplates.toSnakeCase("User"))
    }

    @Test
    fun testTemplatesGeneration() {
        val bloc = CodeTemplates.getBlocTemplate("user", "User", "my_app")
        assertTrue(bloc.contains("class UserBloc extends Bloc<UserEvent, UserState>"))

        val model = CodeTemplates.getModelTemplate("user", "User", "my_app")
        assertTrue(model.contains("class UserModel"))
    }
}
