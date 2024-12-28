package dev.gecco.gijp.language.spellcheck;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.spellchecker.inspections.CommentSplitter;
import com.intellij.spellchecker.inspections.IdentifierSplitter;
import com.intellij.spellchecker.inspections.PlainTextSplitter;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.TokenConsumer;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import dev.gecco.gijp.language.psi.GeccoProperty;
import dev.gecco.gijp.language.psi.GeccoTypes;
import org.jetbrains.annotations.NotNull;

final class GeccoSpellcheckingStrategy extends SpellcheckingStrategy {

    @Override
    public @NotNull Tokenizer<?> getTokenizer(PsiElement element) {
        if (element instanceof PsiComment) {
            return new GeccoCommentTokenizer();
        }

        if (element instanceof GeccoProperty) {
            return new GeccoPropertyTokenizer();
        }

        return EMPTY_TOKENIZER;
    }

    private static class GeccoCommentTokenizer extends Tokenizer<PsiComment> {

        @Override
        public void tokenize(@NotNull PsiComment element, @NotNull TokenConsumer consumer) {
            // Exclude the start of the comment with its # characters from spell checking
            int startIndex = 0;
            for (char c : element.textToCharArray()) {
                if (c == '#' || Character.isWhitespace(c)) {
                    startIndex++;
                } else {
                    break;
                }
            }
            consumer.consumeToken(element, element.getText(), false, 0,
                    TextRange.create(startIndex, element.getTextLength()),
                    CommentSplitter.getInstance());
        }

    }

    private static class GeccoPropertyTokenizer extends Tokenizer<GeccoProperty> {

        public void tokenize(@NotNull GeccoProperty element, @NotNull TokenConsumer consumer) {
            //Spell check the keys and values of properties with different splitters
            final ASTNode key = element.getNode().findChildByType(GeccoTypes.KEY);
            if (key != null && key.getTextLength() > 0) {
                final PsiElement keyPsi = key.getPsi();
                final String text = key.getText();
                //For keys, use a splitter for identifiers
                //Note we set "useRename" to true so that keys will be properly refactored (renamed)
                consumer.consumeToken(keyPsi, text, true, 0,
                        TextRange.allOf(text), IdentifierSplitter.getInstance());
            }

            final ASTNode value = element.getNode().findChildByType(GeccoTypes.VALUE);
            if (value != null && value.getTextLength() > 0) {
                final PsiElement valuePsi = value.getPsi();
                final String text = valuePsi.getText();
                //For values, use a splitter for plain text
                consumer.consumeToken(valuePsi, text, false, 0,
                        TextRange.allOf(text), PlainTextSplitter.getInstance());
            }
        }

    }

}
