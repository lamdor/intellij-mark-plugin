package intellij.mark;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.LightIdeaTestCase;

public class SetMarkActionTest extends MarkTestCase {

    public void testShouldSetMark() {
        Editor editor = createEditorWithText("This is my document");

        CaretModel model = editor.getCaretModel();
        model.moveToOffset(4);

        assertEquals(4, model.getOffset());

        ActionManager actionManager = ActionManager.getInstance();
        AnAction anAction = actionManager.getAction("SetMark");
        anAction.actionPerformed(null);

        model.moveToOffset(10);

        SelectionModel selectionModel = editor.getSelectionModel();
        assertNotNull(selectionModel);
        assertNotNull(selectionModel.getSelectedText());
        assertEquals(" is my", selectionModel.getSelectedText());
        assertEquals(4, selectionModel.getSelectionStart());
        assertEquals(10, selectionModel.getSelectionEnd());
    }

}
