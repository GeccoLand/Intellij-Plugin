package dev.gecco.gijp.language.psi;

import com.intellij.psi.tree.IElementType;
import dev.gecco.gijp.GeccoLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class GeccoElementType extends IElementType {

    public GeccoElementType(@NotNull @NonNls String debugName) {
        super(debugName, GeccoLanguage.INSTANCE);
    }

}
