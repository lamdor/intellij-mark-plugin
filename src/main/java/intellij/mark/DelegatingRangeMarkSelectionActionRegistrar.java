package intellij.mark;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class DelegatingRangeMarkSelectionActionRegistrar implements ApplicationComponent {
    private List<String> actionsToDelegate;
    private ActionManager actionManager;

    public DelegatingRangeMarkSelectionActionRegistrar(ActionManager actionManager) {
        this.actionManager = actionManager;
        actionsToDelegate = new ArrayList<String>();
        actionsToDelegate.add("$Copy");
    }

    public void initComponent() {
        for(String actionToDelegate : actionsToDelegate) {
            AnAction action = actionManager.getAction(actionToDelegate);
            DelegatingRangeMarkSelectionAction markSelectionAction = new DelegatingRangeMarkSelectionAction(action);

            actionManager.unregisterAction(actionToDelegate);
            actionManager.registerAction(actionToDelegate, markSelectionAction);
        }
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "DelegatingRangeMarkSelectionActionRegistrar";
    }
}
