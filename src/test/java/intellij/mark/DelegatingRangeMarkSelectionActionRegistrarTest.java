package intellij.mark;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.actionSystem.ActionManager;

public class DelegatingRangeMarkSelectionActionRegistrarTest extends MarkTestCase {
    private ActionManager actionManager;

    public void testRegisteredActions() {
        Application application = ApplicationManager.getApplication();
        DelegatingRangeMarkSelectionActionRegistrar registrar =
                application.getComponent(DelegatingRangeMarkSelectionActionRegistrar.class);
        assertNotNull(registrar);

        assertActionIsDelegated("Mark.Copy");
        assertActionIsDelegated("Mark.Cut");
        assertActionIsDelegated("EditorToggleColumnMode");
    }

    private void assertActionIsDelegated(String actionId) {
        assertTrue("Action " + actionId + " is not delegated",
                actionManager.getAction(actionId) instanceof DelegatingRangeMarkSelectionAction);
    }

    protected void setUp() throws Exception {
        super.setUp();
        actionManager = ActionManager.getInstance();
    }
}
