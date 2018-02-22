import java.util.LinkedList;

public class ОчередьРабот
{
    private final int числоПотоков;
    private final Рабочий[] потоки;
    private final LinkedList очередь;

    public ОчередьРабот(int числоПотоков)
    {
        this.числоПотоков = числоПотоков;
        очередь = new LinkedList();
        потоки = new Рабочий[числоПотоков];

        for (int и = 0; и< числоПотоков; и++) {
            потоки[и] = new Рабочий();
            потоки[и].start();
        }
    }

    public void execute(Runnable р) {
        synchronized(очередь) {
            очередь.addLast(р);
            очередь.notify();
        }
    }

    private class Рабочий extends Thread {
        public void run() {
            Runnable р;

            while (true) {
                synchronized(очередь) {
                    while (очередь.isEmpty()) {
                        try
                        {
                            очередь.wait();
                        }
                        catch (InterruptedException ignored)
                        {
                        }
                    }
                    р = (Runnable) очередь.removeFirst();
                }
                try {
                    р.run();
                }
                catch (RuntimeException e) {
                    // You might want to log something here
                }
            }
        }
    }
}
