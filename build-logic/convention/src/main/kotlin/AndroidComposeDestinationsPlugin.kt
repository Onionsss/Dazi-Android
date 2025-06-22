import com.onion.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * author : Qi Zhang
 * date : 2025/6/17
 * description :
 */
class AndroidComposeDestinationsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                "implementation"(libs.findLibrary("compose.destinations").get())
                "implementation"(libs.findLibrary("compose.destinations.bottomsheet").get())
                "ksp"(libs.findLibrary("compose.destinations.compiler").get())
            }

        }
    }

}