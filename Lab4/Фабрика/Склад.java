import java.util.LinkedList;

public class Склад {
    private int размер;
    private volatile LinkedList<Деталь> деталиНаСкладе;
    private volatile LinkedList<Изделие> изделияНаСкладе;
    private volatile boolean нуженСборщик = false;

    public Склад(int р) {
        размер = р;
        деталиНаСкладе = new LinkedList<>();
        изделияНаСкладе = new LinkedList<>();
    }

    public synchronized void положить(Деталь деталь) {
        while (нетМестаДляДеталей()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        деталиНаСкладе.add(деталь);
        notifyAll();
    }

    public synchronized void положить(Изделие изделие) {
        while (нетМестаДляИзделий()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        изделияНаСкладе.add(изделие);
        notifyAll();
    }

    public synchronized Деталь отправитьДеталь() {
        while (нетДеталей()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        Деталь деталь = деталиНаСкладе.removeFirst();
        notifyAll();
        return деталь;
    }

    public synchronized Изделие отправитьИзделие() {
        while (нетИзделий()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        Изделие изделие = изделияНаСкладе.removeFirst();
        нуженСборщик = true;
        notifyAll();
        return изделие;
    }

    public synchronized void нуженСборщик() {
        while (!нуженСборщик) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        нуженСборщик = false;
        notifyAll();
    }

    public boolean нетМестаДляДеталей() {
        if (деталиНаСкладе.size() == размер) return true;
        else return false;
    }

    public boolean нетДеталей() {
        if (деталиНаСкладе.size() == 0) return true;
        else return false;
    }

    public boolean нетМестаДляИзделий() {
        if (изделияНаСкладе.size() == размер) return true;
        else return false;
    }

    public boolean нетИзделий() {
        if (изделияНаСкладе.size() == 0) return true;
        else return false;
    }

    public int деталейНаСкладе() {
        return деталиНаСкладе.size();
    }

    public int изделийНаСкладе() {
        return изделияНаСкладе.size();
    }

    public int размер() {
        return размер;
    }
}