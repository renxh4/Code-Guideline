package inspection;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.java.PsiMethodCallExpressionImpl;
import com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl;
import inte.AABaseInspection;
import inte.AALocalInspectionTool;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class LogInspection extends AALocalInspectionTool implements AABaseInspection {

    private final List<String> list;

    public LogInspection() {
        list = Arrays.asList(
                "Log",
                "Log4Android",
                "Log4Android",
                "MDLog"
        );
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return "Verbose 详细日志信息输出，可包含用户敏感信息，或在Loop中频繁输出的日志Debug 程序运行期较详细的调试信息，不包含用户敏感信息Info" +
                " 关心的运行时信息，应该是保守的，且保持最低限度的输出Event 有明确归类的事件打点Warn 异常的状态或逻辑，但是不影响程序运行Error" +
                " 捕获到的Error或ExceptionFatal 引起crash的信息";
    }

    @NotNull
    @Override
    public HighlightDisplayLevel getDefaultLevel() {
        return HighlightDisplayLevel.WARNING;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "请使用MoliveLog，并遵循日志规范";
    }

    @Override
    public String buildErrorString(Object... infos) {
        return "请使用MoliveLog，避免使用Log，System.out.println，printStackTrace()，并遵循日志规范";
    }

    @Override
    public void visitJavaElement(PsiElement element) {
        if (element instanceof PsiMethodCallExpressionImpl) {
            if (element.getFirstChild().getText().equals("System.out.println")) {
                registerProblem(element);
                return;
            }
            PsiElement firstChild = element.getFirstChild().getFirstChild();
            if (firstChild instanceof PsiReferenceExpressionImpl) {
                for (String msg : list) {
                    if (firstChild.getText().equals(msg)) {
                        registerProblem(element);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void visitKotlinElement(PsiElement element) {
        if (element.getClass().getSimpleName().equals("KtDotQualifiedExpression")) {
            if (element.getFirstChild().getClass().getSimpleName().equals("KtDotQualifiedExpression")) {
                if (element.getFirstChild().getText().equals("System.out")) {
                    registerProblem(element);
                    return;
                }
            }

            PsiElement firstChild = element.getFirstChild();
            if (firstChild.getClass().getSimpleName().equals("KtNameReferenceExpression")) {
                for (String msg : list) {
                    if (firstChild.getText().equals(msg)) {
                        registerProblem(element);
                        return;
                    }
                }
            }
        }
    }

}
