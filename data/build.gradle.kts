plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    api(project(":domain"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.adapter.result)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.jakarta.inject)

    implementation(libs.androidx.paging.common)
}