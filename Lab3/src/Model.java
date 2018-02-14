import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Model {
    public enum CageState {BLACK, WHITE, VOID, MOD, MODBLACK, MODWHITE}
    public enum GameMode {EASY, MEDIUM, HARD}
    public enum Role {USER, COMP}
    public enum Result {WIN, LOSE, DRAW}
    private int blackCounter;
    private int whiteCounter;
    private int modCount;
    private int modwhiteCount;
    private int modblackCount;
    private boolean playBlack;
    private CageState[][] field;
    private GameMode gameMode;
    private Result result;

    public Model() {
        blackCounter = 0;
        whiteCounter = 0;
        modCount = 0;
        modblackCount = 0;
        modwhiteCount = 0;
        playBlack = true;
        gameMode = GameMode.EASY;
        field = new CageState[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                field[i][j] = CageState.VOID;
            }
        }

    }

    public void start() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                field[i][j] = CageState.VOID;
            }
        }
        for (int i = 3; i < 5; i++) {
            for (int j = 3; j < 5; j++) {
                if (i == j) {
                    field[i][j] = CageState.BLACK;
                } else {
                    field[i][j] = CageState.WHITE;
                }
            }
        }
        updateGame();
    }

    public boolean playerTurn(int i, int j) {
        CageState cs;
        CageState mcs;
        if (playBlack) {
            cs = CageState.BLACK;
            mcs = CageState.MODBLACK;
        } else {
            cs = CageState.WHITE;
            mcs = CageState.MODWHITE;
        }
        if (field[i][j] == CageState.MOD || field[i][j] == mcs) {
            field[i][j] = cs;
            reverse(i, j);
            updateGame();
            return true;
        }
        return false;
    }

    public boolean computerTurn() {
        boolean f = false;
        CageState cs;
        CageState mcs;
        if (!playBlack) {
            cs = CageState.BLACK;
            mcs = CageState.MODBLACK;
        } else {
            cs = CageState.WHITE;
            mcs = CageState.MODWHITE;
        }
        if (gameMode == GameMode.EASY) f = easyTurn(cs, mcs);
        else if (gameMode == GameMode.MEDIUM) f = mediumTurn(cs, mcs);
        else f = hardTurn(cs, mcs);
        updateGame();
        return f;
    }

    private boolean easyTurn(CageState cs, CageState mcs) {
        boolean f = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (field[i][j] == CageState.MOD || field[i][j] == mcs) {
                    field[i][j] = cs;
                    reverse(i, j);
                    f = true;
                    break;
                }
            }
            if (f) break;
        }
        return f;
    }

    /* It is just an illusion of choice */

    private boolean mediumTurn(CageState cs, CageState mcs) {
        return easyTurn(cs, mcs);
    }

    private boolean hardTurn(CageState cs, CageState mcs) {
        return easyTurn(cs, mcs);
    }

    private int reverse(int x, int y) {
        CageState cs = getCageState(x, y);
        int res = 0;
        int tmp = -1;
        for (int i = x + 1; i < 8; i++) {
            if (getCageState(i, y) == cs) {
                tmp = i;
                break;
            } else if (getCageState(i, y) != CageState.WHITE && getCageState(i, y) != CageState.BLACK) {
                break;
            }
        }
        if (tmp != -1) {
            for (int i = x + 1; i < tmp; i++) {
                field[i][y] = cs;
                res++;
            }
        }
        tmp = -1;
        for (int i = x - 1; i >= 0; i--) {
            if (getCageState(i, y) == cs) {
                tmp = i;
                break;
            } else if (getCageState(i, y) != CageState.WHITE && getCageState(i, y) != CageState.BLACK) {
                break;
            }
        }
        if (tmp != -1) {
            for (int i = x - 1; i > tmp; i--) {
                field[i][y] = cs;
                res++;
            }
        }
        tmp = -1;
        for (int i = y + 1; i < 8; i++) {
            if (getCageState(x, i) == cs) {
                tmp = i;
                break;
            } else if (getCageState(x, i) != CageState.WHITE && getCageState(x, i) != CageState.BLACK) {
                break;
            }
        }
        if (tmp != -1) {
            for (int i = y + 1; i < tmp; i++) {
                field[x][i] = cs;
                res++;
            }
        }
        tmp = -1;
        for (int i = y - 1; i >= 0; i--) {
            if (getCageState(x, i) == cs) {
                tmp = i;
                break;
            } else if (getCageState(x, i) != CageState.WHITE && getCageState(x, i) != CageState.BLACK) {
                break;
            }
        }
        if (tmp != -1) {
            for (int i = y - 1; i > tmp; i--) {
                field[x][i] = cs;
                res++;
            }
        }
        tmp = -1;
        for (int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++) {
            if (getCageState(i, j) == cs) {
                tmp = i;
                break;
            } else if (getCageState(i, j) != CageState.WHITE && getCageState(i, j) != CageState.BLACK) {
                break;
            }
        }
        if (tmp != -1) {
            for (int i = x + 1, j = y + 1; i < tmp && j < 8; i++, j++) {
                field[i][j] = cs;
                res++;
            }
        }
        tmp = -1;
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j --) {
            if (getCageState(i, j) == cs) {
                tmp = i;
                break;
            } else if (getCageState(i, j) != CageState.WHITE && getCageState(i, j) != CageState.BLACK) {
                break;
            }
        }
        if (tmp != -1) {
            for (int i = x - 1, j = y - 1; i > tmp && j >= 0; i--, j --) {
                field[i][j] = cs;
                res++;
            }
        }
        tmp = -1;
        for (int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--) {
            if (getCageState(i, j) == cs) {
                tmp = i;
                break;
            } else if (getCageState(i, j) != CageState.WHITE && getCageState(i, j) != CageState.BLACK) {
                break;
            }
        }
        if (tmp != -1) {
            for (int i = x + 1, j = y - 1; i < tmp && j >= 0; i++, j--) {
                field[i][j] = cs;
                res++;
            }
        }
        tmp = -1;
        for (int i = x - 1, j = y + 1; i >= 0 && j < 8; i--, j++) {
            if (getCageState(i, j) == cs) {
                tmp = i;
                break;
            } else if (getCageState(i, j) != CageState.WHITE && getCageState(i, j) != CageState.BLACK) {
                break;
            }
        }
        if (tmp != -1) {
            for (int i = x - 1, j = y + 1; i > tmp && j < 8; i--, j++) {
                field[i][j] = cs;
                res++;
            }
        }
        return res;
    }

    private CageState countState(int x, int y) {
        if (field[x][y] == CageState.BLACK) {
            blackCounter++;
            return CageState.BLACK;
        }
        if (field[x][y] == CageState.WHITE) {
            whiteCounter++;
            return CageState.WHITE;
        }
        boolean f1 = false, f2 = false;
        CageState[][] tmp = new CageState[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tmp[i][j] = field[i][j];
            }
        }
        field[x][y] = CageState.BLACK;
        if (reverse(x, y) != 0) {
            f1 = true;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    field[i][j] = tmp[i][j];
                }
            }
        }
        field[x][y] = CageState.WHITE;
        if (reverse(x, y) != 0) {
            f2 = true;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    field[i][j] = tmp[i][j];
                }
            }
        } else {
            field[x][y] = CageState.VOID;
        }
        if (f1 && f2) {
            modCount++;
            return CageState.MOD;
        }
        else if (f1) {
            modblackCount++;
            return CageState.MODBLACK;
        }
        else if (f2) {
            modwhiteCount++;
            return CageState.MODWHITE;
        }
        else return CageState.VOID;
    }

    public boolean haveTurn(Role role) {
        if (role == Role.USER) {
            if (playBlack && (modCount != 0 || modblackCount != 0)) return true;
            else if (!playBlack && (modCount != 0 || modwhiteCount != 0)) return true;
        } else {
            if (!playBlack && (modCount != 0 || modblackCount != 0)) return true;
            else if (playBlack && (modCount != 0 || modwhiteCount != 0)) return true;
        }
        return false;
    }

    public static String noTurnMessage(Role role) {
        if (role == Role.USER) return "You have no turn. Computer will turn now";
        else return "Computer have no turn. Make your turn!";
    }

    private void updateGame() {
        modCount = 0;
        modblackCount = 0;
        modwhiteCount = 0;
        blackCounter = 0;
        whiteCounter = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                field[x][y] = countState(x, y);
            }
        }
    }

    public boolean isGameEnd() {
        if (modwhiteCount + modblackCount + modCount == 0) return true;
        else return false;
        //else return true;
    }

    public String GameResult() {
        if ((playBlack && (blackCounter > whiteCounter)) || (!playBlack && (blackCounter < whiteCounter))) {
            result = Result.WIN;
            return "YOU WIN!";
        } else if (blackCounter > whiteCounter) {
            result = Result.DRAW;
            return "DRAW!";
        } else {
            result = Result.LOSE;
            return "YOU LOSE!";
        }
    }

    public void saveResult(String userName) {
        LinkedList<String> text = new LinkedList<String>();
        File highScores = new File("resources/highscores.csv");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(highScores)));
            String strLine;
            while ((strLine = reader.readLine()) != null)
                text.add(strLine);
        } catch (IOException e) { System.err.println("Can not open file");
        } finally {
            if (null != reader) {
                try { reader.close(); }
                catch (IOException e) { e.printStackTrace(System.err); }
            }
        }
        String[] newResult = getStringResult(userName).split(",");
        int pos = -1;
        for (String s: text) {
            String words[] = s.split(",");
            if (words[2].equals(newResult[2]) && ((words[1].equals(newResult[1])
                    && words[1].equals("Black") && (Integer.parseInt(words[3]) < Integer.parseInt(newResult[3]))) ||
                    (words[1].equals(newResult[1]) && words[1].equals("White") && (Integer.parseInt(words[4]) < Integer.parseInt(newResult[4]))) ||
                    (!words[1].equals(newResult[1]) && words[1].equals("Black") && (Integer.parseInt(words[3]) < Integer.parseInt(newResult[4]))) ||
                    (words[1].equals(newResult[1]) && words[1].equals("White") && (Integer.parseInt(words[4]) < Integer.parseInt(newResult[3]))))) {
                pos = text.indexOf(s);
            }
        }
        if (pos != -1) text.add(pos, getStringResult(userName));
        else text.addLast(getStringResult(userName));
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("resources/highscores.csv"));
            for (String s: text) {
                writer.write(s + "\r\n");
            }
        } catch (IOException e) { System.err.println("Error in writing file: " + e.getLocalizedMessage());
        } finally {
            if (null != writer) {
                try { writer.close();
                } catch (IOException e) { e.printStackTrace(System.err); }
            }
        }
    }

    private String getStringResult(String name) {
        String side;
        if (playBlack) side = "Black";
        else side = "White";
        String res;
        if (result == Result.WIN) res = "Win";
        else if (result == Result.LOSE) res = "Lose";
        else res = "Draw";
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd '-' hh:mm:ss");
        String date = formatForDateNow.format(dateNow);
        return name + "," + side + "," + res + "," + Integer.toString(blackCounter) + "," +
                Integer.toString(whiteCounter) + "," + date;
    }

    public int getBlackCounter() { return blackCounter; }
    public void setBlackCounter(int i) { blackCounter = i; }
    public int getWhiteCounter() { return whiteCounter; }
    public void setWhiteCounter(int i) { whiteCounter = i; }
    public boolean getPlayBlack() { return playBlack; }
    public void setPlayBlack(boolean b) { playBlack = b; }
    public CageState getCageState(int i, int j) { return field[i][j]; }
    public GameMode getGameMode() { return gameMode; }
    public void setGameMode(GameMode gm) { gameMode = gm; }
}
