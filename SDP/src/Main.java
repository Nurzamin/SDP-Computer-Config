public class Main {
    public static void main(String[] args) {
        LegacyComputerConf pc= new LegacyComputerConf("Intel Core i7-14700K",850,8,
                new StorageConfig(StorageType.SSD,200),CoolingType.AIR,"RTX 4080","Intel",
                true,true,5);
        System.out.println(pc);
        ComputerConfig gaming_pc = new ComputerConfig.Builder(
                "Intel Core i7-14700K", 950,32, new StorageConfig(StorageType.SSD, 1000))
                .Gpu("RTX 4080")
                .Cooling_Type(CoolingType.LIQUID)
                .Warranty_years(5)
                .build();

        System.out.println(gaming_pc);
        System.out.println("\n");

        System.out.println(Director.budget());
        System.out.println(Director.gaming());
        System.out.println(Director.work());

        System.out.println("\n");

        try{
            new ComputerConfig.Builder("CPU",300 ,16, new StorageConfig(StorageType.SSD, 512))
                    .Gpu("RTX 4090")
                    .build();
        } catch(InvalidConfigException e){
            System.out.println("Error: " + e.getMessage());
        }

        try{
            new ComputerConfig.Builder(null,300 ,16, new StorageConfig(StorageType.SSD, 512))
                    .Gpu("RTX 4090")
                    .build();
        } catch(InvalidConfigException e){
            System.out.println("Error: " + e.getMessage());
        }

        try{
            new ComputerConfig.Builder("Intel Core i7-14700K",750 ,16, new StorageConfig(StorageType.SSD, 512))
                    .Gpu("RTX 4090")
                    .Cooling_Type(CoolingType.NONE)
                    .build();
        } catch(InvalidConfigException e){
            System.out.println("Error: " + e.getMessage());
        }
        try{
            new ComputerConfig.Builder("Intel Core i7-14700K",750 ,4, new StorageConfig(StorageType.SSD, 512))
                    .Gpu("RTX 4090")
                    .Cooling_Type(CoolingType.LIQUID)
                    .build();
        } catch(InvalidConfigException e){
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n");
        ComputerConfig test= new ComputerConfig.Builder("CPU", 400
                ,16, new StorageConfig(StorageType.SSD, 512))
                .build();
        System.out.println(test);

    }
}

