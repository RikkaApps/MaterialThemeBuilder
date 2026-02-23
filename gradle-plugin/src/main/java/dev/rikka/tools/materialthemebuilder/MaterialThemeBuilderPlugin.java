package dev.rikka.tools.materialthemebuilder;

import com.android.build.api.variant.AndroidComponentsExtension;
import com.android.build.api.variant.ApplicationAndroidComponentsExtension;
import com.android.build.api.variant.ApplicationVariant;
import com.android.build.api.variant.LibraryAndroidComponentsExtension;
import com.android.build.api.variant.LibraryVariant;
import com.android.build.api.variant.Variant;
import com.android.build.gradle.api.AndroidBasePlugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

@SuppressWarnings("unused")
public class MaterialThemeBuilderPlugin implements Plugin<Project> {

    private final Logger logger = Logging.getLogger(MaterialThemeBuilderPlugin.class);

    private void registerTask(Variant variant, Project project, MaterialThemeBuilderExtension extension) {
        var variantName = variant.getName();
        var variantNameCapitalized = Util.capitalize(variantName);

        {
            var taskName = String.format("generate%sMaterialThemeBuilderRes", variantNameCapitalized);
            var task = project.getTasks().register(taskName, GenerateResTask.class, t -> {
                t.getExtension().set(extension);
                t.getOutputDir().set(
                        project.getLayout().getBuildDirectory()
                                .dir("generated/materialThemeBuilder/" + variantName + "/res"));
            });

            var res = variant.getSources().getRes();
            if (res != null) {
                res.addGeneratedSourceDirectory(task, GenerateResTask::getOutputDir);
            }
        }

        {
            var taskName = String.format("generate%sMaterialThemeBuilderSource", variantNameCapitalized);
            var task = project.getTasks().register(taskName,
                    GenerateJavaTask.class, t -> {
                        t.getPackageName().set(extension.getPackageName());
                        t.getHarmonizedAttrs().set(
                                extension.getExtendedColors().stream()
                                        .filter(MaterialThemeBuilderExtension.ExtendedColor::isHarmonize)
                                        .flatMap(extendedColor ->
                                                MaterialTheme.COLORS.stream()
                                                        .map(color -> "R.attr." +
                                                                color.getAttributeName(extendedColor.getNameForAttribute()))
                                        )
                                        .toList()
                        );
                        t.getOutputDir().set(
                                project.getLayout().getBuildDirectory()
                                        .dir("generated/materialThemeBuilder/" + variantName + "/java"));
                    });
            var java = variant.getSources().getJava();
            if (java != null) {
                java.addGeneratedSourceDirectory(task, GenerateJavaTask::getOutputDir);
            }
        }
    }

    @Override
    public void apply(Project project) {
        project.getPlugins().withId("com.android.base", (plugin) -> {
            var extension = project.getExtensions().create(
                    MaterialThemeBuilderExtension.class, "materialThemeBuilder", MaterialThemeBuilderExtension.class);
            project.getPlugins().withType(AndroidBasePlugin.class, basePlugin -> {
                var components = project.getExtensions().getByType(AndroidComponentsExtension.class);
                if (components instanceof ApplicationAndroidComponentsExtension componentsExtension) {
                    componentsExtension.onVariants(components.selector().all(), (Action<ApplicationVariant>) variant ->
                            registerTask(variant, project, extension));
                } else if (components instanceof LibraryAndroidComponentsExtension componentsExtension) {
                    componentsExtension.onVariants(components.selector().all(), (Action<LibraryVariant>) variant ->
                            registerTask(variant, project, extension));
                }
            });
        });
    }
}
