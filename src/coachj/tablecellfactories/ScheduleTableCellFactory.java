package coachj.tablecellfactories;

import coachj.ingame.InGamePlayer;
import coachj.structures.ScheduleGame;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Formats cells in schedule table views
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @param <S>
 * @param <T>
 * @date 03/09/2013
 */
public class ScheduleTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    @Override
    public TableCell<S, T> call(TableColumn<S, T> p) {
        TableCell<S, T> cell;
        cell = new TableCell<S, T>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                // CSS Styles
                String win = "win";
                String loss = "loss";
                String notPlayed = "table-cell";
                String cssStyle = "table-cell";

                ScheduleGame game = null;
                if (getTableRow() != null) {
                    game = (ScheduleGame) getTableRow().getItem();
                }

                //Remove all previously assigned CSS styles from the cell.
                /*getStyleClass().remove(loss);
                getStyleClass().remove(win);
                getStyleClass().remove(notPlayed);*/

                super.updateItem((T) item, empty);

                //Determine how to format the cell based on the status of the container.
                if (game == null) {
                    cssStyle = "table-cell";
                } else if (game.getResult().equalsIgnoreCase("---")) {
                    cssStyle = notPlayed;
                } else if (game.getResult(). equalsIgnoreCase("L")) {
                    cssStyle = loss;
                } else {
                    cssStyle = win;
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

} // end ScheduleTableCellFactory
