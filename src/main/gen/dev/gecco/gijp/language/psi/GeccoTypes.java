// This is a generated file. Not intended for manual editing.
package dev.gecco.gijp.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import dev.gecco.gijp.language.psi.impl.*;

public interface GeccoTypes {

  IElementType PROPERTY = new GeccoElementType("PROPERTY");

  IElementType COMMENT = new GeccoTokenType("COMMENT");
  IElementType CRLF = new GeccoTokenType("CRLF");
  IElementType KEY = new GeccoTokenType("KEY");
  IElementType SEPARATOR = new GeccoTokenType("SEPARATOR");
  IElementType VALUE = new GeccoTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == PROPERTY) {
        return new GeccoPropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
