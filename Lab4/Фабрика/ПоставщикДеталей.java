public class ПоставщикДеталей implements Runnable {
    private int идентификатор = 0;
    //public static volatile int идентификатор = 0;
    private String названиеДетали;
    private Склад складДеталей;
    public static final int максСкорость = 10;
    public static final int минСкорость = 1;
    private int скоростьРаботы = (максСкорость + минСкорость) / 2;

    public ПоставщикДеталей(Склад ск, String нд) {
        складДеталей = ск;
        названиеДетали = нд;
    }

    @Override
    public void run() {
        while(true) {
            отправитьДеталь();
            try {
                Thread.sleep(1000 / скоростьРаботы);
            } catch (InterruptedException e) {
                System.out.println("Bad");
            }
        }
    }

    public synchronized void отправитьДеталь() {
        Деталь деталь = new Деталь(идентификатор, названиеДетали);
        идентификатор++;
        складДеталей.положить(деталь);
        //System.out.println(названиеДетали + " " + (идентификатор-1) + " отправлен");
    }

    public void установитьСкоростьРаботы(int скор) {
        System.out.println(скоростьРаботы);
        скоростьРаботы = скор;
    }

    public int получитьСкоростьРаботы() {
        return скоростьРаботы;
    }

    public int всегоПроизведено() {
        return идентификатор;
    }
}
