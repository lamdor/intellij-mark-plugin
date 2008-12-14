package intellij.mark;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;
import com.intellij.testFramework.LightIdeaTestCase;
import com.intellij.ide.DataManager;

import java.util.Map;

public class SetMarkActionTest extends MarkTestCase {

    public void testShouldSetMark() {
        Editor editor = createEditorWithText("This is my document");

        CaretModel model = editor.getCaretModel();
        model.moveToOffset(4);

        assertEquals(4, model.getOffset());

        ActionManager actionManager = ActionManager.getInstance();
        EditorAction editorAction = (EditorAction) actionManager.getAction("SetMark");
        editorAction.actionPerformed(editor, DataManager.getInstance().getDataContext());
        model.moveToOffset(10);

        Application application = ApplicationManager.getApplication();
        MarkManager markManager = application.getComponent(MarkManager.class);
        Map<Editor,MarkCaretListener> editorMarks = markManager.getEditorMarks();
        MarkCaretListener markCaretListener = editorMarks.get(editor);
        assertNotNull(markCaretListener);
        assertEquals(4, markCaretListener.getOriginalOffset());
        assertEquals(10, markCaretListener.getCurrentOffset());
    }

}
