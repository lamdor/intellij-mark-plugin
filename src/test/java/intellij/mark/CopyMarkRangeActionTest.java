package intellij.mark;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.CaretModel;

public class CopyMarkRangeActionTest extends MarkTestCase {

    public void testDummy() {
        assertTrue(true);
    }

    public void testShouldCopyMark() {
        assumeClipboardIs("This is previous text");
        Editor editor = createEditorWithText("01234567890123456789");
        CaretModel caretModel = editor.getCaretModel();
        caretModel.moveToOffset(5);

        MarkManager markManager = getMarkManager();
        markManager.setMark(editor);

        caretModel.moveToOffset(15);

        invokeActionInEditor(editor, "intellij.mark.CopyMarkRangeAction");
        assertEquals("5678901234", contentsOfClipboard());
    }

}
