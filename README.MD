# Lyricfier

Kotlin Multiplatform Library

### Publish to MavenCentral

1) Registering a Sonatype account as described here: 
   https://dev.to/kotlin/how-to-build-and-publish-a-kotlin-multiplatform-library-going-public-4a8k
2) Add developer id, name, email and the project url to
   `/convention-plugins/src/main/kotlin/convention.publication.gradle.kts`
3) Add the secrets to `local.properties`:

```
signing.keyId=...
signing.password=...
signing.secretKeyRingFile=...
ossrhUsername=...
ossrhPassword=...
```

4) Run `./gradlew :dodo:publishAllPublicationsToSonatypeRepository`

### Build platform artifacts

#### Android aar

- Run `./gradlew :lyricfier:assembleRelease`
- Output: `/lyricfier/build/outputs/aar/lyricfier-release.aar`

#### JVM jar

- Run `./gradlew :lyricfier:jvmJar`
- Output: `/lyricfier/build/libs/lyricfier-jvm-1.0.jar`

#### iOS Framework

- Run `./gradlew :lyricfier:linkReleaseFrameworkIosArm64`
- Output: `/lyricfier/build/bin/iosArm64/releaseFramework/lyricfier.framework`

#### JS file

- Run `./gradlew :lyricfier:jsBrowserProductionWebpack`
- Output: `/lyricfier/build/dist/js/productionExecutable/lyricfier.js`

#### macOS Framework

- Run `./gradlew :lyricfier:linkReleaseFrameworkMacosArm64`
- Output: `/lyricfier/build/bin/macosArm64/releaseFramework/lyricfier.framework`

#### Linux static library

- Run `./gradlew :lyricfier:linkReleaseStaticLinuxX64`
- Output: `/lyricfier/build/bin/linuxX64/releaseStatic/liblyricfier.a`

#### Windows static library

- Run `./gradlew :lyricfier:linkReleaseStaticMingwX64`
- Output: `/lyricfier/build/bin/mingwX64/releaseStatic/liblyricfier.a`
