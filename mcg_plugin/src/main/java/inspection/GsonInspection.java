package inspection;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import inte.AABaseInspection;
import inte.AALocalInspectionTool;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GsonInspection extends AALocalInspectionTool implements AABaseInspection {


    //错误信息
    @Override
    public @NotNull
    String buildErrorString(Object... infos) {
        return "建议使用SafeJsonUtil，防止解析崩溃";
    }


    //显示错误内容
    @Override
    public @Nullable
    String getStaticDescription() {
        return "建议使用SafeJsonUtil，防止解析崩溃";
    }

    //错误等级
    @Override
    public @NotNull
    HighlightDisplayLevel getDefaultLevel() {
        return HighlightDisplayLevel.WARNING;
    }


    //错误提示标题
    @Override
    public @Nls
    @NotNull
    String getDisplayName() {
        return "请勿直接new Gson";
    }


    @Override
    public void visitJavaElement(PsiElement element) {
        if (element instanceof PsiNewExpression) {
            PsiElement child = element.getChildren()[3];
            if (child instanceof PsiJavaCodeReferenceElementImpl && child.getText().equals("Gson")) {
                registerProblem(element);
            }
        }

    }

    @Override
    public void visitKotlinElement(PsiElement element) {
        Class<? extends PsiElement> aClass = element.getClass();
        if (aClass.getSimpleName().equals("KtCallExpression")) {
            PsiElement firstChild = element.getFirstChild();
            if (firstChild.getClass().getSimpleName().equals("KtNameReferenceExpression")){
                if (element.getFirstChild().getText().equals("Gson")) {
                    registerProblem(element);
                }
            }
        }
    }
}
