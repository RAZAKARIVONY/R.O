
package application;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.InplaceEditorProvider;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 *
 * @author TOAVINA
 */
public class NodeMenu implements PopupMenuProvider, ActionListener {
    
    private static final String DELETE_NODE_ACTION = "deleteNodeAction"; // NOI18N
    private static final String EDIT_NODE_VALUE = "editNodeValue";
    
    private JPopupMenu menu;
    private IconNodeWidget node;

    private Point point;
    private GraphScene.StringGraph scene;
    
    private WidgetAction inplaceEditorAction = ActionFactory.createInplaceEditorAction (new NodeMenu.LabelTextFieldEditor ());
    
    public NodeMenu(GraphScene.StringGraph scene) {
        this.scene=scene;
        menu = new JPopupMenu("Node Menu");
        JMenuItem item;
        
        item = new JMenuItem("Edit Node Value");
        item.setActionCommand(EDIT_NODE_VALUE);
        item.addActionListener(this);
        menu.add(item);
        
        menu.addSeparator();
        
        item = new JMenuItem("Delete Node");
        item.setActionCommand(DELETE_NODE_ACTION);
        item.addActionListener(this);        
        menu.add(item);
    }
    
    public JPopupMenu getPopupMenu(Widget widget,Point point){
        this.point=point;
        this.node=(IconNodeWidget)widget;
        return menu;
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(DELETE_NODE_ACTION)){
            scene.removeNodeWithEdges((String)scene.findObject (node));
            scene.validate();            
        }else if(e.getActionCommand().equals(EDIT_NODE_VALUE)){
            IconNodeWidget label = (IconNodeWidget)this.scene.findWidget((String) scene.findObject(node));
            InplaceEditorProvider.EditorController inplaceEditorController = ActionFactory.getInplaceEditorController (inplaceEditorAction);            
            inplaceEditorController.openEditor (label.getLabelWidget());
        }
    }
    
    private class LabelTextFieldEditor implements TextFieldInplaceEditor {

        public boolean isEnabled (Widget widget) {
            return true;
        }

        public String getText (Widget widget) {
            return ((LabelWidget) widget).getLabel ();
        }

        public void setText (Widget widget, String text) {
            ((LabelWidget) widget).setLabel (text);
        }

    }
}
