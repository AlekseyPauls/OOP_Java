import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Controller {
    private Model model;
    private View view;
    public FieldListener fl;
    public ReStartListener rl;

    public Controller(Model mod, View v) {
        model = mod;
        view = v;
        fl = new FieldListener();
        rl = new ReStartListener();
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
                    //JOptionPane.showMessageDialog(new JFrame(), "Game is ended!");
                    //JOptionPane.showMessageDialog(view.endGameFrame, "Game is ended!");
//                    Object[] options = {"Yes, please",
//                            "No, thanks",
//                            "No eggs, no ham!"};
//                    int n = JOptionPane.showOptionDialog(view.endGameFrame,
//                            "Would you like some green eggs to go "
//                                    + "with that ham?",
//                            "A Silly Question",
//                            JOptionPane.YES_NO_CANCEL_OPTION,
//                            JOptionPane.QUESTION_MESSAGE,
//                            null,
//                            options,
//                            options[2]);
                    JOptionPane pane = new JOptionPane("");
                    pane.setPreferredSize(new Dimension(300, 300));
                    JButton button1 = new JButton("Btn1");
                    JButton button2 = new JButton("Btn2");
                    JButton button3 = new JButton("Btn3");
                    JDialog dialog = pane.createDialog("Dialog");
                    dialog.setPreferredSize(new Dimension(300, 300));
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(3, 9));
                    panel.add(button1);
                    panel.add(button2);
                    panel.add(button3);
                    panel.add(button3);
                    panel.add(button2);
                    panel.add(button1);
                    panel.add(button3);
                    panel.add(button1);
                    panel.add(button2);
                    dialog.add(panel, BorderLayout.PAGE_START);
                    dialog.show();
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
                        //JOptionPane.showMessageDialog(new JFrame(), "Game is ended!");
                        JOptionPane.showMessageDialog(view.endGameFrame, "Game is ended!");
                    }
                } else {
                    if (model.isGameEnd()) {
                        //JOptionPane.showMessageDialog(new JFrame(), "Game is ended!");
                        JOptionPane.showMessageDialog(view.endGameFrame, "Game is ended!");
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
