/**********************************************
 Workshop 6
 Course: JAC444 - Semester 4
 Last Name: Tse
 First Name: Chungon
 ID: 154928188
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 CHUNGON
 Date: 17 Mar 2023
 **********************************************/
package Task1;
/**
 * Rank num
 */
public class RankNum {
    private String name;
    private int rank;
    private char gender;

    /**
     * Constructor
     */
    public RankNum() {
        setName("");
        setRank(0);
        setGender(' ');
        setQuantity(0);
    }

    /**
     * RankNum extracting from text strings
     * @param src_Name      The actual baby name
     * @param src_Rank      extracts index 0 (ranking) and discards number of boys
     * @param src_Quantity  extracts index 0 (ranking) and discards number of girls
     * @param src_Gender    simply assigns M or F based on user input
     */
    public RankNum(String src_Name, int src_Rank, int src_Quantity, char src_Gender) {
        setName(src_Name);
        setRank(src_Rank);
        setQuantity(src_Quantity);
        setGender(src_Gender);
    }

    /**
     * getName
     * @return  name
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * @param name  name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getRank
     * @return  rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * setRank
     * @param rank  also dummy int
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * getGender
     * @return  gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * setGender
     * @param gender    gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * setQuantity
     * @param quantity  dummy int
     */
    public void setQuantity(int quantity) {
    }
}