package intellij.mark;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.SelectionModel;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;


public class MarkManager implements ApplicationComponent {
    public Map<Editor, MarkCaretListener> getEditorMarks() {
        return editorMarks;
    }

    Map<Editor, MarkCaretListener> editorMarks;

    public MarkManager() {
        editorMarks = new HashMap<Editor, MarkCaretListener>();
    }

    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "MarkManager";
    }

    public void setMark() {
        EditorFactory editorFactory = EditorFactory.getInstance();
        Editor[] allEditors = editorFactory.getAllEditors();
        Editor editor = allEditors[0];
        CaretModel caretModel = editor.getCaretModel();
        SelectionModel selectionModel = editor.getSelectionModel();
        MarkCaretListener listener = new MarkCaretListener(caretModel);
        caretModel.addCaretListener(listener);
        editorMarks.put(editor, listener);
    }
}
