package dev.gecco.gijp.language.style;

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.psi.codeStyle.CodeStyleConfigurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import dev.gecco.gijp.GeccoLanguage;
import org.jetbrains.annotations.NotNull;

final class GeccoCodeStyleSettingsProvider extends CodeStyleSettingsProvider {

    @Override
    public CustomCodeStyleSettings createCustomSettings(@NotNull CodeStyleSettings settings) {
        return new GeccoCodeStyleSettings(settings);
    }

    @Override
    public String getConfigurableDisplayName() {
        return "Gecco";
    }

    @NotNull
    public CodeStyleConfigurable createConfigurable(@NotNull CodeStyleSettings settings, @NotNull CodeStyleSettings modelSettings) {
        return new CodeStyleAbstractConfigurable(settings, modelSettings, this.getConfigurableDisplayName()) {
            @Override
            protected @NotNull CodeStyleAbstractPanel createPanel(@NotNull CodeStyleSettings settings) {
                return new GeccoCodeStyleMainPanel(getCurrentSettings(), settings);
            }
        };
    }

    private static class GeccoCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {

        public GeccoCodeStyleMainPanel(CodeStyleSettings settings, CodeStyleSettings modelSettings) {
            super(GeccoLanguage.INSTANCE, settings, modelSettings);
        }

    }

}
