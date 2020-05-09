package com.lucasdias.buildSrc

/**
 * Artigo com implementação parecida: https://proandroiddev.com/gradle-dependency-management-with-kotlin-94eed4df9a28
 */
object Dependencies {

    object Core {
        /**
         * https://developer.android.com/jetpack/androidx
         */
        const val appcompat: String = "androidx.appcompat:appcompat:" + Versions.app_compat

        const val core_ktx: String = "androidx.core:core-ktx:" + Versions.core_ktx

        const val coroutines_android: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:" + Versions.coroutines

        const val coroutines_core: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:" + Versions.coroutines

        /**
         * https://kotlinlang.org/
         */
        const val kotlin_stdlib: String = "org.jetbrains.kotlin:kotlin-stdlib:" + Versions.kotlin

        const val kotlin_stdlib_jdk7: String =
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:" + Versions.kotlin

        const val kotlin_stdlib_jdk8: String =
            "org.jetbrains.kotlin:kotlin-stdlib-jdk8:" + Versions.kotlin

        const val legacy_support: String =
            "androidx.legacy:legacy-support-v4:" + Versions.legacy_support

        const val lifecycle: String =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:" + Versions.lifecycle
    }

    object Data {
        const val converter_gson: String =
            "com.squareup.retrofit2:converter-gson:" + Versions.converter_gson

        const val logging_interceptor: String = "com.squareup.okhttp3:logging-interceptor:" +
                Versions.logging_interceptor

        const val preferences_ktx: String =
            "androidx.preference:preference-ktx:" + Versions.preferences_ktx

        /**
         * https://github.com/kittinunf/Result
         */
        const val result: String = "com.github.kittinunf.result:result:" + Versions.result

        const val result_coroutines: String = "com.github.kittinunf.result:result-coroutines:" +
                Versions.result

        /**
         * https://github.com/square/retrofit/
         */
        const val retrofit: String = "com.squareup.retrofit2:retrofit:" + Versions.retrofit

        /**
         * https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter/
         */
        const val retrofit2_coroutines_adapter: String =
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:" +
                    Versions.retrofit2_coroutines_adapter

        const val room: String = "android.arch.persistence.room:runtime:" + Versions.room

        const val room_compiler: String = "android.arch.persistence.room:compiler:" + Versions.room
    }

    object DI {
        /**
         * https://github.com/InsertKoinIO/koin
         */
        const val koin: String = "org.koin:koin-android:" + Versions.koin

        const val koin_core: String = "org.koin:koin-core:" + Versions.koin

        const val koin_view_model: String =
            "org.koin:koin-androidx-viewmodel:" + Versions.koin
    }

    object Module {
        const val app = ":app"

        const val core_components = ":core-components"

        const val extensions = ":extensions"

        const val fact_catalog = ":factcatalog"

        const val home = ":home"

        const val resource_components = ":resource-components"

        const val search = ":search"

        const val shared_components = ":shared-components"

        const val ui_components = ":ui-components"
    }

    object Plugin {
        const val kotlin: String = "org.jetbrains.kotlin:kotlin-gradle-plugin:" + Versions.kotlin

        /**
         * https://github.com/pinterest/ktlint
         */
        const val ktlint: String = "com.pinterest:ktlint:" + Versions.ktlint
    }

    object Testing {
        /**
         * https://developer.android.com/testing
         */
        const val espresso_contrib: String = "androidx.test.espresso:espresso-contrib:" +
                Versions.espresso

        const val espresso_core: String = "androidx.test.espresso:espresso-core:" +
                Versions.espresso

        const val espresso_intents: String = "androidx.test.espresso:espresso-intents:" +
                Versions.espresso

        const val fragment_testing: String =
            "androidx.fragment:fragment-testing:" + Versions.fragment_testing

        /**
         * http://junit.org
         */
        const val junit: String = "junit:junit:" + Versions.junit

        const val junit_android: String = "androidx.test.ext:junit:" + Versions.junit_android

        const val koin: String = "org.koin:koin-test:" + Versions.koin

        /**
         * https://github.com/Kotlin/kotlinx.coroutines
         */
        const val kotlinx_coroutines_test: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:" +
                    Versions.coroutines

        /**
         * http://mockk.io
         */
        const val mockk: String = "io.mockk:mockk:" + Versions.mockk

        const val support_espresso_core: String =
            "com.android.support.test.espresso:espresso-core:" +
                    Versions.espresso

        const val test_runner: String = "androidx.test:runner:" + Versions.runner
    }

    object Tool {
        const val gradle: String = "com.android.tools.build:gradle:" + Versions.gradle
    }

    object UI {
        const val card_view: String = "androidx.cardview:cardview:" + Versions.card_view

        const val constraint_layout: String = "androidx.constraintlayout:constraintlayout:" +
                Versions.constraint_layout

        const val coordinator_layout: String =
            "androidx.coordinatorlayout:coordinatorlayout:" + Versions.coordinator_layout

        /**
         * https://github.com/bumptech/glide
         */
        const val glide: String = "com.github.bumptech.glide:glide:" + Versions.glide

        const val glide_annotations: String =
            "com.github.bumptech.glide:annotations:" + Versions.glide

        const val glide_compiler: String = "com.github.bumptech.glide:compiler:" + Versions.glide

        const val material: String = "com.google.android.material:material:" + Versions.material

        /**
         * AndroidX Components and Tools
         * https://developer.android.com/jetpack/androidx
         */
        const val recycler_view: String =
            "androidx.recyclerview:recyclerview:" + Versions.recycler_view

        /**
         * http://facebook.github.io/shimmer-android
         */
        const val shimmer: String = "com.facebook.shimmer:shimmer:" + Versions.shimmer
    }
}
