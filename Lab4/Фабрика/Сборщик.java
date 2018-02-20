import java.util.LinkedList;

public class Сборщик implements Runnable {
    private LinkedList<Склад> склады;
    private Склад складИзделий;
    private static volatile int идентификатор = 0;
    private String названиеИзделия;

    public Сборщик(String ни, Склад ски, LinkedList<Склад> ск) {
        названиеИзделия = ни;
        складИзделий = ски;
        склады = ск;
    }

    public void run() {
        while(true) {
            отправить();
        }
    }

    public synchronized void отправить() {
        boolean всеДеталиДоступны = true;
        for (Склад скл: склады) {
            if (скл.нетДеталей()) всеДеталиДоступны = false;
        }
        System.out.println("Проверка деталей на доступность " + всеДеталиДоступны);
        while (!всеДеталиДоступны) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        LinkedList<Деталь> детали = new LinkedList<>();
        for (Склад скл: склады) {
            детали.add(скл.отправитьДеталь());
        }
        Изделие изделие = new Изделие(идентификатор, названиеИзделия, детали);
        идентификатор++;
        System.out.println("!!!" + названиеИзделия + " " + идентификатор + " готов");
        while (складИзделий.нетМестаДляИзделий()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        складИзделий.положить(изделие);
        System.out.println("!!!" + названиеИзделия + " " + идентификатор + " отправлен");
        notifyAll();
    }
}
