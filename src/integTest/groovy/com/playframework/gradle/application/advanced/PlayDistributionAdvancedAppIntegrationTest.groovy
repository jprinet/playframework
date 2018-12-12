package com.playframework.gradle.application.advanced

import com.playframework.gradle.application.PlayDistributionApplicationIntegrationTest
import com.playframework.gradle.fixtures.app.AdvancedPlayApp
import com.playframework.gradle.fixtures.app.PlayApp

import static com.playframework.gradle.plugins.PlayTwirlPlugin.TWIRL_COMPILE_TASK_NAME

class PlayDistributionAdvancedAppIntegrationTest extends PlayDistributionApplicationIntegrationTest {

    private static final TWIRL_COMPILE_TASK_PATH = ":$TWIRL_COMPILE_TASK_NAME".toString()

    @Override
    PlayApp getPlayApp() {
        new AdvancedPlayApp(playVersion)
    }

    @Override
    void verifyArchives() {
        super.verifyArchives()

        archives()*.containsDescendants(
                "main/conf/jva.routes",
                "main/conf/scala.routes")
    }

    @Override
    void verifyStagedFiles() {
        super.verifyStagedFiles()

        File stageMainDir = file("build/stage/main")
        [
            "conf/jva.routes",
            "conf/scala.routes"
        ].each {
            assert new File(stageMainDir, it).isFile()
        }
    }

    @Override
    void verifyJars() {
        super.verifyJars()

        jar("build/distributionJars/main/${playApp.name}.jar").containsDescendants(
                "views/html/awesome/index.class",
                "jva/html/index.class",
                "special/strangename/Application.class",
                "models/DataType.class",
                "models/ScalaClass.class",
                "controllers/scla/MixedJava.class",
                "controllers/jva/PureJava.class"
        )
    }

    @Override
    String[] getBuildTasks() {
        super.getBuildTasks() + TWIRL_COMPILE_TASK_PATH
    }
}
