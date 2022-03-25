package inspection;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import inte.AALocalInspectionTool;
import inte.AABaseInspection;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThreadInspection extends AALocalInspectionTool implements AABaseInspection {


    //错误信息
    @Override
    public @NotNull
    String buildErrorString(Object... infos) {
        return "避免调用New Thread(),new ThreadPoolExecutor(),请勿自行创建线程，建议使用MoliveThreadUtils.execute，对任务进行统一管理";
    }


    //显示错误内容
    @Override
    public @Nullable
    String getStaticDescription() {
        return "避免调用New Thread(),new ThreadPoolExecutor(),请勿自行创建线程，建议使用MoliveThreadUtils.execute，对任务进行统一管理";
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
        return "请勿直接调用Thread(),new ThreadPoolExecutor()";
    }


    @Override
    public void visitJavaElement(PsiElement element) {
        if (element instanceof PsiNewExpression) {
            PsiElement child = element.getChildren()[3];
            if (child instanceof PsiJavaCodeReferenceElementImpl && child.getText().equals("Thread")) {
                registerProblem(element);
            }
            if (child instanceof PsiJavaCodeReferenceElementImpl && child.getText().equals("ThreadPoolExecutor")) {
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
                if (element.getFirstChild().getText().equals("Thread")) {
                    registerProblem(element);
                }

                if (element.getFirstChild().getText().equals("ThreadPoolExecutor")) {
                    registerProblem(element);
                }
            }
        }
    }
}
