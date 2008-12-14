package intellij.mark;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ide.CopyPasteManager;

import java.util.Map;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class MarkManagerTest extends MarkTestCase {
    private MarkManager markManager;

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

    public void testShouldCopyTextFromMarkRange() {
        assumeClipboardIs("This is previous text");
        Editor editor = createEditorWithText("012345678901234567890123456789");
        CaretModel caretModel = editor.getCaretModel();
        caretModel.moveToOffset(5);

        markManager.setMark(editor);

        caretModel.moveToOffset(15);

        markManager.copyMarkRange(editor);

        assertEquals("5678901234", contentsOfClipboard());
        assertNull(markManager.getEditorMarks().get(editor));
    }

    private String contentsOfClipboard() {
        Transferable contents = CopyPasteManager.getInstance().getContents();
        String copiedData = null;
        try {
            copiedData = (String) contents.getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return copiedData;
    }

    public void testShouldJustCopyTextIfEditorHasNoMark() {
        assumeClipboardIs("This is previous text");
        Editor editor = createEditorWithText("012345678901234567890123456789");

        SelectionModel selectionModel = editor.getSelectionModel();
        selectionModel.setSelection(4, 14);

        markManager.copyMarkRange(editor);

        assertEquals("4567890123", contentsOfClipboard());

    }

    private void assumeClipboardIs(String data) {
        CopyPasteManager.getInstance().setContents(new StringSelection(data));
    }

    protected void setUp() throws Exception {
        super.setUp();
        Application application = ApplicationManager.getApplication();
        markManager = application.getComponent(MarkManager.class);
    }
}
