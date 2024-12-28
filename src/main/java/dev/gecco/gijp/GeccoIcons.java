package dev.gecco.gijp;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class GeccoIcons {

    private static Icon getIcon(String path) {
        return IconLoader.getIcon("/icons/" + path + ".png", GeccoIcons.class);
    }

    public static final Icon FILE_ICON = getIcon("file_x16");

}
