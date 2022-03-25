package utils;

import com.intellij.psi.PsiElement;

public class Utils {
    public static void printSingle(PsiElement psiElement) {
        Log.start();
        Log.d("class=", psiElement.getClass() + "");
        Log.d("text = ", psiElement.getText() + "");
        Log.d("language = ", psiElement.getLanguage() + "");
        Log.end();
    }

    public static void printChild(String msg, PsiElement psiElement) {
        Log.start(msg + "开始");
        Log.d("class=", psiElement.getClass() + "");
        Log.d("text = ", psiElement.getText() + "");
        Log.d("language = ", psiElement.getLanguage() + "");
        for (int i = 0; i < psiElement.getChildren().length; i++) {
            printChild("父类 = " + psiElement.getClass(), psiElement.getChildren()[i]);
        }

        Log.end(msg + "结束");
    }

    public static void printParent(String msg, PsiElement psiElement) {
        if (!psiElement.getLanguage().getDisplayName().equals("Java")||!psiElement.getLanguage().getDisplayName().equals("Kotlin")){
            return;
        }
        Log.start(msg + "开始");
        Log.d("class=", psiElement.getClass() + "");
        Log.d("text = ", psiElement.getText() + "");

        printParent("子类 = " + psiElement.getClass(), psiElement.getParent());


        Log.end(msg + "结束");
    }

    public static void printParent1(String msg, PsiElement psiElement) {
        Log.start(msg + "开始");
        Log.d("class=", psiElement.getClass() + "");
        Log.d("text = ", psiElement.getText() + "");


        Log.d("super class=", psiElement.getParent().getClass() + "");
        Log.d("super text = ", psiElement.getParent().getText() + "");

        Log.end(msg + "结束");
    }

}
