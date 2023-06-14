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
		
		coordinate here = find_p(m,101);
		System.out.println(here);
		
		/*coordinate tmp = Bsearch_y(m,3,100);
		System.out.println(tmp);*/
		
		
	}	
	
	private static coordinate Bsearch_x(ArrayList<ArrayList<Integer>> matrix,int x,int p){

		int l = 0;
		int h = x;
		int mid;

		coordinate result = new coordinate(-1,-1);
		while(l <= h){

			mid = (l+h)/2;

			if(matrix.get(x).get(mid) == p){
				result = new coordinate(x,mid);
				return result;
			}

			if(matrix.get(x).get(mid) < p){
				l = mid+1;
			}

			if(matrix.get(x).get(mid) > p){
				h = mid-1;
			}

		}
		return result;
	}
	
	private static coordinate Bsearch_y(ArrayList<ArrayList<Integer>> matrix,int y,int p){

		int l = 0;
		int h = y;
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
	
	private static coordinate Bsearch_matrix(ArrayList<ArrayList<Integer>> matrix,coordinate low,coordinate high,int p,coordinate last){

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

		//printf("mid: %d\n",mid);
		//System.out.println(mid_c);
		//System.out.println("mid :" + mid);
		//System.out.println();

		coordinate tmp = new coordinate(-1,-1);
		if(mid == p){
			return mid_c;
		}
		coordinate cmp = Bsearch_x(matrix,mid_x,p);
		if(cmp.equals(tmp)){
			cmp =  Bsearch_y(matrix,mid_y,p);
		}
		if(!cmp.equals(cmp)){
			return cmp;
		}

		//avoid stackoverflow:
		
		if(!last.equals(tmp) && last.equals(mid_c)){
			
			//candidate for finish the subtree
			
			//give a little "help" so that the mid can get to all "diagonals":
			if(mid < p) {
				
				cmp = Bsearch_x(matrix,m-1,p);
				if(cmp.equals(tmp)) {
					
					return Bsearch_y(matrix,n-1,p);
				}
				return cmp;
			}
			if(mid > p){
				cmp = Bsearch_x(matrix,0,p);
				if(cmp.equals(tmp)) {
					return Bsearch_y(matrix,0,p);
				}
				return cmp;
			}
			
		}


		if(mid > p){

			//p is on the submatrix that mid is the last value
			cmp = Bsearch_matrix(matrix,low,mid_c,p,mid_c);
			if(!cmp.equals(tmp)){
				return cmp;
			}
		}
		if(mid < p){

			//the mirror matrix of the first if
			cmp = Bsearch_matrix(matrix,mid_c,high,p,mid_c);
			if(!cmp.equals(tmp)){
				return cmp;
			}
		}
		return tmp;
	}
	
	private static coordinate find_p(ArrayList<ArrayList<Integer>> matrix,int p){

		coordinate low = new coordinate(0,0);
		coordinate high = new coordinate(matrix.size()-1,matrix.get(0).size()-1);

		return Bsearch_matrix(matrix,low,high,p,new coordinate(-1,-1));

	}
	
}
