import java.util.ArrayList;

enum RequestKind {
	Commercial, Technical, Administrative
}

class Request {
	protected int complexity;
	protected RequestKind kind;
	
	public Request(int complexity, RequestKind kind) {
		super();
		this.complexity = complexity;
		this.kind = kind;
	}
	
	public int getComplexity() {
		return this.complexity;
	}
	
	public RequestKind getKind() {
		return this.kind;
	}
	
	public void executeRequest() {
		System.out.println("Execution of a request of \"Complexity: " + complexity + " Kind: " + kind + "\" ");
	}
	
	@Override
	public String toString() {
		return "Request \"Complexity: " + complexity + " Kind: " + kind + "\"";
	}
}

interface Employee {
	boolean requestCanBeFulfilled (Request aRequest);
}

class Manager implements Employee {
	protected RequestKind requestKind;
	
	public Manager(RequestKind requestKind) {
		super();
		this.requestKind = requestKind;
	}
	
	public boolean requestCanBeFulfilled (Request aRequest) {
		return requestKind == aRequest.getKind();
	}
	
}

class Technician implements Employee {
	protected int maxComplexity;
	
	public Technician(int maxComplexity) {
		super();
		this.maxComplexity = maxComplexity;
	}
	
	public boolean requestCanBeFulfilled (Request aRequest) {
		return (aRequest.getKind() == RequestKind.Technical) 
			&& (aRequest.getComplexity() <= maxComplexity);
	}
}

class AdvancedTechnician implements Employee {
	protected int minComplexity, maxComplexity;
	
	public AdvancedTechnician(int minComplexity, int maxComplexity) {
		super();
		this.minComplexity = minComplexity;
		this.maxComplexity = maxComplexity;
	}
	
	public boolean requestCanBeFulfilled (Request aRequest) {
		return (((aRequest.getKind() == RequestKind.Technical) 
			&& (aRequest.getComplexity() >= minComplexity)) 
			|| (aRequest.getKind() == RequestKind.Commercial))
			&& (aRequest.getComplexity() <= maxComplexity);
	}
}

class RequestNotHandledException extends Exception {
	public RequestNotHandledException(String error) {
		super(error);
	}
}

class Departement {
	
	ArrayList<Employee> employees;
	
	public Departement() {
		super();
		this.employees = new ArrayList<>();
	}
	
	public void addEmployee(Employee e) {
		this.employees.add(e);
	}
	
	public void processRequest(Request r) throws RequestNotHandledException {
		for(Employee e : employees) {
			if (e.requestCanBeFulfilled (r)) {
				r.executeRequest();
				return;
			}
		}
		throw new RequestNotHandledException("Exception");

	}
}

public class Main {
	public static void main(String[] args) {
		Departement departement = new Departement();
		departement.addEmployee(new Technician(10));
		departement.addEmployee(new AdvancedTechnician(10, 30));
		departement.addEmployee(new Manager(RequestKind.Commercial));
		departement.addEmployee(new Manager(RequestKind.Administrative));
		Request request = new Request(5, RequestKind.Administrative);
		
		try {
			request = new Request(5, RequestKind.Administrative);
			departement.processRequest(request);
		} catch (RequestNotHandledException e) {
			System.out.println("Request " + request + " has not been executed!");
		}
		try {
			request = new Request(15, RequestKind.Administrative);
			departement.processRequest(request);
		} catch (RequestNotHandledException e) {
			System.out.println("Request " + request + " has not been executed!");
		}
		try {
			request = new Request(15, RequestKind.Commercial);
			departement.processRequest(request);
		} catch (RequestNotHandledException e) {
			System.out.println("Request " + request + " has not been executed!");
		}
		try {
			request = new Request(15, RequestKind.Technical);
			departement.processRequest(request);
		} catch (RequestNotHandledException e) {
			System.out.println("Request " + request + " has not been executed!");
		}
		try {
			request = new Request(55, RequestKind.Technical);
			departement.processRequest(request);
		} catch (RequestNotHandledException e) {
			System.out.println("Request " + request + " has not been executed!");
		}
		try {
			request = new Request(5, RequestKind.Technical);
			departement.processRequest(request);
		} catch (RequestNotHandledException e) {
			System.out.println("Request " + request + " has not been executed!");
		}
	}
}
