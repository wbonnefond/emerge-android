package com.emergetools.android.gradle

import com.emergetools.android.gradle.base.EmergeGradleRunner
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MultiProjectDisablePerfEmergePluginTest : EmergePluginTest() {

  @Test
  fun multiProjectWithDisabledPerfShouldNotRegisterTask() {
    EmergeGradleRunner.create("multi-project-disable-perf")
      .withArguments(":app:emergeLocalDebugTest", "--dry-run") // should not be registered when performance is disabled
      .withDefaultServer()
      /**
       * Task should not be registered. But with the proposed fix of moving perfOptions.enabled
       * check inside project.afterEvaluate there is a different failure because the perf project is
       * missing the android test plugin and other configuration.
       */
      .assert { result, _ ->
        assertTrue(
          result.output.contains(
            "task 'emergeLocalDebugTest' not found in project ':app'"
          )
        )
      }
      .buildAndFail()
  }
}
