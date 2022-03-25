package inte;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AABaseInspection {
    //错误信息
    String buildErrorString(Object... infos);

    //  //显示错误详细内容
    String getStaticDescription();

    //错误等级
    HighlightDisplayLevel getDefaultLevel();

    //错误的提示标题
    String getDisplayName();
}

