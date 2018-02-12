import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Controller {
    private Model model;
    private View view;
    public FieldListener fieldListener;
    public ReStartListener reStartListener;
    public SaveListener saveListener;
    public NewGameListener newGameListener;
    public ScoresListener scoresListener;
    public AboutListener aboutListener;
    public ExitListener exitListener;
    public EasyModeListener easyModeListener;
    public MediumModeListener mediumModeListener;
    public HardModeListener hardModeListener;
    public ColorBlackListener colorBlackListener;
    public ColorWhiteListener colorWhiteListener;

    public Controller(Model mod, View v) {
        model = mod;
        view = v;
        fieldListener = new FieldListener();
        reStartListener = new ReStartListener();
        saveListener = new SaveListener();
        newGameListener = new NewGameListener();
        scoresListener = new ScoresListener();
        aboutListener = new AboutListener();
        exitListener = new ExitListener();
        easyModeListener = new EasyModeListener();
        mediumModeListener = new MediumModeListener();
        hardModeListener = new HardModeListener();
        colorBlackListener = new ColorBlackListener();
        colorWhiteListener = new ColorWhiteListener();
    }

//    public class ExitListener implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            Model.
//
//        }
//    }

    public class FieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            Model.Pos pos = getCagePos(btn);
            if (model.playerTurn(pos.I, pos.J)) {
                if (model.getCageState(pos.I, pos.J) == Model.CageState.BLACK) {
                    btn.setIcon(view.blackcage);
                    model.playerTurn(pos.I, pos.J);
                } else if (model.getCageState(pos.I, pos.J) == Model.CageState.WHITE) {
                    btn.setIcon(view.whitecage);
                    model.playerTurn(pos.I, pos.J);
                }
                updateGame();
                view.blackCagesCounter.setText(Integer.toString(model.getBlackCounter()));
                view.whiteCagesCounter.setText(Integer.toString(model.getWhiteCounter()));
                view.repaint();
                if (model.isGameEnd()) {
                    //view.showAbout();
                    //view.showNewGame();
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
                        JOptionPane.showMessageDialog(new JFrame(), "Game is ended!");
                    }
                } else {
                    if (model.isGameEnd()) {
                        JOptionPane.showMessageDialog(new JFrame(), "Game is ended!");
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), Model.noTurnMessage(Model.Role.COMP));
                    }
                }
            }
        }
    }

    public class ReStartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            model.start();
            updateGame();
            btn.setText(" Restart ");
        }
    }

    public class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = view.getUserName();
            model.saveResult(name);
        }
    }

    public class NewGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.showNewGame();
            model.start();
            if (!model.getPlayBlack()) model.computerTurn();
            updateGame();
        }
    }

    public class ScoresListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.showHighScores();
        }
    }

    public class AboutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.showAbout();
        }
    }

    public class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.showNewGame();
            model.start();
        }
    }

    public class EasyModeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.setGameMode(Model.GameMode.EASY);
        }
    }

    public class MediumModeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.setGameMode(Model.GameMode.MEDIUM);
        }
    }

    public class HardModeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.setGameMode(Model.GameMode.HARD);
        }
    }

    public class ColorBlackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.setPlayBlack(true);
        }
    }

    public class ColorWhiteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.setPlayBlack(false);
        }
    }

    private void updateGame() {
        view.blackCagesCounter.setText(Integer.toString(model.getBlackCounter()));
        view.whiteCagesCounter.setText(Integer.toString(model.getWhiteCounter()));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton btn = view.field[i][j];
                if (model.getCageState(i, j) == Model.CageState.BLACK) {
                    view.field[i][j].setIcon(view.blackcage);
                } else if (model.getCageState(i, j) == Model.CageState.WHITE) {
                    view.field[i][j].setIcon(view.whitecage);
                } else {
                    view.field[i][j].setIcon(view.voidcage);
                }
            }
        }

    }

    private Model.Pos getCagePos(JButton btn) {
        int x = -1;
        int y = -1;
        for (int i = 0; i < 8; i++) {
            if (Arrays.asList(view.field[i]).indexOf(btn) != -1) {
                x = i;
                y = Arrays.asList(view.field[i]).indexOf(btn);
            }
        }
        Model.Pos p = new Model.Pos(x, y);
        return p;
    }
}
