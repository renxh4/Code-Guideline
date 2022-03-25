package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class P3cIcons {
    private P3cIcons() {
        throw new AssertionError("icons.P3cIcons"
                + " instances for you!");
    }

    public static final Icon ANALYSIS_ACTION = IconLoader.getIcon("/icons/ali-ide-run.png");

    public static final Icon PROJECT_INSPECTION_ON = IconLoader.getIcon("/icons/qiyong.png");
    public static final Icon PROJECT_INSPECTION_OFF = IconLoader.getIcon("/icons/tingyong.png");
    public static final Icon LANGUAGE = IconLoader.getIcon("/icons/language.png");
}
