import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.konan.target.HostManager

import java.net.URL

plugins {
    kotlin("multiplatform") version "1.6.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.dokka") version "1.6.0"
    id("maven-publish")
    id("signing")
}

val GROUP: String by project
val VERSION_NAME: String by project

group = GROUP
version = VERSION_NAME

repositories {
    mavenCentral()
}

kotlin {
    targets {
        jvm()
        js(BOTH) {
            compilations.all {
                kotlinOptions {
                    sourceMap = true
                    moduleKind = "umd"
                    metaInfo = true
                }
            }
            browser()
            nodejs()
        }
        if (HostManager.hostIsMac) {
            macosX64()
            macosArm64()
            iosX64()
            iosArm64()
            iosSimulatorArm64()
            watchosArm64()
            watchosX86()
            watchosSimulatorArm64()
        }

        if (HostManager.hostIsMingw || HostManager.hostIsMac) {
            mingwX64 {
                binaries.findTest(DEBUG)!!.linkerOpts = mutableListOf("-Wl,--subsystem,windows")
            }
        }

        if (HostManager.hostIsLinux || HostManager.hostIsMac) {
            linuxX64()
            linuxArm32Hfp()
        }
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

tasks {
    val dokkaHtml by getting(DokkaTask::class) {
        dokkaSourceSets {
            configureEach {
                reportUndocumented.set(false)
                skipEmptyPackages.set(true)
                skipDeprecated.set(true)
                jdkVersion.set(8)

                // Add Android SDK packages
                noAndroidSdkLink.set(false)

                sourceLink {
                    localDirectory.set(project.file("src/commonMain/kotlin"))
                    // URL showing where the source code can be accessed through the web browser
                    remoteUrl.set(URL("https://github.com/romainguy/kotlin-math/blob/main/${project.name}/src/commonMain/kotlin"))
                    // Suffix which is used to append the line number to the URL. Use #L for GitHub
                    remoteLineSuffix.set("#L")
                }
            }
        }
    }
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

apply(from = "publish.gradle")
