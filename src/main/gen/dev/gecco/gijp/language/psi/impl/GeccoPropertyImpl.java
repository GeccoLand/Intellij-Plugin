// This is a generated file. Not intended for manual editing.
package dev.gecco.gijp.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static dev.gecco.gijp.language.psi.GeccoTypes.*;
import dev.gecco.gijp.language.GeccoNamedElementImpl;
import dev.gecco.gijp.language.psi.*;
import com.intellij.navigation.ItemPresentation;

public class GeccoPropertyImpl extends GeccoNamedElementImpl implements GeccoProperty {

  public GeccoPropertyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GeccoVisitor visitor) {
    visitor.visitProperty(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GeccoVisitor) accept((GeccoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  public String getKey() {
    return GeccoPsiImplUtil.getKey(this);
  }

  @Override
  public String getValue() {
    return GeccoPsiImplUtil.getValue(this);
  }

  @Override
  public String getName() {
    return GeccoPsiImplUtil.getName(this);
  }

  @Override
  public PsiElement setName(String newName) {
    return GeccoPsiImplUtil.setName(this, newName);
  }

  @Override
  public PsiElement getNameIdentifier() {
    return GeccoPsiImplUtil.getNameIdentifier(this);
  }

  @Override
  public ItemPresentation getPresentation() {
    return GeccoPsiImplUtil.getPresentation(this);
  }

}
