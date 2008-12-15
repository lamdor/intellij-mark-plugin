package intellij.mark;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.SelectionModel;

import java.util.Map;

public class MarkManagerTest extends MarkTestCase {
    private MarkManager markManager;
    private Editor editor;

    public void testShouldSetMark() {
        Editor editor = createEditorWithText("Some text");
        CaretModel caretModel = editor.getCaretModel();
        caretModel.moveToOffset(4);

        markManager.setMark(editor);

        caretModel.moveToOffset(6);

        Map<Editor,MarkCaretListener> editorMarks = markManager.getEditorMarks();
        assertNotNull(editorMarks);
        MarkCaretListener listener = editorMarks.get(editor);
        assertNotNull(listener);
        assertEquals(4, listener.getOriginalOffset());
        assertEquals(6, listener.getCurrentOffset());
    }

    public void testShouldResetMarkIfMarkAlreadySet() {
        Editor editor = createEditorWithText("Some text");
        CaretModel caretModel = editor.getCaretModel();
        caretModel.moveToOffset(4);

        markManager.setMark(editor);
        caretModel.moveToOffset(6);
        markManager.setMark(editor);
        caretModel.moveToOffset(8);

        Map<Editor,MarkCaretListener> editorMarks = markManager.getEditorMarks();
        assertNotNull(editorMarks);
        MarkCaretListener listener = editorMarks.get(editor);
        assertNotNull(listener);
        assertEquals(6, listener.getOriginalOffset());
        assertEquals(8, listener.getCurrentOffset());
    }


    public void testShouldSetSelectionToMarkRange() {
        assumeClipboardIs("This is previous text");
        Editor editor = createEditorWithText("012345678901234567890123456789");
        CaretModel caretModel = editor.getCaretModel();
        caretModel.moveToOffset(5);

        markManager.setMark(editor);

        caretModel.moveToOffset(15);

        markManager.setSelectionToMarkRange(editor);

        SelectionModel selectionModel = editor.getSelectionModel();
        assertEquals("5678901234", selectionModel.getSelectedText());
    }

    public void testShouldNotDoAnythingIfThereAlreadyINoMark() {
        assumeClipboardIs("This is previous text");
        Editor editor = createEditorWithText("012345678901234567890123456789");

        SelectionModel selectionModel = editor.getSelectionModel();
        selectionModel.setSelection(4, 14);

        markManager.setSelectionToMarkRange(editor);

        assertEquals("4567890123", selectionModel.getSelectedText());

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        markManager = getMarkManager();
    }
}
