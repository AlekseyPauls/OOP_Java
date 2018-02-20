public class ПоставщикДеталей implements Runnable {
    //private static volatile int идентификатор = 0;
    private int идентификатор = 0;
    private int скоростьРаботы; // нс/двигатель
    private String названиеДетали;
    private Склад складДеталей;

    public ПоставщикДеталей(Склад ск, String нд) {
        скоростьРаботы = 5;
        складДеталей = ск;
        названиеДетали = нд;
    }

    @Override
    public void run() {
        while(true) {
            отправитьДеталь();
            try {
                Thread.sleep(1000, скоростьРаботы);
            } catch (InterruptedException e) {
                System.out.println("Bad");
            }
        }
    }

    public synchronized void отправитьДеталь() {
        while (складДеталей.нетМестаДляДеталей()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        Деталь деталь = new Деталь(идентификатор, названиеДетали);
        идентификатор++;
        System.out.println(названиеДетали + " " + идентификатор + " отправлен");
        складДеталей.положить(деталь);
        notifyAll();
    }

    public void установитьСкоростьРаботы(int скор) {
        скоростьРаботы = скор;
    }

    public int получитьСкоростьРаботы() {
        return скоростьРаботы;
    }
}
