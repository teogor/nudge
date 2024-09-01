import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.License
import dev.teogor.winds.api.NameFormat
import dev.teogor.winds.api.Person
import dev.teogor.winds.api.Scm.GitHub
import dev.teogor.winds.api.SonatypeHost
import dev.teogor.winds.api.TicketSystem
import dev.teogor.winds.ktx.createVersion
import dev.teogor.winds.ktx.person
import dev.teogor.winds.ktx.scm
import dev.teogor.winds.ktx.ticketSystem
import org.gradle.api.internal.catalog.DelegatingProjectDependency
import org.jetbrains.dokka.gradle.DokkaPlugin

plugins {
  alias(libs.plugins.jetbrains.compose) apply false
  alias(libs.plugins.jetbrains.compose.compiler) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false

  alias(libs.plugins.teogor.winds)
  alias(libs.plugins.vanniktech.maven)
  alias(libs.plugins.jetbrains.dokka)
  alias(libs.plugins.diffplug.spotless)
  alias(libs.plugins.jetbrains.kotlinx.binary.compatibility)
}

winds {
  features {
    mavenPublishing = true
    docsGenerator = true
    workflowSynthesizer = true
  }

  moduleMetadata {
    name = "Nudge"
    description = """
    |🛎️ Nudge simplifies snackbars, bringing quick, customizable notifications to your Compose apps!
    |""".trimMargin()

    yearCreated = 2024
    websiteUrl = "https://source.teogor.dev/nudge/"
    apiDocsUrl = "https://source.teogor.dev/nudge/html/"

    artifactDescriptor {
      group = "dev.teogor.nudge"
      name = "nudge"
      version = createVersion(1, 0, 0) {
        alphaRelease(1)
      }
      nameFormat = NameFormat.FULL
      artifactIdFormat = ArtifactIdFormat.MODULE_NAME_ONLY
    }

    // Providing SCM (Source Control Management)
    scm<GitHub> {
      owner = "teogor"
      repository = "nudge"
    }

    // Providing Ticket System
    ticketSystem<TicketSystem.GitHub> {
      owner = "teogor"
      repository = "nudge"
    }

    // Providing Licenses
    licensedUnder(License.Apache2())

    // Providing Persons
    person<Person.DeveloperContributor> {
      id = "teogor"
      name = "Teodor Grigor"
      email = "open-source@teogor.dev"
      url = "https://teogor.dev"
      roles = listOf("Code Owner", "Developer", "Designer", "Maintainer")
      timezone = "UTC+2"
      organization = "Teogor"
      organizationUrl = "https://github.com/teogor"
    }
  }

  publishing {
    enabled = false
    enablePublicationSigning = true
    optInForVanniktechPlugin = true
    cascade = true
    sonatypeHost = SonatypeHost.S01
  }

  documentationBuilder {
    htmlPath = "html/"
  }
}

subprojects {
  apply<SpotlessPlugin>()
  configure<SpotlessExtension> {
    kotlin {
      target("**/*.kt")
      targetExclude(
        "**/build/**/*.kt",
      )
      licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
      trimTrailingWhitespace()
      endWithNewline()
    }
    format("kts") {
      target("**/*.kts")
      targetExclude("**/build/**/*.kts")
      // Look for the first line that doesn't have a block comment (assumed to be the license)
      licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
    }
    format("xml") {
      target("**/*.xml")
      targetExclude("**/build/**/*.xml")
      // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
      licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
    }
  }
}

val excludedProjects = listOf<DelegatingProjectDependency>(
  projects.demo,
)

subprojects {
  val paths = excludedProjects.map { it.identityPath.path }
  if (!paths.contains(this.path)) {
    apply<DokkaPlugin>()
  }
}

apiValidation {
  val ignoredProjectNames = excludedProjects
    .map { project -> project.identityPath.path.removePrefix(":") }
  ignoredProjects.addAll(ignoredProjectNames)
}
