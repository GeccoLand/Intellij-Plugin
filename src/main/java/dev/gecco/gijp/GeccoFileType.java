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
        return "Gecco Properties File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Gecco Properties file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "gecp";
    }

    @Override
    public Icon getIcon() {
        return GeccoIcons.FILE_ICON;
    }

}
