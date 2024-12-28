// This is a generated file. Not intended for manual editing.
package dev.gecco.gijp.language.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import dev.gecco.gijp.language.GeccoNamedElement;

public class GeccoVisitor extends PsiElementVisitor {

  public void visitProperty(@NotNull GeccoProperty o) {
    visitNamedElement(o);
  }

  public void visitNamedElement(@NotNull GeccoNamedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
