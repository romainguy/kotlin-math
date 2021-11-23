import org.jetbrains.kotlin.konan.target.HostManager

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

dependencies {
    dokkaGfmPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.6.0")
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
            iosArm32()
            iosSimulatorArm64()
            watchosArm32()
            watchosArm64()
            watchosX86()
            watchosSimulatorArm64()
            tvosArm64()
            tvosX64()
            tvosSimulatorArm64()
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

apply(from = "publish.gradle")
