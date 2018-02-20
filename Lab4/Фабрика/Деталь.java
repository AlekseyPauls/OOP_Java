public class Деталь {
    private int идентификатор;
    private String название;

    public Деталь(int ид, String н) {
        идентификатор = ид;
        название = н;
    }

    public int получитьИдентификатор() {
        return идентификатор;
    }

    public String получитьНазвание() {
        return название;
    }
}