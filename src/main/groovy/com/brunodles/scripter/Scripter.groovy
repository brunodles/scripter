package com.brunodles.scripter

import com.brunodles.auto.gradleplugin.AutoPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

@AutoPlugin(["scripter"])
class Scripter implements Plugin<Project> {

    private static final String EXTENSION_KEY = 'scripter'

    @Override
    void apply(Project project) {
        project.extensions.add(EXTENSION_KEY, ScripterPluginExtension)
        project.task('updateScripts') {
            doLast {
                ScripterPluginExtension extension = project.extensions.getByName(EXTENSION_KEY) as ScripterPluginExtension
                for (Map.Entry<String, String> entry in extension.getProperties().entrySet()) {
                    println "${entry.key} ${entry.value}"
                    File file = new File(entry.key)
                    new URL(entry.value).withInputStream { i ->
                        file.withOutputStream { it << i }
                    }
                }
            }
        }
    }
}
