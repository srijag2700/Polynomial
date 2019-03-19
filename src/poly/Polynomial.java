package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) 
	{		
		Node temp = new Node(0,0,null);
		Node tempPointer = temp;
		Node newTerm = null;
		//ADDING THINGS
		while (poly1 != null || poly2 != null)
		{
			if (poly1 == null)
			{
				temp.next = poly2;
				break;
			}
			else if (poly2 == null)
			{
				temp.next = poly1;
				break;
			}
			
			else if (poly1.term.degree == poly2.term.degree)
			{
				newTerm = new Node(poly1.term.coeff + poly2.term.coeff, poly1.term.degree, null);
				poly1 = poly1.next;
				poly2 = poly2.next;
			}
			else if (poly1.term.degree < poly2.term.degree)
			{
				newTerm = new Node(poly1.term.coeff, poly1.term.degree, null);
				poly1 = poly1.next;
			}
			else if (poly2.term.degree < poly1.term.degree)
			{
				newTerm = new Node(poly2.term.coeff, poly2.term.degree, null);
				poly2 = poly2.next;
			}
			
			if (newTerm.term.coeff != 0)
			{
				temp.next = newTerm;
				temp = temp.next;
			}
		}
		return tempPointer.next;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node temp = new Node(0,0,null);
		Node ptr2 = poly2;
		Node newNode = null;
		while (poly1 != null)
		{
			while (poly2 != null)
			{
				newNode = new Node(poly1.term.coeff*poly2.term.coeff, poly1.term.degree+poly2.term.degree, null);
				temp = add(temp,newNode);
				poly2 = poly2.next;
			}
			poly1 = poly1.next;
			poly2 = ptr2;
		}
		return temp;
	}
	
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		Node ptr = poly;
		float sum = 0;
		while (ptr.next != null)
		{
			if (ptr.term.degree > 0)
			{
				sum += (ptr.term.coeff * Math.pow(x, ptr.term.degree));
			}
			else
			{
				sum += ptr.term.coeff;
			}
			
			ptr = ptr.next;
			
			if (ptr.next == null)
			{
				sum += (ptr.term.coeff * Math.pow(x, ptr.term.degree));
			}
		}
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
