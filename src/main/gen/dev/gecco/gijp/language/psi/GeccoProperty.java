// This is a generated file. Not intended for manual editing.
package dev.gecco.gijp.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import dev.gecco.gijp.language.GeccoNamedElement;
import com.intellij.navigation.ItemPresentation;

public interface GeccoProperty extends GeccoNamedElement {

  String getKey();

  String getValue();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

}
