import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class VinaRentSystemTest {
    @Rule
    public Timeout globalTimeout = new Timeout(2000);

    @BeforeClass
    public static void setup() {
        System.out.println("Test for VinaRentSystem");
        System.out.println(new String(new char[80]).replace('\0', '-'));
    }

    @Test
    public void addBranchTest() throws Exception {
        System.out.println("\nTesting for addBranch(branchNumber, name)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add 2 normal branch
        vrs.addBranch("Branch1","BranchName1");
        vrs.addBranch("Branch2","BranchName2");

        // Branch with same name but different branch number is also acceptable
        vrs.addBranch("Branch3","BranchName1");

        System.out.println(vrs.toString("branch"));

        System.out.println("Print out error");
        try {
            // cannot add a branch with the existed branch number, though the name is unique
            vrs.addBranch("Branch1", "BranchName4");
        } catch (Exception e) {
            System.out.println(vrs.toString("branch"));
        }
    }

    @Test
    public void makeNeighborTest() throws Exception {
        System.out.println("\nTesting for makeNeighBor(branchNumber, name)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add some normal branches
        vrs.addBranch("Branch1","BranchName1");
        vrs.addBranch("Branch2","BranchName2");
        vrs.addBranch("Branch3","BranchName3");

        // make branch 1 & 2 become neighbor
        vrs.makeNeighbor("Branch1", "Branch2");

        // 1 branch can have many neighbors
        vrs.makeNeighbor("Branch1", "Branch3");

        System.out.println(vrs.toString("branch"));

        System.out.println("Print out error");

        // make neighbor with non-exist branches
        try {
            vrs.makeNeighbor("Branch0","Branch2");

        } catch (Exception e) {
            System.out.println(vrs.toString("branch"));
        }

        // cannot add 2 neighbored branches
        try {
            vrs.makeNeighbor("Branch2", "Branch1");
        } catch (Exception e) {
            System.out.println(vrs.toString("branch"));
        }
    }
}
