package inspection;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl;
import com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl;
import com.intellij.psi.util.PsiUtil;
import inte.AABaseInspection;
import inte.AALocalInspectionTool;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.Log;

public class MoliveLogInspection extends AALocalInspectionTool implements AABaseInspection {

    //错误信息
    @Override
    public @NotNull
    String buildErrorString(Object... infos) {
        return "提示使用 MoliveLogTag 而不是自己传 String";
    }


    //显示错误内容
    @Override
    public @Nullable
    String getStaticDescription() {
        return "请使用 MoliveLogTag 这个接口下定义的Tag不要直接使用 String 作为 Tag，所有 MoliveLog 的 Tag 统一放在 MoliveLogTag 接口里管理。";
    }

    //错误等级
    @Override
    public @NotNull
    HighlightDisplayLevel getDefaultLevel() {
        return HighlightDisplayLevel.WARNING;
    }

//    //负责的短标题
//    @Override
//    public @NotNull String getShortName() {
//        return "这个是干啥的";
//    }

    //错误提示标题
    @Override
    public @Nls
    @NotNull
    String getDisplayName() {
        return "使用 MoliveLogTag 而不是自己传 String";
    }

    //是否默认开启
    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Override
    public void visitJavaElement(PsiElement element) {
        if (element instanceof PsiMethodCallExpression) {
            if (element.getFirstChild() instanceof PsiReferenceExpressionImpl) {
                if (check(element.getFirstChild().getText())) {
                    if (element.getLastChild().getChildren()[1] instanceof PsiLiteralExpressionImpl) {
                        PsiClass psiClass = PsiUtil.resolveClassInType(((PsiLiteralExpressionImpl) element.getLastChild().getChildren()[1]).getType());
                        if (psiClass != null && psiClass.getQualifiedName() != null && psiClass.getQualifiedName().equals("java.lang.String")) {
                            registerProblem(element);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void visitKotlinElement(PsiElement element) {
        if (element.getClass().getSimpleName().equals("KtDotQualifiedExpression")){
            PsiElement firstChild = element.getFirstChild();
            if (firstChild.getText().equals("MoliveLog")){
                PsiElement lastChild = element.getLastChild().getLastChild();
                if (lastChild.getClass().getSimpleName().equals("KtValueArgumentList")){
                    if (lastChild.getChildren()[0].getClass().getSimpleName().equals("KtValueArgument")){
                        try {
//                            KtValueArgument aa  = (KtValueArgument) lastChild.getChildren()[0];
//                            org.jetbrains.kotlin.com.intellij.psi.PsiType typeByPsiElement = org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil.getTypeByPsiElement(aa);
//                            org.jetbrains.kotlin.com.intellij.psi.PsiClass psiClass = org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil.resolveClassInType(typeByPsiElement);
//                            if (psiClass != null && psiClass.getQualifiedName() != null && psiClass.getQualifiedName().equals("java.lang.String")) {
//                                registerProblem(element);
//                            }
                            if (!lastChild.getChildren()[0].getText().contains("MoliveLogTag")){
                                registerProblem(element);
                            }
                        }catch (Exception e){
                            Log.d("mmm","失败了"+e.getMessage());
                        }
                    }
                }
            }
        }
    }


    public boolean check(String text) {
        if (text.equals("MoliveLog.d") || text.equals("MoliveLog.v") || text.equals("MoliveLog.e") || text.equals("MoliveLog.w") || text.equals("MoliveLog.i")) {
            return true;
        }
        return false;
    }
}
