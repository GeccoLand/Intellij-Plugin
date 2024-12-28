package dev.gecco.gijp.language;

import com.intellij.icons.AllIcons;
import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.lang.Language;
import dev.gecco.gijp.GeccoLanguage;
import dev.gecco.gijp.language.psi.GeccoProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

final class GeccoStructureAwareNavbar extends StructureAwareNavBarModelExtension {

    @NotNull
    @Override
    protected Language getLanguage() {
        return GeccoLanguage.INSTANCE;
    }

    @Override
    public @Nullable String getPresentableText(Object object) {
        if (object instanceof GeccoFile) {
            return ((GeccoFile) object).getName();
        }

        if (object instanceof GeccoProperty) {
            return ((GeccoProperty) object).getName();
        }

        return null;
    }

    @Override
    @Nullable
    public Icon getIcon(Object object) {
        if (object instanceof GeccoProperty) {
            return AllIcons.Nodes.Property;
        }

        return null;
    }

}
