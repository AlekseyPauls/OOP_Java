import java.util.LinkedList;

public class Склад {
    private int размер;
    private volatile LinkedList<Деталь> деталиНаСкладе;
    private volatile LinkedList<Изделие> изделияНаСкладе;
//        private final boolean режим; // true - склад деталей, false - склад изделий

    public Склад(int р) {
        размер = р;
        деталиНаСкладе = new LinkedList<>();
        изделияНаСкладе = new LinkedList<>();
    }

    public void положить(Деталь деталь) {
        деталиНаСкладе.add(деталь);
    }

    public void положить(Изделие изделие) {
        изделияНаСкладе.add(изделие);
    }

    public Деталь отправитьДеталь() {
        Деталь деталь = деталиНаСкладе.getFirst();
        деталиНаСкладе.removeFirst();
        return деталь;
    }

    public Изделие отправитьИзделие() {
        Изделие изделие = изделияНаСкладе.getFirst();
        изделияНаСкладе.removeFirst();
        return изделие;
    }

    public boolean нетМестаДляДеталей() {
        System.out.println("Проверка склада деталей на полноту, " + деталиНаСкладе.size());
        if (деталиНаСкладе.size() == размер) return true;
        else return false;
    }

    public boolean нетДеталей() {
        System.out.println("Проверка склада деталей на пустоту, " + деталиНаСкладе.size());
        if (деталиНаСкладе.size() == 0) return true;
        else return false;
    }

    public boolean нетМестаДляИзделий() {
        System.out.println("Проверка склада изделий на полноту, " + изделияНаСкладе.size());
        if (изделияНаСкладе.size() == размер) return true;
        else return false;
    }

    public boolean нетИзделий() {
        System.out.println("Проверка склада изделий на пустоту, " + изделияНаСкладе.size());
        if (изделияНаСкладе.size() == 0) return true;
        else return false;
    }
}