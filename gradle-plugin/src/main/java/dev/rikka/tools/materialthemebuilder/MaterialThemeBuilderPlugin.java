package dev.rikka.tools.materialthemebuilder;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.BaseExtension;
import com.android.build.gradle.LibraryExtension;
import com.android.build.gradle.api.BaseVariant;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.File;

@SuppressWarnings("unused")
public class MaterialThemeBuilderPlugin implements Plugin<Project> {

    private final Logger logger = Logging.getLogger(MaterialThemeBuilderPlugin.class);

    private void registerTask(@SuppressWarnings("deprecation") BaseVariant variant, Project project, MaterialThemeBuilderExtension extension) {
        var variantName = variant.getName();
        var variantNameCapitalized = Util.capitalize(variantName);

        var resDir = new File(project.getBuildDir(),
                String.format("generated/res/materialThemeBuilder/%s", variantName));

        var taskName = String.format("generate%sMaterialThemeBuilderRes", variantNameCapitalized);

        var generateResTask = project.getTasks().register(taskName,
                GenerateResTask.class, extension, resDir);

        variant.registerGeneratedResFolders(
                project.files(resDir).builtBy(generateResTask));
    }

    @Override
    public void apply(Project project) {
        var baseExtension = project.getExtensions().findByType(BaseExtension.class);
        if (baseExtension == null) throw new GradleException("Android extension not found");

        var extension = project.getExtensions().create(
                "materialThemeBuilder", MaterialThemeBuilderExtension.class);

        if (baseExtension instanceof AppExtension) {
            ((AppExtension) baseExtension).getApplicationVariants().all(applicationVariant ->
                    registerTask(applicationVariant, project, extension));
        } else if (baseExtension instanceof LibraryExtension) {
            ((LibraryExtension) baseExtension).getLibraryVariants().all(libraryVariant ->
                    registerTask(libraryVariant, project, extension));
        }
    }
}
