public class Director {

    public static ComputerConfig budget() {
        return new ComputerConfig.Builder("Intel Core i3-13100", 400, 8,
                new StorageConfig(StorageType.SSD, 256))
                .build();
    }

    public static ComputerConfig gaming() {
        return new ComputerConfig.Builder("Intel Core i7-14700K", 850,32,
                new StorageConfig(StorageType.SSD, 1000))
                .Gpu("RTX 4080")
                .Cooling_Type(CoolingType.LIQUID)
                .Warranty_years(3)
                .build();
    }

    public static ComputerConfig work() {
        return new ComputerConfig.Builder("AMD Threadripper 7960X",1000, 128,
                new StorageConfig(StorageType.SSD, 2000))
                .Gpu("RTX 4000 Ada")
                .Cooling_Type(CoolingType.LIQUID)
                .Warranty_years(5)
                .build();
    }
}