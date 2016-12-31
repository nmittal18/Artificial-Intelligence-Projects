import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.*;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		ArrayList<State> succesors = new ArrayList<State>();	
		
		
		int size;
		int ite;
		State element;
		State node;
		
		int node_exist = 0;
		int goalX, goalY, curX, curY;
		
		goalX = maze.getGoalSquare().X;
		goalY = maze.getGoalSquare().Y;
		
		double curfval, candfval;
		
		
		State curState = new State(maze.getPlayerSquare(), null, 0, 0);    //first element is start square (S)
		curX = curState.getX();
		curY = curState.getY();
		curfval = curState.getGValue() + (Math.abs(curX-goalX) + Math.abs(curY-goalY));
		StateFValuePair curpair = new StateFValuePair(curState, curfval);
		frontier.add(curpair);

		maxSizeOfFrontier = 1;
		maxDepthSearched = 1;
		// TODO initialize the root state and add
		// to frontier list
		// ...

		while (!frontier.isEmpty()) {
			
			curpair = frontier.poll();
			noOfNodesExpanded++;
			curState = curpair.getState();
			
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
				curX = curState.getX();
				curY = curState.getY();
				explored[curX][curY] = true;
				if(curState.getDepth() > maxDepthSearched)
					maxDepthSearched = curState.getDepth();
				
				succesors = curState.getSuccessors(explored, maze);
				size = succesors.size();
				
		if(size !=0) 
		{	
			for(ite=0; ite <size ; ite++)
			{
				node = succesors.get(ite);  //gives State..iterating thru generated nodes
				node_exist = 0;
				Iterator<StateFValuePair> it = frontier.iterator(); 
				
				while(it.hasNext())
			     {
			  	    element = it.next().getState();
				    if((element.getX() == node.getX()) && (element.getY()==node.getY()) && (node.getGValue() >= element.getGValue()))
				    {
						node_exist = 1;
				    }
				    if((element.getX() == node.getX()) && (element.getY()==node.getY()) && (node.getGValue() < element.getGValue()))
				    {
				    	node_exist = 0;
				    	it.remove();
				    }
			     }
					      
				if(node_exist==0) 
				{  
					candfval = node.getGValue()+ (Math.abs(node.getX()-goalX) + Math.abs(node.getY()-goalY));
					StateFValuePair candidate = new StateFValuePair(node, candfval);
					frontier.add(candidate); 
				}
			}
		}
				
				if(frontier.size() > maxSizeOfFrontier)
					maxSizeOfFrontier = frontier.size();
			}
			
			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
		}
		System.out.println("No Solution");
		return false;
		// TODO return false if no solution
	}

}
