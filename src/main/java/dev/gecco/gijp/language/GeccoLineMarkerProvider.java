package dev.gecco.gijp.language;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.impl.source.tree.java.PsiJavaTokenImpl;
import dev.gecco.gijp.GeccoIcons;
import dev.gecco.gijp.language.psi.GeccoProperty;
import dev.gecco.gijp.language.psi.impl.GeccoUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

final class GeccoLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        // This must be an element with a literal expression as a parent.
        if (!(element instanceof PsiJavaTokenImpl) || !(element.getParent() instanceof PsiLiteralExpression literalExpression)) {
            return;
        }

        // The literal expression must start with the Gecco language literal
        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        if ((value == null) || !value.startsWith(GeccoAnnotator.GECCO_PREFIX_STR + GeccoAnnotator.GECCO_SEPARATOR_STR)) {
            return;
        }

        // Get the Gecco language property usage
        Project project = element.getProject();
        String possibleProperties = value.substring(GeccoAnnotator.GECCO_PREFIX_STR.length() + GeccoAnnotator.GECCO_SEPARATOR_STR.length());

        final List<GeccoProperty> properties = GeccoUtil.findProperties(project, possibleProperties);
        if (!properties.isEmpty()) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(GeccoIcons.FILE_ICON)
                            .setTargets(properties)
                            .setTooltipText("Navigate to gecco property");
            result.add(builder.createLineMarkerInfo(element));
        }
    }

}
