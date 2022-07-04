package dev.rikka.tools.materialthemebuilder;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.BaseExtension;
import com.android.build.gradle.LibraryExtension;
import com.android.build.gradle.api.BaseVariant;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.SourceTask;

import java.io.File;

@SuppressWarnings("unused")
public class MaterialThemeBuilderPlugin implements Plugin<Project> {

    private final Logger logger = Logging.getLogger(MaterialThemeBuilderPlugin.class);

    private void registerTask(@SuppressWarnings("deprecation") BaseVariant variant, Project project, MaterialThemeBuilderExtension extension) {
        var variantName = variant.getName();
        var variantNameCapitalized = Util.capitalize(variantName);

        {
            var dir = new File(project.getBuildDir(),
                    String.format("generated/materialThemeBuilder/%s/res", variantName));
            var taskName = String.format("generate%sMaterialThemeBuilderRes", variantNameCapitalized);
            var task = project.getTasks().register(taskName,
                    GenerateResTask.class, extension, dir);

            variant.registerGeneratedResFolders(
                    project.files(dir).builtBy(task));
        }

        {
            var dir = new File(project.getBuildDir(),
                    String.format("generated/materialThemeBuilder/%s/java", variantName));
            var taskName = String.format("generate%sMaterialThemeBuilderSource", variantNameCapitalized);
            var task = project.getTasks().register(taskName,
                    GenerateJavaTask.class, extension, dir);

            variant.registerJavaGeneratingTask(task, dir);
        }
    }

    @Override
    public void apply(Project project) {
        project.getPlugins().withId("com.android.base", (plugin) -> {
            var extension = project.getExtensions().create(
                    MaterialThemeBuilderExtension.class, "materialThemeBuilder", MaterialThemeBuilderExtension.class);

            var baseExtension = project.getExtensions().getByType(BaseExtension.class);

            if (baseExtension instanceof AppExtension) {
                ((AppExtension) baseExtension).getApplicationVariants().all(applicationVariant ->
                        registerTask(applicationVariant, project, extension));
            } else if (baseExtension instanceof LibraryExtension) {
                ((LibraryExtension) baseExtension).getLibraryVariants().all(libraryVariant ->
                        registerTask(libraryVariant, project, extension));
            }
        });
    }
}
