/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coachj.utils;

/**
 * 
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date /2013
 */
public class Teste {
    
    public static void main(String[] args) {
        int[] numeros = {7, 8, 10, 12, 3, 6, 11, 2, 2, 13, 475, 34};
        int transporte;
        
        imprimirVetor(numeros);
        
        for (int i = 0; i < numeros.length; i++) {
            for (int j = i; j < numeros.length; j++) {
                if (numeros[j] > numeros[i]) {
                    transporte = numeros[i];
                    numeros[i] = numeros[j];
                    numeros[j] = transporte;
                    imprimirVetor(numeros);
                }
            }
        }
    }

    private static void imprimirVetor(int[] vetor) {
        System.out.print("Vetor: ");
        for(int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i] + ", ");
        }
        System.out.println();
    }
    
    
} // end Teste
