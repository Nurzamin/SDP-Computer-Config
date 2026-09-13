public class LegacyComputerConf {
    private String cpu;
    private int PSU_wattage;
    private int ram;
    private StorageType storage;
    private CoolingType coolingType;
    private int gpu;
    private String motherboard;
    private boolean wifi_module;
    private boolean bluetooth_module;
    private int warranty_years;


    public LegacyComputerConf(String cpu,int PSU_wattage,int ram,StorageType storage,
                              CoolingType coolingType,int gpu,String motherboard,boolean wifi_module,
                              boolean bluetooth_module,int warranty_years){
        this.cpu=cpu;
        this.PSU_wattage=PSU_wattage;
        this.ram=ram;
        this.storage=storage;
        this.coolingType=coolingType;
        this.gpu=gpu;
        this.motherboard=motherboard;
        this.wifi_module=wifi_module;
        this.bluetooth_module=bluetooth_module;
        this.warranty_years=warranty_years;
    }
}

