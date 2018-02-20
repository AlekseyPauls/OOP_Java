import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class Фабрика {
    private int размерСкладаКузовов = 100;
    private int размерСкладаМоторов = 100;
    private int размерСкладаАксессуаров = 100;
    private int размерСкладаМашин = 100;
    private int поставщикиАксессуаров = 5;
    private int сборщики = 10;
    private int продавцы = 10;
    private boolean логирование = false;

    public void Фабрика() {
        получитьКонфигурацию();

    }

    private void получитьКонфигурацию() {
        BufferedReader считыватель = null;
        try {
            считыватель = new BufferedReader(new InputStreamReader(new FileInputStream("Конфигурация.txt")));
            String строкаИзФайла = "";
            while ((строкаИзФайла = считыватель.readLine()) != null) {
                String[] слова = строкаИзФайла.split("=");
                switch (слова[0]) {
                    case ("РазмерСкладаКузовов"):
                        размерСкладаКузовов = Integer.parseInt(слова[1]);
                        break;
                    case ("РазмерСкладаМоторов"):
                        размерСкладаКузовов = Integer.parseInt(слова[1]);
                        break;
                    case ("РазмерСкладаАксессуаров"):
                        размерСкладаКузовов = Integer.parseInt(слова[1]);
                        break;
                    case ("РазмерСкладаМашин"):
                        размерСкладаКузовов = Integer.parseInt(слова[1]);
                        break;
                    case ("ПоставщикиАксессуаров"):
                        размерСкладаКузовов = Integer.parseInt(слова[1]);
                        break;
                    case ("Сборщики"):
                        размерСкладаКузовов = Integer.parseInt(слова[1]);
                        break;
                    case ("Продавцы"):
                        размерСкладаКузовов = Integer.parseInt(слова[1]);
                        break;
                    case ("Логирование"):
                        размерСкладаКузовов = Integer.parseInt(слова[1]);
                        break;
                    default:
                        // Неправильная конфигурация
                }
            }
        } catch (IOException e) { System.err.println("Не получилось открыть файл с конфигурацией");
        } finally {
            if (null != считыватель) {
                try { считыватель.close(); }
                catch (IOException e) { e.printStackTrace(System.err); }
            }
        }
    }

    public void работать() {
        Склад складМоторов = new Склад(размерСкладаМоторов);
        Склад складКузовов = new Склад(размерСкладаКузовов);
        Склад складАксессуаров = new Склад(размерСкладаАксессуаров);
        Склад складМашин = new Склад(размерСкладаМашин);

        ПоставщикДеталей поставщикМоторов = new ПоставщикДеталей(складМоторов, "Мотор");
        ПоставщикДеталей поставщикКузовов = new ПоставщикДеталей(складКузовов, "Кузов");

        ПоставщикДеталей поставщикАксессуаров = new ПоставщикДеталей(складАксессуаров, "Аксессуар");
        ПоставщикДеталей поставщикАксессуаров2 = new ПоставщикДеталей(складАксессуаров, "Аксессуар");
        ПоставщикДеталей поставщикАксессуаров3 = new ПоставщикДеталей(складАксессуаров, "Аксессуар");
        ПоставщикДеталей поставщикАксессуаров4 = new ПоставщикДеталей(складАксессуаров, "Аксессуар");
        ПоставщикДеталей поставщикАксессуаров5 = new ПоставщикДеталей(складАксессуаров, "Аксессуар");

        Контроллер контроллер = new Контроллер(сборщики, "Машина", складМашин, складМоторов, складКузовов, складАксессуаров);

        Продавец продавец = new Продавец(складМашин);
        Продавец продавец2 = new Продавец(складМашин);
        Продавец продавец3 = new Продавец(складМашин);

        Thread m1 = new Thread(поставщикМоторов);
        Thread m2 = new Thread(поставщикКузовов);
        Thread m3 = new Thread(поставщикАксессуаров);
//        Thread m4 = new Thread(поставщикАксессуаров2);
//        Thread m5 = new Thread(поставщикАксессуаров3);
//        Thread m6 = new Thread(поставщикАксессуаров4);
//        Thread m7 = new Thread(поставщикАксессуаров5);
        //Thread m8 = new Thread(контроллер);
        Thread m9 = new Thread(продавец);
//        Thread m10 = new Thread(продавец2);
//        Thread m11 = new Thread(продавец3);
        LinkedList<Склад> ll = new LinkedList<Склад>();
        ll.add(складМоторов);
        ll.add(складКузовов);
        ll.add(складАксессуаров);
        Thread m12 = new Thread(new Сборщик("Машина", складМашин, ll));

        m1.start();
        m2.start();
        m3.start();
//        m4.start();
//        m5.start();
//        m6.start();
//        m7.start();
        //m8.start();
        m9.start();
//        m10.start();
//        m11.start();
        m12.start();
    }
}
