package intellij.mark;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.testFramework.LightIdeaTestCase;

public class SetMarkActionTest extends LightIdeaTestCase {

    public void testShouldSetMark() {
        ActionManager actionManager = ActionManager.getInstance();
        AnAction anAction = actionManager.getAction("SetMark");
        anAction.actionPerformed(null);
    }

}
