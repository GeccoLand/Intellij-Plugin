package dev.gecco.gijp.iconProvider;

import com.intellij.ide.IconProvider;
import com.intellij.psi.PsiElement;
import dev.gecco.gijp.GeccoIcons;
import dev.gecco.gijp.language.psi.GeccoProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

final class GeccoPropertyIconProvider extends IconProvider {

    @Override
    public @Nullable Icon getIcon(@NotNull PsiElement element, int flags) {
        return element instanceof GeccoProperty ? GeccoIcons.FILE_ICON : null;
    }

}
