package dev.gecco.gijp.language;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiPolyVariantReferenceBase;
import com.intellij.psi.ResolveResult;
import dev.gecco.gijp.GeccoIcons;
import dev.gecco.gijp.language.psi.GeccoProperty;
import dev.gecco.gijp.language.psi.impl.GeccoUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GeccoReference extends PsiPolyVariantReferenceBase<PsiElement> {

    private final String key;

    GeccoReference(PsiElement element, TextRange textRange) {
        super(element, textRange);
        key = element.getText()
                .substring(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        List<GeccoProperty> properties = GeccoUtil.findProperties(project, key);
        List<ResolveResult> results = new ArrayList<>();
        for (GeccoProperty property : properties) {
            results.add(new PsiElementResolveResult(property));
        }
        return results.toArray(new ResolveResult[0]);
    }

    @Override
    public Object @NotNull [] getVariants() {
        Project project = myElement.getProject();
        List<GeccoProperty> properties = GeccoUtil.findProperties(project);
        List<LookupElement> variants = new ArrayList<>();
        for (GeccoProperty property : properties) {
            if (property.getKey() != null && !property.getKey().isEmpty()) {
                variants.add(LookupElementBuilder
                        .create(property).withIcon(GeccoIcons.FILE_ICON)
                        .withTypeText(property.getContainingFile().getName())
                );
            }
        }
        return variants.toArray();
    }

}
