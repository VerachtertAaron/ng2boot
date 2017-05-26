package com.cegeka.gradle

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertTrue

class ManageVersionPluginTest {

    @Test
    void givenProject_whenApplyManageVersionPlugin_thenVersionTasksAreAdded() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.cegeka.gradle.manage-version'

        assertTrue(project.tasks.bumpMajorVersion instanceof Task)
        assertTrue(project.tasks.bumpMinorVersion instanceof Task)
        assertTrue(project.tasks.bumpPatchVersion instanceof Task)
    }
}
