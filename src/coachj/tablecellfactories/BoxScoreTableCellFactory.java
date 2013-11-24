package coachj.tablecellfactories;

import coachj.ingame.InGamePlayer;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Formats cells in boxscores table views
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 03/09/2013
 */
public class BoxScoreTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    public BoxScoreTableCellFactory() {
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> p) {
        TableCell<S, T> cell = new TableCell<S, T>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                // CSS Styles
                String onTheCourt = "onTheCourt";
                String inTheBench = "inTheBench";
                String notPlayable = "notPlayable";
                String cssStyle;

                InGamePlayer player = null;
                if (getTableRow() != null) {
                    player = (InGamePlayer) getTableRow().getItem();
                }

                //Remove all previously assigned CSS styles from the cell.
                getStyleClass().remove(inTheBench);
                getStyleClass().remove(onTheCourt);
                getStyleClass().remove(notPlayable);

                super.updateItem((T) item, empty);

                //Determine how to format the cell based on the status of the container.
                if (player.isOnCourt()) {
                    cssStyle = onTheCourt;
                } else {
                    cssStyle = inTheBench;
                }

                if (player.isEjected() || !player.getBaseAttributes().getPlayable()) {
                    cssStyle = notPlayable;
                }

                //Set the CSS style on the cell and set the cell's text.
                getStyleClass().add(cssStyle);
                if (item != null) {
                    setText(item.toString());
                } else {
                    setText("");
                }
            }
        };
        return cell;
    }
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1E3D9607-0CEC-6DAA-6CD8-756A76DDF136]
    // </editor-fold> 
 
} // end class BoxScoreTableCellFactory
