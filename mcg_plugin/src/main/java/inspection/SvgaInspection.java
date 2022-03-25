package inspection;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.impl.source.tree.java.PsiIdentifierImpl;
import com.intellij.psi.impl.source.tree.java.PsiMethodCallExpressionImpl;
import inte.AABaseInspection;
import inte.AALocalInspectionTool;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SvgaInspection extends AALocalInspectionTool implements AABaseInspection {

    public SvgaInspection() {
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return "提示使用 MoliveSvgaConstant 常量定义的本地SVGA资源建议使用MoliveSvgaConstant定义的常量, 方便对本地SVGA资源进行管理";
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
        return "建议使用MoliveSvgaConstant定义的常量";
    }

    @Override
    public String buildErrorString(Object... infos) {
        return "提示使用 MoliveSvgaConstant 常量定义的本地SVGA资源建议使用MoliveSvgaConstant定义的常量, 方便对本地SVGA资源进行管理";
    }

    @Override
    public void visitJavaElement(PsiElement element) {
        if (element instanceof PsiIdentifierImpl) {
            if (element.getText().equals("startSVGAAnim")) {
                PsiElement parent = element.getParent().getParent();
                if (parent instanceof PsiMethodCallExpressionImpl) {
                    PsiMethodCallExpressionImpl methodCallExpression = (PsiMethodCallExpressionImpl) parent;
                    PsiExpressionList argumentList = methodCallExpression.getArgumentList();
                    PsiElement[] children = argumentList.getChildren();
                    if (!children[1].getText().contains("MoliveSvgaConstant")) {
                        registerProblem(parent);
                    }
                }
            }

        }
    }

    @Override
    public void visitKotlinElement(PsiElement element) {
        if (element.getClass().getSimpleName().equals("KtNameReferenceExpression")) {
            if (element.getText().equals("startSVGAAnim")) {
                PsiElement parent = element.getParent();
                if (parent.getClass().getSimpleName().equals("KtCallExpression")) {
                    PsiElement lastChild = parent.getLastChild();
                    if (lastChild.getClass().getSimpleName().equals("KtValueArgumentList")) {
                        PsiElement[] children = lastChild.getChildren();
                        if (children.length >= 2) {
                            PsiElement child = children[1];
                            if (!child.getText().contains("MoliveSvgaConstant")) {
                                registerProblem(parent);
                            }
                        }
                    }
                }
            }
        }
    }

}
