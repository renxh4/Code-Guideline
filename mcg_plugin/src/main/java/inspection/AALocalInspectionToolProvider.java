package inspection;

import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;


public class AALocalInspectionToolProvider implements InspectionToolProvider {

    @NotNull
    @Override
    public Class<? extends LocalInspectionTool>[] getInspectionClasses() {
        return add();
    }

    public Class[] add(){
       return new Class[]{
               GlideInspection.class,
               ColorParseInspection.class,
               MoliveLogInspection.class,
               ThreadInspection.class,
               LogInspection.class,
//               SvgaInspection.class,
//               GsonInspection.class
       };
    }
}
