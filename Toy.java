import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Toy {
    private int id;
    private String name;
    private int quantity;
    private double frequency;

    public Toy(int id, String name, int quantity, double frequency) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.frequency = frequency;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getFrequency() {
        return frequency;
    }
    
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }


public static void addNewToy(List<Toy> toyList, int id, String name, int quantity, double frequency) {
        // проверяем, что игрушки с таким id еще нет в списке
        for (Toy toy : toyList) {
            if (toy.getId() == id) {
                System.out.println("Такая игрушка уже есть в списке.");
                return;
            }
        }
        // создаем новую игрушку
        Toy newToy = new Toy(id, name, quantity, frequency);
        // добавляем ее в список
        toyList.add(newToy);
        System.out.println("Игрушка успешно добавлена в список.");
    }
// Метод изменения веса игрушки (частоты выпадения):
public static void changeToyFrequency(List<Toy> toyList, int id, double frequency) {
        // ищем игрушку в списке
        for (Toy toy : toyList) {
            if (toy.getId() == id) {
                // изменяем частоту и выходим из метода
                toy.setFrequency(frequency);
                System.out.println("Частота игрушки с id " + id + " успешно изменена на " + frequency + ".");
                return;
            }
        }
        // если игрушка не найдена
        System.out.println("Игрушка с id " + id + " не найдена в списке.");
    }    
// Можно также добавить проверки на корректность введенных данных (например, что частота не может быть отрицательной или больше 100%).

// Для выбора призовой игрушки можно реализовать метод getRandomToy, который будет принимать список игрушек и возвращать одну случайную
// игрушку в соответствии с их частотами выпадения:

public static Toy getRandomToy(List<Toy> toyList) {
    double totalFrequency = 0;
    for (Toy toy : toyList) {
        totalFrequency += toy.getFrequency();
    }
    double randomValue = Math.random() * totalFrequency;
    for (Toy toy : toyList) {
        randomValue -= toy.getFrequency();
        if (randomValue <= 0) {
            return toy;
        }
    }
    // если вдруг не получилось выбрать игрушку
    return null;
}
// Метод возвращает одну случайную игрушку из списка в соответствии с их частотами выпадения.
// Вес каждой игрушки определяется отношением ее частоты к общей сумме частот всех игрушек в списке.
// Теперь мы можем вызвать этот метод из метода розыгрыша и записать выбранную игрушку в список призовых игрушек:

public static void playAndAddToPrizes(List<Toy> toyList, List<Toy> prizesList) {
    Toy selectedToy = getRandomToy(toyList);
    if (selectedToy != null) {
        // уменьшаем количество выбранной игрушки
        // selectedToy.setQuantity(selectedToy.getQuantity() - 1);
        // добавляем выбранную игрушку в список призовых игрушек
        prizesList.add(selectedToy);
        System.out.println("Призовая игрушка добавлена в список!");
    } else {
        System.out.println("Не удалось выбрать призовую игрушку.");
    }
}
// Метод принимает список всех игрушек (той, из которых будет выбрана одна призовая) и список призовых игрушек (в который будет добавлена выбранная игрушка). Если призовая игрушка выбрана успешно, ее количество уменьшается на 1 и она добавляется в список призовых игрушек.

// Метод получения призовой игрушки, ее удаления из списка призовых игрушек и записи в файл можно реализовать следующим образом:

public static void getPrize(List<Toy> prizesList, String filename) {
    // проверяем, что список призовых игрушек не пустой
    if (prizesList.isEmpty()) {
        System.out.println("Список призовых игрушек пуст.");
        return;
    }
    // получаем первую игрушку из списка
    Toy selectedToy = prizesList.get(0);
    // уменьшаем количество выбранной игрушки
    // selectedToy.setQuantity(selectedToy.getQuantity() - 1);
    // удаляем выбранную игрушку из списка призовых игрушек
    prizesList.remove(0);
    // записываем выбранную игрушку в файл
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))){
        out.println(selectedToy.getId() + "," + selectedToy.getName());
    } catch (IOException e) {
        System.out.println("Ошибка записи в файл.");
    }
    System.out.println("Выбрана игрушка \"" + selectedToy.getName() + "\". Записана в файл " + filename + ".");
}

// Метод принимает список призовых игрушек и имя файла, в который будет записана выбранная игрушка.
// Если список призовых игрушек пустой, метод просто завершается. Иначе он получает первую игрушку из списка, уменьшает ее количество на 1, удаляет ее из списка призовых игрушек, записывает в файл и выводит информацию о выбранной игрушке и файле, в который она записана.



// Для сохранения и загрузки информации об игрушках в текстовый файл можно использовать классы FileWriter и FileReader. 
// Для сохранения информации в файл нужно создать метод saveToFile, который будет записывать данные в файл в формате CSV:

public static void saveToFile(List<Toy> toyList, String filename) {
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
        for (Toy toy : toyList) {
            out.println(toy.getId() + "," + toy.getName() + "," + toy.getQuantity() + "," + toy.getFrequency());
        }
    } catch (IOException e) {
        System.out.println("Ошибка записи в файл.");
    }
}

// Метод принимает список игрушек и имя файла, в который нужно записать данные.
// Далее, с помощью объекта PrintWriter и метода println каждая игрушка записывается в файл в формате "id,name,quantity,frequency".













public static void main(String[] args) {
    
// Пример использования этих методов:
  
    List<Toy> toyList = new ArrayList<>();
    
    // добавляем новую игрушку
    Toy.addNewToy(toyList, 1, "Мяч", 10, 50.0);
    
    // изменяем частоту выпадения игрушки
    Toy.changeToyFrequency(toyList, 1, 60.0);

// Пример использования метода playAndAddToPrizes:
    toyList.add(new Toy(1, "Мяч", 10, 50.0));
    toyList.add(new Toy(2, "Кукла", 5, 30.0));
    toyList.add(new Toy(3, "Машинка", 8, 20.0));
    
    List<Toy> prizesList = new ArrayList<>();
    playAndAddToPrizes(toyList, prizesList);

// В этом примере из списка toyList будет выбрана одна игрушка с вероятностью
// 50% (если это будет мяч),
// 30% (если кукла) или
// 20% (если машинка). Если игрушка выбрана успешно, она добавится в список призовых игрушек.

// Пример использования метода getPrize:

prizesList.add(new Toy(1, "Мяч", 1, 50.0));
prizesList.add(new Toy(2, "Кукла", 2, 30.0));
prizesList.add(new Toy(3, "Машинка", 3, 20.0));

getPrize(prizesList, "prizes.txt");
// Этот пример получит первую игрушку из списка призовых игрушек, которой соответствует вероятность выпадения 50%.
// Если игрушка успешно выбрана, ее количество уменьшится на 1, она удалится из списка призовых игрушек и будет записана в файл "prizes.txt".

Scanner scanner = new Scanner(System.in);

System.out.print("Введите id новой игрушки: ");
int id = scanner.nextInt();

System.out.print("Введите название новой игрушки: ");
String name = scanner.next();

System.out.print("Введите количество новой игрушки: ");
int quantity = scanner.nextInt();

System.out.print("Введите частоту выпадения новой игрушки (в % от 100): ");
double frequency = scanner.nextDouble();

Toy.addNewToy(toyList, id, name, quantity, frequency);

System.out.print("Введите id игрушки, частоту которой нужно изменить: ");
int idToChange = scanner.nextInt();

System.out.print("Введите новую частоту (в % от 100): ");
double newFrequency = scanner.nextDouble();

Toy.changeToyFrequency(toyList, idToChange, newFrequency);

// Для запуска розыгрыша и получения призовой игрушки можно предложить пользователю ввести команду в консоль, например "play".
// При этом вызовется метод розыгрыша и, если игрушка выбрана успешно, она добавится в список призовых игрушек.
// Затем пользователь может вызвать метод получения призовой игрушки и ввести имя файла, в который будет записана игрушка.
// Пример интерфейса пользователя:

while (true) {
    System.out.println();
    System.out.println("Выберите действие:");
    System.out.println("1. Добавить новую игрушку и изменить ее вес");
    System.out.println("2. Розыгрыш приза");
    System.out.println("3. Получить приз");
    System.out.println("4. Выйти");

    String action = scanner.next();

    if (action.equals("1")) {
        System.out.print("Введите id новой игрушки: ");
        
        System.out.print("Введите название новой игрушки: ");
        
        System.out.print("Введите количество новой игрушки: ");
        
        System.out.print("Введите частоту выпадения новой игрушки (в % от 100): ");
        
        Toy.addNewToy(toyList, id, name, quantity, frequency);

        System.out.print("Введите id игрушки, частоту которой нужно изменить: ");
        
        System.out.print("Введите новую частоту (в % от 100): ");
        
        Toy.changeToyFrequency(toyList, idToChange, newFrequency);

    } else if (action.equals("2")) {
        Toy.playAndAddToPrizes(toyList, prizesList);
        System.out.println("Список призовых игрушек: " + prizesList);

    } else if (action.equals("3")) {
        System.out.print("Введите имя файла для записи приза: ");
        String filename = scanner.next();
        Toy.getPrize(prizesList, filename);

    } else if (action.equals("4")) {
        break;
    }
}
// В этом примере пользователь может выбрать действие (добавление новой игрушки, розыгрыш приза, получение приза или выход из программы) и
// следовать инструкциям ввода в консоль. При получении приза пользователь должен ввести имя файла, в который будет записана игрушка.
// Если пользователь выбрал розыгрыш приза, он может посмотреть список призовых игрушек в конце каждого розыгрыша.




    }
}