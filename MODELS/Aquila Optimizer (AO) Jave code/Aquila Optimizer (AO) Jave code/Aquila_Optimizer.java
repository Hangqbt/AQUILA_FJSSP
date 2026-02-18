import java.io.*;
import java.util.*;


abstract class f_xj
{abstract double func(double x[]);}


public class Aquila_Optimizer
{

double[] Lower;
double[] Upper;
int N;
int D;
f_xj ff;
int Maxiter;
 

double[] BEST;
double[][] X;               
double[][] XNEW;
double[] fitness;
double[] fitnessnew;
double[] D1;
double[] XMEAN2D;
double XMEAN;
double alpha;
double delta;
int iter;
double Bestfitval;
double G2;
double G1;
double u;
double omega;
double phi0;
double r0;
double r[];
double phi[];
double[] xx;
double[] yy;
double[] Levyvals;
double QF;
int NRAND;
              public Aquila_Optimizer(double[] iLower, double[] iUpper, int iN, f_xj iff, int iMaxiter)
              {
              ff=iff;
	          Lower=iLower;
	          Upper=iUpper;
	          D=Lower.length;
	          N=iN;
              Maxiter=iMaxiter;
              BEST=new double[D];
              X=new double[N][D]; 
              XNEW=new double[N][D];
              fitness=new double[N];
              fitnessnew=new double[N];
              D1=new double[D];
              r=new double[D];
              phi=new double[D];
              xx=new double[D];
              yy=new double[D];
              XMEAN2D=new double[D];
              Levyvals=new double[D];
              alpha=0.1;
              delta=0.1; 
              Bestfitval=1E+200; 
              u=0.0265;
              omega=0.005; 
              phi0=3.0*Math.PI/2.0;
              int t=1;
              for(int j=0;j<D;j++)
              {D1[j]=t;t++;}  
              
              
              }
 
              void init()
              {
	            for(int i=0;i<N;i++)
	            {for(int j=0;j<D;j++)
		            {X[i][j]=Lower[j]+(Upper[j]-Lower[j])*Math.random();XNEW[i][j]=X[i][j];}}  
	                X=sorted(X);
	                for(int j=0;j<D;j++)
	                {BEST[j]=X[0][j];} 
		              
	              
	          }


              double[][] sorted(double[][] TT)
		      {
	  			int mm=TT.length;
	  			int nn=TT[0].length;
	  			double[][] TNEW=new double[mm][nn];
	  			double[] fitnessTT=new double[mm];
	  			for(int i=0;i<mm;i++)
	  			{fitnessTT[i]=ff.func(TT[i]);}
	  			double[][] FF=sort_and_index(fitnessTT);
	  			for(int i=0;i<mm;i++)
	    			{for(int j=0;j<nn;j++)
					{TNEW[i][j]=TT[(int)FF[1][i]][j];}}
			
	  			return TNEW;
		      }
    
    
    
              double[][] sort_and_index(double[] A)
	  		  { 
				ArrayList<Double> B=new ArrayList<Double>(); 	
				for(int i=0;i<A.length;i++)
				{B.add(A[i]);}	
				ArrayList<Double> nstore=new ArrayList<Double>(B);
				Collections.sort(B);
				double[] ret=new double[B.size()];
				Iterator<Double> iterator=B.iterator();
				int ii=0;
				while(iterator.hasNext())
				{ret[ii]=iterator.next().doubleValue();ii++;}
				int[] indexes=new int[B.size()];
				for(int n=0;n<B.size();n++)
				{indexes[n]=nstore.indexOf(B.get(n));}
				double[][] outt=new double[2][B.size()];
				for(int i=0;i<B.size();i++)
				{outt[0][i]=ret[i];outt[1][i]=indexes[i];}
				return outt;
	  		 }


             double logGamma(double x) {
                double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
                double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
                       + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
                       +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
                return tmp + Math.log(ser * Math.sqrt(2 * Math.PI));
             }
             
            double gamma(double x) { return Math.exp(logGamma(x)); }
      
            double[] Levy()
            {   
	   			double beta=1.5;
       			double sigma=Math.pow((gamma(1+beta)*Math.sin(Math.PI*beta/2)/(gamma((1+beta)/2)*beta*Math.pow(2,((beta-1)/2)))),(1/beta));
	   			Random rnd=new Random();
	   			double[] u=new double[D];
	   			double[] v=new double[D];
	   			double[] step=new double[D];
	   			for(int j=0;j<D;j++)
	   			{u[j]=rnd.nextGaussian()*sigma;}
	   			for(int j=0;j<D;j++)
	   			{v[j]=rnd.nextGaussian();}
	   			for(int j=0;j<D;j++)
	   			{step[j]=u[j]/(Math.pow(Math.abs(v[j]),(1.0/beta)));}
	   			return step;  
	  		}

	  		
	  		double[][] boundary(double[][] XX)
            { 
	        	int NN=XX.length;
	        	int DD=XX[0].length;  
	        	for(int i=0;i<NN;i++)
   			   		{for(int j=0;j<DD;j++)
      		    		{if((XX[i][j]<Lower[j])||(XX[i][j]>Upper[j]))
           		    		{XX[i][j]=Lower[j]+((Upper[j]-Lower[j])*Math.random());}}}
   		    	return XX;
	      	}
	      	
	      	double mean1D(double[] XX)
	      	{
		     int d=XX.length;
		     double sum=0.0;
		     for(int j=0;j<d;j++)
		     {sum+=XX[j];}
		     return sum/(double)d;  	
		    
		    }
		    

				
				
		double[] mean2D(double[][] XX)
		{
		  int DD=XX[0].length;	
		  int NN=XX.length;
		  double[] MEAN=new double[DD]; 
		  double sum1;
			for(int j=0;j<DD;j++)
			{sum1=0.0;
			   	for(int i=0;i<NN;i++)
			   	{sum1+=X[i][j];}
				MEAN[j]=sum1/(double)NN;}	
		  return MEAN;	
	    }		
		    
	  		

            double[][] solution()
            {
	            
	           init(); 
	           iter=1; 
	            
	           while(iter<=Maxiter)
	           {
		           
		          X=boundary(X);
		          for(int i=0;i<N;i++)
		          {   
			          fitness[i]=ff.func(X[i]);
			          if(fitness[i]<Bestfitval)
			          {
				        Bestfitval=ff.func(X[i]);
				        for(int j=0;j<D;j++)
				        {BEST[j]=X[i][j];}
				        
				      }
				  } 
		           
		          G2=2.0*Math.random()-1.0;
		          G1=2.0*(1.0-((double)iter/(double)Maxiter)); 
		          r0=Math.ceil((Math.random()*20.0));
		           
		          for(int j=0;j<D;j++)
		          {r[j]=r0+u*D1[j];phi[j]=-omega*D1[j]+phi0;}
		          
		          for(int j=0;j<D;j++)
		          {xx[j]=r[j]*Math.cos(phi[j]);yy[j]=r[j]*Math.sin(phi[j]);}
		          
		          QF= Math.pow((double)iter,((2.0*Math.random()-1.0)/((1-(double)Maxiter)*(1-(double)Maxiter))));
		          
		       if((double)iter<=0.666*(double)Maxiter)
		       {   
		          for(int i=0;i<N;i++)
		          {
			          if(Math.random()<0.5)
			          {  
				         //XMEAN2D=mean2D(X); 
				         double xmean=mean1D(X[i]);
				         for(int j=0;j<D;j++)
				         {XNEW[i][j]=BEST[j]*(1.0-((double)iter/(double)Maxiter)) + (xmean - BEST[j])*Math.random();}     
				         fitnessnew[i]=ff.func(XNEW[i]);
				         if(fitnessnew[i]<fitness[i])
				         {
					       for(int j=0;j<D;j++)
					       {X[i][j]=XNEW[i][j];}
					       fitness[i]=fitnessnew[i];    
					     }
				       }    
			           else
			           {
				          Levyvals=Levy();
				          NRAND=(int)Math.floor((double)N*Math.random());
				          for(int j=0;j<D;j++)
				          {XNEW[i][j]=BEST[j]*Levyvals[j]+X[NRAND][j]+(yy[j]-xx[j])*Math.random();} 
				          if(fitnessnew[i]<fitness[i])
				          {
					       for(int j=0;j<D;j++)
					       {X[i][j]=XNEW[i][j];}
					       fitness[i]=fitnessnew[i];    
					      } 
				       }
			          
			       }
	            }
	            else
	            {
		            
		          for(int i=0;i<N;i++)
		          {
			          if(Math.random()<0.5)
			          {  
				         XMEAN2D=mean2D(X); 
				         for(int j=0;j<D;j++)
				         {XNEW[i][j]=(BEST[j]-XMEAN2D[j])*alpha-Math.random()+ (Lower[j]+(Upper[j]-Lower[j])*Math.random())*delta ;}     
				         fitnessnew[i]=ff.func(XNEW[i]);
				         if(fitnessnew[i]<fitness[i])
				         {
					       for(int j=0;j<D;j++)
					       {X[i][j]=XNEW[i][j];}
					       fitness[i]=fitnessnew[i];    
					     }
				       }    
			           else
			           {
				          Levyvals=Levy();
				          for(int j=0;j<D;j++)
				          {XNEW[i][j]=QF*BEST[j] - (G2*X[i][j]*Math.random()) - G1*Levyvals[j]+Math.random()*G2;} 
				          if(fitnessnew[i]<fitness[i])
				          {
					       for(int j=0;j<D;j++)
					       {X[i][j]=XNEW[i][j];}
					       fitness[i]=fitnessnew[i];    
					      } 
				       }
			          
			       }
		            
		         } 
		          
		       iter++;  
		       }
	            
	     		double out[][]=new double[2][D];
	    		for(int i=0;i<D;i++)
				{out[0][i]=BEST[i];}
				out[1][0]=ff.func(BEST);
                return out; 
	            
	         }


		 void toStringnew()
		 {
  			double[][] out=solution();
  			System.out.println("Optimized value = "+out[1][0]);
  			for(int i=0;i<D;i++)
  			{System.out.println("x["+i+"] = "+out[0][i]);}	
		 }







}