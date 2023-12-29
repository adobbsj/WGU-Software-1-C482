package model;

/** InHouse class for storing in house parts. */
public class inHouse extends Part{
    private int machineId;

    /** when a new in house part is made.
     * @param id        the id
     * @param name      the name
     * @param price     the price
     * @param stock     the stock
     * @param min       the min
     * @param max       the max
     * @param machineId the machine id
     */
    public inHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** method sets the machine id. */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** method gets the machine id. */
    public int getMachineId() {
            return machineId;
        }
    }