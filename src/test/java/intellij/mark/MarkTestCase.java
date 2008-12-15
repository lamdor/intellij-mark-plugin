package intellij.mark;

import com.intellij.ide.DataManager;
import com.intellij.ide.CopyProvider;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.testFramework.LightIdeaTestCase;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarkTestCase extends LightIdeaTestCase {
    private EditorFactory editorFactory;
    private List<Editor> openEditors;
    private Editor currentEditor;

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
        Document document = getDocument(createFile("test.txt", text));
        Editor editor = editorFactory.createEditor(document);
        openEditors.add(editor);
        return editor;
    }

    protected void invokeActionInEditor(Editor editor, String actionName) {
        this.currentEditor = editor;
        ActionManager actionManager = ActionManager.getInstance();
        AnAction editorAction = actionManager.getAction(actionName);
        Application application = ApplicationManager.getApplication();

        DataContext dataContext = DataManager.getInstance().getDataContext();

        editorAction.actionPerformed(new AnActionEvent(
                null,
                dataContext,
                "",
                editorAction.getTemplatePresentation(),
                ActionManager.getInstance(),
                0
        )
        );
    }

    @Override
    public Object getData(String s) {
        if ("editor".equals(s)) {
            return currentEditor;
        }
        return super.getData(s);
    }

    protected MarkManager getMarkManager() {
        Application application = ApplicationManager.getApplication();
        return application.getComponent(MarkManager.class);
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
