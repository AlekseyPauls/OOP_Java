import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Controller {
    private Model model;
    private View view;
    public Controller() {}

//    public class ExitListener implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            Model.
//
//        }
//    }

    public void makeTurn(int i, int j, JButton btn) {
        if (model.playerTurn(i, j)) {
            if (model.getCageState(i, j) == Model.CageState.BLACK) {
                view.setBlackImage(btn);
                model.playerTurn(i, j);
            } else if (model.getCageState(i, j) == Model.CageState.WHITE) {
                view.setWhiteImage(btn);
                model.playerTurn(i, j);
            }
            updateGame();
            view.blackCagesCounter.setText(Integer.toString(model.getBlackCounter()));
            view.whiteCagesCounter.setText(Integer.toString(model.getWhiteCounter()));
            view.repaint();
            if (model.isGameEnd()) {
                view.showGameEnd(model.GameResult());
                return;
            }
            if (model.haveTurn(Model.Role.COMP)) {
                try {
                    Thread.sleep(1000);
                } catch (Exception exc) {}
                if (model.computerTurn()) {
                    updateGame();
                    view.blackCagesCounter.setText(Integer.toString(model.getBlackCounter()));
                    view.whiteCagesCounter.setText(Integer.toString(model.getWhiteCounter()));
                }
                while (!model.haveTurn(Model.Role.USER) && model.haveTurn(Model.Role.COMP)) {
                    JOptionPane.showMessageDialog(new JFrame(), Model.noTurnMessage(Model.Role.USER));
                    try {
                        Thread.sleep(1000);
                    } catch (Exception exc) {
                    }
                    model.computerTurn();
                    updateGame();
                    view.blackCagesCounter.setText(Integer.toString(model.getBlackCounter()));
                    view.whiteCagesCounter.setText(Integer.toString(model.getWhiteCounter()));
                    view.repaint();
                }
                if (model.isGameEnd()) {
                    view.showGameEnd(model.GameResult());
                    return;
                }
            } else {
                if (model.isGameEnd()) {
                    view.showGameEnd(model.GameResult());
                    return;
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), Model.noTurnMessage(Model.Role.COMP));
                }
            }
        }
    }

    private void updateGame() {
        view.blackCagesCounter.setText(Integer.toString(model.getBlackCounter()));
        view.whiteCagesCounter.setText(Integer.toString(model.getWhiteCounter()));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (model.getCageState(i, j) == Model.CageState.BLACK) {
                    view.setBlackImage(view.field[i][j]);
                } else if (model.getCageState(i, j) == Model.CageState.WHITE) {
                    view.setWhiteImage(view.field[i][j]);
                } else {
                    view.setVoidImage(view.field[i][j]);
                }
            }
        }

    }

    public void setModel(Model m) {
        model = m;
    }

    public void setView(View v) {
        view = v;
    }

    public void start() {
        model.start();
        updateGame();
    }

    public void saveResult(String name) {
        model.saveResult(name);
    }

    public void newGame() {
        model.start();
        if (!model.getPlayBlack()) model.computerTurn();
        updateGame();
    }

    public void setGameMode(Model.GameMode mode) {
        model.setGameMode(mode);
    }

    public void setPlayBlack(boolean playBlack) {
        model.setPlayBlack(playBlack);
    }
}
