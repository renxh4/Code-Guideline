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

public class ColorParseInspection extends AALocalInspectionTool implements AABaseInspection {

    public ColorParseInspection() {
        System.out.println("创建了ColorParseInspection");
    }

    //错误信息
    @Override
    public @NotNull
    String buildErrorString(Object... infos) {
        return "请勿直接调用Color.parseColor,建议使用MoliveKit.parseColor替换";
    }


    //显示错误内容
    @Override
    public @Nullable
    String getStaticDescription() {
        return "避免调用Color.parseColor(String colorString)该方法会抛异常，处理不当会导致奔溃，建议使用MoliveKit.parseColor替换";
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

    @Override
    public void visitJavaElement(PsiElement element) {
        if (element instanceof PsiMethodCallExpression) {
            @NotNull PsiElement[] children = element.getChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i] instanceof PsiReferenceExpressionImpl) {
                    if (children[i].getText().equals("Color.parseColor")) {
                        registerProblem(children[i]);
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void visitKotlinElement(PsiElement element) {
        if (element==null)return;
        if (element.getClass().getSimpleName().equals("KtDotQualifiedExpression")){
            PsiElement firstChild = element.getFirstChild();
            if (firstChild.getText().equals("Color")){
                PsiElement lastChild = element.getLastChild();
                if (lastChild.getFirstChild().getText().equals("parseColor")){
                    registerProblem(element);
                }
            }
        }
    }
}
