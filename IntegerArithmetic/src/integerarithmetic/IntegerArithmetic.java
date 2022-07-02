/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integerarithmetic;

/**
 *
 * @author Ayush Chachan
 */
public class IntegerArithmetic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String a = "34";
        String b = "58";
        System.out.println("a = " + a + ", b = " + b);
        System.out.print("a - b = ");
        IntegerMultiplication T = new IntegerMultiplication();
        String ab = T.IntegerSubtraction(a, b);
        System.out.println(ab);
        
        a = "92964789756415219848485649846516589416519891865";
        b = "91456485464321644645878932121214654584651486484";
        
        System.out.println("a = " + a + ", b = " + b);
        System.out.print("a + b = ");
        ab = T.integerAddition(a, b);
        System.out.println(ab);
    }
    
}
 