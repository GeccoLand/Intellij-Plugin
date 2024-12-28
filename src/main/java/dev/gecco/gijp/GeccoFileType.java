package dev.gecco.gijp;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class GeccoFileType extends LanguageFileType {

    public static final GeccoFileType INSTANCE = new GeccoFileType();

    private GeccoFileType() {
        super(GeccoLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Gecco File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Gecco language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "gec";
    }

    @Override
    public Icon getIcon() {
        return GeccoIcons.FILE_ICON;
    }

}
