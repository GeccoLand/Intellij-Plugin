package dev.gecco.gijp;

import com.intellij.lang.Language;

public class GeccoLanguage extends Language {

    public static final GeccoLanguage INSTANCE = new GeccoLanguage();

    private GeccoLanguage() {
        super("Gecco Properties");
    }

}
