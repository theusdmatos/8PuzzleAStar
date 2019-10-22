
package mainPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author matos
 */
public class StatePuzzle {

	public static final int TOTAL_COLUNAS = 3;
	public static final int TOTAL_LINHAS = 3;
	public static final int TOTAL_NUMEROS = TOTAL_COLUNAS * TOTAL_LINHAS;
	int[][] state;
	int cost;
	int h;
	int value;
	int rowItinial;
	int columnInit;
	StatePuzzle father = null;

	public StatePuzzle(int[][] values) {
		this.state = Arrays.copyOf(values, values.length);
		this.cost = 0;
		this.heuristic();
		this.calculeValue();
	}

	public StatePuzzle(int[][] values, int cost, int lineItinial, int columnInit, StatePuzzle father) {
		this.state = new int[TOTAL_LINHAS][TOTAL_COLUNAS];
		for (int i = 0; i < TOTAL_LINHAS; i++) {
			for (int j = 0; j < TOTAL_COLUNAS; j++) {
				this.state[i][j] = values[i][j];
			}
		}
		this.cost = cost;
		this.rowItinial = lineItinial;
		this.columnInit = columnInit;
		this.father = father;
	}

	public void outPrint() {
            
		
             for (int i = 0; i < TOTAL_LINHAS; i++) {
			for (int j = 0; j < TOTAL_COLUNAS; j++) {
			   
                            System.out.print(" |  " + this.state[i][j] + "  |  ");
                            
			}
			System.out.print("\n");
		}
                     
          
	}

	public List<StatePuzzle> createChildren() {
		List<StatePuzzle> children = new ArrayList<>();

		this.positionIni();

		if (this.rowItinial != 0) {
			children.add(this.createChildren(this.rowItinial - 1, this.columnInit));
		}

		if (this.rowItinial < (TOTAL_LINHAS - 1)) {
			children.add(this.createChildren(this.rowItinial + 1, this.columnInit));
		}

		if (this.columnInit != 0) {
			children.add(this.createChildren(this.rowItinial, this.columnInit - 1));
		}

		if (this.columnInit < (TOTAL_COLUNAS - 1)) {
			children.add(this.createChildren(this.rowItinial, this.columnInit + 1));
		}

		return children;
	}

	private void positionIni() {
		for (short i = 0; i < TOTAL_LINHAS; i++) {
			for (short j = 0; j < TOTAL_COLUNAS; j++) {
				if (this.state[i][j] == 0) {
					this.rowItinial = i;
					this.columnInit = j;
				}
			}
		}
	}

	private StatePuzzle createChildren(int row, int column) {
		StatePuzzle  puzzle = moreCost();
		puzzle.move(row, column);
		puzzle.heuristic();
		puzzle.calculeValue();
		return puzzle;
	}

	private void calculeValue() {
		this.value = this.h + this.cost;
	}

	private void heuristic() {
		int[][] stateFinal = JFramePuzzle.isGoal;
		int count = 0;
		for (int i = 0; i < TOTAL_LINHAS; i++) {
			for (int j = 0; j < TOTAL_COLUNAS; j++) {
				int value = state[i][j];
				if (value != 0) {
					int linhaDistante = Math.abs(i - lFinal(stateFinal, value));
					int colunaDistante = Math.abs(j - cFinal(stateFinal, value));
					count +=   linhaDistante + colunaDistante;
				}
			}
		}
		this.h = count;
	}

	public int cFinal(int[][] stateFinal, int value) {
		for (int i = 0; i < TOTAL_LINHAS; i++) {
			for (int j = 0; j < TOTAL_COLUNAS; j++) {
				if (stateFinal[i][j] == value) {
					return j;
				}
			}
		}
		return -1;
	}

	public int lFinal(int[][] stateFinal, int value) {
		for (int i = 0; i < TOTAL_LINHAS; i++) {
			for (int j = 0; j < TOTAL_COLUNAS; j++) {
				if (stateFinal[i][j] == value) {
					return i;
				}
			}
		}
		return -1;
	}

	public StatePuzzle move(int rowFinal, int columnFinal) {
		int valueMoved = this.state[rowFinal][columnFinal];
		this.state[this.rowItinial][this.columnInit] = valueMoved;
		this.state[rowFinal][columnFinal] = 0;
		return this;
	}

	public StatePuzzle moreCost() {
		return new StatePuzzle(this.state, this.cost + 1, this.rowItinial, this.columnInit, this);
	}

	
	public boolean isSolvable() {
	
		int invCount = this.getInvCount(this.state);
	
		return (invCount % 2 == 0);
	}

	private int getInvCount(int array[][]) {
		int inversaoCont = 0;
		int arr[] = new int[TOTAL_NUMEROS];
		int cont = 0;
		for (int i = 0; i < TOTAL_LINHAS; i++) {
			for (int j = 0; j < TOTAL_COLUNAS; j++) {
				arr[cont] = array[i][j];
				cont++;
			}
		}
		for (int i = 0; i < TOTAL_NUMEROS - 1; i++)
			for (int j = i + 1; j < TOTAL_NUMEROS; j++)
		
				if (arr[i] > 0 && arr[j] > 0 && arr[i] > arr[j])
					inversaoCont++;
		return inversaoCont;
	}

	public boolean equalState(int[][] stateFinal) {
		return Arrays.deepEquals(stateFinal, this.state);
	}

	@Override
	public String toString() {
		String tab = "";
		for (int i = 0; i < TOTAL_LINHAS; i++) {
			for (int j = 0; j < TOTAL_COLUNAS; j++) {
				tab = tab + " |  " + this.state[i][j] + "  |  ";
			}
			tab = tab + "\n";
		}
		return tab;
	}


	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatePuzzle other = (StatePuzzle) obj;
		if (!Arrays.deepEquals(state, other.state))
			return false;
		return true;
	}

}
