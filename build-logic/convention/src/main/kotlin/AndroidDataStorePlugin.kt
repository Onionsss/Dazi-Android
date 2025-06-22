import com.onion.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * author : Qi Zhang
 * date : 2025/6/17
 * description :
 */
class AndroidDataStorePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
//            with(pluginManager) {
//                apply("com.google.devtools.ksp")
//                apply("dagger.hilt.android.plugin")
//            }
            
            dependencies {
                "implementation"(libs.findLibrary("androidx.dataStore").get())
                "implementation"(libs.findLibrary("androidx.dataStore.core").get())
                "implementation"(libs.findLibrary("androidx.dataStore.preferences").get())
            }

        }
    }

}