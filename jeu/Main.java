package jeu;

public class Main {
	public static void main(String[] args) {
		System.out.println(combinaisons(6,4,1,2));
	}
	
	public static int combinaisons(int k, int n, int b, int m) {
		//condition initial
		if(n==b) return 0;
		
		else {
			int bienPlace;
			int malPlace;
			int absent;
			
			//si le premier element est bien place
			if(b==0) bienPlace = 0;
			else bienPlace = 1+combinaisons(k-1,n-1,b-1,m);
			
			//si le premier element est mal place
			if(m==0) malPlace = 0;
			else malPlace = 1+combinaisons(k,n,b,m-1);
			
			//si le premier element est absent
			if(n==k) absent = 0;
			else absent = 1+combinaisons(k-1,n,b,m);
			
			return bienPlace + malPlace + absent;
		}
	}
}
