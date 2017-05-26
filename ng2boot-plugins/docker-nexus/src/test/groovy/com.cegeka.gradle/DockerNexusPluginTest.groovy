package com.cegeka.gradle

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertTrue

class DockerNexusPluginTest {

    @Test
    void givenProjectWithDockerBuild_whenApplyDockerNexusPlugin_thenPushImageTaskIsAdded() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.cegeka.gradle.docker-build'
        project.pluginManager.apply 'com.cegeka.gradle.docker-nexus'

        assertTrue(project.tasks.dockerPushImageToNexus instanceof Task)
    }
}
