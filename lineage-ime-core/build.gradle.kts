plugins {
    id("com.android.library")
}

val lineageIme = rootProject.file("third_party/lineage-latinime")

android {
    namespace = "com.android.inputmethod.latin"
    compileSdk = 36

    defaultConfig {
        minSdk = 30
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            java.setSrcDirs(
                listOf(
                    File(lineageIme, "common/src"),
                    File(lineageIme, "java/src"),
                )
            )
            res.setSrcDirs(listOf(File(lineageIme, "java/res")))
        }
    }

    androidResources {
        noCompress += "dict"
    }
}

dependencies {
    api("com.google.code.findbugs:jsr305:3.0.2")
    api("androidx.viewpager2:viewpager2:1.0.0")
    api("com.google.android.material:material:1.14.0-alpha09")
    api("androidx.preference:preference:1.2.1")
    api("androidx.autofill:autofill:1.1.0")
}
