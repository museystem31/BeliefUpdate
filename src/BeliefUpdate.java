import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BeliefUpdate {
	
	// given an initial belief state, a sequence of actions and observations, compute a new belief state
	public static void updateBelief(State[] beliefState,String[] actions, String[] observations){
		String curAction = "";
		String curObs = "";
		
		for(int j = 0; j < actions.length; j++){
			curAction = actions[j];
			curObs = observations[j];
			State[] copiedStates= new State[11];
			
			for(int k = 0; k < 11; k++){
				State state = new State(beliefState[k].col,beliefState[k].row,beliefState[k].beliefVal);
				copiedStates[k] = state;
			}

			for(int i = 0; i < 11 ; i++){
				updateBeliefForEach(copiedStates, beliefState[i], curAction, curObs);
			}
			
			Double sum = 0.0;
			for(int z = 0; z < 11; z++){
				sum = sum + beliefState[z].beliefVal;
			}
			
			
			// normalize the belief value of each state
			for(int w = 0; w < 11; w++){
				Double valBeforeNormalized = beliefState[w].beliefVal;
				beliefState[w].updateBeliefVal(valBeforeNormalized/sum);
			}
			
		}
	}
	
	// update belief value for each state
	public static void updateBeliefForEach(State[] states, State state, String action, String obs){
		
		//System.out.println("current state:" + "(" + state.col + "," + state.row + ")");
		Double updatedBeliefVal=0.0;
	
		State sr = new State(0,0,0.0);
		State sl = new State(0,0,0.0);
		State sa = new State(0,0,0.0);
		State sb = new State(0,0,0.0);
		
		// s' on right
		if((state.col==1 && state.row ==2) || (state.col+1 >4)){
			sr = state;
		}else{
			for(int i = 0; i < states.length ; i++){
				if(states[i].col == state.col + 1 && states[i].row == state.row){
					sr = states[i];
					break;
				}
			}		
		}
		//System.out.println("state on right:(" + sr.col + "," + sr.row + ")");
		
		// s' on left
		if((state.col==3 && state.row ==2) || (state.col-1 < 1)){
			sl = state;
		}else{
			for(int i = 0; i < states.length ; i++){
				if(states[i].col == state.col - 1 && states[i].row == state.row){
					sl = states[i];
					break;
				}
			}			
		}
		
		//System.out.println("state on left:(" + sl.col + "," + sl.row + ")");
		// s' above
		if((state.col==2 && state.row ==1) || (state.row+1 > 3)){
			sa = state;
		}else{
			for(int i = 0; i < states.length ; i++){
				if(states[i].col == state.col && states[i].row == state.row + 1){
					sa = states[i];
					break;
				}
			}		
		}
		//System.out.println("state above:(" + sa.col + "," + sa.row + ")");
		
		// s' below
		if((state.col==2 && state.row ==3) || (state.row-1 < 1)){
			sb = state;
		}else{
			for(int i = 0; i < states.length ; i++){
				if(states[i].col == state.col && states[i].row == state.row - 1){
					sb = states[i];
					break;
				}
			}				
		}
		//System.out.println("state below:(" + sb.col + "," + sb.row + ")");
		
		// transition probabilities
		Double probr;
		Double probl;
		Double proba;
		Double probb;
		
		if(action == "up"){
			// sr
			if((sr.col == 4 && sr.row == 3) || (sr.col == 4 && sr.row == 2)){
				if((state.col == 4 && state.row == 3) || (state.col == 4 && state.row == 2)){
					probr = 1.0;
				}else{
					probr = 0.0;
				}
			
			}else{
				probr = 0.1;
			}
			
			// sl
			if((sl.col == 4 && sl.row == 3) || (sl.col == 4 && sl.row == 2)){
				probl = 0.0;
			
			}else{
				probl = 0.1;
			}
			
			// sa
			if((sa.col == 4 && sa.row == 3) || (sa.col == 4 && sa.row == 2)){
				if((state.col == 4 && state.row == 3) || (state.col == 4 && state.row == 2)){
					proba = 1.0;
				}else{
					proba = 0.0;
				}
			
			}else if(sa.col == state.col && sa.row == state.row ){
				proba = 0.8;
			}else{
				proba = 0.0;
			}
			
			// sb
			if((sb.col == 4 && sb.row == 3) || (sb.col == 4 && sb.row == 2)){
				probb = 0.0;
			}else if(sb.col == state.col && sb.row == state.row){
				probb = 0.0;
			}else{
				probb = 0.8;
			}
		}
		
		else if(action == "down"){
			// sr
			if((sr.col == 4 && sr.row == 3) || (sr.col == 4 && sr.row == 2)){
				if((state.col == 4 && state.row == 3) || (state.col == 4 && state.row == 2)){
					probr = 1.0;
				}else{
					probr = 0.0;
				}
			}else{
				probr = 0.1;
			}
			
			// sl
			if((sl.col == 4 && sl.row == 3) || (sl.col == 4 && sl.row == 2)){
				probl = 0.0;
			}else{
				probl = 0.1;
			}
			
			// sa
			if((sa.col == 4 && sa.row == 3) || (sa.col == 4 && sa.row == 2)){
				proba = 0.0;
			}else if(sa.col == state.col && sa.row == state.row){
				proba = 0.0;
			}else{
				proba = 0.8;
			}
			
			// sb
			if((sb.col == 4 && sb.row == 3) || (sb.col == 4 && sb.row == 2)){
				probb = 0.0;
			}else if(sb.col == state.col && sb.row == state.row ){
				probb = 0.8;
			}else{
				probb = 0.0;
			}
		}
		
		else if(action == "left"){
			// sr
			if((sr.col == 4 && sr.row == 3) || (sr.col == 4 && sr.row == 2)){
				probr = 0.0;
			}else if(sr.col == state.col && sr.row == state.row){
				probr = 0.0;
			}else{
				probr = 0.8;
			}
			
			// sl
			if((sl.col == 4 && sl.row == 3) || (sl.col == 4 && sl.row == 2)){
				probl = 0.0;
			}else if(sl.col == state.col && sl.row == state.row){
				probl = 0.8;
			}else{
				probl = 0.0;
			}
			// sa
			if((sa.col == 4 && sa.row == 3) || (sa.col == 4 && sa.row == 2)){
				if(state.col == 4 && state.row == 3){
					proba = 1.0;
				}else{
					proba = 0.0;
				}
			}else{
				proba = 0.1;
			}
			// sb
			if((sb.col == 4 && sb.row == 3) || (sb.col == 4 && sb.row == 2)){
				probb = 0.0;
			}else{
				probb = 0.1;
			}
		}
		
		else{
			// sr
			if((sr.col == 4 && sr.row == 3) || (sr.col == 4 && sr.row == 2)){
				if((state.col == 4 && state.row == 3) || (state.col == 4 && state.row == 2)){
					probr = 1.0;
				}else{
					probr = 0.0;
				}
			}else if(sr.col == state.col && sr.row == state.row ){
				probr = 0.8;
			}else{
				probr = 0.0;
			}
			
			// sl
			if((sl.col == 4 && sl.row == 3) || (sl.col == 4 && sl.row == 2)){
				probl = 0.0;
			}else if(sl.col == state.col && sl.row == state.row){
				probl = 0.0;
			}else{
				probl = 0.8;
			}
			
			// sa
			if((sa.col == 4 && sa.row == 3) || (sa.col == 4 && sa.row == 2)){
				if(state.col == 4 && state.row == 3){
					proba = 1.0;
				}else{
					proba = 0.0;
				}
			}else{
				proba = 0.1;
			}
			
			// sb
			if((sb.col == 4 && sb.row == 3) || (sb.col == 4 && sb.row == 2)){
				probb = 0.0;
			}else{
				probb = 0.1;
			}
		}
		
		//System.out.println("prob of getting from sr to s:" + probr);
		//System.out.println("prob of getting from sl to s:" + probl);
		//System.out.println("prob of getting from sa to s:" + proba);
		//System.out.println("prob of getting from sb to s:" + probb);
		
		// computes the updated belief value
		Double sensorProb = sensor(obs,state);
		Double r = probr * sr.beliefVal;
		Double l = probl * sl.beliefVal;
		Double a = proba * sa.beliefVal;
		Double b = probb * sb.beliefVal;
		
		updatedBeliefVal = sensorProb * (r + l + a + b);
		//updatedBeliefVal = (r + l + a + b);		
		//System.out.println("b(s') = " + sensorProb + "(" + probr + "*" + sr.beliefVal + "+"  + probl + "*" + sl.beliefVal + "+" + proba + "*" + sa.beliefVal + "+" + probb + "*" + sb.beliefVal + ")" + "=" + updatedBeliefVal);
		
		state.updateBeliefVal(updatedBeliefVal);
	}
	
	// compute the probability of getting certain evidence given a state
	public static Double sensor(String obs, State state){
		Double sensorProb = 0.0;
		
		// Non-terminal in third column
		if(state.col == 3){
			if(obs=="2"){
				sensorProb = 0.1;
			}else if(obs == "1"){
				sensorProb = 0.9;
			}else {
				sensorProb = 0.0;
			}
		}
		// All other non-terminal
		else if(state.col != 4 && (state.row != 2 || state.row != 3)){
			if(obs=="2"){
				sensorProb = 0.9;
			}else if(obs == "1"){
				sensorProb = 0.1;
			}else {
				sensorProb = 0.0;
			}			
		}

		// Terminal
		else{
			if(obs == "end"){
				sensorProb = 1.0;
			}else{
				sensorProb = 0.0; 
			}
		}
		return sensorProb;
	}
	
	
	public static void main (String[] args){
		
		String newLine = System.getProperty("line.separator");
		
		// parameters of a state (for printing use)
		int col;
		int row;
		Double beliefVal;
		
		// tool for rounding off decimals
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		

		//*** test 1:
		System.out.println("first sequence:");
		// create a new Grid World
		GridWorld gridWorld = new GridWorld();
		
		// initialize non-terminal states' belief value to 0.111
		for(int i = 0; i < gridWorld.gridWorld.length;i++){
			gridWorld.gridWorld[i].updateBeliefVal(0.111);
		}
		
		// initialize terminal states' belief value to 0.0
		gridWorld.gridWorld[9].updateBeliefVal(0.0);
		gridWorld.gridWorld[10].updateBeliefVal(0.0);	
		
		// specifying actions and observations
		String[] act1 = {"up","up","up"};
		String[] obs1 = {"2","2","2"};
		
		// update belief val for each state given actions and observations
		updateBelief(gridWorld.gridWorld,act1, obs1);
	
		for(int i = 0; i < gridWorld.gridWorld.length; i++){
			col = gridWorld.gridWorld[i].col;
			row = gridWorld.gridWorld[i].row;
			beliefVal = gridWorld.gridWorld[i].beliefVal;
			System.out.println("b("+col+","+row+")=" + df.format(beliefVal));
			//System.out.println("b("+col+","+row+")=" + beliefVal);
		}
		
		//*** test 2:
		System.out.println(newLine + "second sequence:");
		
		GridWorld gridWorld2 = new GridWorld();
		

		for(int i = 0; i < gridWorld2.gridWorld.length;i++){
			gridWorld2.gridWorld[i].updateBeliefVal(0.111);
		}
		
		gridWorld2.gridWorld[9].updateBeliefVal(0.0);
		gridWorld2.gridWorld[10].updateBeliefVal(0.0);	
		

		String[] act2 = {"up","up","up"};
		String[] obs2 = {"1","1","1"};
		

		updateBelief(gridWorld2.gridWorld,act2, obs2);
	
		for(int i = 0; i < gridWorld2.gridWorld.length; i++){
			col = gridWorld2.gridWorld[i].col;
			row = gridWorld2.gridWorld[i].row;
			beliefVal = gridWorld2.gridWorld[i].beliefVal;
			System.out.println("b("+col+","+row+")=" + df.format(beliefVal));
			//System.out.println("b("+col+","+row+")=" + beliefVal);
		}	


		//*** test 3:
		System.out.println(newLine + "third sequence:");
		
		GridWorld gridWorld3 = new GridWorld();
		

		for(int i = 0; i < gridWorld3.gridWorld.length;i++){
			gridWorld3.gridWorld[i].updateBeliefVal(0.0);
		}
		
		// s0 = (2,3)
		gridWorld3.gridWorld[4].updateBeliefVal(1.0);

		String[] act3 = {"right","right","up"};
		String[] obs3 = {"1","1","end"};
		

		updateBelief(gridWorld3.gridWorld,act3, obs3);
	
		for(int i = 0; i < gridWorld3.gridWorld.length; i++){
			col = gridWorld3.gridWorld[i].col;
			row = gridWorld3.gridWorld[i].row;
			beliefVal = gridWorld3.gridWorld[i].beliefVal;
			System.out.println("b("+col+","+row+")=" + df.format(beliefVal));
			//System.out.println("b("+col+","+row+")=" + beliefVal);
		}	
		
		//*** test 4:
		System.out.println(newLine + "fourth sequence:");
		
		GridWorld gridWorld4 = new GridWorld();
		
		for(int i = 0; i < gridWorld4.gridWorld.length;i++){
			gridWorld4.gridWorld[i].updateBeliefVal(0.0);
		}
		
		// s0 = (1,1)
		gridWorld4.gridWorld[0].updateBeliefVal(1.0);

		String[] act4 = {"up","right","right","right"};
		String[] obs4 = {"2","2","1","1"};
		

		updateBelief(gridWorld4.gridWorld,act4, obs4);
	
		for(int i = 0; i < gridWorld4.gridWorld.length; i++){
			col = gridWorld4.gridWorld[i].col;
			row = gridWorld4.gridWorld[i].row;
			beliefVal = gridWorld4.gridWorld[i].beliefVal;
			System.out.println("b("+col+","+row+")=" + df.format(beliefVal));
		}	

		
	}
}

class State{
	int col;
	int row;
	Double beliefVal;
	
	State(int col, int row, Double beliefVal){
		this.col = col;
		this.row = row;
		this.beliefVal = beliefVal;
	}
	
	void updateBeliefVal(Double newVal){
		this.beliefVal = newVal;
	}
	
	void setCol(int col){
		this.col = col;
	}
	
	void setRow(int row){
		this.row = row;
	}
}

class GridWorld{	
	
	State[] gridWorld = new State[11];
	
	GridWorld(){
		
		State oneone = new State(1,1,0.0);
		State onetwo = new State(1,2,0.0);
		State onethree = new State(1,3,0.0);
		
		State twoone = new State(2,1,0.0);
		State twothree = new State(2,3,0.0);
		
		State threeone = new State(3,1,0.0);
		State threetwo = new State(3,2,0.0);
		State threethree = new State(3,3,0.0);
		
		State fourone = new State(4,1,0.0);
		State fourtwo = new State(4,2,0.0);
		State fourthree = new State(4,3,0.0);
		
		gridWorld[0] = oneone;
		gridWorld[1] = onetwo;
		gridWorld[2] = onethree;
		gridWorld[3] = twoone;
		gridWorld[4] = twothree;
		gridWorld[5] = threeone;
		gridWorld[6] = threetwo;
		gridWorld[7] = threethree;
		gridWorld[8] = fourone;
		gridWorld[9] = fourtwo;
		gridWorld[10] = fourthree;
	}
}