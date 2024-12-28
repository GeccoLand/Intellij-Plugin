package dev.gecco.gijp.language.psi;

import com.intellij.psi.tree.IElementType;
import dev.gecco.gijp.GeccoLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class GeccoTokenType extends IElementType {

    public GeccoTokenType(@NotNull @NonNls String debugName) {
        super(debugName, GeccoLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "GeccoTokenType." + super.toString();
    }

}
