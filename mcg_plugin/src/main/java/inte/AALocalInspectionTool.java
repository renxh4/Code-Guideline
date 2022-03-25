package inte;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.siyeh.ig.InspectionGadgetsFix;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public abstract class AALocalInspectionTool extends LocalInspectionTool {

    private ProblemsHolder holder;

    @Override
    public boolean runForWholeFile() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "com";
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Override
    public boolean isSuppressedFor(@NotNull PsiElement element) {
        return false;
    }

    //每个element回调，在这里判断规则
    public abstract void visitJavaElement(PsiElement element);

    public abstract void visitKotlinElement(PsiElement element);


    public abstract String buildErrorString(Object... infos);


    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        this.holder = holder;
        return new PsiElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                if (element == null) return;
                if (element.getLanguage().getDisplayName().equals("Java")) {
                    AALocalInspectionTool.this.visitJavaElement(element);
                }

                if (element.getLanguage().getDisplayName().equals("Kotlin")) {
                    AALocalInspectionTool.this.visitKotlinElement(element);
                }
            }
        };
    }

    public void registerProblem(PsiElement element) {
        holder.registerProblem(element, buildErrorString(), ProblemHighlightType.GENERIC_ERROR_OR_WARNING, InspectionGadgetsFix.EMPTY_ARRAY);
    }

//    @NotNull
//    @Override
//    public String getShortName() {
//        return "shortname";
//    }
}
