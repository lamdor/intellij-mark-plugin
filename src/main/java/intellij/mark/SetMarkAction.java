package intellij.mark;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.Editor;


public class SetMarkAction extends EditorAction {

    public SetMarkAction() {
        super(new EditorActionHandler() {
            public void execute(Editor editor, DataContext dataContext) {
                Application application = ApplicationManager.getApplication();
                MarkManager markManager = application.getComponent(MarkManager.class);
                markManager.setMark(editor);                
            }
        });
    }


}
