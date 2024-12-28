package dev.gecco.gijp.language;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static dev.gecco.gijp.language.GeccoAnnotator.GECCO_PREFIX_STR;
import static dev.gecco.gijp.language.GeccoAnnotator.GECCO_SEPARATOR_STR;

final class GeccoReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class),
                new PsiReferenceProvider() {
                    @Override
                    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
                        String value = literalExpression.getValue() instanceof String ?
                                (String) literalExpression.getValue() : null;
                        if ((value != null && value.startsWith(GECCO_PREFIX_STR + GECCO_SEPARATOR_STR))) {
                            TextRange property = new TextRange(GECCO_PREFIX_STR.length() + GECCO_SEPARATOR_STR.length() + 1,
                                    value.length() + 1);
                            return new PsiReference[]{new GeccoReference(element, property)};
                        }
                        return PsiReference.EMPTY_ARRAY;
                    }
                }
        );
    }

}
