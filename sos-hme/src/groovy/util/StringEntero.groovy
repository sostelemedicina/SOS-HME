/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util

/**
 *
 * @author leacar21
 *
 */
class StringEntero implements Comparable{
        String str = null;
	int entero = 0;

        public int compareTo(Object o){
            return new Integer(entero).compareTo(new Integer(((StringEntero)o).entero));
        }
}

