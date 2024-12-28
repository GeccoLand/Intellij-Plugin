package dev.gecco.gijp.language.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import dev.gecco.gijp.language.GeccoElementFactory;
import dev.gecco.gijp.language.psi.GeccoProperty;
import dev.gecco.gijp.language.psi.GeccoTypes;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GeccoPsiImplUtil {

    public static String getKey(GeccoProperty element) {
        ASTNode keyNode = element.getNode().findChildByType(GeccoTypes.KEY);
        if (keyNode != null) {
            // IMPORTANT: convert embedded escaped spaces to simple spaces.
            return keyNode.getText().replaceAll("\\\\ ", " ");
        } else {
            return null;
        }
    }

    public static String getValue(GeccoProperty element) {
        ASTNode valueNode = element.getNode().findChildByType(GeccoTypes.VALUE);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static String getName(GeccoProperty element) {
        return getKey(element);
    }

    public static PsiElement setName(GeccoProperty element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(GeccoTypes.KEY);
        if (keyNode != null) {
            GeccoProperty property = GeccoElementFactory.createProperty(element.getProject(), newName);
            ASTNode newKeyNode = property.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(GeccoProperty element) {
        ASTNode keyNode = element.getNode().findChildByType(GeccoTypes.KEY);
        return keyNode != null ? keyNode.getPsi() : null;
    }

    public static ItemPresentation getPresentation(GeccoProperty element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getKey();
            }

            @Nullable
            @Override
            public String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile == null ? null : containingFile.getName();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return element.getIcon(0);
            }
        };
    }

}
