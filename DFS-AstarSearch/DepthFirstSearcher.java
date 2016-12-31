import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Depth-First Search (DFS)
 * 
 * You should fill the search() method of this class.
 */
public class DepthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public DepthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main depth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// Stack implementing the Frontier list
		LinkedList<State> stack = new LinkedList<State>();
		
		
		ArrayList<State> succesors = new ArrayList<State>();	
		int i,j;
		int size;
		int ite;
		State element;
		State node;
		int node_exist = 0;
		ListIterator<State> litr = null;
		
		State curState = new State(maze.getPlayerSquare(), null, 0, 0);    //first element is start square (S)
		stack.push(curState);

		maxSizeOfFrontier = 1;
		

		while (!stack.isEmpty()) {
			curState = stack.pop();
			noOfNodesExpanded++;
			
			
			if(curState.isGoal(maze))
			{
				
				if(curState.getDepth() > maxDepthSearched)
					maxDepthSearched = curState.getDepth();
				
				//update the maze
				
				while(curState.getParent() != null)
				{				
					cost++;
					curState = curState.getParent();
					if(maze.getSquareValue(curState.getX(), curState.getY()) != 'S')
					maze.setOneSquare(curState.getSquare(), '.');					
				}
				
				return true;
			}
			else //if not goal
			{			
				i = curState.getX();
				j = curState.getY();
				explored[i][j] = true;
				if(curState.getDepth() > maxDepthSearched)
				maxDepthSearched = curState.getDepth();
				
				succesors = curState.getSuccessors(explored, maze);
				size = succesors.size();
				
				if(size !=0) 
				{	
				for(ite=0; ite <size ; ite++)
				{
					node = succesors.get(ite);
					
				   if (explored[node.getX()][node.getY()] !=true)
				   {
					   
					   litr=stack.listIterator();
					   node_exist = 0;
					   while(litr.hasNext())
					    {
						   element = litr.next();
						   if((element.getX() == node.getX()) && (element.getY()==node.getY()))
							   node_exist = 1;
					    }
					   if(node_exist==0)
						   stack.push(succesors.get(ite));
					   
				    }	
				}
			 }
				
				if(stack.size() > maxSizeOfFrontier)
					maxSizeOfFrontier = stack.size();
			}
			
			
		}
		System.out.println("No Solution");
		return false;
		
	}
	

}
