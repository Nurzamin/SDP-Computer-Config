public class ComputerConfig {
    private String cpu;
    private int PSU_wattage;
    private int ram;
    private StorageConfig storage;

    private CoolingType coolingType;
    private String gpu;
    private String motherboard;
    private boolean wifi_module;
    private boolean bluetooth_module;
    private int warranty_years;

    private static final java.util.Set<String> HIGH_END_GPUS =
            java.util.Set.of("RTX 4080", "RTX 4090", "RTX 5080", "RTX 5090");
    private static final int MIN_PSU = 750;

    private ComputerConfig(Builder builder){
        this.cpu=builder.cpu;
        this.PSU_wattage=builder.PSU_wattage;
        this.ram= builder.ram;
        this.storage=builder.storage;
        this.coolingType=builder.coolingType;
        this.gpu=builder.gpu;
        this.motherboard=builder.motherboard;
        this.wifi_module= builder.wifi_module;
        this.bluetooth_module= builder.bluetooth_module;
        this.warranty_years= builder.warranty_years;
    }

    @Override
    public String toString() {
        return cpu + " " + PSU_wattage + " " + ram + " " + storage + " " + coolingType
                + " " + gpu + " " + motherboard + " " + wifi_module
                + " " + bluetooth_module + " " + warranty_years;
    }

    public static class Builder {
        private String cpu;
        private  int PSU_wattage;
        private int ram;
        private StorageConfig storage;

        private CoolingType coolingType = CoolingType.AIR;
        private String gpu = null;
        private String motherboard = "Intel";
        private boolean wifi_module = true;
        private boolean bluetooth_module = false;
        private int warranty_years= 1;

        public  Builder (String cpu, int PSU_wattage,int ram,StorageConfig storage){
            this.cpu=cpu;
            this.PSU_wattage=PSU_wattage;
            this.ram=ram;
            this.storage=storage;
        }
        public Builder Cooling_Type(CoolingType coolingType){
            this.coolingType=coolingType;
            return this;
        }
        public Builder Gpu(String gpu){
            this.gpu=gpu;
            return this;
        }
        public Builder Mother_board(String motherboard){
            this.motherboard=motherboard;
            return this;
        }
        public Builder Wifi_module(boolean wifi_module){
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
        private void validate() {
            if (cpu == null || cpu.isBlank())
                throw new InvalidConfigException("CPU must not be null.");
            if (ram < 4 || ram > 256)
                throw new InvalidConfigException("RAM must be 4-256 GB, got: " + ram);
            if (PSU_wattage < 300)
                throw new InvalidConfigException("PSU must be at least 300W, got  " + PSU_wattage);

            boolean highEndGpu = gpu != null && HIGH_END_GPUS.contains(gpu);
            if (highEndGpu && PSU_wattage < MIN_PSU)
                throw new InvalidConfigException("GPU '" + gpu + "' requires PSU >= " + MIN_PSU + "W, got: " + PSU_wattage);
            if (highEndGpu && coolingType == CoolingType.NONE)
                throw new InvalidConfigException("GPU " + gpu + " requires cooling.");
        }
        public ComputerConfig build(){
            validate();
            return new ComputerConfig(this);
        }

    }


}
