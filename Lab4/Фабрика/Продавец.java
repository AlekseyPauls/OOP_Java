public class Продавец implements Runnable{
    private Склад складИзделий;
    private int номерПродавца;
    private static volatile int всегоПродано = 0;
    private static int количествоПродавцов = 0;
    public static final int максСкорость = 10;
    public static final int минСкорость = 1;
    private static int скоростьПродажи = (максСкорость + минСкорость) / 2;

    public Продавец(Склад ски) {
        складИзделий = ски;
        номерПродавца = количествоПродавцов;
        количествоПродавцов++;
    }

    public void run() {
        while (true) {
            продажа();
            try {
                Thread.sleep(1000 / скоростьПродажи);
            } catch (InterruptedException e) {
                System.out.println("Bad");
            }
        }
    }

    public void продажа() {
        Изделие изделие = складИзделий.отправитьИзделие();
        всегоПродано++;
        //System.out.println("Продавец " + номерПродавца + ": " + изделие.получитьНазвание() + " " + изделие.получитьИдентификатор() + "");
    }

    public static void установитьСкоростьПродажи(int скор) {
        скоростьПродажи = скор;
    }

    public static int получитьСкоростьПродажи() {
        return скоростьПродажи;
    }

    public static int получитьВсегоПродано() {
        return всегоПродано;
    }
}
