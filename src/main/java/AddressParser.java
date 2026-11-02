import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

interface AddressParser {
    public List<DataModel> parseAddressList(String filePath);
}

class CsvParser implements AddressParser{

    @Override
    public List<DataModel> parseAddressList(String filePath) {
        try( Reader in = new FileReader(filePath);) {

            List<DataModel> addressList=new ArrayList<>();

            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder()
                    .setDelimiter(';')
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(in);
            for (CSVRecord record:records){
                String city =   record.get("city");
                String street = record.get("street");
                String inputFloor= record.get("floor");
                String inputHouse= record.get("house");

                int floor=Integer.parseInt(inputFloor);
                int house=Integer.parseInt(inputHouse);

                DataModel d=new DataModel(city,street,house,floor);
                addressList.add(d);
            }
            return addressList;
        }
        catch (Exception e){
            System.err.println("Ошибка при чтении CSV файла: " + e.getMessage());
            return null;
        }
    }
}

class XmlParser implements AddressParser{
    @Override
    public List<DataModel> parseAddressList(String filePath) {
        return List.of();
    }
}
