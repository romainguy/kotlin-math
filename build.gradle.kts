plugins {
    kotlin("multiplatform") version "1.6.0"
}

val GROUP: String by project
val VERSION_NAME: String by project

group = GROUP
version = VERSION_NAME

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(BOTH) {
        nodejs {
            testTask {
                useMocha {
                    timeout = "30000"
                }
            }
        }
        compilations.all {
            kotlinOptions {
                sourceMap = true
                moduleKind = "umd"
                metaInfo = true
            }
        }
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.kotest:kotest-assertions-core:5.0.0.RC")
            }
        }

        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.0")
                runtimeOnly("junit:junit:4.13.2")
            }
        }

        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val nativeMain by getting
        val nativeTest by getting
    }
}