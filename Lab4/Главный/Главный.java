public class Главный {
    public static void main(String[] args) {
        Фабрика фабрика = new Фабрика();
        Вид view = new Вид(фабрика);
        фабрика.работать();
        view.старт();
    }
}