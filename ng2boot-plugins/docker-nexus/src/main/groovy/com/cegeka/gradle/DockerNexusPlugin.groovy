package com.cegeka.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerNexusPlugin implements Plugin<Project> {

    private static final String NEXUS_EXTENSION = 'dockerNexus'
    private static final String BUILD_TASK_NAME = 'buildDockerImage'
    private static final String TAG_TASK_NAME = 'dockerTagImageInNexus'
    private static final String PUSH_TASK_NAME = 'dockerPushImageToNexus'

    @Override
    void apply(Project project) {
        project.extensions.create(NEXUS_EXTENSION, DockerNexusExtension)

        if (hasDockerBuildTask(project)) {
            addDockerTagInNexusTask(project)
            addDockerPushToNexusTask(project)
        }
    }

    private boolean hasDockerBuildTask(Project project) {
        project.tasks.hasProperty(BUILD_TASK_NAME)
    }

    private void addDockerTagInNexusTask(Project project) {
        project.task(TAG_TASK_NAME) {
            group 'Docker'

            doLast {
                project.exec {
                    commandLine 'docker', 'tag', localImageName(project), remoteImageName(project)
                }
            }
        }
    }

    private void addDockerPushToNexusTask(Project project) {
        project.task(dependsOn: TAG_TASK_NAME, PUSH_TASK_NAME) {
            group 'Docker'

            doLast {
                project.exec {
                    commandLine 'docker', 'push', remoteImageName(project)
                }
            }
        }
    }

    private String remoteImageName(Project project) {
        def imageName = localImageName(project)
        def nexusHost = project.dockerNexus.nexusHost
        def nexusPort = project.dockerNexus.nexusPort

        return "$nexusHost:$nexusPort/$imageName"
    }

    private String localImageName(Project project) {
        def imageName = "$project.name".replace('-image', '')

        if (hasDockerRegistry(project)) {
            imageName = project.dockerBuild.registry + "/" + imageName
        }

        if (hasProjectVersion(project)) {
            imageName = imageName + ":" + project.version
        }

        return imageName
    }

    private boolean hasProjectVersion(Project project) {
        !project.version.toString().equalsIgnoreCase("unspecified")
    }

    private boolean hasDockerRegistry(Project project) {
        project.dockerBuild.registry != null
    }
}