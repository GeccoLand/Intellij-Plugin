package dev.gecco.gijp.language;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import dev.gecco.gijp.GeccoFileType;
import dev.gecco.gijp.language.psi.GeccoProperty;

public class GeccoElementFactory {

    public static GeccoProperty createProperty(Project project, String name) {
        GeccoFile file = createFile(project, name);
        return (GeccoProperty) file.getFirstChild();
    }

    public static GeccoFile createFile(Project project, String text) {
        String name = "dummy.gec";
        return (GeccoFile) PsiFileFactory.getInstance(project)
                .createFileFromText(name, GeccoFileType.INSTANCE, text);
    }

}
