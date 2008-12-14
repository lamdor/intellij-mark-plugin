package intellij.mark;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;
import com.intellij.ide.DataManager;

import java.util.Map;

public class SetMarkActionTest extends MarkTestCase {

    public void testShouldSetMark() {
        Editor editor = createEditorWithText("This is my document");

        CaretModel model = editor.getCaretModel();
        model.moveToOffset(4);

        assertEquals(4, model.getOffset());

        invokeActionInEditor(editor, "intellij.mark.SetMarkAction");
        model.moveToOffset(10);

        MarkManager markManager = getMarkManager();
        Map<Editor,MarkCaretListener> editorMarks = markManager.getEditorMarks();
        MarkCaretListener markCaretListener = editorMarks.get(editor);
        assertNotNull(markCaretListener);
        assertEquals(4, markCaretListener.getOriginalOffset());
        assertEquals(10, markCaretListener.getCurrentOffset());
    }

}
