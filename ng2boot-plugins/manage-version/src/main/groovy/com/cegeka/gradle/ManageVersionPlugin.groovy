package com.cegeka.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ManageVersionPlugin implements Plugin<Project> {

    private static final String PROPERTY_FILE_PATH = 'gradle.properties'

    private static final String BUMP_MAJOR_TASK_NAME = 'bumpMajorVersion'
    private static final String BUMP_MINOR_TASK_NAME = 'bumpMinorVersion'
    private static final String BUMP_PATCH_TASK_NAME = 'bumpPatchVersion'

    enum VersionIndex {
        MAJOR, MINOR, PATCH
    }

    @Override
    void apply(Project project) {
        addVersionTask(project, BUMP_MAJOR_TASK_NAME, VersionIndex.MAJOR)
        addVersionTask(project, BUMP_MINOR_TASK_NAME, VersionIndex.MINOR)
        addVersionTask(project, BUMP_PATCH_TASK_NAME, VersionIndex.PATCH)
    }

    private void addVersionTask(Project project, String taskName, VersionIndex versionIndex) {
        project.task(taskName) {
            group 'Manage Version'

            doLast {
                bumpVersion(versionIndex)
            }
        }
    }

    private void bumpVersion(VersionIndex versionIndex) {
        Properties props = new Properties()
        File propsFile = new File(PROPERTY_FILE_PATH)

        def stream = propsFile.newDataInputStream()
        props.load(stream)
        stream.close()
        def version = props.getProperty('applicationVersion')
        def versionSplit = version.split('\\.')

        versionSplit[versionIndex.ordinal()] = Integer.parseInt(versionSplit[versionIndex.ordinal()]) + 1

        props.setProperty('applicationVersion', versionSplit.join("."))
        def writer = propsFile.newWriter()
        props.store(writer, null)
        writer.close()
    }
}