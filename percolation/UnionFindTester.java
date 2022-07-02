package percolation;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chankit Chachan
 */
public class UnionFindTester {
    public static void main(String[] args) {
        String filename = "test/largeUF.txt";
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(filename));
            int N = Integer.parseInt(sc.nextLine());
            WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
            
            while (sc.hasNext()) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                uf.union(u, v);
                
            }
            System.out.println("number of connected components = " + uf.count());
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (sc != null) sc.close();
        }
        
                
    }
}
