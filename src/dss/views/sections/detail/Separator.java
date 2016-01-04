package dss.views.sections.detail;

import dss.Developer;
import javafx.scene.layout.VBox;

@Developer({
    Developer.Value.SEBASTIAN,
})
public class Separator extends VBox {

    private static final String STYLE_CLASS = "separator";
    private static final String STYLE_CLASS_OUTER = "outer";
    private static final String STYLE_CLASS_INNER = "inner";

    public Separator() {
        super();

        getStyleClass().add(STYLE_CLASS);

        VBox outer = new VBox();
        VBox inner = new VBox();

        outer.getStyleClass().add(STYLE_CLASS_OUTER);
        inner.getStyleClass().add(STYLE_CLASS_INNER);

        getChildren().add(outer);
        outer.getChildren().add(inner);
    }
}
