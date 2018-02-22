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
        отправить();
    }

    public synchronized void отправить() {
        LinkedList<Деталь> детали = new LinkedList<>();
        for (Склад скл: склады) {
            детали.add(скл.отправитьДеталь());
        }
        Изделие изделие = new Изделие(идентификатор, названиеИзделия, детали);
        идентификатор++;
        //System.out.println("!!!" + названиеИзделия + " " + (идентификатор-1) + " готов");
        складИзделий.положить(изделие);
        //System.out.println("!!!" + названиеИзделия + " " + (идентификатор-1) + " отправлен");
    }

    public static int всегоПроизведено() {
        return идентификатор;
    }
}
