import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.android.test) apply false
  alias(libs.plugins.detekt) apply false
  alias(libs.plugins.grgit) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.benchmark) apply false
}

allprojects {
  apply<DetektPlugin>()

  configure<DetektExtension> {
    buildUponDefaultConfig = true
    config.setFrom("$rootDir/detekt/detekt.yml")
    baseline = file("$rootDir/detekt/baseline.xml")
  }

  dependencies {
    add("detektPlugins", rootProject.libs.detekt.formatting)
  }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
  exclude("**/reaper/sample/stress/**")
}
