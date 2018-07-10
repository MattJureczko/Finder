import javax.swing.*;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class FindMe {

    public static void main(String[] args) throws Exception {
        // System.out.println(searchMe());
        Findet obiekt = new Findet();
        obiekt.printout(obiekt.getJavaList());
        System.out.println(obiekt.countJava(obiekt.getLista()));
        System.out.println(obiekt.getNumberOfJob());
        // System.out.println(countJava(searchMe()));
        String answer = new String();
        int start = obiekt.countJava(obiekt.getLista());
        for (int k = 0; k < 3;k++) {
            TimeUnit.SECONDS.sleep(10);
            obiekt.refreshMe();
            answer = "" + obiekt.getNumberOfJob();
            if (start != obiekt.countJava(obiekt.getLista())) {
                JOptionPane.showMessageDialog(null, answer);
            } else {
                System.out.println(LocalTime.now().toString() + " no changes " + k);
            }
        }
        JOptionPane.showMessageDialog(null, "end of loop");
    }
}
