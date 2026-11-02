class DataModel {
    private String city;
    private String street;
    private int house;
    private int floor;

    DataModel(String city,String street,int house,int floor){
        this.city=city;
        this.street=street;
        this.house=house;
        this.floor=floor;
    }

    public int getFloor() {
        return floor;
    }

    public int getHouse() {
        return house;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }


}
