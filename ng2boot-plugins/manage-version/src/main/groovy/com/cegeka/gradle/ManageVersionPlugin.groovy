package com.cegeka.gradle

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.gradle.api.Plugin
import org.gradle.api.Project

class ManageVersionPlugin implements Plugin<Project> {

    private static final String PROPERTY_FILE_PATH = 'gradle.properties'
    private static final String VERSION_PROPERTY = 'applicationVersion'

    private static final String BUMP_MAJOR_TASK_NAME = 'bumpMajorVersion'
    private static final String BUMP_MINOR_TASK_NAME = 'bumpMinorVersion'
    private static final String BUMP_PATCH_TASK_NAME = 'bumpPatchVersion'
    private static final String BUMP_TAG_PUSH_TASK_NAME = 'bumpTagAndPushVersion'

    enum VersionIndex {
        MAJOR, MINOR, PATCH
    }

    @Override
    void apply(Project project) {
        addVersionTask(project, BUMP_MAJOR_TASK_NAME, VersionIndex.MAJOR)
        addVersionTask(project, BUMP_MINOR_TASK_NAME, VersionIndex.MINOR)
        addVersionTask(project, BUMP_PATCH_TASK_NAME, VersionIndex.PATCH)
        addTagAndPushVersionTask(project)
    }

    private void addVersionTask(Project project, String taskName, VersionIndex versionIndex) {
        project.task(taskName) {
            group 'Manage Version'

            doLast {
                bumpVersion(versionIndex)
            }
        }
    }

    private void addTagAndPushVersionTask(Project project) {
        project.task(BUMP_TAG_PUSH_TASK_NAME) {
            group 'Manage Version'

            doLast {
                def username = project.properties.get("username")
                def password = project.properties.get("password")
                def versionIndex = project.properties.get("versionIndex")
                def oldVersion = getCurrentVersion()

                Git.open(project.projectDir)
                        .tag()
                        .setName("v" + oldVersion)
                        .setMessage("Release v" + oldVersion)
                        .call()

                def newVersion = bumpVersion(VersionIndex.valueOf(versionIndex))

                Git.open(project.projectDir)
                        .add()
                        .addFilepattern(PROPERTY_FILE_PATH)
                        .call()

                Git.open(project.projectDir)
                        .commit()
                        .setMessage("Prepare new version v" + newVersion)
                        .call()

                Git.open(project.projectDir)
                        .push()
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                        .setPushTags()
                        .call()
            }
        }
    }

    private String getCurrentVersion() {
        Properties properties = new Properties()
        File propertyFile = new File(PROPERTY_FILE_PATH)

        def inputStream = propertyFile.newDataInputStream()
        properties.load(inputStream)
        inputStream.close()

        return properties.getProperty(VERSION_PROPERTY)
    }

    private String bumpVersion(VersionIndex versionIndex) {
        Properties properties = new Properties()
        File propertyFile = new File(PROPERTY_FILE_PATH)

        def inputStream = propertyFile.newDataInputStream()
        properties.load(inputStream)
        inputStream.close()

        def version = properties.getProperty(VERSION_PROPERTY)
        def versionSplit = version.split('\\.')
        def versionSplitIndex = versionIndex.ordinal()

        versionSplit[versionSplitIndex] = Integer.parseInt(versionSplit[versionSplitIndex]) + 1
        def newVersion = versionSplit.join(".")

        properties.setProperty(VERSION_PROPERTY, newVersion)

        def fileWriter = propertyFile.newWriter()
        properties.store(fileWriter, null)
        fileWriter.close()

        return newVersion
    }
}