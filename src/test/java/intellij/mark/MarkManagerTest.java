package intellij.mark;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.CaretModel;

import java.util.Map;

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

    protected void setUp() throws Exception {
        super.setUp();
        Application application = ApplicationManager.getApplication();
        markManager = application.getComponent(MarkManager.class);
    }
}
