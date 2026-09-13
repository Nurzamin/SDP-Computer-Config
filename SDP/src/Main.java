public class Main {
    public static void main(String[] args) {
        LegacyComputerConf pc= new LegacyComputerConf("Intel Core i7-14700K",850,32,
                StorageType.SSD,CoolingType.LIQUID,"RTX 4080","Intel",
                true,true,5);
        System.out.println(pc);
    }
}

