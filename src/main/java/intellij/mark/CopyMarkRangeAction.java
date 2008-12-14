package intellij.mark;

import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;

public class CopyMarkRangeAction extends EditorAction {
    protected CopyMarkRangeAction() {
        super(new EditorActionHandler() {
            public void execute(Editor editor, DataContext dataContext) {
                Application application = ApplicationManager.getApplication();
                MarkManager markManager = application.getComponent(MarkManager.class);
                markManager.copyMarkRange(editor);
            }
        } );
    }
}
