package intellij.mark;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;


public class SetMarkAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        Application application = ApplicationManager.getApplication();
        MarkManager markManager = application.getComponent(MarkManager.class);
        markManager.setMark();
    }
}
