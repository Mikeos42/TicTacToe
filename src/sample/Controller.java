package sample;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Controller {

    private Background schwarz = new Background(new BackgroundFill(new Color(0,0,0,0.75), CornerRadii.EMPTY, Insets.EMPTY));
    private Background rot = new Background(new BackgroundFill(new Color(1,0,0,0.75), CornerRadii.EMPTY, Insets.EMPTY));
    private Background weiss = new Background(new BackgroundFill(new Color(1,1,1,1), CornerRadii.EMPTY, Insets.EMPTY));
    private boolean turn = false; // Change player - set player turn
    private ArrayList<String> blockade = new ArrayList<>(); //Blockade to prevent clicking twice on a field
    public GridPane gpane;
    private boolean won = false;
    public Label lblWin;

    @FXML
    private void initialize() {
        Region rg;
        for (Node nd : gpane.getChildren()) {
            if (nd.getClass().getSimpleName().equalsIgnoreCase("region")) {
                rg = (Region) nd;
                rg.setBackground(weiss);
            }
        }
        if (!turn) {
            lblWin.setText("Red's turn");
        } else {
            lblWin.setText("Black's turn");
        }
        blockade.clear();
        won = false;
    }

    private int colour(String id) { //Return the value of Background
        Region rg = (Region) gpane.lookup("#" + id);
        if (rg.getBackground().equals(schwarz)) {
            return 3;
        } else if (rg.getBackground().equals(rot)) {
            return 4;
        } else {
            return 0;
        }
//        System.out.println(gpane.lookup("#" + id));
//        return 0;
    }
    @FXML
    void klick(MouseEvent event) {
        Region region = (Region) event.getSource(); // Get clicked part turn into id
        String id = region.getId();
        if (!blockade.contains(id) && !won) {
            //Set colour
            if (turn) {
                region.setBackground(schwarz);
                lblWin.setText("Red's turn");
            } else {
                region.setBackground(rot);
                lblWin.setText("Black's turn");
            }
            blockade.add(id);
            turn = !turn; // Change Colour
            //Check if won
            int r1 = colour("c00") * colour("c10") * colour("c20"),
                    r2 = colour("c01") * colour("c11") * colour("c21"),
                    r3 = colour("c02") * colour("c12") * colour("c22"),
                    c1 = colour("c00") * colour("c01") * colour("c02"),
                    c2 = colour("c10") * colour("c11") * colour("c12"),
                    c3 = colour("c20") * colour("c21") * colour("c22"),
                    x1 = colour("c00") * colour("c11") * colour("c22"),
                    x2 = colour("c20") * colour("c11") * colour("c02");

            if (r1 == 64 || r2 == 64 || r3 == 64 || c1 == 64 || c2 == 64 || c3 == 64 || x1 == 64 || x2 == 64) {
                lblWin.setText("Red Won!");
                won = true;
            } else if (r1 == 27 || r2 == 27 || r3 == 27 || c1 == 27 || c2 == 27 || c3 == 27 || x1 == 27 || x2 == 27){
                lblWin.setText("Black Won!");
                won = true;
            }
        }
        //System.out.println(id);
    }

    @FXML
    void toRed() {
        if (blockade.isEmpty()) {
            turn = false;
        }
    }

    @FXML
    void toBlack() {
        if (blockade.isEmpty()) {
            turn = true;
        }
    }

    @FXML
    void restartField() {
        initialize();
    }
}
