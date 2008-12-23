package intellij.mark;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.CaretModel;

import java.util.Map;

public class ExchangePointAndMarkActionTest extends MarkTestCase {

    public void testShouldSetMark() {
        Editor editor = createEditorWithText("This is my document");

        CaretModel model = editor.getCaretModel();
        model.moveToOffset(4);

        assertEquals(4, model.getOffset());

        invokeActionInEditor(editor, "intellij.mark.SetMarkAction");
        model.moveToOffset(10);

        invokeActionInEditor(editor, "intellij.mark.ExchangePointAndMarkAction");

        MarkManager markManager = getMarkManager();
        Map<Editor,MarkCaretListener> editorMarks = markManager.getEditorMarks();
        MarkCaretListener markCaretListener = editorMarks.get(editor);
        assertNotNull(markCaretListener);
        assertEquals(10, markCaretListener.getOriginalOffset());
        assertEquals(4, markCaretListener.getCurrentOffset());
        assertEquals(4, editor.getCaretModel().getOffset());
    }

}
