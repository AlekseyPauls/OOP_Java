import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class View {
    private JFrame frame;
    public JFrame endGameFrame;
    private JPanel infoPanel;
    private JPanel gamePanel;
    private JPanel fieldPanel;
    public JButton[][] field;
    public JLabel blackCagesCounter;
    public JLabel whiteCagesCounter;
    public ImageIcon blackcage;
    public ImageIcon whitecage;
    public ImageIcon voidcage;


    public View() {
        // Create table model
        Model model = new Model();

        // Create controller
        Controller controller = new Controller(model, this);

        blackcage = new ImageIcon("resources/blackcage.png");
        whitecage = new ImageIcon("resources/whitecage.png");
        voidcage = new ImageIcon("resources/voidcage.png");

        // Create views swing UI components
        Font menuFont = new Font("", Font.BOLD, 14);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.setFont(menuFont);
        JMenuItem newgameItem = new JMenuItem("New Game");
        newgameItem.setFont(menuFont);
        newgameItem.addActionListener(controller.fl);
        menu.add(newgameItem);
        JMenuItem scoresItem = new JMenuItem("High scores");
        scoresItem.setFont(menuFont);
        menu.add(scoresItem);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setFont(menuFont);
        menu.add(aboutItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(menuFont);
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
        reStart.addActionListener(controller.rl);
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

        field = new JButton[8][8];
        ImageIcon cageIcon = new ImageIcon("resources/voidcage.png");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                field[i][j] = new JButton(cageIcon);
                //field[i][j].setBackground(Color.WHITE);
                field[i][j].setBorder(blackline);
                fieldPanel.add(field[i][j]);
                field[i][j].addActionListener(controller.fl);
            }
        }
        endGameFrame = new JFrame("Game End");
        endGameFrame.setPreferredSize(new Dimension(400, 400));
        JButton btn = new JButton("Text");
        JPanel pnl = new JPanel();
        pnl.add(btn);
        endGameFrame.add(pnl);
        endGameFrame.pack();


        // Display it all in a scrolling window and make the window appear
        frame = new JFrame("IAmSwinger:)"); // Создание окна с названием
        frame.setPreferredSize(new Dimension(700, 830));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Деструктор окна (при нажатии на крестик)
        frame.add(infoPanel, BorderLayout.PAGE_START);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); // Делает окно видимым
    }

    public void repaint() {
        infoPanel.paintImmediately(0,0,600,600);
        fieldPanel.paintImmediately(0,0,600,600);
        gamePanel.paintImmediately(0,0,600,600);
    }

    private class EndGameFrame {
        public EndGameFrame() {
            JFrame endGameFrame = new JFrame("");

        }
    }
}
