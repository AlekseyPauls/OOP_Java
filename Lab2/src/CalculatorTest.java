import CalcException.CalcException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;



class CalculatorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @AfterAll
    public static void deleteTestProg() {
        File f = new File("testprog.txt");
        f.delete();
    }

    @Test
    void AddTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH 1\r\n");
            writer.write("PUSH 2\r\n");
            writer.write("+\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        c.calc();
        assertEquals("3.0\n", outContent.toString());
    }

    @Test
    void OwerflowAddTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH 333333333333333333333333333333333338888888\r\n");
            writer.write("PUSH 888888888888888888888888888888888888888888\r\n");
            writer.write("+\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        try {
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error in arithmetical command '+': float number is overflow", e.getMsg());
        }
    }

    @Test
    void SubTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH 2\r\n");
            writer.write("PUSH 1\r\n");
            writer.write("-\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        c.calc();
        assertEquals("1.0\n", outContent.toString());
    }

    @Test
    void OwerflowSubTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH -333333333333333333333333333333333338888888\r\n");
            writer.write("PUSH -888888888888888888888888888888888888888888\r\n");
            writer.write("-\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        try {
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error in arithmetical command '-': float number is overflow", e.getMsg());
        }
    }

    @Test
    void MultTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH 1\r\n");
            writer.write("PUSH 3\r\n");
            writer.write("*\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        c.calc();
        assertEquals("3.0\n", outContent.toString());
    }

    @Test
    void OwerflowMultTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH 333333333333333333333333333333333338888888\r\n");
            writer.write("PUSH 888888888888888888888888888888888888888888\r\n");
            writer.write("*\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        try {
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error in arithmetical command '*': float number is overflow", e.getMsg());
        }
    }

    @Test
    void DivTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH 4\r\n");
            writer.write("PUSH 2\r\n");
            writer.write("/\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        c.calc();
        assertEquals("2.0\n", outContent.toString());
    }

    @Test
    void OwerflowDivTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH 0.0000000000000000000000000000000000000008\r\n");
            writer.write("PUSH 0.0000000000000000000000000000000000000008\r\n");
            writer.write("+\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        try {
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error in arithmetical command '/': float number is overflow", e.getMsg());
        }
    }

    @Test
    void SqrtTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH 4\r\n");
            writer.write("SQRT\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        c.calc();
        assertEquals("2.0\n", outContent.toString());
    }

    @Test
    void OwerflowSqrtTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH -1\r\n");
            writer.write("SQRT\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        try {
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error in command Sqrt: negative number", e.getMsg());
        }
    }

    @Test
    void LackOfArgumentsTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("SQRT\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        Calculator c = new Calculator(new File("testprog.txt"));
        try {
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error: stack have no needed count of numbers to execute command" +
                    " or stack is empty", e.getMsg());
        }
    }

    @Test
    void WrongCommandTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("SQR\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        try {
            Calculator c = new Calculator(new File("testprog.txt"));
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error in parsing program: Command SQR is unacceptable", e.getMsg());
        }
    }

    @Test
    void WrongArgumentsCountTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("+ 5\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        try {
            Calculator c = new Calculator(new File("testprog.txt"));
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error in parsing program: wrong count of arguments for command '+'. " +
                    "This command must contain 0 arguments", e.getMsg());
        }
    }

    @Test
    void WrongArgumentsTypeTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("DEFINE a b\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        try {
            Calculator c = new Calculator(new File("testprog.txt"));
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error in command Define: second argument must be float", e.getMsg());
        }
    }

    @Test
    void UndefinedPushTest() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("testprog.txt"));
            writer.write("PUSH a\r\n");
            writer.write("PRINT\r\n");
        } catch (IOException e) {
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        try {
            Calculator c = new Calculator(new File("testprog.txt"));
            c.calc();
        } catch (CalcException e) {
            assertEquals("Error in command Push: using undeclared definition", e.getMsg());
        }
    }
}