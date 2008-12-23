package intellij.mark;

import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;

public class ExchangePointAndMarkAction extends EditorAction {
    protected ExchangePointAndMarkAction() {
        super(new EditorActionHandler() {
            public void execute(Editor editor, DataContext dataContext) {
                Application application = ApplicationManager.getApplication();
                MarkManager markManager = application.getComponent(MarkManager.class);
                markManager.exchangePointAndMark(editor);
            }
        });
    }
}
