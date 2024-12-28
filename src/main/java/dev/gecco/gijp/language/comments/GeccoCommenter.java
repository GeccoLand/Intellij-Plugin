package dev.gecco.gijp.language.comments;

import com.intellij.lang.Commenter;

final class GeccoCommenter implements Commenter {

    @Override
    public String getLineCommentPrefix() {
        return "#";
    }

    @Override
    public String getBlockCommentPrefix() {
        return "!";
    }

    @Override
    public String getBlockCommentSuffix() {
        return "";
    }

    @Override
    public String getCommentedBlockCommentPrefix() {
        return null;
    }

    @Override
    public String getCommentedBlockCommentSuffix() {
        return null;
    }

}
