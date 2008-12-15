package intellij.mark;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class DelegatingRangeMarkSelectionActionTest extends MarkTestCase {

    public void testShouldDelegateAfterSettingSelectionToMarkRange() {
        Editor editor = createEditorWithText("01234567890123456789");
        CaretModel caretModel = editor.getCaretModel();
        caretModel.moveToOffset(5);

        ActionManager actionManager = ActionManager.getInstance();

        FakeAction fakeAction = new FakeAction();
        DelegatingRangeMarkSelectionAction delegatingAction = new DelegatingRangeMarkSelectionAction(fakeAction);
        actionManager.registerAction("intellij.mark.test.delegated.fake", delegatingAction);

        MarkManager markManager = getMarkManager();
        markManager.setMark(editor);

        caretModel.moveToOffset(15);

        invokeActionInEditor(editor, "intellij.mark.test.delegated.fake");
        assertEquals("5678901234", editor.getSelectionModel().getSelectedText());
        assertTrue(fakeAction.actionWasPerformed);
        assertEquals("Fake Action", delegatingAction.getTemplatePresentation().getText());
    }

    public static class FakeAction extends AnAction {
        public boolean actionWasPerformed;

        public FakeAction() {
            super("Fake Action");
        }

        public void actionPerformed(AnActionEvent anActionEvent) {
            actionWasPerformed = true;
        }
    }

}
