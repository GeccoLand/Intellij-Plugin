package dev.gecco.gijp.language;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import dev.gecco.gijp.GeccoFileType;
import dev.gecco.gijp.language.psi.GeccoProperty;

public class GeccoElementFactory {

    public static GeccoProperty createProperty(Project project, String name) {
        GeccoFile file = createFile(project, name);
        return (GeccoProperty) file.getFirstChild();
    }

    public static GeccoFile createFile(Project project, String text) {
        String name = "dummy.gecp";
        return (GeccoFile) PsiFileFactory.getInstance(project)
                .createFileFromText(name, GeccoFileType.INSTANCE, text);
    }

    public static GeccoProperty createProperty(Project project, String name, String value) {
        final GeccoFile file = createFile(project, name + " = " + value);
        return (GeccoProperty) file.getFirstChild();
    }

    public static PsiElement createCRLF(Project project) {
        final GeccoFile file = createFile(project, "\n");
        return file.getFirstChild();
    }

}
