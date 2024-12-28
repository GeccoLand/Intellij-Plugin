package dev.gecco.gijp.language;

import com.intellij.psi.tree.TokenSet;
import dev.gecco.gijp.language.psi.GeccoTypes;

public interface GeccoTokenSets {

    TokenSet IDENTIFIERS = TokenSet.create(GeccoTypes.KEY);

    TokenSet COMMENTS = TokenSet.create(GeccoTypes.COMMENT);

}
