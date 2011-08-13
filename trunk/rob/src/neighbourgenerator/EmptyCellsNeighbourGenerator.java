package neighbourgenerator;

import java.util.ArrayList;
import java.util.HashSet;
import data.Problem;
import data.Solution;
import data.Supplier;


public class EmptyCellsNeighbourGenerator extends NeighbourGenerator implements DistancedNeighbourGenerator{
	private Problem problem;
	private int numSuppliers;
	private int numProducts;
	private int totalCells;
	private double randomizationFactor;
	
	private ArrayList<Integer> cellsToEmpty;
	private ArrayList<Integer> cellsNotEmptiable;
	
	private final int DEFAULT_RAND_FACTOR = 0;
	
	
	
	public EmptyCellsNeighbourGenerator(Problem problem) {
		this.problem=problem;
		this.numSuppliers = problem.getDimension();
		this.numProducts = problem.getNumProducts();
		this.randomizationFactor = DEFAULT_RAND_FACTOR;
		totalCells=numSuppliers*numProducts;
	}
	
	
	public EmptyCellsNeighbourGenerator(Problem problem, double randomizationFactor) {
		this(problem);
		this.randomizationFactor = randomizationFactor;
	}
	
	
	public Solution generate(Solution solution){
		final int DISTANCE = 1;
		return generate(solution, DISTANCE);
	}
	
	
	@Override
	public Solution generate(Solution s0, int distance){
		//lista delle celle da svuotare
		cellsToEmpty = new ArrayList<Integer>();
		cellsNotEmptiable = new ArrayList<Integer>();
		
		while(cellsToEmpty.size()<distance){
			if(cellsFinished()){
				System.err.println("Warning EmptyCellsNG: "+cellsToEmpty.size()+"celle svuotabili " +
						"[anzichè "+distance+"]");
				break;
			}
			
			//cella random
			int cell = (int)(Math.random()*totalCells+1);
			
			if(cellsToEmpty.contains(cell) || cellsNotEmptiable.contains(cell)){
				//la cella è già stata scelta o scartata precedentemente
			}else if(isEmpty(s0, cell) || !problem.cellIsEmptiable(cell, s0, cellsToEmpty)){
				//scarto la cella perchè vuota o non svuotabile
				cellsNotEmptiable.add(cell);
			}else{
				//cella ok: la aggiungo alla lista delle celle da svuotare
				cellsToEmpty.add(cell);
			}
		}
		//lista celle da svuotare riempita
		
		//risultato
		Solution s1 = new Solution(s0);
	
		//svuoto tutte le celle da svuotare
		for(int i=0; i<cellsToEmpty.size(); i++){
			empty(s1, cellsToEmpty.get(i));
		}
		
		return s1;
	}
	
	
	/*
	 * restituisce true se ho finito le celle
	 */
	private boolean cellsFinished(){
		if(cellsToEmpty.size()+cellsNotEmptiable.size()==totalCells){
			return true;
		}else{
			return false;
		}
	}

	
	private boolean isEmpty(Solution sol, int cell){
		int product = problem.getProductFromCell(cell);
		int supplier = problem.getSupplierFromCell(cell);
		if(sol.getQuantity(supplier, product)==0){
			return true;
		}else{
			return false;
		}
	}
	
	
	private void empty(Solution sol, int cell){
		int supplier = problem.getSupplierFromCell(cell);
		int product = problem.getProductFromCell(cell);
		
		Supplier orderedSuppliers[] = problem.sortByCurrentPrice(product, sol);
		
		int quantity = sol.getQuantity(supplier, product); 
			
		for(int i=1; i<=numSuppliers && quantity>0; i++){
			Supplier receivingSupplier = orderedSuppliers[i];
			int recSup = receivingSupplier.getId();
			int residualAvailability = problem.getSupplier(recSup).getResidual(product, sol);
			int recCell = problem.getCell(recSup, product);
			
			if(recSup!=supplier && !cellsToEmpty.contains(recCell) && residualAvailability>0){
				int quantityToMove = Math.min(quantity, residualAvailability);
				sol.moveQuantity(product, supplier, recSup, quantityToMove, problem);
				quantity -= quantityToMove; 
			}
		}		
	}
	
	
	/*
	 * Altera l'ordine in maniera casuale dei primi floor(ratio * (array.length-1)) elementi.
	 * array[0] non viene considerato.
	 */
	public Supplier[] scramble(Supplier array[]){
		//numero elementi da "mischiare"
		int elementsToMove = (int)((array.length-1)*randomizationFactor);
		
		Supplier result[] = new Supplier[array.length];
		
		//result[0] non usato
		result[0] = null;
		
		HashSet<Integer> alreadyChosen = new HashSet<Integer>();
		//"mischio" i primi elementsToMove elementi
		int randomSup;
		for(int i=1; i<=elementsToMove; i++){
			do{
				randomSup = (int)(Math.random()*elementsToMove+1);
			}while(alreadyChosen.contains(randomSup));
			
			result[i] = array[randomSup];
			alreadyChosen.add(randomSup);
		}
		
		//i restanti supllier li mantengo nello stesso ordine 
		for(int i=elementsToMove+1; i<=array.length-1; i++){
			result[i] = array[i];
		}
		
		return result;
	}
	
	
   public void setRandomizationFactor(double randomizationFactor){
	   this.randomizationFactor = randomizationFactor;
   }
}
