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
      .buildAndFail()
  }
}
