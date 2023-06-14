package misc;

import java.util.ArrayList;
import java.util.Arrays;

public class main_misc2 {
	
   
	public static class coordinate{
		
		int x;
		int y;
		
		public coordinate(int a,int b){
			x = a;
			y  = b;
		}
		public boolean equals(coordinate cmp) {
			if(cmp.x == x && cmp.y == y) {
				return true;
			}
			return false;
		}
		
		public String toString(){
			return "("+x+","+y+")";
			
		}
	}
	
	public static void main(String[] args) {
	//(a+b)%k = ((a % k)+(b % k)) % k    || ||
	
	
		ArrayList<ArrayList<Integer>> m =  new ArrayList<>();
		ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1,2,3,99,100));
		ArrayList<Integer> b = new ArrayList<>(Arrays.asList(2,3,5,100,101));
		ArrayList<Integer> c = new ArrayList<>(Arrays.asList(3,4,6,101,102));
		m.add(a);
		m.add(b);
		m.add(c);

		
		for(int i = 0;i < m.size();i++) {
			for(int j = 0;j < m.get(i).size();j++){
				System.out.println("value: "+m.get(i).get(j)+" --> "+find_p(m,m.get(i).get(j)));
			}
		}
		
		
	}	
	
	
	private static coordinate Bsearch_upper(ArrayList<ArrayList<Integer>> matrix,int x,int y,int p){

		int l = 0;
		int h = x;
		int mid;

		coordinate result = new coordinate(-1,-1);
		while(l <= h){

			mid = (l+h)/2;

			if(matrix.get(mid).get(y) == p){
				result = new coordinate(mid,y);
				return result;
			}

			if(matrix.get(mid).get(y) < p){
				l = mid+1;
			}

			if(matrix.get(mid).get(y) > p){
				h = mid-1;
			}

		}
		return result;
	}
	
	private static coordinate Bsearch_lower(ArrayList<ArrayList<Integer>> matrix,int x,int y,int p){

		int l = x;
		int h = matrix.size()-1;
		int mid;

		coordinate result = new coordinate(-1,-1);
		while(l <= h){

			mid = (l+h)/2;

			if(matrix.get(mid).get(y) == p){
				result = new coordinate(mid,y);
				return result;
			}

			if(matrix.get(mid).get(y) < p){
				l = mid+1;
			}

			if(matrix.get(mid).get(y) > p){
				h = mid-1;
			}

		}
		return result;
	}
	
	
	
	private static coordinate Bsearch_matrix(ArrayList<ArrayList<Integer>> matrix,coordinate low,coordinate high,int p){

		int m = matrix.size();
		int n = matrix.get(0).size();
		
		int low_x = low.x;
		int low_y = low.y;
		int high_x = high.x;
		int high_y = high.y;

		int mid_x = (low_x+high_x)/2;
		int mid_y = (low_y+high_y)/2;

		coordinate mid_c = new coordinate(mid_x,mid_y);
		int mid = matrix.get(mid_x).get(mid_y);
		
		coordinate tmp = new coordinate(-1,-1);
		coordinate cmp;
		if(mid == p) {
			return mid_c;
		}
		if(mid > p) {
			
			cmp = Bsearch_upper(matrix,mid_x,mid_y,p);
			if(!cmp.equals(tmp)) {
				return cmp;
			}
			
		}
		if(mid < p) {
			
			cmp = Bsearch_lower(matrix,mid_x,mid_y,p);
			if(!cmp.equals(tmp)) {
				return cmp;
			}
			
		}
		
		//avoid stack overflow:
		
		if(low_x > high_x) {
			if(low_y > high_y) {
				return tmp;
			}
		}
		
		//end of base cases
		
		
		if(mid > p){
			cmp = Bsearch_matrix(matrix,low,new coordinate(mid_x,mid_y-1),p);
			if(!cmp.equals(tmp)){
				return cmp;
			}
		}
		if(mid < p) {
			cmp = Bsearch_matrix(matrix,new coordinate(mid_x,mid_y+1),high,p);
			if(!cmp.equals(tmp)){
				return cmp;
			}
		}
		return tmp;
	}
	
	private static coordinate find_p(ArrayList<ArrayList<Integer>> matrix,int p){

		coordinate low = new coordinate(0,0);
		coordinate high = new coordinate(matrix.size()-1,matrix.get(0).size()-1);

		return Bsearch_matrix(matrix,low,high,p);

	}
	
}
