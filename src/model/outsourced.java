package model;
/** outsourced class for storing outsourced parts. */
public class outsourced extends Part{
    private String companyName;

    /** When a new outsourced part is made.
     * @param id          the id
     * @param name        the name
     * @param price       the price
     * @param stock       the stock
     * @param min         the min
     * @param max         the max
     * @param companyName the company name
     */
    public outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** method sets the Company Name. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** method gets the Company Name. */
    public String getCompanyName() {
        return companyName;
    }
}