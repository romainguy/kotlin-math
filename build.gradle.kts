import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform") version "2.1.20"
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.vanniktech.maven.publish") version "0.31.0"
}

val GROUP: String by project
val VERSION_NAME: String by project

group = GROUP
version = VERSION_NAME

repositories {
    mavenCentral()
}

kotlin {
    jvm()

    js {
        browser()
        nodejs()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs { browser() }

    // Tier 1
    macosArm64()
    iosArm64()
    iosSimulatorArm64()

    // Tier 2
    linuxX64()
    macosX64()
    iosX64()
    watchosArm64()
    watchosSimulatorArm64()
    watchosX64()

    // Tier 3
    androidNativeArm64()
    mingwX64 {
        binaries.findTest(DEBUG)!!.linkerOpts = mutableListOf("-Wl,--subsystem,windows")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

dokka {
    dokkaSourceSets.configureEach {
        reportUndocumented.set(false)
        skipEmptyPackages.set(true)
        skipDeprecated.set(true)
        jdkVersion.set(8)

        // Add Android SDK packages
        enableAndroidDocumentationLink.set(true)

        sourceLink {
            localDirectory.set(project.file("src/commonMain/kotlin"))
            // URL showing where the source code can be accessed through the web browser
            remoteUrl.set(uri("https://github.com/romainguy/kotlin-math/blob/main/${project.name}/src/commonMain/kotlin"))
            // Suffix which is used to append the line number to the URL. Use #L for GitHub
            remoteLineSuffix.set("#L")
        }
    }
}

val dokkaGeneratePublicationHtml by tasks.getting(org.jetbrains.dokka.gradle.tasks.DokkaGeneratePublicationTask::class)

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaGeneratePublicationHtml)
    archiveClassifier.set("javadoc")
    from(dokkaGeneratePublicationHtml.outputDirectory)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}
