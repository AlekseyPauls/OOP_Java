import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class View {
    private Controller controller;

    private JFrame frame;
    private JPanel infoPanel;
    private JPanel gamePanel;
    private JPanel fieldPanel;
    public JButton[][] field;
    public JLabel blackCagesCounter;
    public JLabel whiteCagesCounter;
    private ImageIcon blackcage;
    private ImageIcon whitecage;
    private ImageIcon voidcage;

    private NewGame newGame;
    private HighScores highScores;
    private About about;
    private GameEnd gameEnd;

    public View() {
        newGame = new NewGame();
        highScores = new HighScores();
        about = new About();
        gameEnd = new GameEnd();

        blackcage = new ImageIcon("resources/blackcage1.png");
        whitecage = new ImageIcon("resources/whitecage1.png");
        voidcage = new ImageIcon("resources/voidcage1.png");
    }

    public void start() {
        // Create views swing UI components
        Font menuFont = new Font("", Font.BOLD, 16);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.setFont(menuFont);
        JMenuItem newgameItem = new JMenuItem("New Game");
        newgameItem.setFont(menuFont);
        newgameItem.addActionListener(new NewGameListener());
        menu.add(newgameItem);
        JMenuItem scoresItem = new JMenuItem("High scores");
        scoresItem.setFont(menuFont);
        scoresItem.addActionListener(new ScoresListener());
        menu.add(scoresItem);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setFont(menuFont);
        aboutItem.addActionListener(new AboutListener());
        menu.add(aboutItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(menuFont);
        exitItem.addActionListener(new ExitListener());
        menu.add(exitItem);
        menuBar.add(menu);

        Border blackline = BorderFactory.createLineBorder(Color.black, 1);
        Border redline = BorderFactory.createLineBorder(Color.red, 5);
        Font infoFont = new Font("Serif", Font.BOLD, 34);
        JButton reStart = new JButton(" Start! ");
        JLabel blackCages = new JLabel("Black: ");
        JLabel whiteCages = new JLabel("White: ");
        blackCagesCounter = new JLabel("0");
        whiteCagesCounter = new JLabel("0");
        reStart.setFont(infoFont);
        reStart.setBackground(Color.WHITE);
        reStart.setBorder(redline);
        reStart.addActionListener(new ReStartListener());
        blackCages.setFont(infoFont);
        whiteCages.setFont(infoFont);
        blackCagesCounter.setFont(infoFont);
        whiteCagesCounter.setFont(infoFont);


        GridLayout fieldGrid = new GridLayout(8,8);

        ImageIcon background = new ImageIcon("resources/background.png");
        infoPanel = new JPanel();
        gamePanel = new JPanel();
        fieldPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
        infoPanel.add(blackCages);
        infoPanel.add(blackCagesCounter);
        infoPanel.add(new JLabel("            "));
        infoPanel.add(reStart);
        infoPanel.add(new JLabel("            "));
        infoPanel.add(whiteCages);
        infoPanel.add(whiteCagesCounter);
        gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 60));
        gamePanel.add(fieldPanel);
        gamePanel.setBackground(Color.LIGHT_GRAY);
        fieldPanel.setLayout(fieldGrid);
        fieldPanel.setBorder(blackline);

        fieldPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        field = new JButton[8][8];
        ImageIcon cageIcon = new ImageIcon("resources/voidcage1.png");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                field[i][j] = new JButton(cageIcon);
                field[i][j].setBorder(blackline);
                fieldPanel.add(field[i][j]);
                field[i][j].addActionListener(new FieldListener());
            }
        }

        // Display it all in a scrolling window and make the window appear
        frame = new JFrame("Reversi"); // Создание окна с названием
        frame.setPreferredSize(new Dimension(700, 830));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Деструктор окна (при нажатии на крестик)
        frame.add(infoPanel, BorderLayout.PAGE_START);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); // Делает окно видимым
    }

    public void setController(Controller c) {
        controller = c;
    }

    public void repaint() {
        infoPanel.paintImmediately(0,0,600,600);
        fieldPanel.paintImmediately(0,0,600,600);
        gamePanel.paintImmediately(0,0,600,600);
    }

    public String getUserName() {
        return gameEnd.getName();
    }

    public void showNewGame() {
        newGame.show();
    }

    public void showHighScores() {
        highScores.show();
    }

    public void showAbout() {
        about.show();
    }

    public void showGameEnd(String res) {
        gameEnd.setResult(res);
        gameEnd.show();
    }

    private static class Pos {
        public int I;
        public int J;

        public Pos(int i, int j) {
            I = i;
            J = j;
        }
    }

    private Pos getCagePos(JButton btn) {
        int x = -1;
        int y = -1;
        for (int i = 0; i < 8; i++) {
            if (Arrays.asList(field[i]).indexOf(btn) != -1) {
                x = i;
                y = Arrays.asList(field[i]).indexOf(btn);
            }
        }
        Pos p = new Pos(x, y);
        return p;
    }

    public void setBlackImage(JButton btn) {
        btn.setIcon(blackcage);
    }

    public void setWhiteImage(JButton btn) {
        btn.setIcon(whitecage);
    }

    public void setVoidImage(JButton btn) {
        btn.setIcon(voidcage);
    }

    private class NewGame {
        private JDialog dialog;

        public NewGame() {
            JOptionPane pane = new JOptionPane("");
            pane.setPreferredSize(new Dimension(350, 250));
            JRadioButton easyMode = new JRadioButton("Easy");
            easyMode.addActionListener(new EasyModeListener());
            easyMode.setSelected(true);
            JRadioButton mediumMode = new JRadioButton("Medium");
            mediumMode.addActionListener(new MediumModeListener());
            JRadioButton hardMode = new JRadioButton("Hard");
            hardMode.addActionListener(new HardModeListener());
            ButtonGroup groupMode = new ButtonGroup();
            groupMode.add(easyMode);
            groupMode.add(mediumMode);
            groupMode.add(hardMode);
            ImageIcon blackSide = new ImageIcon("resources/blackside.png");
            //JRadioButton colorBlack = new JRadioButton("Black");
            JRadioButton colorBlack = new JRadioButton("Black", blackSide);
            colorBlack.addActionListener(new ColorBlackListener());
            colorBlack.setSelected(true);
            ImageIcon whiteSide = new ImageIcon("resources/whiteside.png");
            //JRadioButton colorWhite = new JRadioButton("White");
            JRadioButton colorWhite = new JRadioButton("White", whiteSide);
            colorWhite.addActionListener(new ColorWhiteListener());
            ButtonGroup groupColor = new ButtonGroup();
            groupColor.add(colorBlack);
            groupColor.add(colorWhite);
            dialog = pane.createDialog("New Game");
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            GridBagConstraints constraint = new GridBagConstraints();
            constraint.gridx = 0;
            constraint.gridy = 0;
            constraint.gridwidth = 3;
            constraint.ipady = 30;
            constraint.fill = GridBagConstraints.CENTER;
            JLabel gameModeText = new JLabel("Game mode:");
            panel.add(gameModeText, constraint);
            constraint.gridy = 1;
            constraint.gridwidth = 1;
            constraint.ipady = 0;
            panel.add(easyMode, constraint);
            constraint.gridx = 1;
            panel.add(mediumMode, constraint);
            constraint.gridx = 2;
            panel.add(hardMode, constraint);
            constraint.gridx = 0;
            constraint.gridy = 2;
            constraint.gridwidth = 3;
            constraint.ipady = 30;
            JLabel colorText = new JLabel("Your side:");
            panel.add(colorText, constraint);
            constraint.gridy = 3;
            constraint.gridwidth = 1;
            constraint.ipady = 0;
            panel.add(colorBlack, constraint);
            constraint.gridx = 1;
            panel.add(colorWhite, constraint);
            Font dialogFont = new Font("", Font.BOLD, 16);
            easyMode.setFont(dialogFont);
            mediumMode.setFont(dialogFont);
            hardMode.setFont(dialogFont);
            colorBlack.setFont(dialogFont);
            colorWhite.setFont(dialogFont);
            gameModeText.setFont(dialogFont);
            colorText.setFont(dialogFont);
            dialog.add(panel, BorderLayout.PAGE_START);
        }

        public void show() {
            dialog.show();
        }
    }

    private class HighScores {
        private JDialog dialog;

        public HighScores() {
            JOptionPane pane = new JOptionPane("");
            pane.setPreferredSize(new Dimension(650, 450));
            dialog = pane.createDialog("HighScores");
            Font font = new Font("Serif", Font.BOLD, 14);
            Font bigFont = new Font("Serif", Font.BOLD, 16);
            File about = new File("resources/highscores.csv");
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            GridBagConstraints constraint = new GridBagConstraints();
            constraint.gridx = 0;
            constraint.gridy = 0;
            constraint.fill = GridBagConstraints.HORIZONTAL;
            JLabel nameLabel = new JLabel(" User Name ");
            nameLabel.setFont(bigFont);
            panel.add(nameLabel, constraint);
            constraint.gridx = 1;
            JLabel sideLabel = new JLabel(" Side ");
            sideLabel.setFont(bigFont);
            panel.add(sideLabel, constraint);
            constraint.gridx = 2;
            JLabel resultLabel = new JLabel(" Result ");
            resultLabel.setFont(bigFont);
            panel.add(resultLabel, constraint);
            constraint.gridx = 3;
            JLabel blackCountLabel = new JLabel(" Black ");
            blackCountLabel.setFont(bigFont);
            panel.add(blackCountLabel, constraint);
            constraint.gridx = 4;
            JLabel whiteCountLabel = new JLabel(" White ");
            whiteCountLabel.setFont(bigFont);
            panel.add(whiteCountLabel, constraint);
            constraint.gridx = 5;
            JLabel dateTimeLabel = new JLabel(" Date & Time ");
            dateTimeLabel.setFont(bigFont);
            panel.add(dateTimeLabel, constraint);
            constraint.gridx = 0;
            constraint.gridy = 1;
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(about)));
                String strLine;
                while ((strLine = reader.readLine()) != null) {
                    String[] words = strLine.split(",");
                    for (String word: words) {
                        JLabel tmpLabel = new JLabel(" " + word + " ");
                        tmpLabel.setFont(font);
                        tmpLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        panel.add(tmpLabel, constraint);
                        constraint.gridx++;
                    }
                    constraint.gridx = 0;
                    constraint.gridy++;
                }
            } catch (IOException e) { System.err.println("Can not open file");
            } finally {
                if (null != reader) {
                    try { reader.close(); }
                    catch (IOException e) { e.printStackTrace(System.err); }
                }
            }
            JScrollPane scr = new JScrollPane(panel);
            scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scr.setPreferredSize(new Dimension(300, 400));

            dialog.add(scr, BorderLayout.PAGE_START);
        }

        public void show() {
            dialog.show();
        }
    }

    private class About {
        private JDialog dialog;

        public About() {
            JOptionPane pane = new JOptionPane("");
            pane.setPreferredSize(new Dimension(500, 450));
            dialog = pane.createDialog("About");
            Font font = new Font("Serif", Font.BOLD, 14);
            File about = new File("resources/about.txt");
            JTextArea textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            textArea.setFont(font);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(about)));
                String strLine;
                while ((strLine = reader.readLine()) != null)
                    textArea.append(strLine + '\n');
            } catch (IOException e) { System.err.println("Can not open file");
            } finally {
                if (null != reader) {
                    try { reader.close(); }
                    catch (IOException e) { e.printStackTrace(System.err); }
                }
            }
            JScrollPane scr = new JScrollPane(textArea);
            scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scr.setPreferredSize(new Dimension(300, 400));
            textArea.setCaretPosition(0);
            dialog.add(scr, BorderLayout.PAGE_START);
        }

        public void show() {
            dialog.show();
        }
    }

    private class GameEnd {
        private JDialog dialog;
        private JLabel resultLabel;
        private JTextField name;

        public GameEnd() {
            JOptionPane pane = new JOptionPane("");
            pane.setPreferredSize(new Dimension(450, 200));
            dialog = pane.createDialog("Game End");
            Font font = new Font("Serif", Font.BOLD, 14);
            Font resultFont = new Font("Serif", Font.BOLD, 28);
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            GridBagConstraints constraint = new GridBagConstraints();
            constraint.gridx = 0;
            constraint.gridy = 0;
            constraint.gridwidth = 4;
            constraint.ipady = 60;
            constraint.fill = GridBagConstraints.CENTER;
            resultLabel = new JLabel();
            resultLabel.setFont(resultFont);
            panel.add(resultLabel, constraint);
            constraint.gridy = 1;
            constraint.gridwidth = 1;
            constraint.ipady = 0;
            constraint.ipadx = 10;
            JLabel enterText = new JLabel("Enter your name: ");
            enterText.setFont(font);
            panel.add(enterText, constraint);
            constraint.gridx = 1;
            name = new JTextField("Username");
            name.setFont(font);
            panel.add(name, constraint);
            constraint.gridx = 2;
            constraint.ipadx = 30;
            JLabel fictiveLabel = new JLabel("");
            panel.add(fictiveLabel, constraint);
            constraint.gridx = 3;
            constraint.ipadx = 0;
            JButton save = new JButton("Save result");
            save.setFont(font);
            save.addActionListener(new SaveListener());
            panel.add(save, constraint);
            dialog.add(panel, BorderLayout.PAGE_START);
        }

        public void show() {
            dialog.show();
        }

        public void setResult(String s) {
            resultLabel.setText(s);
        }

        public String getName() {
            return name.getText();
        }
    }

    private class FieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            View.Pos pos = getCagePos(btn);
            controller.makeTurn(pos.I, pos.J, btn);
        }
    }

    private class ReStartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            controller.start();
            btn.setText(" Restart ");
        }
    }

    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = getUserName();
            controller.saveResult(name);
            JButton btn = (JButton) e.getSource();
            btn.setText("Saved!");
            btn.setEnabled(false);
        }
    }

    private class NewGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showNewGame();
            controller.newGame();
        }
    }

    private class ScoresListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showHighScores();
        }
    }

    private class AboutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showAbout();
        }
    }

    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class EasyModeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.setGameMode(Model.GameMode.EASY);
        }
    }

    private class MediumModeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.setGameMode(Model.GameMode.MEDIUM);
        }
    }

    private class HardModeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.setGameMode(Model.GameMode.HARD);
        }
    }

    private class ColorBlackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.setPlayBlack(true);
        }
    }

    private class ColorWhiteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.setPlayBlack(false);
        }
    }
}
