import java.util.List;
import java.util.Map;
import java.util.Scanner;

class ConsoleUIService {
    private ConsoleUIService() {}

    private static class Holder {
        private static final ConsoleUIService INSTANCE = new ConsoleUIService();
    }

    public static ConsoleUIService getInstance() {
        return Holder.INSTANCE;
    }

    private Scanner scanner = new Scanner(System.in);
    private Statistics statistics = Statistics.getInstance();
    private Map<DataModel, Integer> duplicates = null;
    private Map<String, Map<Integer, Integer>> floorsByTowns = null;

    public void startWorkLoop() {

        System.out.println("Введите путь до файла (xml или csv) или exit чтобы завершить работу проограммы");

        while (true) {
            System.out.println("Ввод: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Arrivederci!");
                break;
            }

            try {
                AddressParser parser = null;

                if (input.endsWith(".xml")) {
                    parser = new XmlParser();
                }
                else if (input.endsWith(".csv")) {
                    parser = new CsvParser();
                } else {
                    System.out.println("Программа поддерживает только xml и csv файлы");
                    continue;
                }

                long startTime = System.currentTimeMillis();

                List<DataModel> adressList = parser.parseAddressList(input);

                if (adressList == null || adressList.isEmpty()) {
                    System.out.println("Файл пуст или не прочитался");
                    continue;
                }

                statistics.computeStatistics(adressList);

                duplicates = statistics.getDuplicates();
                System.out.println("---Строки дубликаты---");
                for (DataModel address : duplicates.keySet()) {
                    int repCnt = duplicates.get(address);
                    if (repCnt > 1) {
                        System.out.println("Повторов строки: " + repCnt);
                        System.out.println(address.getCity() + " " + address.getStreet() + " "
                                + address.getHouse() + " " + address.getFloor() + "\n");
                    }
                }

                floorsByTowns = statistics.getFloorsByTowns();
                System.out.println("---Кол-во домов по этажам в каждом городе---");
                for (String town : floorsByTowns.keySet()) {
                    System.out.println("^^^^^");
                    System.out.println(town);
                    Map<Integer,Integer> mapForEachTown = floorsByTowns.get(town);
                    for (int floors:mapForEachTown.keySet()) {
                        System.out.println(floors + "-этажных зданий: " + mapForEachTown.get(floors));
                    }
                }
                System.out.println("^^^^^");


                long endTime = System.currentTimeMillis();
                long workTime = endTime - startTime;
                System.out.println("Время обработки программы составило: " + workTime);


            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Проверьте путь и попробуйте еще раз!");
            }
        }
    }
}
