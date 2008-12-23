package intellij.mark;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;

public class MarkCaretListener implements CaretListener {
    private int originalOffset;
    private CaretModel caretModel;
    private int currentOffset;

    public MarkCaretListener(CaretModel caretModel) {
        this.caretModel = caretModel;
        originalOffset = caretModel.getOffset();
    }

    public int getOriginalOffset() {
        return originalOffset;
    }

    public int getCurrentOffset() {
        return currentOffset;
    }

    public void caretPositionChanged(CaretEvent caretEvent) {
        currentOffset = caretModel.getOffset();
    }

    public void exchange() {
        int orig = originalOffset;
        originalOffset = currentOffset;
        currentOffset = orig;
    }
}
