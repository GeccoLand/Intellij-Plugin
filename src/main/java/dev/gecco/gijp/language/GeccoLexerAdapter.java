package dev.gecco.gijp.language;

import com.intellij.lexer.FlexAdapter;

public class GeccoLexerAdapter extends FlexAdapter {
    
    public GeccoLexerAdapter() {
        super(new GeccoLexer(null));
    }
    
}
