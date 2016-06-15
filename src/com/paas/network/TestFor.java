package com.paas.network;

public class TestFor {
	
	public static void main(String[] args) {
		String value="172.0.1.0/24";
		/*String ad [] = vlue.split("/");
		System.out.println("array lengh "+ad.length);
		
		String s=ad[0];
		System.out.println("s value "+s);
		      String[] s2 = s.split("\\.");
		    int value=Integer.parseInt(s2[s2.length-1]);
		 System.out.println("value "+value);
		 value= value+1;
		 s2[s2.length-1]=value+"";
		 String data="";
		 for (int i = 0; i < s2.length-1; i++) {
			 data=data+s2[i]+".";
		}
		 data=data+value+"/"+ad[1];
		 System.out.println("value "+data );*/
		
		String ad [] = value.split("/");
		System.out.println("array lengh "+ad.length);
		
		String s=ad[0];
		System.out.println("s value "+s);
		      String[] s2 = s.split("\\.");
		    int value1=Integer.parseInt(s2[s2.length-1]);
		 System.out.println("value "+value1);
		 value= value+1;
		 s2[s2.length-1]=value1+"";
		 String data="";
		 for (int i = 0; i < s2.length-1; i++) {
			 data=data+s2[i]+".";
		}
		 data=data+value+"/"+ad[1];
		 System.out.println("value "+data );
	}
	
	

}
