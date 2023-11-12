import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Controller {

    private Scanner sc = new Scanner(System.in);
    private Random r = new Random();
    private ArrayList<Toy> wonToys = new ArrayList<>();
    private WriteToFile writer = new WriteToFile();

    public void start() {
        Toy.getInstant();
        while (true) {
            System.out.println("Колличество игрушек в розыгрыше: " + Toy.getAmount());
            System.out.println("Колличество выигранных игрушек: " + wonToys.size());
            System.out.println("""
                    1 - создать игрушку
                    2 - изменить вероятность выпадения
                    3 - участвовать в розыгрыше
                    4 - забрать игрушки
                    5 - посмотреть выигранные игрушки
                    exit - чтобы выйти
                    """);
            System.out.print("Введите команду: ");
            switch (sc.next()) {
                case "1" -> createToy();
                case "2" -> setProbability();
                case "3" -> play();
                case "4" -> pickUpToys();
                case "5" -> seeTheWonToys();
                case "exit" -> {
                    writer.writeToFileClose();
                    return;
                }
                default -> System.out.println("Такой команды нет!\n");
            }
        }
    }

    private void seeTheWonToys() {
        if (wonToys.size() == 0) {
            System.out.println("Вы ещё не выиграли ни одной игрушки.\n");
            return;
        }
        for (int i = 0; i < wonToys.size(); i++) {
            if (i == wonToys.size() - 1) {
                System.out.println(wonToys.get(i).getName());
            } else {
                System.out.println(wonToys.get(i).getName() + ", ");
            }
        }
    }

    private void pickUpToys() {
        if (wonToys.size() == 0) {
            System.out.println("Вы ещё не выиграли ни одной игрушки.\n");
            return;
        }
        StringBuilder toysNames = new StringBuilder();
        for (int i = 0; i < wonToys.size(); i++) {
            if (i == wonToys.size() - 1) {
                toysNames.append(wonToys.get(i).getName());
            } else {
                toysNames.append(wonToys.get(i).getName()).append(", ");
            }
        }
        writer.writeToFile(toysNames.toString());
        wonToys.clear();
    }

    private void play() {
        if (Toy.getAllToys().size() == 0) {
            System.out.println("Чтобы участвовать в розыгрыше нужно создать игрушки!");
            return;
        }
        System.out.println("Выберите игрушку которую хотите выиграть!");
        for (Toy toy : Toy.getAllToys()) {
            System.out.printf("%-10s id = %-3d\n", toy.getName(), toy.getId());
        }
        System.out.print("Введите id игрушки: ");
        int id = sc.nextInt();
        Toy desiredToy = Toy.getToy(id);
        if (desiredToy == null) {
            System.out.println("Такой игрушки нет!");
            return;
        }
        System.out.println("Вероятность выпадения " + desiredToy.getProbability());
        System.out.println("""
                1 - попытать удачу
                2 - выйти в главное меню
                """);
        System.out.print("Введите команду: ");
        switch (sc.next()) {
            case "1":
                int random = r.nextInt(100) + 1;
                if (random <= desiredToy.getProbability()) {
                    System.out.println("Вам повезло и вы выиграли!\n");
                    wonToys.add(desiredToy);
                    Toy.removeToy(desiredToy);
                    Toy.decrementAmount();
                } else {
                    System.out.println("Вам не повезло!\n");
                }
                break;
            case "2":
                return;
            default:
                System.out.println("Такой команды нет!");
        }
    }

    private void setProbability() {
        System.out.println("Выберите игрушку которой хотите изменить вероятность!");
        for (Toy toy : Toy.getAllToys()) {
            System.out.printf("%-10s id = %-3d\n", toy.getName(), toy.getId());
        }
        System.out.print("Введите id игрушки: ");
        int id = sc.nextInt();
        boolean bool = !(id >= 1 && id <= Toy.getCounter());
        if (bool) {
            System.out.println("Такого id нет!");
            return;
        }
        System.out.print("Вероятность должна быть больше нуля и меньше 100\n"
                + "Введите вероятность выпадения: ");
        int probability = sc.nextInt();
        if (!(probability > 0 && probability <= 100)) {
            try {
                throw new NameOrProbabilityException("Некорректно введены данные!", Toy.getAllToys().get(id).getName(), probability);
            } catch (NameOrProbabilityException e) {
                System.out.println("Ошибка в вероятности!");
                System.out.printf("Введено '%s', '%d'\n", e.getName(), e.getProbability());
            }
        }
        Toy.getToy(id).setProbability(probability);
        System.out.println();
    }

    private void createToy() {
        System.out.println("Название игрушки должно содержать не менее 3-х символов и без пробелов.\n" +
                "Вероятность должна быть больше нуля и меньше 100");
        System.out.print("Введите название игрушки: ");
        String name = sc.next();
        System.out.print("Введите вероятность выпадения: ");
        int probability = sc.nextInt();
        try {
            Toy.createToy(name, probability);
            System.out.println();
        } catch (NameOrProbabilityException e) {
            System.out.println("\nОшибка в имени или в вероятности!");
            System.out.printf("Введено '%s', '%d'\n\n", e.getName(), e.getProbability());
        }
    }
}
