
package application;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.graph.GraphScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.InplaceEditorProvider;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.LabelWidget;

/**
 *
 * @author TOAVINA
 */
public class EdgeMenu implements PopupMenuProvider, ActionListener {
    
    private static final String ADD_REMOVE_CP_ACTION = "addRemoveCPAction"; // NOI18N
    //private static final String DELETE_ALL_CP_ACTION = "deleteAllCPAction"; // NOI18N
    private static final String DELETE_TRANSITION = "deleteTransition"; // NOI18N
    private static final String EDIT_EDGE_VALUE = "editEdgeValue";

    private GraphScene.StringGraph scene;

    private JPopupMenu menu;
    private ConnectionWidget edge;
    private Point point;
    
    private WidgetAction inplaceEditorAction = ActionFactory.createInplaceEditorAction (new EdgeMenu.LabelTextFieldEditor ());

    public EdgeMenu(GraphScene.StringGraph scene) {
        this.scene = scene;
        menu = new JPopupMenu("Transition Menu");
        JMenuItem item;
        
        item = new JMenuItem("Edit Cost Value");
        item.setActionCommand(EDIT_EDGE_VALUE);
        item.addActionListener(this);
        menu.add(item);
        
        menu.addSeparator();

        item = new JMenuItem("Delete Transition");
        item.setActionCommand(DELETE_TRANSITION);
        item.addActionListener(this);
        menu.add(item);

    }
    
    public JPopupMenu getPopupMenu(Widget widget, Point point){
        if (widget instanceof ConnectionWidget) {
            this.edge = (ConnectionWidget) widget;
            this.point=point;
            return menu;
        }
        return null;
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(ADD_REMOVE_CP_ACTION)) {
            addRemoveControlPoint(point);
        }else if(e.getActionCommand().equals(EDIT_EDGE_VALUE)){
            ConnectionWidget connection = (ConnectionWidget)this.scene.findWidget((String) scene.findObject(edge));
            InplaceEditorProvider.EditorController inplaceEditorController = ActionFactory.getInplaceEditorController (inplaceEditorAction);  
            for (Widget label : connection.getChildren()){
                inplaceEditorController.openEditor (label);
            }
        }else if(e.getActionCommand().equals(DELETE_TRANSITION)) {
            scene.removeEdge ((String) scene.findObject (edge));
        }
    }
    
    private void addRemoveControlPoint (Point localLocation) {
        ArrayList<Point> list = new ArrayList<Point> (edge.getControlPoints());
        double createSensitivity=1.00, deleteSensitivity=5.00;
            if(!removeControlPoint(localLocation,list,deleteSensitivity)){
                Point exPoint=null;int index=0;
                for (Point elem : list) {
                    if(exPoint!=null){
                        Line2D l2d=new Line2D.Double(exPoint,elem);
                        if(l2d.ptLineDist(localLocation)<createSensitivity){
                            list.add(index,localLocation);
                            break;
                        }
                    }
                    exPoint=elem;index++;
                }
            }
            edge.setControlPoints(list,false);
    }
    
    private boolean removeControlPoint(Point point, ArrayList<Point> list, double deleteSensitivity){
        for (Point elem : list) {
            if(elem.distance(point)<deleteSensitivity){
                list.remove(elem);
                return true;
            }
        }
        return false;
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
