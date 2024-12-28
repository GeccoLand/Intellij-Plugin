package dev.gecco.gijp.language;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import dev.gecco.gijp.GeccoLanguage;
import dev.gecco.gijp.language.parser.GeccoParser;
import dev.gecco.gijp.language.psi.GeccoTypes;
import org.jetbrains.annotations.NotNull;

final class GeccoParserDefinition implements ParserDefinition {

    public static final IFileElementType FILE = new IFileElementType(GeccoLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new GeccoLexerAdapter();
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return GeccoTokenSets.COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public PsiParser createParser(Project project) {
        return new GeccoParser();
    }

    @NotNull
    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new GeccoFile(viewProvider);
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return GeccoTypes.Factory.createElement(node);
    }

}
