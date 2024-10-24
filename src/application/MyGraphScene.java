package application;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Utilities;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Collections;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;


/**
 * @author Alex
 */
public class MyGraphScene extends GraphScene.StringGraph {
    
    private static final Image IMAGE = Utilities.loadImage("application/resources/node.png"); // NOI18N
    
    public static long nodeCounter = 1;
    public static long edgeCounter = 1;
    
    private LayerWidget mainLayer = new LayerWidget(this);
    private LayerWidget connectionLayer = new LayerWidget(this);
    private LayerWidget interractionLayer = new LayerWidget(this);
    private LayerWidget backgroundLayer = new LayerWidget(this);
    private Router router = RouterFactory.createFreeRouter();
    
    private NodeMenu nodeMenu=new NodeMenu(this);
    private EdgeMenu edgeMenu=new EdgeMenu(this);
    
    private WidgetAction moveAction = ActionFactory.createMoveAction();
    private WidgetAction connectAction = ActionFactory.createConnectAction(interractionLayer, new SceneConnectProvider(this));
    private WidgetAction connectActionCtr = ActionFactory.createExtendedConnectAction(interractionLayer, new SceneConnectProvider(this));
    private WidgetAction reconnectAction = ActionFactory.createReconnectAction(new SceneReconnectProvider(this));
    private WidgetAction moveControlPointAction = ActionFactory.createFreeMoveControlPointAction();
    private WidgetAction selectAction = ActionFactory.createSelectAction(new ObjectSelectProvider());
    private WidgetAction rectangularSelectAction = ActionFactory.createRectangularSelectAction(this, backgroundLayer);
    private WidgetAction zoomAction = ActionFactory.createZoomAction();
    private WidgetAction editorAction = ActionFactory.createInplaceEditorAction (new LabelTextFieldEditor ());
    private WidgetAction nodePopupMenuAction = ActionFactory.createPopupMenuAction(nodeMenu);
    private WidgetAction edgePopupMenuAction = ActionFactory.createPopupMenuAction(edgeMenu);
    private WidgetAction mainPopupMenuAction = ActionFactory.createPopupMenuAction(new SceneMainMenu(this));
    
    private SceneCreateAction createAction = new SceneCreateAction();
    private SceneDeleteNodeAction deleteNodeAction = new SceneDeleteNodeAction();
    private SceneDeleteEdgeAction deleteEdgeAction = new SceneDeleteEdgeAction();
    
    
    public MyGraphScene() {
        addChild(mainLayer);
        addChild(connectionLayer);
        addChild(interractionLayer);
        getActions().addAction(rectangularSelectAction);
        //getActions().addAction(mainPopupMenuAction);      
        //getActions().addAction(createAction);
        initGrids();
    }
    
    protected Widget attachNodeWidget(String node) {
        IconNodeWidget label = new IconNodeWidget(this);
        label.setLabel(node);        
        label.setImage(IMAGE);
        label.getActions().addAction(createSelectAction());
        label.getActions().addAction(createObjectHoverAction());
        //label.getActions().addAction(connectActionCtr);
        label.getActions().addAction(moveAction);
        //label.getActions().addAction(nodePopupMenuAction);        
        //label.getLabelWidget ().getActions ().addAction (editorAction);
        mainLayer.addChild(label);
        return label;
    }
    
    protected Widget attachEdgeWidget(String edge) {
        ConnectionWidget connection = new ConnectionWidget(this);
        connection.setRouter(router);
        connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        //connection.setControlPointShape(PointShape.SQUARE_FILLED_BIG);
        connection.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
        connection.getActions().addAction(createObjectHoverAction());
        connection.getActions().addAction(createSelectAction());  
        //connection.getActions().addAction(reconnectAction);
        //connection.getActions().addAction(edgePopupMenuAction);   
        connectionLayer.addChild(connection);
                
        LabelWidget label = new LabelWidget (this, edge.split(":")[2]);
        label.setOpaque (true);  
        label.getActions().addAction(createObjectHoverAction());
        
        connection.addChild (label);        
        connection.setConstraint(label, LayoutFactory.ConnectionWidgetLayoutAlignment.CENTER, 0.5f);
        
        return connection;
    }
    
    protected void attachEdgeSourceAnchor(String edge, String oldSourceNode, String sourceNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget sourceNodeWidget = findWidget (sourceNode);
        widget.setSourceAnchor(sourceNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(sourceNodeWidget, true) : null);
    }
    
    protected void attachEdgeTargetAnchor(String edge, String oldTargetNode, String targetNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget targetNodeWidget = findWidget (targetNode);
        widget.setTargetAnchor(targetNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(targetNodeWidget, true) : null);
    }
    
    private class ObjectSelectProvider implements SelectProvider {
        
        public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }
        
        public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }
        
        public void select(Widget widget, Point localLocation, boolean invertSelection) {
            Object object = findObject(widget);
            
            if (object != null) {
                if (getSelectedObjects().contains(object))return;
                userSelectionSuggested(Collections.singleton(object), invertSelection);
            } else
                userSelectionSuggested(Collections.emptySet(), invertSelection);
        }
    }
    
    public void initGrids(){
        Image sourceImage = Utilities.loadImage("application/resources/grid.png"); // NOI18N
        int width = sourceImage.getWidth(null);
        int height = sourceImage.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(sourceImage, 0, 0, null);
        graphics.dispose();
        TexturePaint PAINT_BACKGROUND = new TexturePaint(image, new Rectangle(0, 0, width, height));
        setBackground(PAINT_BACKGROUND);
        repaint();
        revalidate(false);
        validate();
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
    
    public class SceneDeleteNodeAction extends WidgetAction.Adapter {

        public WidgetAction.State mouseClicked (Widget widget, WidgetAction.WidgetMouseEvent event) {
            if (event.getClickCount () == 1)
                if (event.getButton () == MouseEvent.BUTTON1) {                
                    removeNodeWithEdges((String)findObject(widget));
                    validate(); 
                    return WidgetAction.State.CONSUMED;
                }
            return WidgetAction.State.REJECTED;
        }

    }    
    
     public class SceneDeleteEdgeAction extends WidgetAction.Adapter {

        public WidgetAction.State mouseClicked (Widget widget, WidgetAction.WidgetMouseEvent event) {
            if (event.getClickCount () == 1)
                if (event.getButton () == MouseEvent.BUTTON1) {                
                    removeEdge((String)findObject(widget));
                    validate();  
                    return WidgetAction.State.CONSUMED;
                }
            return WidgetAction.State.REJECTED;
        }

    } 
    
    private class SceneCreateAction extends WidgetAction.Adapter {        
        
        public WidgetAction.State mouseClicked (Widget widget, WidgetAction.WidgetMouseEvent event) {
            if (event.getClickCount () == 2)
                if (event.getButton () == MouseEvent.BUTTON1) {
                    String hm = "Node" + (nodeCounter++);
                    Widget newNode = addNode(hm);
                    getSceneAnimator().animatePreferredLocation(newNode, widget.convertLocalToScene (event.getPoint ()));
                    validate();
                    return WidgetAction.State.CONSUMED;
                }
            return WidgetAction.State.REJECTED;
        }

    }
    
    /* getters */

    public static Image getIMAGE() {
        return IMAGE;
    }

    public static long getNodeCounter() {
        return nodeCounter;
    }

    public static long getEdgeCounter() {
        return edgeCounter;
    }

    public LayerWidget getMainLayer() {
        return mainLayer;
    }

    public LayerWidget getConnectionLayer() {
        return connectionLayer;
    }

    public LayerWidget getInterractionLayer() {
        return interractionLayer;
    }

    public LayerWidget getBackgroundLayer() {
        return backgroundLayer;
    }

    public Router getRouter() {
        return router;
    }

    public NodeMenu getNodeMenu() {
        return nodeMenu;
    }

    public EdgeMenu getEdgeMenu() {
        return edgeMenu;
    }

    public WidgetAction getMoveAction() {
        return moveAction;
    }

    public WidgetAction getConnectAction() {
        return connectAction;
    }

    public WidgetAction getConnectActionCtr() {
        return connectActionCtr;
    }

    public WidgetAction getReconnectAction() {
        return reconnectAction;
    }

    public WidgetAction getMoveControlPointAction() {
        return moveControlPointAction;
    }

    public WidgetAction getSelectAction() {
        return selectAction;
    }

    public WidgetAction getRectangularSelectAction() {
        return rectangularSelectAction;
    }

    public WidgetAction getZoomAction() {
        return zoomAction;
    }

    public WidgetAction getNodePopupMenuAction() {
        return nodePopupMenuAction;
    }

    public WidgetAction getEdgePopupMenuAction() {
        return edgePopupMenuAction;
    }

    public WidgetAction getMainPopupMenuAction() {
        return mainPopupMenuAction;
    }

    public SceneCreateAction getCreateAction() {
        return createAction;
    }

    public SceneDeleteNodeAction getDeleteNodeAction() {
        return deleteNodeAction;
    }

    public SceneDeleteEdgeAction getDeleteEdgeAction() {
        return deleteEdgeAction;
    }

    public WidgetAction getEditorAction() {
        return editorAction;
    }    
}
