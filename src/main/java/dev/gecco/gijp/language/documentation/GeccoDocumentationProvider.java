package dev.gecco.gijp.language.documentation;

import com.google.common.collect.Lists;
import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.psi.util.PsiTreeUtil;
import dev.gecco.gijp.GeccoLanguage;
import dev.gecco.gijp.language.GeccoReference;
import dev.gecco.gijp.language.psi.GeccoProperty;
import dev.gecco.gijp.language.psi.impl.GeccoUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.internal.StringUtil;

import java.util.ArrayList;
import java.util.List;

final class GeccoDocumentationProvider extends AbstractDocumentationProvider {

    /**
     * Extracts the key, value, file and documentation comment of a Gecco key/value entry and returns
     * a formatted representation of the information.
     */
    @Override
    public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (element instanceof GeccoProperty) {
            final String key = ((GeccoProperty) element).getKey();
            final String value = ((GeccoProperty) element).getValue();
            final String file = SymbolPresentationUtil.getFilePathPresentation(element.getContainingFile());
            final String docComment = GeccoUtil.findDocumentationComment((GeccoProperty) element);

            return renderFullDoc(key, value, file, docComment);
        }
        return null;
    }

    /**
     * Attempts to collect any comment elements above the simple key/value pair.
     */
    public static @NotNull String findDocumentationComment(GeccoProperty property) {
        List<String> result = new ArrayList<>();
        PsiElement element = property.getPrevSibling();
        while (element instanceof PsiComment || element instanceof PsiWhiteSpace) {
            if (element instanceof PsiComment) {
                String commentText = element.getText().replaceFirst("[!# ]+", "");
                result.add(commentText);
            }
            element = element.getPrevSibling();
        }
        return StringUtil.join(Lists.reverse(result), "\n ");
    }

    /**
     * Creates the formatted documentation using {@link DocumentationMarkup}. See the Java doc of
     * {@link com.intellij.lang.documentation.DocumentationProvider#generateDoc(PsiElement, PsiElement)} for more
     * information about building the layout.
     */
    private String renderFullDoc(String key, String value, String file, String docComment) {
        StringBuilder sb = new StringBuilder();
        sb.append(DocumentationMarkup.DEFINITION_START);
        sb.append("Gecco Property");
        sb.append(DocumentationMarkup.DEFINITION_END);
        sb.append(DocumentationMarkup.CONTENT_START);
        sb.append(value);
        sb.append(DocumentationMarkup.CONTENT_END);
        sb.append(DocumentationMarkup.SECTIONS_START);
        addKeyValueSection("Key:", key, sb);
        addKeyValueSection("Value:", value, sb);
        addKeyValueSection("File:", file, sb);
        addKeyValueSection("Comment:", docComment, sb);
        sb.append(DocumentationMarkup.SECTIONS_END);
        return sb.toString();
    }

    /**
     * Creates a key/value row for the rendered documentation.
     */
    private void addKeyValueSection(String key, String value, StringBuilder sb) {
        sb.append(DocumentationMarkup.SECTION_HEADER_START);
        sb.append(key);
        sb.append(DocumentationMarkup.SECTION_SEPARATOR);
        sb.append("<p>");
        sb.append(value);
        sb.append(DocumentationMarkup.SECTION_END);
    }

    @Override
    public @Nullable @Nls String generateHoverDoc(@NotNull PsiElement element, @Nullable PsiElement originalElement) {
        return generateDoc(element, originalElement);
    }

    /**
     * Provides the information in which file the Gecco Properties language key/value is defined.
     */
    @Override
    public @Nullable String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        if (element instanceof GeccoProperty) {
            final String key = ((GeccoProperty) element).getKey();
            final String file = SymbolPresentationUtil.getFilePathPresentation(element.getContainingFile());
            return "\"" + key + "\" in " + file;
        }
        return null;
    }

    @Override
    public @Nullable PsiElement getCustomDocumentationElement(@NotNull Editor editor, @NotNull PsiFile file, @Nullable PsiElement context, int targetOffset) {
        if (context != null) {
            // In this part the GeccoProperty element
            // is extracted from inside a Java string
            if (context instanceof PsiJavaToken &&
                    ((PsiJavaToken) context).getTokenType().equals(JavaTokenType.STRING_LITERAL)) {
                PsiElement parent = context.getParent();
                PsiReference[] references = parent.getReferences();
                for (PsiReference ref : references) {
                    if (ref instanceof GeccoReference) {
                        PsiElement property = ref.resolve();
                        if (property instanceof GeccoProperty) {
                            return property;
                        }
                    }
                }
            }
            // In this part the GeccoProperty element is extracted
            // when inside a .gecp file
            else if (context.getLanguage() == GeccoLanguage.INSTANCE) {
                PsiElement property =  PsiTreeUtil.getParentOfType(context, GeccoProperty.class);
                if (property != null) {
                    return property;
                }
            }
        }
        return super.getCustomDocumentationElement(editor, file, context, targetOffset);
    }

}
