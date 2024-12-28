package dev.gecco.gijp.language;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class GeccoNamedElementImpl extends ASTWrapperPsiElement {

    public GeccoNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

}
