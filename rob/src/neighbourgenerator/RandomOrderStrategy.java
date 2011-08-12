package neighbourgenerator;

import data.Problem;
import data.Solution;

public class RandomOrderStrategy extends SupplierOrderStrategy {

	public RandomOrderStrategy(Problem problem) {
		super(problem);
	}

	/**
	 * Questo metodo crea una lista di dimensione size contenente ID di fornitori presi casualmente
	 * tra tutti i fornitori del problema che sono presenti in soluzione con almeno un'unità 
	 * di prodotto acquistata
	 */
	@Override
	IdList createList(Solution solution, int size) {
		IdList list = new IdList(size);
		for (int i=0;i<size;i++) {
			int randomSup = problem.getRandomSupplierId(list.getHashSet());
			int total=solution.totalQuantityBought(randomSup);
			if (total==0) {
				i--;
				continue;
			}
			list.add(randomSup,i);
		}
		return list;
	}
}
