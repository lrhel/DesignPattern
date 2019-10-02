import java.util.Scanner;

class Stack {
	protected final static int maxSize = 10;
	protected StackState state = new EmptyState(this);
	protected int buffer[] = new int[maxSize];
	protected int top = 0;
	
	public void push(int value) {
		StackState newState = state.push(value);
		state = newState;
	}
	public Integer pop() {
		Stack.ParamInt paramValue = this.new ParamInt();
		StackState newState = state.pop(paramValue);
		state = newState;
		return paramValue.value;
	}
	class ParamInt {
		public Integer value;
	}
}

abstract class StackState {
	protected Stack stack;
	
	StackState(Stack stack) {
		this.stack = stack;
	}
	
	abstract protected StackState push(int value);
	abstract protected StackState pop(Stack.ParamInt paramValue);
}

class EmptyState extends StackState {
	protected EmptyState(Stack stack) {
		super(stack);
	}
	
	@Override
	protected StackState pop(Stack.ParamInt paramValue) {
		paramValue.value=null;
		return this;
	}
	
	@Override
	protected StackState push(int value) {
		stack.buffer[stack.top]=value;
		stack.top++;
		return new RegularState(stack);
	}
}

class RegularState extends StackState {
	protected RegularState(Stack stack) {
		super(stack);
	}
	
	@Override
	protected StackState push(int value) {
		stack.buffer[stack.top]=value;
		stack.top++;
		if (stack.top == Stack.maxSize) {
			stack.top--;
			return new FullState(stack);
		}
		return this;
	}
	
	@Override
	protected StackState pop(Stack.ParamInt paramValue) {
		stack.top--;
		paramValue.value = stack.buffer[stack.top];
		if (stack.top <= 0) {
			stack.top = 0;
			return new EmptyState(stack);
		}
		return this;
	}
}

class FullState extends StackState {
	protected FullState(Stack stack) {
		super(stack);
	}
	
	@Override 
	protected StackState push(int value) {
		System.out.println("The Stack is full and the value " + value + " couldn't be added");
		return this;
	}
	
	@Override
	protected StackState pop(Stack.ParamInt paramValue) {
		paramValue.value = stack.buffer[stack.top];
		stack.top--;
		return new RegularState(stack);
	}
}

public class Main {
	public static void main(String args[]) {
		Stack stack = new Stack();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("[0] Push\n[1] Pop\n[2] Exit");
			int choice = scanner.nextInt();
			Stack.ParamInt paramInt = stack.new ParamInt();
			switch (choice) {
				case 0:
					System.out.print("Which number to push: ");
					stack.push(scanner.nextInt());
					break;
				case 1:
					paramInt.value = stack.pop();
					if(paramInt.value != null)
						System.out.println("The number poped is: " + paramInt.value);
					break;
				case 2:
					return;
			}
		}
	}
}
