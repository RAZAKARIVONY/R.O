
package application;

import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import java.awt.*;

/**
 *
 * @author TOAVINA
 */
public class SceneConnectProvider implements ConnectProvider {
    
    private String source = null;
    private String target = null;
    
    private GraphScene.StringGraph scene;
    
    public SceneConnectProvider(GraphScene.StringGraph scene){
        this.scene=scene;
    }
    
    public boolean isSourceWidget(Widget sourceWidget) {
        Object object = scene.findObject(sourceWidget);
        source = scene.isNode(object) ? (String) object : null;
        return source != null;
    }
    
    public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
        Object object = scene.findObject(targetWidget);
        target = scene.isNode(object) ? (String) object : null;
        if (target != null)
            return ! source.equals(target) ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
        return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
    }
    
    public boolean hasCustomTargetWidgetResolver(Scene scene) {
        return false;
    }
    
    public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
        return null;
    }
    
    public void createConnection(Widget sourceWidget, Widget targetWidget) {
        String edge = "edge" + MyGraphScene.edgeCounter ++;
        scene.addEdge(edge);
        scene.setEdgeSource(edge, source);
        scene.setEdgeTarget(edge, target);
        scene.validate();
    }
    
}
