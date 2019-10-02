import java.util.ArrayList;
import java.util.Scanner;

interface SumStrategy {
	public static final int[] result = {0,1,3,6,10,15,21,28,36,45,55};
	public int sum(int n);
}

class SumStrategyGauss implements SumStrategy {
	public int sum(int n) {
		return (n*(n+1))/2;
	}
}

class SumStrategyLoop implements SumStrategy {
	public int sum(int n) {
		int result = 0;
		for(int i = n; i > 0; i--) {
			if (i <= 10)
				return result + SumStrategy.result[i];
			result += i;
		}
		return result;
	}
}

class SumStrategyRecursion implements SumStrategy {
	public int sum(int n) {
		if (n <= 10) 
			return SumStrategy.result[n];
		return n + sum(n-1);
	}
}
class Strategy {
	private SumStrategy strategy;
	public void setStrategy(SumStrategy strategy) {
		this.strategy = strategy;
	}
	public int sum(int n) {
		return this.strategy.sum(n);
	}
}
public class Main {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a number: ");
		int n = scanner.nextInt();
		System.out.println("Select a Strategy to use");
		System.out.println("[1] Gauss function (n*(n+1))/2");
		System.out.println("[2] Loop function");
		System.out.println("[3] Recursive function");
		int choice = scanner.nextInt();
		Strategy strategy = new Strategy();
		switch (choice) {
			case 1:
				strategy.setStrategy(new SumStrategyGauss());
				break;
			case 2:
				strategy.setStrategy(new SumStrategyLoop());
				break;
			case 3:
				strategy.setStrategy(new SumStrategyRecursion());
				break;
		}
		System.out.print("The sum of the n first numbers is: " + strategy.sum(n));
		
	}
}
