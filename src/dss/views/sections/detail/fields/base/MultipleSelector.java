package dss.views.sections.detail.fields.base;

import dss.Developer;
import dss.views.base.Widget;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

@Developer({
    Developer.Value.SEBASTIAN,
})
public class MultipleSelector<T> extends Widget implements Initializable {

    private static class ItemCheckBox<T> extends CheckBox {

        private static final double WIDTH = 100;

        private T item;

        public ItemCheckBox(MultipleSelector<T> selector, T item) {
            super(item.toString());

            setPrefWidth(WIDTH);

            this.item = item;

            setOnAction((event) -> {
                if (isSelected()) {
                    selector.addItem(item);
                } else {
                    selector.removeItem(item);
                }
            });
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }
    }

    @FXML
    FlowPane container;

    private Set<T> selected = new HashSet<>();

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
    }

    public void setItems(T[] items) {
        ObservableList<Node> children = container.getChildren();
        children.clear();
        for (T item : items) {
            children.add(new ItemCheckBox<>(this, item));
        }
    }

    protected void addItem(T item) {
        selected.add(item);
    }

    protected void removeItem(T item) {
        selected.remove(item);
    }

    public Set<T> getSelectedItems() {
        return selected;
    }

    public void setSelectedItems(Set<T> items) {
        this.selected = items;

        ObservableList<Node> children = container.getChildren();
        for (Node node : children) {
            ItemCheckBox box = (ItemCheckBox) node;
            box.setSelected(items.contains(box.getItem()));
        }
    }
}
