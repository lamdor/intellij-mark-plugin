package intellij.mark;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.extensions.PluginId;
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
        actionsToDelegate.add("$Cut");
        actionsToDelegate.add("EditorToggleColumnMode");
    }

    public void initComponent() {
        for (String actionToDelegate : actionsToDelegate) {
            AnAction action = actionManager.getAction(actionToDelegate);

            if (actionToDelegate.startsWith("$")) {
                DelegatingRangeMarkSelectionAction delegatingAction =
                        new DelegatingRangeMarkSelectionAction(action, "Mark " + action.getTemplatePresentation().getText());
                actionManager.registerAction("Mark." + actionToDelegate.replace("$", ""), delegatingAction,
                        PluginId.getId("Mark"));
            } else {
                DelegatingRangeMarkSelectionAction markSelectionAction = new DelegatingRangeMarkSelectionAction(action);
                actionManager.unregisterAction(actionToDelegate);
                actionManager.registerAction(actionToDelegate, markSelectionAction);
            }
        }
    }

    public void disposeComponent() {
        // nothing to do
    }

    @NotNull
    public String getComponentName() {
        return "DelegatingRangeMarkSelectionActionRegistrar";
    }
}
