import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Statistics {
    private Map<DataModel,Integer> duplicates= new HashMap<>();
    private Map<String,Map<Integer,Integer>> floorsByTowns=new HashMap<>();

    private void computeStatistics(List<DataModel> addressList){

        for (DataModel address :addressList){
            //если есть, то записывается старое++ или 1 если нет
            duplicates.put(address,duplicates.getOrDefault(address,0)+1);

            //пытаемся достать кол-во этажей в городе,котрому принадлежит данный дом
            Map<Integer,Integer> floorCnt=floorsByTowns.get(address.getCity());

            //если мы еще мапу <int,int> под этот город, то создаем
            if(floorCnt==null){
                floorCnt=new HashMap<>();
                floorsByTowns.put(address.getCity(),floorCnt);
            }

            //обновляем кол-во домов по этажам
            floorCnt.put(address.getFloor(),floorCnt.getOrDefault(address.getFloor(),0)+1);

        }
    }

    public Map<DataModel, Integer> getDuplicates() {
        return duplicates;
    }

    public Map<String, Map<Integer, Integer>> getFloorsByTowns() {
        return floorsByTowns;
    }
}
