public class StorageConfig {
    private StorageType type;
    private int capacity;
    public StorageConfig(StorageType type,int capacity){
        if (capacity<=0){
            throw new IllegalArgumentException("capacity must be grater than 0");
        }
        this.type=type;
        this.capacity=capacity;
    }

    public StorageType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }
}
