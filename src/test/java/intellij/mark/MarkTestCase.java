package intellij.mark;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.testFramework.LightIdeaTestCase;

import java.util.List;
import java.util.ArrayList;

public class MarkTestCase extends LightIdeaTestCase {
    private EditorFactory editorFactory;
    private List<Editor> openEditors;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        editorFactory = EditorFactory.getInstance();
        openEditors = new ArrayList<Editor>();
    }

    @Override
    protected void tearDown() throws Exception {
        for (Editor editor : openEditors) {
            editorFactory.releaseEditor(editor);
        }
    }

    protected Editor createEditorWithText(String text) {
        Document document = editorFactory.createDocument(text);
        Editor editor = editorFactory.createEditor(document);
        openEditors.add(editor);
        return editor;
    }

}
