package inspection;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl;
import inte.AABaseInspection;
import inte.AALocalInspectionTool;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GlideInspection extends AALocalInspectionTool implements AABaseInspection {

    //错误信息
    @Override
    public @NotNull
    String buildErrorString(Object... infos) {
        return "请勿使用Glide.with 方法,存在生命周期问题，使用ImageLoader进行替换";
    }


    //显示错误内容
    @Override
    public @Nullable
    String getStaticDescription() {
        return "提示使用 ImageLoader 替换Glide.with(context) 方法,建议使用ImageLoader类, Glide.with(context) 方法存在生命周期问题";
    }

    //错误等级
    @Override
    public @NotNull
    HighlightDisplayLevel getDefaultLevel() {
        return HighlightDisplayLevel.ERROR;
    }


    //错误提示标题
    @Override
    public @Nls
    @NotNull
    String getDisplayName() {
        return "请勿直接调用Color.parseColor";
    }

    //是否默认开启
    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Override
    public void visitJavaElement(PsiElement element) {
        if (element instanceof PsiMethodCallExpression) {
            @NotNull PsiElement[] children = element.getChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i] instanceof PsiReferenceExpressionImpl) {
                    if (children[i].getText().equals("Glide.with")) {
                        registerProblem(children[i]);
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void visitKotlinElement(PsiElement element) {
        if (element.getClass().getSimpleName().equals("KtDotQualifiedExpression")){
            PsiElement firstChild = element.getFirstChild();
            if (firstChild.getText().equals("Glide")){
                PsiElement lastChild = element.getLastChild();
                if (lastChild.getFirstChild().getText().equals("with")){
                    registerProblem(element);
                }
            }
        }
    }


}
