package dev.gecco.gijp.language;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import dev.gecco.gijp.language.psi.GeccoTypes;
import org.jetbrains.annotations.NotNull;

final class GeccoCompletionContributor extends CompletionContributor {

    GeccoCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(GeccoTypes.VALUE),
                new CompletionProvider<>() {
                    public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
                        resultSet.addElement(LookupElementBuilder.create("Hello"));
                    }
                }
        );
    }

}
