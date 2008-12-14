package intellij.mark;

import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.SelectionModel;

public class MarkCaretListener implements CaretListener {
    private int originalOffset;
    private CaretModel caretModel;
    private SelectionModel selectionModel;

    public MarkCaretListener(CaretModel caretModel, SelectionModel selectionModel) {
        this.caretModel = caretModel;
        this.selectionModel = selectionModel;
        originalOffset = caretModel.getOffset();
    }

    public void caretPositionChanged(CaretEvent caretEvent) {
        selectionModel.setSelection(originalOffset, caretModel.getOffset());
    }
}
