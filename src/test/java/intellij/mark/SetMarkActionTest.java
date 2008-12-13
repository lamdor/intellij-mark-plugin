package intellij.mark;

import org.junit.Test;

public class SetMarkActionTest {

    @Test
    public void shouldSetMark() {
        SetMarkAction action = new SetMarkAction();
        action.actionPerformed(null);
    }
}
