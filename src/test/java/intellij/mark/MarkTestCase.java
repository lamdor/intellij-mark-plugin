package intellij.mark;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.testFramework.LightIdeaTestCase;
import com.intellij.ide.DataManager;

import java.util.List;
import java.util.ArrayList;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

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

    protected void invokeActionInEditor(Editor editor, String actionName) {
        ActionManager actionManager = ActionManager.getInstance();
        EditorAction editorAction = (EditorAction) actionManager.getAction(actionName);
        editorAction.actionPerformed(editor, DataManager.getInstance().getDataContext());
    }

    protected MarkManager getMarkManager() {
        Application application = ApplicationManager.getApplication();
        MarkManager markManager = application.getComponent(MarkManager.class);
        return markManager;
    }

    protected String contentsOfClipboard() {
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

    protected void assumeClipboardIs(String data) {
        CopyPasteManager.getInstance().setContents(new StringSelection(data));
    }
}
