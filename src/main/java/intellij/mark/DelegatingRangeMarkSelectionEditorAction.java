package intellij.mark;

import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.ide.DataManager;

public abstract class DelegatingRangeMarkSelectionEditorAction extends AnAction {
    private String actionToPerformAfterMarkRangeSet;

    public DelegatingRangeMarkSelectionEditorAction(String actionToPerformAfterMarkRangeSet) {
        this.actionToPerformAfterMarkRangeSet = actionToPerformAfterMarkRangeSet;
    }

    public void actionPerformed(AnActionEvent anActionEvent) {
        Application application = ApplicationManager.getApplication();
        MarkManager markManager = application.getComponent(MarkManager.class);
        Editor editor = PlatformDataKeys.EDITOR.getData(anActionEvent.getDataContext());
        markManager.setSelectionToMarkRange(editor);

        ActionManager actionManager = ActionManager.getInstance();
        AnAction delegatingAction = actionManager.getAction(actionToPerformAfterMarkRangeSet);
        delegatingAction.actionPerformed(anActionEvent);
    }
}
