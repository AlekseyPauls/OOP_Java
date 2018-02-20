import java.util.LinkedList;

public class Контроллер implements Runnable{
    private ОчередьРабот очередьРабот;
    private int количествоСборщиков;
    private LinkedList<Склад> склады;
    private Склад складИзделий;
    private static volatile int идентификатор = 0;
    private String названиеИзделия;

    public Контроллер(int кс, String ни, Склад ски, Склад ... ск) {
        количествоСборщиков = кс;
        очередьРабот = new ОчередьРабот(количествоСборщиков);
        названиеИзделия = ни;
        складИзделий = ски;
        склады = new LinkedList<>();
        if (ск.length == 0);
        for (int и = 0; и < ск.length; и++) {
            склады.add(ск[и]);
        }
    }

    public void run() {
        while(true) {
            добавитьЗадачу();
        }
    }

    public synchronized void добавитьЗадачу() {
        System.out.println("Вход в контроллер");
        try {
            wait();
        }
        catch (InterruptedException e) {}
        очередьРабот.execute(new Сборщик(названиеИзделия, складИзделий, склады));
        System.out.println("Сборщик создан");
    }
}
