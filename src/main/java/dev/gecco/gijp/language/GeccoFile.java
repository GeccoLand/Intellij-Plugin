package dev.gecco.gijp.language;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import dev.gecco.gijp.GeccoFileType;
import dev.gecco.gijp.GeccoLanguage;
import org.jetbrains.annotations.NotNull;

public class GeccoFile extends PsiFileBase {

    public GeccoFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, GeccoLanguage.INSTANCE);
    }


    @Override
    public @NotNull FileType getFileType() {
        return GeccoFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Gecco File";
    }
}
