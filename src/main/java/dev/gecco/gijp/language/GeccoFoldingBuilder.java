package dev.gecco.gijp.language;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiLiteralUtil;
import com.intellij.util.containers.ContainerUtil;
import dev.gecco.gijp.language.psi.GeccoProperty;
import dev.gecco.gijp.language.psi.impl.GeccoUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class GeccoFoldingBuilder extends FoldingBuilderEx implements DumbAware {


    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        FoldingGroup group = FoldingGroup.newGroup(GeccoAnnotator.GECCO_PREFIX_STR);
        List<FoldingDescriptor> descriptors = new ArrayList<>();

        root.accept(new JavaRecursiveElementWalkingVisitor() {
            @Override
            public void visitLiteralExpression(@NotNull PsiLiteralExpression literalExpression) {
                super.visitLiteralExpression(literalExpression);

                String value = PsiLiteralUtil.getStringLiteralContent(literalExpression);
                if (value != null &&
                        value.startsWith(GeccoAnnotator.GECCO_PREFIX_STR + GeccoAnnotator.GECCO_SEPARATOR_STR)) {
                    Project project = literalExpression.getProject();
                    String key = value.substring(
                            GeccoAnnotator.GECCO_PREFIX_STR.length() + GeccoAnnotator.GECCO_SEPARATOR_STR.length()
                    );
                    // find SimpleProperty for the given key in the project
                    GeccoProperty simpleProperty = ContainerUtil.getOnlyItem(GeccoUtil.findProperties(project, key));
                    if (simpleProperty != null) {
                        // Add a folding descriptor for the literal expression at this node.
                        descriptors.add(new FoldingDescriptor(literalExpression.getNode(),
                                new TextRange(literalExpression.getTextRange().getStartOffset() + 1,
                                        literalExpression.getTextRange().getEndOffset() - 1),
                                group, Collections.singleton(simpleProperty)));
                    }
                }
            }
        });

        return descriptors.toArray(FoldingDescriptor.EMPTY_ARRAY);
    }

    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        if (node.getPsi() instanceof PsiLiteralExpression psiLiteralExpression) {
            String text = PsiLiteralUtil.getStringLiteralContent(psiLiteralExpression);
            if (text == null) {
                return null;
            }

            String key = text.substring(GeccoAnnotator.GECCO_PREFIX_STR.length() + GeccoAnnotator.GECCO_SEPARATOR_STR.length());
            GeccoProperty simpleProperty = ContainerUtil.getOnlyItem(GeccoUtil.findProperties(psiLiteralExpression.getProject(), key));
            if (simpleProperty == null) {
                return StringUtil.THREE_DOTS;
            }

            String propertyValue = simpleProperty.getValue();
            // IMPORTANT: keys can come with no values, so a test for null is needed
            // IMPORTANT: Convert embedded \n to backslash n, so that the string will look
            // like it has LF embedded in it and embedded " to escaped "
            if (propertyValue == null) {
                return StringUtil.THREE_DOTS;
            }

            return propertyValue.replaceAll("\n", "\\n").replaceAll("\"", "\\\\\"");
        }

        return null;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode astNode) {
        return true;
    }
}
