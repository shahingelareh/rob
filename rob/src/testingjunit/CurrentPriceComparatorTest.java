package testingjunit;

import static org.junit.Assert.*;
import io.ProblemParser;

import org.junit.Before;
import org.junit.Test;

import rob.CurrentPriceComparator;
import rob.Problem;
import rob.Solution;
import rob.Supplier;

public class CurrentPriceComparatorTest {
	ProblemParser pp = new ProblemParser(Constants.INPUT_PATH);
	
	/*
	 * sup1 < sup2
	 */
	@Test
	public void testCompare1() {
		final String PROBLEM_NAME = "problema4.txt";
		Problem problem = null;
		try {
			problem = pp.parse(PROBLEM_NAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final int sup1Id = 1;
		Supplier sup1 = problem.getSupplier(sup1Id);
		final int sup2Id = 2;
		Supplier sup2 = problem.getSupplier(sup2Id);
		
		int s0[] = {0, 0, 0, 0};
		int s1[] = {0, 51, 28, 0};
		int s2[] = {0, 9, 12, 60};
		int matrix[][] = new int[3][];
		matrix[0] = s0;
		matrix[1] = s1;
		matrix[2] = s2;
		
		Solution sol = new Solution(matrix, problem);
		assertTrue(sol.isAdmissible(problem));
		
		final int product1 = 1;
		CurrentPriceComparator comparator = new CurrentPriceComparator(sol, product1);
		assertTrue(comparator.compare(sup1,sup2) < 0);
	}
	
	/*
	 * sup1 = sup2
	 */
	@Test
	public void testCompare2() {
		final String PROBLEM_NAME = "problema5.txt";
		Problem problem = null;
		try {
			problem = pp.parse(PROBLEM_NAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final int sup1Id = 1;
		Supplier sup1 = problem.getSupplier(sup1Id);
		final int sup2Id = 2;
		Supplier sup2 = problem.getSupplier(sup2Id);
		
		int s0[] = {0, 0, 0, 0};
		int s1[] = {0, 51, 28, 0};
		int s2[] = {0, 9, 12, 60};
		int matrix[][] = new int[3][];
		matrix[0] = s0;
		matrix[1] = s1;
		matrix[2] = s2;
		
		Solution sol = new Solution(matrix, problem);
		assertTrue(sol.isAdmissible(problem));
		
		final int product2 = 2;
		CurrentPriceComparator comparator = new CurrentPriceComparator(sol, product2);
		assertTrue(comparator.compare(sup1,sup2) == 0);	
	}
	
	/*
	 * sup1 > sup2
	 */
	@Test
	public void testCompare3() {
		final String PROBLEM_NAME = "problema5.txt";
		Problem problem = null;
		try {
			problem = pp.parse(PROBLEM_NAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final int sup1Id = 1;
		Supplier sup1 = problem.getSupplier(sup1Id);
		final int sup2Id = 2;
		Supplier sup2 = problem.getSupplier(sup2Id);
		
		int s0[] = {0, 0, 0, 0};
		int s1[] = {0, 51, 28, 0};
		int s2[] = {0, 9, 12, 60};
		int matrix[][] = new int[3][];
		matrix[0] = s0;
		matrix[1] = s1;
		matrix[2] = s2;
		
		Solution sol = new Solution(matrix, problem);
		assertTrue(sol.isAdmissible(problem));
		
		final int product3 = 3;
		CurrentPriceComparator comparator = new CurrentPriceComparator(sol, product3);
		assertTrue(comparator.compare(sup1,sup2) > 0);	
	}

}
