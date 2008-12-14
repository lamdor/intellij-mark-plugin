package intellij.mark;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.CaretModel;
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

    public void testShouldCopyTextFromMarkRange() throws UnsupportedFlavorException, IOException {
        CopyPasteManager.getInstance().setContents(new StringSelection("This is previous text"));
        Editor editor = createEditorWithText("012345678901234567890123456789");
        CaretModel caretModel = editor.getCaretModel();
        caretModel.moveToOffset(5);

        markManager.setMark(editor);

        caretModel.moveToOffset(15);

        markManager.copyMarkRange(editor);

        Transferable contents = CopyPasteManager.getInstance().getContents();
        String copiedData = (String) contents.getTransferData(DataFlavor.stringFlavor);

        assertEquals("5678901234", copiedData);
    }

    public void testShouldJustCopyTextIfEditorHasNoMark() {
        //todo
    }

    protected void setUp() throws Exception {
        super.setUp();
        Application application = ApplicationManager.getApplication();
        markManager = application.getComponent(MarkManager.class);
    }
}
