import java.util.LinkedList;

public class Изделие {
    private int идентификатор;
    private String название;
    private LinkedList<Деталь> детали;

//    public Изделие(int ид, String н, Деталь ... д) {
//        идентификатор = ид;
//        название = н;
//        детали = new LinkedList<>();
//        if (д.length == 0);
//        for (int и = 0; и < д.length; и++) {
//            детали.add(д[и]);
//        }
//    }

    public Изделие(int ид, String н, LinkedList<Деталь> д) {
        идентификатор = ид;
        название = н;
        детали = new LinkedList<>();
        детали = д;
    }

    public int получитьИдентификатор() {
        return идентификатор;
    }

    public String получитьНазвание() {
        return название;
    }

    public LinkedList<Деталь> получитьДетали() {
        return детали;
    }
}