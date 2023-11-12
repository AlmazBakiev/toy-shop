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
            System.out.println("����������� ������� � ���������: " + Toy.getAmount());
            System.out.println("����������� ���������� �������: " + wonToys.size());
            System.out.println("""
                    1 - ������� �������
                    2 - �������� ����������� ���������
                    3 - ����������� � ���������
                    4 - ������� �������
                    5 - ���������� ���������� �������
                    exit - ����� �����
                    """);
            System.out.print("������� �������: ");
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
                default -> System.out.println("����� ������� ���!\n");
            }
        }
    }

    private void seeTheWonToys() {
        if (wonToys.size() == 0) {
            System.out.println("�� ��� �� �������� �� ����� �������.\n");
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
            System.out.println("�� ��� �� �������� �� ����� �������.\n");
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
            System.out.println("����� ����������� � ��������� ����� ������� �������!");
            return;
        }
        System.out.println("�������� ������� ������� ������ ��������!");
        for (Toy toy : Toy.getAllToys()) {
            System.out.printf("%-10s id = %-3d\n", toy.getName(), toy.getId());
        }
        System.out.print("������� id �������: ");
        int id = sc.nextInt();
        Toy desiredToy = Toy.getToy(id);
        if (desiredToy == null) {
            System.out.println("����� ������� ���!");
            return;
        }
        System.out.println("����������� ��������� " + desiredToy.getProbability());
        System.out.println("""
                1 - �������� �����
                2 - ����� � ������� ����
                """);
        System.out.print("������� �������: ");
        switch (sc.next()) {
            case "1":
                int random = r.nextInt(100) + 1;
                if (random <= desiredToy.getProbability()) {
                    System.out.println("��� ������� � �� ��������!\n");
                    wonToys.add(desiredToy);
                    Toy.removeToy(desiredToy);
                    Toy.decrementAmount();
                } else {
                    System.out.println("��� �� �������!\n");
                }
                break;
            case "2":
                return;
            default:
                System.out.println("����� ������� ���!");
        }
    }

    private void setProbability() {
        System.out.println("�������� ������� ������� ������ �������� �����������!");
        for (Toy toy : Toy.getAllToys()) {
            System.out.printf("%-10s id = %-3d\n", toy.getName(), toy.getId());
        }
        System.out.print("������� id �������: ");
        int id = sc.nextInt();
        boolean bool = !(id >= 1 && id <= Toy.getCounter());
        if (bool) {
            System.out.println("������ id ���!");
            return;
        }
        System.out.print("����������� ������ ���� ������ ���� � ������ 100\n"
                + "������� ����������� ���������: ");
        int probability = sc.nextInt();
        if (!(probability > 0 && probability <= 100)) {
            try {
                throw new NameOrProbabilityException("����������� ������� ������!", Toy.getAllToys().get(id).getName(), probability);
            } catch (NameOrProbabilityException e) {
                System.out.println("������ � �����������!");
                System.out.printf("������� '%s', '%d'\n", e.getName(), e.getProbability());
            }
        }
        Toy.getToy(id).setProbability(probability);
        System.out.println();
    }

    private void createToy() {
        System.out.println("�������� ������� ������ ��������� �� ����� 3-� �������� � ��� ��������.\n" +
                "����������� ������ ���� ������ ���� � ������ 100");
        System.out.print("������� �������� �������: ");
        String name = sc.next();
        System.out.print("������� ����������� ���������: ");
        int probability = sc.nextInt();
        try {
            Toy.createToy(name, probability);
            System.out.println();
        } catch (NameOrProbabilityException e) {
            System.out.println("\n������ � ����� ��� � �����������!");
            System.out.printf("������� '%s', '%d'\n\n", e.getName(), e.getProbability());
        }
    }
}
