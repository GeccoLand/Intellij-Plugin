package dev.gecco.gijp.language;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import dev.gecco.gijp.language.psi.GeccoProperty;
import dev.gecco.gijp.language.psi.impl.GeccoUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

final class GeccoAnnotator implements Annotator {

    public static final String GECCO_PREFIX_STR = "gecp";
    public static final String GECCO_SEPARATOR_STR = "::";

    @Override
    public void annotate (@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        // Ensure the PSI element is an expression
        if (!(element instanceof PsiLiteralExpression literalExpression)) {
            return;
        }

        // Ensure the PSI element contains a string that starts with the prefix and separator
        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        if (value == null || !value.startsWith(GECCO_PREFIX_STR + GECCO_SEPARATOR_STR)) {
            return;
        }

        // Define the text ranges (start is inclusive, end is exclusive)
        // "gecp::key"
        // 0123456790
        TextRange prefixRange = TextRange.from(element.getTextRange().getStartOffset(), GECCO_PREFIX_STR.length() + 1);
        TextRange separatorRange = TextRange.from(prefixRange.getEndOffset(), GECCO_SEPARATOR_STR.length());
        TextRange keyRange = new TextRange(separatorRange.getEndOffset(), element.getTextRange().getEndOffset() - 1);

        // highlight "gecp" prefix and "::" separator
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(prefixRange).textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create();
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(separatorRange).textAttributes(GeccoSyntaxHighlighter.SEPARATOR).create();

        // Get the list of properties for a given key
        String key = value.substring(GECCO_PREFIX_STR.length() + GECCO_SEPARATOR_STR.length());
        List<GeccoProperty> properties = GeccoUtil.findProperties(element.getProject(), key);
        if (properties.isEmpty()) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved property")
                    .range(keyRange)
                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    .withFix(new GeccoCreatePropertyQuickFix(key))
                    .create();
        } else {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(keyRange)
                    .textAttributes(GeccoSyntaxHighlighter.VALUE)
                    .create();
        }
    }

}
