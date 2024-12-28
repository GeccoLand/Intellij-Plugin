package dev.gecco.gijp.language.psi.impl;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import dev.gecco.gijp.GeccoFileType;
import dev.gecco.gijp.language.GeccoFile;
import dev.gecco.gijp.language.psi.GeccoProperty;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GeccoUtil {

    /**
     * Searches the entire project for Gecco language files with instances of the Gecco property with the given key.
     * @param project current project
     * @param key to check
     * @return matching properties
     */
    public static List<GeccoProperty> findProperties (Project project, String key) {
        List<GeccoProperty> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(GeccoFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            GeccoFile simpleFile = (GeccoFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                GeccoProperty[] properties = PsiTreeUtil.getChildrenOfType(simpleFile, GeccoProperty.class);
                if (properties != null) {
                    for (GeccoProperty property : properties) {
                        if (key.equals(property.getKey())) {
                            result.add(property);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<GeccoProperty> findProperties (Project project) {
        List<GeccoProperty> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(GeccoFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            GeccoFile simpleFile = (GeccoFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                GeccoProperty[] properties = PsiTreeUtil.getChildrenOfType(simpleFile, GeccoProperty.class);
                if (properties != null) {
                    Collections.addAll(result, properties);
                }
            }
        }
        return result;
    }

    /**
     * Attempts to collect any comment elements above the Simple key/value pair.
     */
    public static @NotNull String findDocumentationComment(GeccoProperty property) {
        List<String> result = new LinkedList<>();
        PsiElement element = property.getPrevSibling();
        while (element instanceof PsiComment || element instanceof PsiWhiteSpace) {
            if (element instanceof PsiComment) {
                String commentText = element.getText().replaceFirst("[!# ]+", "");
                result.add(commentText);
            }
            element = element.getPrevSibling();
        }
        return StringUtil.join(Lists.reverse(result), "\n ");
    }

}
