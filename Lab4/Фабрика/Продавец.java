public class Продавец implements Runnable{
    private Склад складИзделий;
    private int номерПродавца;
    private static int количествоПродавцов = 0;
    private int скоростьПродажи;

    public Продавец(Склад ски) {
        складИзделий = ски;
        скоростьПродажи = 5;;
        номерПродавца = количествоПродавцов;
        количествоПродавцов++;
    }

    public void run() {
        while (true) {
            продажа();
            try {
                Thread.sleep(0, скоростьПродажи);
            } catch (InterruptedException e) {
                System.out.println("Bad");
            }
        }
    }

    public synchronized void продажа() {
        System.out.println("Продавец разбужен");
        while (складИзделий.нетИзделий()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        Изделие изделие = складИзделий.отправитьИзделие();
        System.out.println("Продавец " + номерПродавца + ": " + изделие.получитьНазвание() + " " + изделие.получитьИдентификатор() + "");
        notifyAll();
    }

    public void установитьСкоростьПродажи(int скор) {
        скоростьПродажи = скор;
    }

    public int получитьСкоростьПродажи() {
        return скоростьПродажи;
    }
}
