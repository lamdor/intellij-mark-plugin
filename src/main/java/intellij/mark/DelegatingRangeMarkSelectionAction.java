package intellij.mark;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;

@SuppressWarnings({"ComponentNotRegistered"})
public class DelegatingRangeMarkSelectionAction extends AnAction {
    private AnAction delegatingAction;

    public DelegatingRangeMarkSelectionAction(AnAction delegatingAction) {
        this.delegatingAction = delegatingAction;
    }

    public void actionPerformed(AnActionEvent anActionEvent) {
        Application application = ApplicationManager.getApplication();
        MarkManager markManager = application.getComponent(MarkManager.class);
        Editor editor = PlatformDataKeys.EDITOR.getData(anActionEvent.getDataContext());
        markManager.setSelectionToMarkRange(editor);

        delegatingAction.actionPerformed(anActionEvent);
    }
}
