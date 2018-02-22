import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class Фабрика {
    private int размерСкладаКузовов;
    private int размерСкладаМоторов;
    private int размерСкладаАксессуаров;
    private int размерСкладаМашин;
    private int количествоПоставщиковМоторов;
    private int количествоПоставщиковКузовов;
    private int количествоПоставщиковАксессуаров;
    private int количествоСборщиков;
    private int количествоПродавцов;
    private boolean логирование;
    private LinkedList<Склад> склады;
    private LinkedList<ПоставщикДеталей> поставщикиМоторов;
    private LinkedList<ПоставщикДеталей> поставщикиКузовов;
    private LinkedList<ПоставщикДеталей> поставщикиАксессуаров;
    private Контроллер контроллер;
    private LinkedList<Продавец> продавцы;


    public Фабрика() {
        получитьКонфигурацию();

        склады = new LinkedList<>();
        поставщикиМоторов = new LinkedList<>();
        поставщикиКузовов = new LinkedList<>();
        поставщикиАксессуаров = new LinkedList<>();
        продавцы = new LinkedList<>();
        склады.add(new Склад(размерСкладаМоторов));
        склады.add(new Склад(размерСкладаКузовов));
        склады.add(new Склад(размерСкладаАксессуаров));
        склады.add(new Склад(размерСкладаМашин));
        for (int и = 0; и < количествоПоставщиковМоторов; и++) {
            поставщикиМоторов.add(new ПоставщикДеталей(склады.get(0), "Мотор"));
        }
        for (int и = 0; и < количествоПоставщиковКузовов; и++) {
            поставщикиКузовов.add(new ПоставщикДеталей(склады.get(1), "Кузов"));
        }
        for (int и = 0; и < количествоПоставщиковАксессуаров; и++) {
            поставщикиАксессуаров.add(new ПоставщикДеталей(склады.get(2), "Аксессуар"));
        }
        контроллер = new Контроллер(количествоСборщиков, "Машина", склады.get(3), склады.get(0), склады.get(1), склады.get(2));
        for (int и = 0; и < количествоПродавцов; и++) {
            продавцы.add(new Продавец(склады.get(3)));
        }

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
                        размерСкладаМоторов = Integer.parseInt(слова[1]);
                        break;
                    case ("РазмерСкладаАксессуаров"):
                        размерСкладаАксессуаров = Integer.parseInt(слова[1]);
                        break;
                    case ("РазмерСкладаМашин"):
                        размерСкладаМашин = Integer.parseInt(слова[1]);
                        break;
                    case ("ПоставщикиМоторов"):
                        количествоПоставщиковМоторов = Integer.parseInt(слова[1]);
                        break;
                    case ("ПоставщикиКузовов"):
                        количествоПоставщиковКузовов = Integer.parseInt(слова[1]);
                        break;
                    case ("ПоставщикиАксессуаров"):
                        количествоПоставщиковАксессуаров = Integer.parseInt(слова[1]);
                        break;
                    case ("Сборщики"):
                        количествоСборщиков = Integer.parseInt(слова[1]);
                        break;
                    case ("Продавцы"):
                        количествоПродавцов = Integer.parseInt(слова[1]);
                        break;
                    case ("Логирование"):
                        логирование = Boolean.parseBoolean(слова[1]);
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
        LinkedList<Thread> потоки = new LinkedList<>();
        for (int и = 0; и < количествоПоставщиковМоторов; и++) {
            потоки.add(new Thread(поставщикиМоторов.get(и)));
        }
        for (int и = 0; и < количествоПоставщиковКузовов; и++) {
            потоки.add(new Thread(поставщикиКузовов.get(и)));
        }
        for (int и = 0; и < количествоПоставщиковАксессуаров; и++) {
            потоки.add(new Thread(поставщикиАксессуаров.get(и)));
        }
        потоки.add(new Thread(контроллер));
        for (int и = 0; и < количествоПродавцов; и++) {
            потоки.add(new Thread(продавцы.get(и)));
        }

        for (Thread поток: потоки) {
            поток.start();
        }
    }

    public String получитьРазмерСкладаМоторов() {
        return Integer.toString(размерСкладаМоторов);
    }

    public String получитьРазмерСкладаКузовов() {
        return Integer.toString(размерСкладаКузовов);
    }

    public String получитьРазмерСкладаАксессуаров() {
        return Integer.toString(размерСкладаАксессуаров);
    }

    public String получитьРазмерСкладаМашин() {
        return Integer.toString(размерСкладаМашин);
    }

    public String получитьКоличествоПоставщиковМоторов() {
        return Integer.toString(количествоПоставщиковМоторов);
    }

    public String получитьКоличествоПоставщиковКузовов() {
        return Integer.toString(количествоПоставщиковКузовов);
    }

    public String получитьКоличествоПоставщиковАксессуаров() {
        return Integer.toString(количествоПоставщиковАксессуаров);
    }

    public String получитьКоличествоСборщиков() {
        return Integer.toString(количествоСборщиков);
    }

    public String получитьКоличествоПродавцов() {
        return Integer.toString(количествоПродавцов);
    }

    public String получитьТекущееКоличествоМоторов() {
        return Integer.toString(склады.get(0).деталейНаСкладе());
    }

    public String получитьТекущееКоличествоКузовов() {
        return Integer.toString(склады.get(1).деталейНаСкладе());
    }

    public String получитьТекущееКоличествоАксессуаров() {
        return Integer.toString(склады.get(2).деталейНаСкладе());
    }

    public String получитьТекущееКоличествоМашин() {
        return Integer.toString(склады.get(3).изделийНаСкладе());
    }

    public String получитьВсегоПроизведеноМоторов() {
        int сумма = 0;
        for (int и = 0; и < поставщикиМоторов.size(); и++) {
            сумма += поставщикиМоторов.get(и).всегоПроизведено();
        }
        return Integer.toString(сумма);
    }

    public String получитьВсегоПроизведеноКузовов() {
        int сумма = 0;
        for (int и = 0; и < поставщикиКузовов.size(); и++) {
            сумма += поставщикиКузовов.get(и).всегоПроизведено();
        }
        return Integer.toString(сумма);
    }

    public String получитьВсегоПроизведеноАксессуаров() {
        int сумма = 0;
        for (int и = 0; и < поставщикиАксессуаров.size(); и++) {
            сумма += поставщикиАксессуаров.get(и).всегоПроизведено();
        }
        return Integer.toString(сумма);
    }

    public String получитьВсегоПроизведеноМашин() {
        return Integer.toString(Сборщик.всегоПроизведено());
    }

    public String получитьСкоростьПроизводстваМоторов() {
        return Integer.toString(поставщикиМоторов.getFirst().получитьСкоростьРаботы());
    }

    public String получитьСкоростьПроизводстваКузовов() {
        return Integer.toString(поставщикиКузовов.getFirst().получитьСкоростьРаботы());
    }

    public String получитьСкоростьПроизводстваАксессуаров() {
        return Integer.toString(поставщикиАксессуаров.getFirst().получитьСкоростьРаботы());
    }

    public String получитьСкоростьПродажиМашин() {
        return Integer.toString(Продавец.получитьСкоростьПродажи());
    }

    public String получитьМаксимальноеКоличествоМоторов() {
        return Integer.toString(размерСкладаМоторов);
    }

    public String получитьМаксимальноеКоличествоКузовов() {
        return Integer.toString(размерСкладаКузовов);
    }

    public String получитьМаксимальноеКоличествоАксессуаров() {
        return Integer.toString(размерСкладаАксессуаров);
    }

    public String получитьМаксимальноеКоличествоМашин() {
        return Integer.toString(размерСкладаМашин);
    }

    public String получитьВсегоПроданоМашин() {
        return Integer.toString(Продавец.получитьВсегоПродано());
    }

    public void установитьСкоростьПроизводстваМоторов(int скорость) {
        for (int и = 0; и < поставщикиМоторов.size(); и++) {
            поставщикиМоторов.get(и).установитьСкоростьРаботы(скорость);
        }
    }

    public void установитьСкоростьПроизводстваКузовов(int скорость) {
        for (int и = 0; и < поставщикиКузовов.size(); и++) {
            поставщикиКузовов.get(и).установитьСкоростьРаботы(скорость);
        }
    }

    public void установитьСкоростьПроизводстваАксессуаров(int скорость) {
        for (int и = 0; и < поставщикиМоторов.size(); и++) {
            поставщикиАксессуаров.get(и).установитьСкоростьРаботы(скорость);
        }
    }

    public void установитьСкоростьПокупкиМашин(int скорость) {
        for (int и = 0; и < поставщикиМоторов.size(); и++) {
            продавцы.get(и).установитьСкоростьПродажи(скорость);
        }
    }
}
