public class Builder {
    private String cpu;
    private int PSU_wattage;
    private int ram;
    private StorageType storage;

    private CoolingType coolingType = CoolingType.AIR;
    private String gpu = null;
    private String motherboard = "Intel";
    private boolean wifi_module = true;
    private boolean bluetooth_module = false;
    private int warranty_years= 1;

    public  Builder (String cpu, int PSU_wattage,int ram,StorageType storage){
        this.cpu=cpu;
        this.PSU_wattage=PSU_wattage;
        this.ram=ram;
        this.storage=storage;
    }
    public Builder Gpu(String gpu){
        this.gpu=gpu;
        return this;
    }
    public Builder Mother_board(String motherboard){
        this.motherboard=motherboard;
        return this;
    }
    public  Builder Wifi_module(boolean wifi_module){
        this.wifi_module=wifi_module;
        return this;
    }
    public Builder Bluetooth_module(boolean bluetooth_module){
        this.bluetooth_module=bluetooth_module;
        return this;
    }
    public Builder Warranty_years(int warranty_years){
        this.warranty_years=warranty_years;
        return this;
    }
    public ComputerConfig build(){
        return new ComputerConfig(this);
    }

}
