//http://extreme.adorio-research.org


class f1 extends f_xj//Gold stein f(x)=3.0 @x=(0,-1) -2<x[i]<2 i=1,2
{
double func(double x[])
{
double first=0.0;double second=0.0;
first=(1.0+(x[0]+x[1]+1.0)*(x[0]+x[1]+1.0)*(19.0-14.0*x[0]+3.0*x[0]*x[0]-14.0*x[1]+6.0*x[0]*x[1]+3.0*x[1]*x[1]));	
second=30.0+(2.0*x[0]-3.0*x[1])*(2.0*x[0]-3.0*x[1])*(18.0-32.0*x[0]+12.0*x[0]*x[0]+48.0*x[1]-36.0*x[0]*x[1]+27*x[1]*x[1]);	
return first*second;	
}	
}

class f2 extends f_xj// Beale f(x)=0    @x=(3,0.5)   -4.5<x[i]<4.5, i = 1, 2.
{
double func(double x[])
{
double first=0.0;	
first=((1.5-x[0]+x[0]*x[1])*(1.5-x[0]+x[0]*x[1]))+((2.25-x[0]+x[0]*x[1]*x[1])*(2.25-x[0]+x[0]*x[1]*x[1]))+((2.625-x[0]+x[0]*x[1]*x[1]*x[1])*(2.625-x[0]+x[0]*x[1]*x[1]*x[1]));
return first;	
}	
}

class f3 extends f_xj// Bohachecsky 1 f(x)=0  @x=(0.0,0.0)   -5.0<x[i]<5.0, i = 1, 2.
{
double func(double x[])
{
double first=0.0;	
first=x[0]*x[0]+2.0*x[1]*x[1]-0.3*(Math.cos(Math.PI*3.0*x[0]))-0.4*Math.cos(4.0*Math.PI*x[1])+0.7;
return first;	
}	
}

class f4 extends f_xj// Bohachecsky 2 f(x)=0  @x=(0.0,0.0)   -5.0<x[i]<5.0, i = 1, 2.
{
double func(double x[])
{
double first=0.0;	
first=x[0]*x[0]+2.0*x[1]*x[1]-(0.3*(Math.cos(Math.PI*3.0*x[0]))*Math.cos(4.0*Math.PI*x[1]))+0.3;
return first;	
}	
}

class f5 extends f_xj// Bohachecsky 3 f(x)=0  @x=(0.0,0.0)   -5.0<x[i]<5.0, i = 1, 2.
{
double func(double x[])
{
double first=0.0;	
first=x[0]*x[0]+2.0*x[1]*x[1]-(0.3*(Math.cos(Math.PI*3.0*x[0]+Math.PI*4.0*x[1])))+0.3;
return first;	
}	
}

class f6 extends f_xj// Booth  f(x)=0  @x=(1.0,3.0)   -10.0<x[i]<10.0, i = 1, 2.
{
double func(double x[])
{
double first=0.0;	
first=(x[0]+2.0*x[1]-7.0)*(x[0]+2.0*x[1]-7.0)+(2.0*x[0]+x[1]-5.0)*(2.0*x[0]+x[1]-5.0);
return first;	
}	
}

class f7 extends f_xj// Branin  f(x)=0.397887  @x=(-pi,12.275),(pi,2.275),(9.42478,2.475)   -5.0<=x[0]<=10.0, 0.0<=x[1]<=15.0
{
double func(double x[])
{
double first=0.0;	
first=((x[1]-(5.1*x[0]*x[0]/(4.0*Math.PI*Math.PI))+(5.0*x[0]/Math.PI)-6.0)*(x[1]-(5.1*x[0]*x[0]/(4.0*Math.PI*Math.PI))+(5.0*x[0]/Math.PI)-6.0))+(10.0*(1.0-(1.0/(8.0*3.1415)))*Math.cos(x[0]))+10.0;
return first;	
}	
}

class f8 extends f_xj// Colville  f(x)=0.0  @x=(1,1,1,1)   -10.0<=x[i]<=10.0 i=0,1,2,3
{
double func(double x[])
{
double first=0.0;	
first=(100.0*(x[0]-x[1]*x[1])*(x[0]-x[1]*x[1]))+((1.0-x[0])*(1.0-x[0]))+(90.0*(x[3]-x[2]*x[2])*(x[3]-x[2]*x[2]))+((1.0-x[2])*(1.0-x[2]))+(10.1*((x[1]-1.0)*(x[1]-1.0)+(x[3]-1.0)*(x[3]-1.0)))+(19.8*(x[1]-1.0)*(x[3]-1.0));
return first;	
}	
}

class f9 extends f_xj// Easom  f(x)=-1.0  @x=(pi,pi)   -100.0<=x[i]<=100.0 i=0,1
{
double func(double x[])
{
double first=0.0;	
first=-Math.cos(x[0])*Math.cos(x[1])*Math.exp(-(x[0]-Math.PI)*(x[0]-Math.PI)-(x[1]-Math.PI)*(x[1]-Math.PI));
return first;	
}	
}

class f10 extends f_xj// Himmelblau f(x)=0.0  @x=(3.0,2.0),(-2.8051,3.1313),(-3.7793,-3.2831),(3.5844,-1.8481)   -6.0<=x[i]<=6.0 i=0,1
{
double func(double x[])
{
double first=0.0;	
first=(((x[0]*x[0]+x[1]-11.0)*(x[0]*x[0]+x[1]-11.0))+(x[0]+x[1]*x[1]-7.0)*(x[0]+x[1]*x[1]-7.0));
return first;	
}	
}

class f11 extends f_xj// Griewank f(x)=0.0  @x=(0,0)<---global minima     several local minimas      -600<x[i]<600 i=1,2,.. x.length 
{
double func(double x[])
{
   double s=0.0;
   double fact=1.0;
   int m=x.length;
   for(int i=0;i<m;i++)
   {s+=x[i]*x[i];}	
   for(int i=0;i<m;i++)
   {fact*=Math.cos(x[i]/Math.sqrt(i+1));}
   return (s/4000.0)+1.0+(-fact);	
}	
}

class f12 extends f_xj// Hartman3 f(x)=-3.86  @x=(0.114,0.556,0.852)   0.0<x[i]<1.0 for n=3 variable 
{
double func(double x[])
{
double[][] A={{3.0,10.0,30.0},{0.1,10.0,35.0},{3.0,10.0,30.0},{0.1,10.0,35.0}};
double c[]={1.0,1.2,3.0,3.2};
double p[][]={{0.3689,0.1170,0.2673},{0.4699,0.4387,0.7470},{0.1091,0.8732,0.5547},{0.03815,0.5743,0.8828}}   ;
double sin;
double sout=0.0;
    for(int i=0;i<=3;i++)	
    {  sin=0.0;
	    for(int j=0;j<=3;)
	   {sin+=A[i][j]*(x[j]-p[i][j])*(x[j]-p[i][j]);} 
	   sout+=c[i]*Math.exp(-sin);}	
	
return -sout;	
}	
}

class f13 extends f_xj// Matyas function f(x)=0.0 @x(0,0)  -10.0<=x[i]<=10.0
{
double func(double x[])
{
return 0.26*(x[0]*x[0]+x[1]*x[1])-0.48*x[0]*x[1];
}	
}

class f14 extends f_xj //Michalewicz n=x.length=2 f(x)=-1.8013    0<=x[i]<=pi
{                                        //    =5 f(x)=-4.687658        
   double func(double x[])               //   =10 f(x)=-9.66015  
   {
	int n=x.length;   
	double m=10.0;
	double s=0.0;
	for(int i=0;i<n;i++)
	{s+=Math.sin(x[i])*Math.pow(Math.sin(((double)i+1.0)*x[i]*x[i]/3.1415),2.0*m);}   
	return -s;   
   }	
}

class f15 extends f_xj //Perm function!!!!!! error  
{                                               
   double func(double x[])                
   {
	int n=x.length;   
	double b=0.5;

	double sin;
	double sout=0.0;
	for(int k=0;k<n;k++)
	{sin=0.0;
	 for(int i=0;i<n;i++)
	 {sin+=(Math.pow((double)i,(double)k)+b)*(Math.pow((x[i]/(double)i),(double)k)-1.0);}	
	 sout+=sin*sin;	
	}
	return sout;   
   }	
}

class f16 extends f_xj //Powell function !!Error     Must--->mod(x.length,4)==0 
{                                               
   double func(double x[])                
   {
	int n=x.length;
	int m=n/4;double s=0.0;
	for(int j=1;j<=m;j++)
	{s+=((x[4*j-4]+10.0*x[4*j-3])*(x[4*j-4]+10.0*x[4*j-3]))+(Math.sqrt(5.0)*(x[4*j-2]-x[4*j-1])*(x[4*j-2]-x[4*j-1]))+(Math.pow((x[4*j-3]-2.0*x[4*j-2]),4.0))+(Math.sqrt(10.0)*Math.pow(x[4*j-4]-x[4*j-1],4.0));}
	return s;
	   
   }
}

class f17 extends f_xj //Shekel function     f(x)=-10.1532 m=5; @x=(4,4,4,4) 0<=x[i]<=10.0   
{                                          //f(x)=-10.4029 m=7;     
   double func(double x[])                 //f(x)=-10.5364 m=10;
   {
	int n=x.length;
	double A[][]={{4.0,4.0,4.0,4.0},{1.0,1.0,1.0,1.0},{8.0,8.0,8.0,8.0},{6.0,6.0,6.0,6.0},{3.0,7.0,3.0,7.0},{2.0,9.0,2.0,9.0},{5.0,5.0,3.0,3.0},{8.0,1.0,8.0,1.0},{6.0,2.0,6.0,2.0},{7.0,3.6,7.0,3.6}};
	double c[]={0.1,0.2,0.2,0.4,0.4,0.6,0.3,0.7,0.5,0.5};
	double sin=0.0;
	double sout=0.0;
	for(int i=0;i<10;i++)
	{ sin=c[i];
	  for(int j=0;j<n;j++)	
	  {sin+=(x[j]-A[i][j])*(x[j]-A[i][j]);}
	  sout+=(1.0/sin);	
	}
	return -sout;
	}
}

class f18 extends f_xj //Trid function     f(x)=-50.0 x.length=6;  -x.length^2<=x[i]<=x.length^2   
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double s1=0.0;double s2=0.0;
	for(int i=0;i<n;i++)
	{s1+=Math.pow(x[i]-1.0,2.0);}
	for(int i=1;i<n;i++)
	{s2+=x[i]*x[i-1];}
	return s1-s2;
	}
}

class f19 extends f_xj //Zakharov function     f(x)=0.0 @x=(0,0,..)   -5<=x[i]<=10   
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double s1=0.0;double s2=0.0;double s3=0.0;
	for(int i=0;i<n;i++)
	{s1+=x[i]*x[i];}
	for(int i=0;i<n;i++)
	{s2+=0.5*(double)i*x[i];}
	return s1+Math.pow(s2,2.0)+Math.pow(s2,4.0);
	
	}
}

class f20 extends f_xj //Levy function         f(x)=0   @x=(1,1,1...) -10<x[i]<10 
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double z[]=new double[n];
	for(int i=0;i<n;i++)
	{z[i]=1.0+((x[i]-1.0)/4.0);}
	double s=Math.pow(Math.sin(3.1415*z[0]),2.0);
	for(int i=0;i<n-1;i++)
	{s+=Math.pow((z[i]-1.0),2.0)*(1.0+10.0*Math.pow(Math.sin(3.1415*z[i]+1.0),2.0));}
	return s+Math.pow(z[n-1]-1.0,2.0)*(Math.pow(Math.sin(2.0*3.1415*z[n-1]),2.0)+1.0);
   }
}


class f21 extends f_xj //Dixon price function         f(x)=0    -10<x[i]<10 
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double s1=0.0;
	for(int i=1;i<n;i++)
	{s1+=((double)i+1.0)*(2.0*x[i]*x[i]-x[i-1])*(2.0*x[i]*x[i]-x[i-1]);}
	return s1+(x[0]-1.0)*(x[0]-1.0);
	}
}

class f22 extends f_xj //Salomon's function         f(x)=0      @x=(0,0,0...) -10<x[i]<10 
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double s1=0.0;
    for(int i=0;i<n;i++)
    {s1+=x[i]*x[i];}
    s1=Math.sqrt(s1);
	return -Math.cos(2.0*3.1415*s1)+0.1*s1+1.0;	
   }
}

class f23 extends f_xj //Whitley's function         f(x)=0      @x=(0,0,0...) -10<x[i]<10 
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double s1=0.0;
    for(int i=0;i<n;i++)
    {
	  for(int j=0;j<n;j++)
	  {s1+=(Math.pow((100.0*(x[i]*x[i]-x[j])*(x[i]*x[i]-x[j])+(1.0-x[j])*(1.0-x[j])),2.0)/4000.0)-Math.cos((100.0*(x[i]*x[i]-x[j])*(x[i]*x[i]-x[j])+(1.0-x[j])*(1.0-x[j])))+1.0;}    
	}
	return s1;
  }
}

class f24 extends f_xj //quartic function         f(x)=0      @x=(0,0,0...) -10<x[i]<10 
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double s1=0.0;
    for(int i=0;i<n;i++)
    {s1+=((double)i+1.0)*Math.pow(x[i],4.0);}
    s1+=Math.random();
	return s1;
  }
}

class f25 extends f_xj //quartic function         f(x)=0      @x=(0,0,0...) -10<x[i]<10 
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double s1=0.0;
    for(int i=0;i<n;i++)
    {s1+=((double)i+1.0)*Math.pow(x[i],4.0);}
    s1+=Math.random();
	return s1;
  }
}

class f26 extends f_xj //Camel Back -6 Hump function         f(x)=-1.0316285       -5<x[i]<5 
{                                              
   double func(double x[])                 
   {
	int n=x.length;
    double s1=4.0*x[0]*x[0]-2.1*x[0]*x[0]*x[0]*x[0]+(x[0]*x[0]*x[0]*x[0]*x[0]*x[0]/3.0)+(x[0]*x[1])-4.0*x[1]*x[1]+4.0*x[1]*x[1]*x[1]*x[1];
    return s1;
   }
}

class f27 extends f_xj //Schwefel 2.22         f(x)=0    @x=(0,0,...)  -100<x[i]<100  
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double s1=0.0;
	double f1=1.0;
    for(int i=0;i<n;i++)
    {s1+=Math.abs(x[i]);f1*=Math.abs(x[i]);}
    return s1+f1;
   }
}

class f28 extends f_xj //Kowalik         f(x)=3.0748e-4    @x=(0,0,...)  -5<x[i]<5  
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double a[]={0.1957,0.1947,0.1735,0.16,0.0844,0.0627,0.0456,0.0342,0.0323,0.0235,0.0246};
    double b[]={0.25, 0.5, 1.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0};
    double s1=0.0;
    for(int i=0;i<11;i++)
    {s1+=Math.pow((a[i]-((x[0]*(1.0+x[1]*b[i]))/(1.0+x[2]*b[i]+x[3]*b[i]*b[i]))),2.0);              }
    return s1;
   }
}

class f29 extends f_xj //Schaffer function         f(x)=0    @x=(0,0,...)  -100<x[i]<100  
{                                              
   double func(double x[])                 
   {
	int n=x.length;
	double s1=0.0;
	for(int i=0;i<n;i++)
	{s1+=x[i]*x[i];}
	double s2=Math.sqrt(s1);
	return 0.5+((Math.pow(Math.sin(s1),2.0)-0.5)/(1.0+0.001*s1));
	}
}


class f30 extends f_xj  // Rosenbrock's valley     f(x)=0.0     -2.048<x[i]<2.048
{
double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;
double ff=0.0;
for(int i=0;i<n-1;i++)
{ff+=(100.0*(x[i+1]-x[i]*x[i])*(x[i+1]-x[i]*x[i])+(1.0-x[i])*(1.0-x[i]));}
return ff; 
}
}

class f31 extends f_xj // De Jong’s first function     f(x)=0  @x=(0,0,...)     -5.12<x[i]<5.12
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double ff=0;
int n=x.length;
for(int i=0;i<n;i++)
{ff+=x[i]*x[i];}
return ff;
}
} 

class f32 extends f_xj //Axis parallel hyper-ellipsoid 2.2    f(x)=0  @x=(0,0,...)     -5.12<x[i]<5.12
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double ff=0;
int n=x.length;

for(int i=0;i<n;i++)
{ff+=i*x[i]*x[i];}
return ff;
}
}

class f33 extends f_xj //Rotated hyper-ellipsoid function  -65.536<x[i]<65.536  f(x)=0   @x=(0,0,...)
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double ff=0;
int n=x.length;

for(int i=0;i<n;i++)
{
  for(int j=0;j<i;j++)
  {ff+=x[j]*x[j];}	
}
return ff;
}
}

class f34 extends f_xj //Rastrigin’s function 2.5        f(x)=0  @x=(0,0,...)     -5.12<x[i]<5.12
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double ff=0;
int n=x.length;
for(int i=0;i<n;i++)
{
ff+=x[i]*x[i]-10*Math.cos(2.0*Math.PI*x[i]);
}
return ff+10*n;
}
}

class f35 extends f_xj //sum of a different power function 2.8       f(x)=0   @x=(0,0,...)      -1<x[i]<1
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double ff=0;
int n=x.length;
double top=0;
for(int i=0;i<n;i++)
{top+=Math.pow(Math.abs(x[i]),(i+2));}
return top;
}
} 

class f36 extends f_xj //Ackley’s function 2.9        f(x)=0;      @x=(0,0,0...)     -32.768<x[i]<32.768
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double a=20.0;
double b=0.2;
double c=2.*Math.PI;

int n=x.length;

double r=Math.PI/180;
double top1=0.0;
for(int i=0;i<n;i++)
{top1+=x[i]*x[i];}
top1=Math.sqrt(top1/n);
double top2=0.0;
for(int i=0;i<n;i++)
{top2+=Math.cos(r*c*x[i]);}
top2=top2/n;
double top=-a*Math.exp(-b*top1)-Math.exp(top2)+a+Math.exp(1);
return top;
}
}

class f37 extends f_xj //“Drop wave” function ++        i=1,2...    f(x)=-1.0  @x=(0,0)   -5.12<x[i]<5.12
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double r=Math.PI/180;
double top1=-(1+Math.cos((12*Math.sqrt(x[0]*x[0]+x[1]*x[1]))))/(0.5*(x[0]*x[0]+x[1]*x[1])+2.0);
return top1;
}
}

class f38 extends f_xj //Shubert’s function   -10<x[i]<10   18 global minima
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	

double r=Math.PI/180;
double top1=0;
double top2=0;
for(int i=1;i<=5;i++)     
{top1+=i*Math.cos((i+1)*x[0]+1);}
for(int i=1;i<=5;i++)     
{top2+=i*Math.cos((i+1)*x[1]+1);}
return -top1*top2;
}
}

class f39 extends f_xj //Fletcher and Powell function---> min(alfa)<x[i]<max(alfa)  f(x)=0 @x=(alfa[1],alfa[2],,,alfa[i]) 
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double a[][]={{-79.0, 56.0, -62.0, -9.0, 92.0, 48.0, -22.0, -34.0, -39.0, -40.0, -95.0, -69.0, -20.0, -66.0, -98.0, -66.0,-67.0, 37.0,-83.0, -45.0},{91.0, -9.0, -18.0, -59.0, 99.0, -45.0, 88.0, -14.0, -29.0, 26.0, 71.0, -65.0, 19.0, 45.0, 88.0, 18.0, -11.0, -81.0, -10.0, 42.0},{-38.0, 8.0, -12.0, -73.0, 40.0, 26.0, -64.0, 29.0, -82.0, -32.0, -89.0, -3.0, 88.0, 98.0, 53.0, 58.0, 45.0, -39.0, 34.0, -23.0},{-78.0,-18.0 -49.0, 65.0, 66.0, -40.0, 88.0, -95.0, -57.0, 10.0, -98.0, -11.0, -16.0, -55.0, 33.0, 84.0, 21.0, -43.0, 45.0, 100.0,},{-1.0, -43.0, 93.0, -18.0, -76.0, -68.0, -42.0, 22.0, 46.0, -14.0, 69.0, 27.0, -12.0, -26.0, 57.0, -13.0, 0.0, 1.0, 56.0, 17.0},{34.0, -96.0, 26.0, -56.0, -36.0, -85.0, -62.0, 13.0, 93.0, 78.0, -43.0, 96.0, 77.0, 65.0, -34.0, -52.0, 82.0, 18.0, -59.0, -55.0},{52.0, -46.0, -69.0, 99.0, -47.0, -72.0, -11.0, 55.0, -55.0, 91.0, -30.0, 7.0, -35.0, 23.0, -20.0, 55.0, 61.0, -39.0, -58.0, 13.0},{81.0, 47.0, 35.0, 55.0, 67.0, -13.0, 33.0, 14.0, 83.0, -42.0, 8.0, -45.0, -44.0, 12.0, 100.0, -9.0,-33.0, -11.0, 21.0, 14.0},{5.0, -43.0, -45.0, 46.0, 56.0, -94.0, -62.0, 52.0, 66.0, 55.0, -86.0, -29.0, -52.0, -71.0, -91.0, -46.0, 27.0, -27.0, 6.0, 67.0},{-50.0, 66.0, -47.0, -75.0, 89.0, -16.0, 82.0, 6.0, -85.0, -62.0, -30.0, 31.0, -7.0, -75.0, -26.0, -24.0, 46.0, -95.0, -71.0, -57.0},{24.0, 98.0, -50.0, 68.0, -97.0, -64.0, -24.0, 81.0, -59.0, -7.0, 85.0, -92.0, 2.0, 61.0, 52.0, -59.0, -91.0, 74.0, -99.0, -95.0},{-30.0, -63.0, -32.0, -90.0, -35.0, 44.0, -64.0, 57.0, 27.0, 87.0, -70.0, -39.0, -18.0, -89.0, 99.0, 40.0, 14.0, -58.0 ,-5.0, -42.0},{56.0, 3.0, 88.0, 38.0, -14.0, -15.0, 84.0, -9.0, 65.0, -20.0, -75.0, -37.0, 74.0, 66.0, -44.0, 72.0, 74.0, 90.0, -83.0, -40.0},{84.0, 1.0, 73.0, 43.0, 84.0, -99.0, -35.0, 24.0, -78.0, -58.0, 47.0, -83.0, 94.0, -86.0, -65.0, 63.0, -22.0, 65.0, 50.0, -40.0},{-21.0, -8.0, -48.0, 68.0, -91.0, 17.0, -52.0, -99.0, -23.0, 43.0, -8.0, -5.0, -98.0, -17.0, -62.0, -79.0, 60.0, -18.0, 54.0, 74.0},{35.0, 93.0, -98.0, -88.0, -8.0, 64.0, 15.0, 69.0, -65.0, -86.0, 58.0, -44.0, -9.0, -94.0, 68.0, -27.0, -79.0, -67.0, -35.0, -56.0},{-91.0, 73.0, 51.0, 68.0, 96.0, 49.0, 10.0, -13.0, -6.0, -23.0, 50.0, -89.0, 19.0, -67.0, 36.0, -97.0, 0.0, 3.0, 1.0, 39.0},{53.0, 66.0, 23.0, 10.0, -33.0, 62.0, -73.0, 22.0, -65.0, 37.0, -83.0, -65.0, 59.0, -51.0, -56.0, 98.0,-57.0, -11.0, -48.0, 88.0},{83.0, 48.0, 67.0, 27.0, 91.0, -33.0, -90.0, -34.0, 39.0, -36.0, -68.0, 17.0, -7.0, 14.0, 11.0, -10.0, 96.0, 98.0, -32.0, 56.0},{52.0, -52.0, -5.0, 19.0, -25.0, 15.0, -1.0, -11.0, 8.0, -70.0, -4.0, -7.0, -4.0, -6.0, 48.0, 88.0, 13.0, -56.0, 85.0, -65.0}};
double b[][]={{-65.0, -11.0, 76.0, 78.0, 30.0, 93.0, -86.0, -99.0, -37.0, 52.0, -20.0, -10.0, -97.0, -71.0, 16.0, 9.0, -99.0, -84.0, 90.0, -18.0, -94.0},
{59.0, 67.0, 49.0, -45.0, 52.0, -33.0, -34.0, 29.0, -39.0, -80.0, 22.0, 7.0, 3.0, -19.0, -15.0, 7.0, -83.0, -4.0, 84.0 -60.0, -4.0},
{21.0, -23.0, -80.0, 86.0, 86.0, -30.0, 39.0, -73.0, -91.0, 5.0, 83.0, -2.0, -45.0, -54.0, -81.0, -8.0, 14.0, 83.0, 73.0, 45.0, 32.0},
{-91.0, -75.0, 20.0, -64.0, -15.0, 17.0, -89.0, 36.0, -49.0, -2.0, 56.0, -6.0, 76.0, 56.0, 2.0, -68.0, -59.0, -70.0, 48.0, 2.0, 24.0},
{-79.0, 99.0, -31.0, -8.0, -67.0, -72.0, -43.0, -55.0, 76.0, -57.0, 1.0, -58.0, 3.0,-59.0, 30.0, 32.0, 57.0, 29.0, 66.0, 50.0, -80.0},
{-89.0, -35.0, -55.0, 75.0, 15.0, -6.0, -53.0, -56.0, -96.0, 87.0, -90.0, -93.0, 52.0, -86.0, -38.0, -55.0, -53.0, 94.0, 98.0, 4.0,-79.0},
{-76.0, 45.0, 74.0, 12.0, -12.0, -69.0, 2.0, 71.0, 75.0, -60.0, -50.0, 23.0, 0.0, 6.0, 44.0, -82.0, 37.0, 91.0, 84.0, -15.0, -63.0},
{-50.0, -88.0, 93.0, 68.0, 10.0, -13.0, 84.0, -21.0, 65.0, 14.0, 4.0, 92.0, 11.0, 67.0, -18.0, -51.0, 4.0, 21.0, -38.0, 75.0, -59.0},
{-23.0, -95.0, 99.0, 62.0, -37.0, 96.0, 27.0, 69.0, -64.0,-92.0, -12.0, 87.0, 93.0, -19.0, -99.0, -92.0, -34.0, -77.0, 17.0, -72.0, 29.0},
{-5.0, -57.0, -30.0, -6.0, -96.0, 75.0, 25.0, -6.0, 96.0, 77.0, -35.0, -10.0, 82.0, 82.0, 97.0, -39.0, -65.0, -8.0, 34.0, 72.0, 65.0},
{85.0, -9.0, -14.0, 27.0, -45.0, 70.0, 55.0, 26.0, -87.0, -98.0, -25.0, -12.0, 60.0, -45.0, -24.0, -42.0, -88.0, -46.0, -95.0, 53.0, 28.0},
{80.0, -47.0, 38.0, -6.0, 43.0, -59.0, 91.0, -41.0, 90.0, -63.0, 11.0, -54.0, 33.0, -61.0, 74.0, 96.0, 21.0, -77.0, -58.0, -75.0, -9.0},
{-66.0, -98.0, -4.0,96.0, -11.0, 88.0, -99.0, 5.0, 5.0, 58.0, -53.0, 52.0, -98.0, -97.0, 50.0, 49.0, 97.0, -62.0, 79.0, -10.0, -80.0},
{80.0, -95.0, 82.0, 5.0, -68.0, -54.0, 64.0, -2.0, 5.0, 10.0, 85.0, -33.0, -54.0, -30.0, -65.0, 58.0, 40.0, -21.0, -84.0, -66.0, -11.0},
{94.0, 85.0, -31.0, 37.0, -25.0, 60.0, 55.0, -13.0, 48.0, -23.0, -50.0, 84.0, -71.0, 54.0, 47.0, 18.0, -67.0, -30.0, 5.0, -46.0, 53.0},
{-29.0, 54.0, -10.0, -68.0, -54.0, -24.0, -16.0, 21.0, 32.0, 33.0, -27.0, 48.0, 37.0, -61.0, 97.0, 45.0, -90.0, 87.0, -95.0, 85.0, 67.0},
{76.0, -11.0, -48.0, 38.0, -7.0, 86.0, -55.0, 51.0, 26.0, 8.0, -96.0, 99.0, 69.0, -84.0, 41.0, 78.0, -53.0, 4.0, 29.0, 38.0, 16.0},
{-8.0, 48.0, 95.0, 47.0, 39.0, -11.0, -72.0, -95.0, -17.0, 33.0, 65.0, 96.0, -52.0, -17.0, -22.0, -15.0, -91.0, -41.0, -16.0, 23.0, 14.0},
{92.0, 87.0, 63.0, -63.0, -80.0, 96.0, -62.0, 71.0, -58.0, 17.0, -89.0, -35.0, -96.0, -79.0, 7.0, 46.0, -74.0, 88.0, 93.0, -44.0, 52.0},
{-21.0, 35.0, 16.0, -17.0, 54.0, -22.0, -93.0, 27.0, 88.0, 0.0, -67.0, 94.0, -24.0, -30.0, -90.0, -5.0, -48.0, 45.0, -90.0, 32.0, -81.0},
{-86.0, 31.0, -80.0, -79.0, -5.0, 11.0, -20.0, 9.0, 52.0, -38.0, 67.0, 64.0, -49.0, 23.0, -86.0, 39.0, -97.0, 76.0, 10.0, 81.0, 20.0}};


double alfa[]={-2.7910,2.5623,-1.0429,0.5097,-2.8096,1.1883,2.0771,-2.9926,0.0715,0.4142,-2.5010,1.7731,1.6473,0.4934,2.1038,-1.9930,0.3813,-2.2144,-2.5572,2.9449};
int n=x.length;
double A[]=new double[x.length];
double B[]=new double[x.length];
double s1=0.0;
double s2=0.0;
for(int i=0;i<n;i++)
{s1=0.0;   
   for(int j=0;j<n;j++)
   {s1+=a[i][j]*Math.sin(alfa[j])+b[i][j]*Math.cos(alfa[j]);}
 A[i]=s1;
}    
for(int i=0;i<n;i++)
{s2=0.0;   
   for(int j=0;j<n;j++)
   {s2+=a[i][j]*Math.sin(x[j])+b[i][j]*Math.cos(x[j]);}
 B[i]=s2;
}
double s3=0.0;
for(int i=0;i<n;i++)
{s3+=(A[i]-B[i])*(A[i]-B[i]);}
return s3;
}
}


class f40 extends f_xj //Step function   f(x)=0   @x(-0.5,-0.5)   -5.0<x[i]<5.0
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;
for(int i=0;i<n;i++)
{s1+=Math.pow(Math.abs(x[i]+0.5),2.0);}
return s1;
}
}



class f41 extends f_xj //Penalized function   f(x)=0   @x(-1.0,-1.0)   -5.0<x[i]<5.0
{
public double u(double x,double a,double k,double m)
{
   double c=0.0;	
   	
     if(x>a){c=k*Math.pow(x-a,m);return c;}	
	  if((x>=-a)||(x<=a)){c=0;return c;}
	  if(x<-a){c=k*Math.pow(-x-a,m);return c;}
   
   return c;	
}	

public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;
double[] y=new double[n];
for(int i=0;i<n;i++)
{y[i]=(0.25*(x[i]+1.0))+1.0;}
s1=Math.pow(Math.sin(3.1415*y[0]),2.0)*10.0;

for(int i=0;i<n-1;i++)
{s1+=Math.pow((y[i]-1.0),2.0)*(10.0*Math.pow(Math.sin(3.1415*y[i+1]),2.0)+1.0);}
s1+=Math.pow((y[n-1]-1.0),2.0);
s1=s1*3.1415/(double)n;
double s2=0.0;
for(int i=0;i<n;i++)
{s2+=u(x[i],10.0,100.0,4.0);}
return s1+s2;
}
}

class f42 extends f_xj //Penalized2 function   f(x)=0   @x(1.0,1.0,....)   -5.0<x[i]<5.0
{
public double u(double x,double a,double k,double m)
{
   double c=0.0;	
   	
     if(x>a){c=k*Math.pow(x-a,m);return c;}	
	  if((x>=-a)||(x<=a)){c=0;return c;}
	  if(x<-a){c=k*Math.pow(-x-a,m);return c;}
   
   return c;	
}	

public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;

s1=Math.pow(Math.sin(3.1415*x[0]),2.0);

for(int i=0;i<n-1;i++)
{s1+=Math.pow((x[i]-1.0),2.0)*(Math.pow(Math.sin(3.0*3.1415*x[i+1]),2.0)+1.0);}
s1+=Math.pow((x[n-1]-1.0),2.0)*(Math.pow(Math.sin(2.0*3.1415*x[n-1]),2.0)+1.0);
s1=s1*0.1;
double s2=0.0;
for(int i=0;i<n;i++)
{s2+=u(x[i],5.0,100.0,4.0);}
return s1+s2;
}
}

class f43 extends f_xj //Shekel's Foxholes
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;

double[][] a={{-32.0,-16.0,0.0,16.0,32.0,-32.0,-16.0,0.0,16.0,32.0,-32.0,-16.0,0.0,16.0,32.0,-32.0,-16.0,0.0,16.0,32.0,-32.0,-16.0,0.0,16.0,32.0},{-32.0,-32.0,-32.0,-32.0,-32.0,-16.0,-16.0,-16.0,-16.0,-16.0,0.0,0.0,0.0,0.0,0.0,16.0,16.0,16.0,16.0,16.0,32.0,32.0,32.0,32.0,32.0}};


double sin=0.0;double sout=0.0;
for(int j=0;j<25;j++)
{   sin=0.0;
    for(int i=0;i<2;i++)
    {sin+=Math.pow((x[i]-a[i][j]),6.0);}	
	sout+=1.0/((double)j+sin);
}
return 1.0/(0.002+sout);
}
}

class f44 extends f_xj //Shekel's Foxholes
{
public double func(double x[]) 
{
double a[][] = {
	{9.681, 0.667, 4.783, 9.095, 3.517, 9.325, 6.544, 0.211, 5.122, 2.020},
	{9.400, 2.041, 3.788, 7.931, 2.882, 2.672, 3.568, 1.284, 7.033, 7.374},
	{8.025, 9.152, 5.114, 7.621, 4.564, 4.711, 2.996, 6.126, 0.734, 4.982},
	{2.196, 0.415, 5.649, 6.979, 9.510, 9.166, 6.304, 6.054, 9.377, 1.426},
	{8.074, 8.777, 3.467, 1.863, 6.708, 6.349, 4.534, 0.276, 7.633, 1.567},
	{7.650, 5.658, 0.720, 2.764, 3.278, 5.283, 7.474, 6.274, 1.409, 8.208},
	{1.256, 3.605, 8.623, 6.905, 4.584, 8.133, 6.071, 6.888, 4.187, 5.448},
	{8.314, 2.261, 4.224, 1.781, 4.124, 0.932, 8.129, 8.658, 1.208, 5.762},
	{0.226, 8.858, 1.420, 0.945, 1.622, 4.698, 6.228, 9.096, 0.972, 7.637},
	{7.305, 2.228, 1.242, 5.928, 9.133, 1.826, 4.060, 5.204, 8.713, 8.247},
	{0.652, 7.027, 0.508, 4.876, 8.807, 4.632, 5.808, 6.937, 3.291, 7.016},
	{2.699, 3.516, 5.874, 4.119, 4.461, 7.496, 8.817, 0.690, 6.593, 9.789},
	{8.327, 3.897, 2.017, 9.570, 9.825, 1.150, 1.395, 3.885, 6.354, 0.109},
	{2.132, 7.006, 7.136, 2.641, 1.882, 5.943, 7.273, 7.691, 2.880, 0.564},
	{4.707, 5.579, 4.080, 0.581, 9.698, 8.542, 8.077, 8.515, 9.231, 4.670},
	{8.304, 7.559, 8.567, 0.322, 7.128, 8.392, 1.472, 8.524, 2.277, 7.826},
	{8.632, 4.409, 4.832, 5.768, 7.050, 6.715, 1.711, 4.323, 4.405, 4.591},
	{4.887, 9.112, 0.170, 8.967, 9.693, 9.867, 7.508, 7.770, 8.382, 6.740},
	{2.440, 6.686, 4.299, 1.007, 7.008, 1.427, 9.398, 8.480, 9.950, 1.675},
	{6.306, 8.583, 6.084, 1.138, 4.350, 3.134, 7.853, 6.061, 7.457, 2.258},
	{0.652, 2.343, 1.370, 0.821, 1.310, 1.063, 0.689, 8.819, 8.833, 9.070},
	{5.558, 1.272, 5.756, 9.857, 2.279, 2.764, 1.284, 1.677, 1.244, 1.234},
	{3.352, 7.549, 9.817, 9.437, 8.687, 4.167, 2.570, 6.540, 0.228, 0.027},
	{8.798, 0.880, 2.370, 0.168, 1.701, 3.680, 1.231, 2.390, 2.499, 0.064},
	{1.460, 8.057, 1.336, 7.217, 7.914, 3.615, 9.981, 9.198, 5.292, 1.224},
	{0.432, 8.645, 8.774, 0.249, 8.081, 7.461, 4.416, 0.652, 4.002, 4.644},
	{0.679, 2.800, 5.523, 3.049, 2.968, 7.225, 6.730, 4.199, 9.614, 9.229},
	{4.263, 1.074, 7.286, 5.599, 8.291, 5.200, 9.214, 8.272, 4.398, 4.506},
	{9.496, 4.830, 3.150, 8.270, 5.079, 1.231, 5.731, 9.494, 1.883, 9.732},
	{4.138, 2.562, 2.532, 9.661, 5.611, 5.500, 6.886, 2.341, 9.699, 6.500}};
	
	double c[] = {0.806,0.517,0.100,0.908,0.965,0.669,0.524,0.902,0.531,0.876,0.462,0.491,0.463,0.714,0.352,0.869,0.813,0.811,0.828,0.964,0.789,0.360,0.369,0.992,0.332,0.817,0.632,0.883,0.608,0.326};
	int dimension=x.length;
	double sum=0.0;
	double h=0.0;
	double sp=0.0;
	for (int j=0; j<30; j++)
	{
		sp=0.0;
		for (int i=0; i<dimension; i++)
		{
			h=(x[i])-a[j][i];
			sp+=h*h;
		}
		sum-=1.0/(sp+c[j]);
	}
	
	
	return sum;
}
}

class f45 extends f_xj //Exp2 function   f(x)=0   @x(1.0,10.0)   0.0<x[i]<20.0
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;
for(int i=0;i<10;i++)
{s1+=Math.pow((Math.exp(-(double)i*x[0]/10.0)-(5.0*Math.exp(-(double)i*x[1]/10.0))-Math.exp(-(double)i/10.0)+5.0*Math.exp(-(double)i)),2.0);}
return s1;
}
}

class f46 extends f_xj //Stretched V function   
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;
for(int i=0;i<=n-2;i++)
{s1+=Math.pow((x[i+1]*x[i+1]+x[i]*x[i]),0.25)*Math.sin(50.0*Math.pow((x[i+1]*x[i+1]+x[i]*x[i]),0.1))+1.0;}
return s1;
}
}

class f47 extends f_xj //Trecanni function   f(x)=0   @x(0.0,0.0,....)   -5.0<x[i]<5.0
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;
s1=Math.pow(x[0],4.0)+4.0*Math.pow(x[0],3.0)+4.0*Math.pow(x[0],2.0)+x[1]*x[1];
return s1;
}
}

class f48 extends f_xj //Trefethen4 function   f(x)=-3.306868   @x(-0.0244031,0.2106124)   -6.5<x[0]<6.5    -4.5<x[1]<4.5                  
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;
s1=Math.exp(Math.sin(50.0*x[0]))+Math.sin(60.0*Math.exp(x[1]))+Math.sin(70.0*Math.sin(x[0]))+Math.sin(Math.sin(80.0*x[1]))-Math.sin(10.0*(x[0]+x[1]))+1.0/4.0*(x[0]*x[0]+x[1]*x[1]);
return s1;
}
}

class f49 extends f_xj //Paviani function                    
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;double fact=1.0;
for(int i=0;i<=n-1;i++)
{s1+=Math.pow(Math.log(x[i]-2.0),2.0)+Math.pow(Math.log(10.0-x[i]),2.0);}
for(int i=0;i<=n-1;i++)
{fact*=x[i];}
fact=Math.pow(fact,0.2);
return s1-fact;
}
}

class f50 extends f_xj //McCormick function      -1.5<=x[0]<=4 , -3.0<=x[1]<=4    f(x)=-1.9133  @x(-0.547,-1.54719)            
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	

double s1=Math.sin(x[0]+x[1])+Math.pow(x[0]-x[1],2.0)-1.5*x[0]+2.5*x[1]+1.0;
return s1;
}
}

class f51 extends f_xj //Leon function     f(x)=0.0  @x(1.0,1.0)           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	

double s1=100.0*(x[1]-x[0]*x[0]*x[0])*(x[1]-x[0]*x[0]*x[0])+(x[0]-1.0)*(x[0]-1.0);
return s1;
}
}

class f52 extends f_xj //Hosaki function     f(x)=-2.3458  @x(4.0,2.0) Error!!!!           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	

double s1=(1.0-8.0*x[0]+7.0*x[0]*x[0]-2.33*x[0]*x[0]*x[0]+0.25*x[0]*x[0]*x[0]*x[0])*x[1]*x[1]*Math.exp(-x[1]);
return s1;
}
}

class f53 extends f_xj //Hansen function     f(x)=-176.54  @x(-1.30,-1.42)           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double s1=0.0;double s2=0.0;
for(int i=0;i<=4;i++)
{s1+=((double)i+1.0)*Math.cos((double)i*x[0]+(double)i+1.0);}
for(int j=0;j<=4;j++)
{s2+=((double)j+1.0)*Math.cos(((double)j+2.0)*x[1]+(double)j+1.0);}
return s1*s2;
}
}

class f54 extends f_xj //Gear function     f(x)=0.0  @x(16,19,43,49)  12<=x[i]<=60           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	

double s1=Math.pow((1.0/6.931-(x[0]*x[1])/(x[2]*x[3])),2.0);
return s1;
}
}

class f55 extends f_xj //Gear function     f(x)=0.0  @x(16,19,43,49)  12<=x[i]<=60           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double s1=0.0;int n=x.length;
for(int i=0;i<=n-2;i++)
{s1+=-(x[i+1]+47.0)*Math.sin(Math.sqrt(Math.abs(x[i+1]+0.5*x[i]+47.0)))+ Math.sin(Math.sqrt(Math.abs(x[i]-(x[i+1]+47.0))))*(-x[i]);}

return s1;
}
}

class f56 extends f_xj //Chichinadze function     f(x1, x2) = f(5.90133, 0.5) = -43.3159.  -30<=x[i]<=30           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double s1=x[0]*x[0]-12.0*x[0]+11.0+10.0*Math.cos(x[0]*3.1415/2.0)+8.0*Math.sin(5.0*3.1415*x[0])-((1.0/Math.sqrt(5.0))*Math.exp(-(x[1]-0.5)*(x[1]-0.5)/2.0));

return s1;
}
}

class f57 extends f_xj //Zettl function     f(x1, x2) = f(-0.02990, 0.0) = -0.003791.  -30<=x[i]<=30           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double s1=Math.pow((x[0]*x[0]+x[1]*x[1]-2.0*x[0]),2.0)+0.25*x[0];
return s1;
}
}

class f58 extends f_xj //Plateau function     f(x1, x2) = f(0.0, 0.0,0.0,0.0,0.0) = 30.0  -5.12<=x[i]<=5.12           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;
for(int i=0;i<n;i++)
{s1+=Math.abs(x[i]);}
return s1+30.0;

}
}


class f59 extends f_xj //Xin She Yang function1     f(x) = f(0.0, 0.0,0.0,0.0,0.0,....) = 0.0  -2pi<=x[i]<=2pi           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;double s2=0.0;
for(int i=0;i<n;i++)
{s1+=Math.abs(x[i]);}
for(int i=0;i<n;i++)
{s2+=-Math.sin(x[i]*x[i]);}
return s1*Math.exp(-s2);
}
}

class f60 extends f_xj //     f(x) = f(1.0,0.5,0.333,...) = 0.0  -5<=x[i]<=5           
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;
for(int i=0;i<n;i++)
{s1+=Math.random()*Math.abs(x[i]-(1.0/((double)i+1.0)));}
return s1;
}
}

class f61 extends f_xj //              
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
int n=x.length;double s1=0.0;double s2=0.0;double s3=0.0;
for(int i=0;i<n;i++)
{s1+=Math.sin(x[i])*Math.sin(x[i]);}
for(int i=0;i<n;i++)
{s2+=x[i]*x[i];}
double c1=(s1-Math.exp(-s2));
for(int i=0;i<n;i++)
{s3+=Math.pow(Math.sin(Math.sqrt(Math.abs(x[i]))),2.0);}
return c1*Math.exp(-s3);
}
}

class f62 extends f_xj // Yang funtion     x(pi,pi)             
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double alfa=1.0;
double beta=1.0;
int K=10;
double s1=0.0;
for(int j=0;j<K;j++)
{
  for(int i=0;i<K;i++)
  {
  s1+=Math.random()*Math.exp(-alfa*((x[0]-(double)i)*(x[0]-(double)i)+(x[1]-(double)j)*(x[1]-(double)j)));	  
  }	
	
}
double s2=-5.0*Math.exp(-beta*((x[0]-3.1415)*(x[0]-3.1415)+(x[1]-3.1415)*(x[1]-3.1415)));	

return s2-s1;

}
}

//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////// CONSTRAINED OPTIMIZATION ////////////////////////////
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////





// Minimize f(x)=(x[0]-6.0)*(x[0]-6.0)+(x[1]-7.0)*(x[1]-7.0)
//constrains
// g1(x)=-3*x[0]-2*x[1]+6.0<=0.0
// g2(x)=-x[0]-x[1]-3.0<=0.0
// g3(x)=x[0]+x[1]-7.0<=0.0
// g4(x)=0.66*x[0]-x[1]-1.333<=0.0
class f63 extends f_xj          
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=8.0;
return (x[0]-6.0)*(x[0]-6.0)+(x[1]-7.0)*(x[1]-7.0)+mu*(G1(x)+G2(x)+G3(x)+G4(x));
}

double G1(double x[])
{
return Math.pow(Math.max(0,-3*x[0]-2*x[1]+6.0),2.0);	
}
double G2(double x[])
{
return Math.pow(Math.max(0,-x[0]-x[1]-3.0),2.0);	
}
double G3(double x[])
{
return Math.pow(Math.max(0,x[0]+x[1]-7.0),2.0);	
}
double G4(double x[])
{
return Math.pow(Math.max(0,0.66*x[0]-x[1]-1.333),2.0);	
}
}
// Water cycle makalesinden alýndý

// Minimize ((x[0]-10.0)*(x[0]-10.0))+(5.0*(x[1]-12.0)*(x[1]-12.0))+(x[2]*x[2]*x[2]*x[2])+(3.0*(x[3]-11.0)*(x[3]-11.0))+(10.0*(x[4]*x[4]*x[4]*x[4]*x[4]*x[4]))+(7.0*x[5]*x[5])+(x[6]*x[6]*x[6]*x[6])-(4.0*x[5]*x[6])-10*x[5]-8.0*x[6];
// constrains
// g1(x)=127.0-2.0*x[0]*x[0]-3.0*x[1]*x[1]*x[1]*x[1]-x[2]-4.0*x[3]*x[3]-5.0*x[4]>=0.0
// g2(x)=282.0-7.0*x[0]-3.0*x[1]-10.0*x[2]*x[2]-x[3]+x[4]>=0.0
// g3(x)=196.0-23.0*x[0]-x[1]*x[1]-6.0*x[5]*x[5]+8.0*x[6]>=0.0
// g4(x)=-4.0*x[0]*x[0]-x[1]*x[1]+3.0*x[0]*x[1]-2.0*x[2]*x[2]-5.0*x[5]+11.0*x[6]>=0.0

class f64 extends f_xj          
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=0.25;
return ((x[0]-10.0)*(x[0]-10.0))+(5.0*(x[1]-12.0)*(x[1]-12.0))+(x[2]*x[2]*x[2]*x[2])+(3.0*(x[3]-11.0)*(x[3]-11.0))+(10.0*(x[4]*x[4]*x[4]*x[4]*x[4]*x[4]))+(7.0*x[5]*x[5])+(x[6]*x[6]*x[6]*x[6])-(4.0*x[5]*x[6])-10*x[5]-8.0*x[6]+mu*(G1(x)+G2(x)+G3(x)+G4(x));
}

double G1(double x[])
{
return Math.pow(Math.min(0.0,127.0-2.0*x[0]*x[0]-3.0*x[1]*x[1]*x[1]*x[1]-x[2]-4.0*x[3]*x[3]-5.0*x[4]),2.0);	
}
double G2(double x[])
{
return Math.pow(Math.min(0.0,282.0-7.0*x[0]-3.0*x[1]-10.0*x[2]*x[2]-x[3]+x[4]),2.0);	
}
double G3(double x[])
{
return Math.pow(Math.min(0.0,196.0-23.0*x[0]-x[1]*x[1]-6.0*x[5]*x[5]+8.0*x[6]),2.0);	
}
double G4(double x[])
{
return Math.pow(Math.min(0.0,-4.0*x[0]*x[0]-x[1]*x[1]+3.0*x[0]*x[1]-2.0*x[2]*x[2]-5.0*x[5]+11.0*x[6]),2.0);	
}
}


// minimize 5.3578547*x[2]*x[2]*x[2]+0.8356891*x[0]*x[4]+37.203239*x[0]+40729.141
// constraints
// g1(x)=85.334407+0.0056858*x[1]*x[4]+0.0006262*x[0]*x[3]-0.0022053*x[2]*x[4]-92.0<=0.0
// g2(x)=-85.334407-0.0056858*x[1]*x[4]-0.0006262*x[0]*x[3]-0.0022053*x[2]*x[4]<=0.0
// g3(x)=80.51249+0.0071317*x[1]*x[4]+0.0029955*x[0]*x[1]+0.0021813*x[2]*x[2]-110.0<=0.0
// g4(x)=-80.51249-0.0071317*x[1]*x[4]-0.0029955*x[0]*x[1]-0.0021813*x[2]*x[2]+90.0<=0.0
// g5(x)=9.300961+0.0047026*x[2]*x[4]+0.0012547*x[0]*x[2]+0.0019085*x[2]*x[3]-25.0<=0.0
// g6(x)=-9.300961-0.0047026*x[2]*x[4]-0.0012547*x[0]*x[2]-0.0019085*x[2]*x[3]+20.0<=0.0
// 78<x[0]<102, 33<x[1]<45, 27<x[i]<45 i=2,3,4
class f65 extends f_xj          
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=1953;
return (5.3578547*x[2]*x[2])+(0.8356891*x[0]*x[4])+(37.203239*x[0])-(40729.141)+(mu*(G1(x)+G2(x)+G3(x)+G4(x)+G5(x)+G6(x)));
}

double G1(double x[])
{
return Math.pow(Math.max(0.0,85.334407+0.0056858*x[1]*x[4]+0.0006262*x[0]*x[3]-0.0022053*x[2]*x[4]-92.0),2.0);	
}
double G2(double x[])
{
return Math.pow(Math.max(0.0,-85.334407-0.0056858*x[1]*x[4]-0.0006262*x[0]*x[3]+0.0022053*x[2]*x[4]),2.0);	
}
double G3(double x[])
{
return Math.pow(Math.max(0.0,80.51249+0.0071317*x[1]*x[3]+0.0029955*x[0]*x[1]+0.0021813*x[2]*x[2]-110.0),2.0);	
}
double G4(double x[])
{
return Math.pow(Math.max(0.0,-80.51249-0.0071317*x[1]*x[3]-0.0029955*x[0]*x[1]-0.0021813*x[2]*x[2]+90.0),2.0);	
}
double G5(double x[])
{
return Math.pow(Math.max(0.0,9.300961+0.0047026*x[2]*x[4]+0.0012547*x[0]*x[2]+0.0019085*x[2]*x[3]-25.0),2.0);	
}
double G6(double x[])
{
return Math.pow(Math.max(0.0,-9.300961-0.0047026*x[2]*x[4]-0.0012547*x[0]*x[2]-0.0019085*x[2]*x[3]+20.0),2.0);	
}
}

class f66 extends f_xj          
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=1.1;
double s1=1.0;
int n=x.length;
for(int i=0;i<n;i++)
{s1*=x[i];}
s1=-Math.pow(Math.sqrt((double)n),(double)n)*s1;
return s1+(mu*G1(x));
}

double G1(double x[])
{
double c1=0.0;
int n=x.length;
for(int i=0;i<n;i++)
{c1+=(x[i]*x[i]);}
c1=c1-1;

return Math.abs(c1)-1e-4;
}
}

class f67 extends f_xj          
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=1.2;
double s1=((3.0*x[0])+(1e-6*x[0]*x[0]*x[0])+(2.0*x[1])+((2e-6/3.0)*x[1]*x[1]*x[1]))+mu*(G1(x)+G2(x)+H1(x)+H2(x)+H3(x));
return s1;
}

double G1(double x[])
{
return  Math.pow(Math.max(0.0,x[2]-x[3]-0.55),2.0);	
}

double G2(double x[])
{
return Math.pow(Math.max(0.0,x[3]-x[2]-0.55),2.0);		
}

double H1(double x[])
{
return Math.abs(1000.0*(Math.sin(-x[2]-0.25)+Math.sin(-x[3]-0.25))+894.8-x[0])-1e-4;	
}

double H2(double x[])
{
return Math.abs(1000.0*(Math.sin(x[2]-0.25)+Math.sin(x[2]-x[3]-0.25))+894.8-x[1])-1e-4;	
}

double H3(double x[])
{
return Math.abs(1000.0*(Math.sin(x[3]-0.25)+Math.sin(x[3]-x[2]-0.25))+1294.8)-1e-4;	
}
}

class f68 extends f_xj          
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=0.007;
return ((x[0]-10.0)*(x[0]-10.0)*(x[0]-10.0))+((x[1]-20.0)*(x[1]-20.0)*(x[1]-20.0))+(mu*(G1(x)+G2(x)));
}

double G1(double x[])
{
return Math.pow(Math.max(0.0,(x[0]-5.0)*(x[0]-5.0)+(x[1]-5.0)*(x[1]-5.0)+100.0),2.0);
}
double G2(double x[])
{
return Math.pow(Math.max(0.0,(x[0]-5.0)*(x[0]-5.0)+(x[1]-5.0)*(x[1]-5.0)-82.81),2.0);
}
}

class f69 extends f_xj          
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=1.0;
double s1=x[0]*x[0]+(x[1]-1.0)*(x[1]-1.0)+mu*(H1(x));
return s1;
}

double H1(double x[])
{
return Math.abs(x[1]-x[0]*x[0])-1e-4;
}
}

class f70 extends f_xj          
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=0.012;
double s1=Math.exp(x[0]*x[1]*x[2]*x[3]*x[4])+mu*(H1(x)+H2(x)+H3(x));
return s1;
}

double H1(double x[])
{
  int n=x.length;
  double d=0.0;
  for(int i=0;i<n;i++)
  {d+=x[i]*x[i];}
  return Math.abs(d-10.0)-1e-4; 	
	
}

double H2(double x[])
{
return  Math.abs(x[1]*x[2]-5.0*x[3]*x[4])-1e-4; 	
}

double H3(double x[])
{
return Math.abs(x[0]*x[0]*x[0]+x[1]*x[1]*x[1]+1.0)-1e-4;	
}

}

class f71 extends f_xj   //minr blsdt 1       
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=180.0;
double s1=(x[0]-2.0)*(x[0]-2.0)+(x[1]-1.0)*(x[1]-1.0)+(mu*(H1(x)+H2(x)));
return s1;
}

double H1(double x[])
{return Math.abs(x[0]-2.0*x[1]+1.0)-1e-4;}

double H2(double x[])
{return  Math.pow(Math.min(0.0,(-x[0]*x[0]/4.0)-x[1]*x[1]+1.0),2.0);}
}

class f72 extends f_xj   //minr blsdt 3       
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=10000.0;
double s1=(x[0]*x[0]+x[1]-11.0)*(x[0]*x[0]+x[1]-11.0)+(x[0]+x[1]*x[1]-7.0)*(x[0]+x[1]*x[1]-7.0)+(mu*(H1(x)+H2(x)));
return s1;
}

double H1(double x[])
{return Math.pow(Math.min(0.0,4.84-((x[0]-0.05)*(x[0]-0.05))-((x[1]-2.5)*(x[1]-2.5))),2.0);}

double H2(double x[])
{return  Math.pow(Math.min(0.0,x[0]*x[0]+(x[1]-2.5)*(x[1]-2.5)-4.84),2.0);}
}


// Three bar truss design problem
class f73 extends f_xj         
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=10000000.0;
double s1=((2.0*Math.sqrt(2.0)*x[0]+x[1])*100)+mu*(H1(x)+H2(x)+H3(x));
return s1;
}

double H1(double x[])
{return Math.pow(Math.max(0.0, (((Math.sqrt(2.0)*x[0]+x[1])/(Math.sqrt(2)*x[0]*x[0]+2.0*x[0]*x[1]))*2.0)-2.0  ),2.0);}

double H2(double x[])
{return  Math.pow(Math.max(0.0,    2.0*(x[1]/(Math.sqrt(2)*x[0]*x[0]+2.0*x[0]))-2.0),2.0);}

double H3(double x[])
{return  Math.pow(Math.max(0.0,2.0*(1.0/(Math.sqrt(2.0)*x[1]+x[0]))-2.0),2.0);}
}

// Presure vessel design problem !!!error
class f74 extends f_xj         
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=1.0;
double s1=(0.6224*x[0]*x[2]*x[3])+(1.7781*x[1]*x[2]*x[2])+(3.1661*x[0]*x[0]*x[3])+(19.84*x[0]*x[0]*x[2])+(mu*(H1(x)+H2(x)+H3(x)+H4(x)));
return s1;
}

double H1(double x[])
{return Math.pow(Math.min(0.0, -x[0]+0.0193*x[2]),2.0);}

double H2(double x[])
{return  Math.pow(Math.min(0.0, -x[1]+0.00954*x[2]),2.0);}

double H3(double x[])
{return  Math.pow(Math.min(0.0,-3.1415*x[2]*x[2]*x[3]-(1.333)*3.1415*x[2]*x[2]*x[2]+1296000),2.0);}

double H4(double x[])
{return  Math.pow(Math.min(0.0,x[3]-240.0),2.0);}
}


//Tension/compression spring design problem
class f75 extends f_xj         
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=0.3;
double s1=((x[2]+2.0)*x[1]*x[0]*x[0])+(mu*(H1(x)+H2(x)+H3(x)+H4(x)));
return s1;
}

double H1(double x[])
{return Math.pow(Math.max(0.0,         1.0-((x[1]*x[1]*x[1]*x[2])/(71785.0*x[0]*x[0]*x[0]*x[0]))                  ),2.0);}

double H2(double x[])
{return  Math.pow(Math.max(0.0,        (((4.0*x[1]*x[1])-(x[0]*x[1]))/(12566.0*(x[1]*x[0]*x[0]*x[0]-x[0]*x[0]*x[0]*x[0])))+(1.0/(5108.0*x[0]*x[0]))-1.0              ),2.0);}

double H3(double x[])
{return  Math.pow(Math.max(0.0,        1.0-((140.45*x[0])/(x[1]*x[1]*x[2]))        ),2.0);}

double H4(double x[])
{return  Math.pow(Math.max(0.0,     ((x[0]+x[1])/1.5)-1.0        ),2.0);}
}


// Welded beam design

class f76 extends f_xj         
{
	
double P=6000.0;
double L=14.0;
double E=30000000.0;	
double G=12000000.0;
double tomax=13600.0;
double sigmax=30000.0;
double deltamax=0.25;

double sigma(double x[])
{return (6.0*P*L)/(x[3]*x[2]*x[2]);}

double delta(double x[])
{return (4.0*P*L*L*L)/(E*x[2]*x[2]*x[2]*x[3]);}

double Pc(double x[])
{return (4.013*E/(6.0*L*L))*x[2]*x[3]*x[3]*x[3]*(1-0.25*x[2]*Math.sqrt(E/G)/L);}
	
double J(double x[])
{return   2.0/Math.sqrt(2)*x[0]*x[1]*(x[1]*x[1]/12.0+0.25*(x[0]+x[2])*(x[0]+x[2]));                    }
	
double R(double x[])
{return	Math.sqrt(0.25*(x[1]*x[1]+(x[0]+x[2])*(x[0]+x[2])));}

double M(double x[])
{return P*(L+x[1]/2.0);}

double to1(double x[])
{return P/(Math.sqrt(2)*x[0]*x[1]);}

double to2(double x[])
{return M(x)*R(x)/J(x);}

double toend(double x[])
{return Math.sqrt(to1(x)*to1(x)+(2.0*to1(x)*to2(x)*x[1]/(2.0*R(x)))+to2(x)*to2(x));}
	
	
	
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=0.0000000015;
double s1=(1.10471*x[0]*x[0]*x[1])+(0.04811*x[2]*x[3]*(14.0+x[1]))+(mu*(H1(x)+H2(x)+H3(x)+H4(x)+H5(x)+H6(x)+H7(x)));
return s1;
}

double H1(double x[])
{return Math.pow(Math.max(0.0,         toend(x)-tomax                 ),2.0);}

double H2(double x[])
{return  Math.pow(Math.max(0.0,         sigma(x)-sigmax           ),2.0);}

double H3(double x[])
{return  Math.pow(Math.max(0.0,        x[0]-x[3]        ),2.0);}

double H4(double x[])
{return  Math.pow(Math.max(0.0,       0.10471*x[0]*x[0]+0.04811*x[2]*x[3]*(14.0+x[1])-5.0         ),2.0);}

double H5(double x[])
{return  Math.pow(Math.max(0.0,      0.125-x[0]         ),2.0);}

double H6(double x[])	
{return  Math.pow(Math.max(0.0,       delta(x)-deltamax         ),2.0);}

double H7(double x[])
{return  Math.pow(Math.max(0.0,       P-Pc(x)         ),2.0);}

}


// speed reducer design problem
class f77 extends f_xj         
{
public double func(double x[]) 
{
//çözümü istenen fonksiyon	
double mu=1820000000000000.7;
double s1=0.7854*x[0]*x[1]*x[1]*(3.3333*x[2]*x[2]+14.9334*x[2]-43.0934)-(1.508*x[0]*(x[5]*x[5]+x[6]*x[6]))+(7.4777*(x[5]*x[5]*x[5]+x[6]*x[6]*x[6]))+(0.7854*(x[3]*x[5]*x[5]+x[4]*x[6]*x[6]))+(mu*(H1(x)+H2(x)+H3(x)+H4(x)+H5(x)+H6(x)+H7(x)+H8(x)+H9(x)+H10(x)+H11(x)));
return s1;
}

double H1(double x[])
{return Math.pow(Math.max(0.0,         (27.0/(x[0]*x[1]*x[1]*x[2]))-1.0                 ),2.0);}

double H2(double x[])
{return  Math.pow(Math.max(0.0,       (397.5/(x[0]*x[1]*x[1]*x[2]*x[2]))-1.0             ),2.0);}

double H3(double x[])
{return  Math.pow(Math.max(0.0,      ((1.93*x[3]*x[3]*x[3])/(x[1]*x[5]*x[5]*x[5]*x[5]*x[2]))-1.0          ),2.0);}

double H4(double x[])
{return  Math.pow(Math.max(0.0,     ((1.93*x[4]*x[4]*x[4])/(x[1]*x[6]*x[6]*x[6]*x[6]*x[2]))-1.0        ),2.0);}

double H5(double x[])
{return  Math.pow(Math.max(0.0,     (Math.sqrt((745.0*(x[3]/(x[1]*x[2])))*(745.0*(x[3]/(x[1]*x[2])))+16.9e6)/(110.0*x[5]*x[5]*x[5]))-1.0                                    ),2.0);}

double H6(double x[])
{return  Math.pow(Math.max(0.0,    (Math.sqrt((745.0*(x[4]/(x[1]*x[2])))*(745.0*(x[3]/(x[1]*x[2])))+157.5e6)/(85.0*x[6]*x[6]*x[6]))-1.0     ),2.0);}

double H7(double x[])
{return  Math.pow(Math.max(0.0,     (x[1]*x[2]/40.0)-1.0        ),2.0);}

double H8(double x[])
{return  Math.pow(Math.max(0.0,     (5.0*x[1]/x[0])-1.0        ),2.0);}

double H9(double x[])
{return  Math.pow(Math.max(0.0,     (x[0]/(12.0*x[1]))-1.0      ),2.0);}

double H10(double x[])
{return  Math.pow(Math.max(0.0,     ((1.5*x[5]+1.9)/x[3])-1.0        ),2.0);}

double H11(double x[])
{return  Math.pow(Math.max(0.0,     ((1.1*x[6]+1.9)/x[4])-1.0       ),2.0);}


}



class par_est2 extends f_xj
{
	
public double func(double x[])
{
double[] I1={4.84992,10.7714,16.0843,21.1287,26.5745,32.2935,37.7378,42.7127,48.5654,54.0119,59.3275,65.3104,70.2269,75.6784};
double[] V1={0.921044,0.875721,0.848175,0.819634,0.798022,0.756648,0.741955,0.723297,0.684891,0.660314,0.620907,0.600297,0.544075,0.497752};
double[] V_dummy1=new double[14];
double N=1.0;
//double Jmax=0.86;//I/cm2
double RHa=1.0;
double RHb=1.0;
double A=50.6;//cm2
double l=178.0e-4;//cm
//initialize
double Rm=0.0;
double T=343.15;//K
double PO2=1.0;//atm
double PH2=1.0;
double V_Ernst=0.0;
double CO2=0.0;
double V_act=0.0;
double ro_m=0.0;
double R_m=0.0;
double V_ohm=0.0;
double V_con=0.0;

for(int i=0;i<14;i++)
{
    
    
			
	   
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(  (I1[i]+(x[10]/x[9]))   )));
        ro_m=181.6*(1.0+(0.03*(   (I1[i]+(x[10]/x[9]))    /x[9]  ))+(0.062*(T/303.0)*(T/303.0)*Math.pow((     (I1[i]+(x[10]/x[9]))   /   x[9]),2.5)))/((x[4]-0.634-3.0*(   (I1[i]+(x[10]/x[9]))  /    x[9]))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*x[7]/x[9];
	    V_ohm=(I1[i]+(x[10]/x[9]))*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((( (I1[i]+(x[10]/x[9]))  /  x[9]))/x[8]));
	    V_dummy1[i]=N*(V_Ernst-V_act-V_ohm-V_con);
    
	
	
	
	
}

double val1=0.0;	
for(int i=0;i<14;i++)
{val1+=(V1[i]-V_dummy1[i])*(V1[i]-V_dummy1[i]);}

return val1;	
	
}	


double psat(double T)
{
return	Math.pow(10.0,((2.95e-2*(T-273.15))-((9.18e-5)*(T-273.15)*(T-273.15))+((1.44e-7)*(T-273.15)*(T-273.15)*(T-273.15))-2.18));	
}	
	
double bartoatm(double barr)
{return barr*0.986923267;}	
	
	
	
}


class par_est extends f_xj
{
	
public double func(double x[])
{
double[] I1={0.227213,1.3085,2.6534,3.99747,5.36203,6.70621,8.05038,10.7587,13.4467,16.1351,17.4995,18.8436,20.208,21.5522,22.9172};
double[] V1={23.5485,21.4567,20.3221,19.935,19.4284,18.9516,18.4748,17.8501,17.2553,16.212,15.8548,15.4976,15.1405,14.6338,13.6786};
double[] I2={0.224307,1.30506,2.6509,3.99674,5.36297,6.70881,8.05465,10.7463,13.4584,16.1705,17.5163,18.8825,20.2284,21.5742,22.9201};
double[] V2={21.5522,19.7015,18.7463,18.0,17.5522,17.1642,16.6567,15.9104,15.1642,14.4478,14.0299,13.5522,12.6567,10.8955,8.89552};
double[] I3={0.285837,1.32988,2.66286,4.05778,5.39127,6.76593,8.079,10.7874,13.4959,16.1836,17.5584,18.8716,20.2667,21.5792,22.9325};
double[] V3={23.235,21.0279,20.0851,19.4364,18.9054,18.4626,18.0199,17.2814,16.543,15.6869,15.3029,14.9484,14.4762,13.5923,12.5024};
double[] I4={0.248484,1.29844,2.65425,4.03034,5.36496,6.74076,8.07542,10.7858,13.4961,16.1653,17.5409,18.8553,20.2521,21.5878,22.9242};
double[] V4={22.6822,20.1581,19.2495,18.5463,18.1662,17.698,17.2885,16.4695,15.7092,14.9782,14.6862,14.0711,13.2212,12.0188,10.1705};


double[][] V_dummy=new double[4][15];
double[][] V_real=new double[4][15];
double[][] Ireal=new double[4][15];

for(int j=0;j<15;j++)
{V_real[0][j]=V1[j];}	
for(int j=0;j<15;j++)
{V_real[1][j]=V2[j];}	
for(int j=0;j<15;j++)
{V_real[2][j]=V3[j];}	
for(int j=0;j<15;j++)
{V_real[3][j]=V4[j];}

for(int j=0;j<15;j++)
{Ireal[0][j]=I1[j];}	
for(int j=0;j<15;j++)
{Ireal[1][j]=I2[j];}	
for(int j=0;j<15;j++)
{Ireal[2][j]=I3[j];}	
for(int j=0;j<15;j++)
{Ireal[3][j]=I4[j];}

double N=24.0;
double Jmax=0.86;//I/cm2
double RHa=1.0;
double RHb=1.0;
double A=27.0;//cm2
double l=127.0e-4;//cm


//initialize





double Rm=0.0;


double Pa_atm=0.0;
double Pb_atm=0.0;
double T=0.0;//K
double Pa=0.0;//bar
double Pb=0.0;//bar
double PO2=0.0;//atm
double PH2=0.0;
double V_Ernst=0.0;
double CO2=0.0;
double V_act=0.0;
double ro_m=0.0;
double R_m=0.0;
double V_ohm=0.0;
double V_con=0.0;

for(int i=0;i<4;i++)
{
    if(i==0)
    {
	 T=353.15;    
	 Pa=3.0;
	 Pb=5.0; 
	 Pa_atm=bartoatm(Pa);
	 Pb_atm=bartoatm(Pb);  
	 for(int j=0;j<15;j++)
     { 		
	    PO2=RHb*psat(T)*((1.0/(Math.exp((4.192*Ireal[i][j]/A)/(Math.pow(T,1.334)))*(RHb*psat(T)/Pb_atm)))-1.0);
		PH2=0.5*RHa*psat(T)*((1.0/(Math.exp((1.635*Ireal[i][j]/A)/(Math.pow(T,1.334)))*(RHa*psat(T)/Pa_atm)))-1.0);
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(Ireal[i][j])));
        ro_m=181.6*(1.0+(0.03*(Ireal[i][j]/A))+(0.062*(T/303.0)*(T/303.0)*Math.pow((Ireal[i][j]/A),2.5)))/((x[4]-0.634-3.0*(Ireal[i][j]/A))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*l/A;
	    V_ohm=Ireal[i][j]*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((Ireal[i][j]/A)/Jmax));
	    V_dummy[i][j]=N*(V_Ernst-V_act-V_ohm-V_con);
     }	    
	}
	if(i==1)
    {
	 T=343.15;    
	 Pa=1.0;
	 Pb=1.0; 
	 Pa_atm=bartoatm(Pa);
	 Pb_atm=bartoatm(Pb);  
	 for(int j=0;j<15;j++)
     { 		
	    PO2=RHb*psat(T)*((1.0/(Math.exp((4.192*Ireal[i][j]/A)/(Math.pow(T,1.334)))*(RHb*psat(T)/Pb_atm)))-1.0);
		PH2=0.5*RHa*psat(T)*((1.0/(Math.exp((1.635*Ireal[i][j]/A)/(Math.pow(T,1.334)))*(RHa*psat(T)/Pa_atm)))-1.0);
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(Ireal[i][j])));
        ro_m=181.6*(1.0+(0.03*(Ireal[i][j]/A))+(0.062*(T/303.0)*(T/303.0)*Math.pow((Ireal[i][j]/A),2.5)))/((x[4]-0.634-3.0*(Ireal[i][j]/A))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*l/A;
	    V_ohm=Ireal[i][j]*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((Ireal[i][j]/A)/Jmax));
	    V_dummy[i][j]=N*(V_Ernst-V_act-V_ohm-V_con);
     }	    
	}	
	if(i==2)
    {
	 T=343.15;    
	 Pa=2.5;
	 Pb=3.0; 
	 Pa_atm=bartoatm(Pa);
	 Pb_atm=bartoatm(Pb);  
	 for(int j=0;j<15;j++)
     { 		
	    PO2=RHb*psat(T)*((1.0/(Math.exp((4.192*Ireal[i][j]/A)/(Math.pow(T,1.334)))*(RHb*psat(T)/Pb_atm)))-1.0);
		PH2=0.5*RHa*psat(T)*((1.0/(Math.exp((1.635*Ireal[i][j]/A)/(Math.pow(T,1.334)))*(RHa*psat(T)/Pa_atm)))-1.0);
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(Ireal[i][j])));
        ro_m=181.6*(1.0+(0.03*(Ireal[i][j]/A))+(0.062*(T/303.0)*(T/303.0)*Math.pow((Ireal[i][j]/A),2.5)))/((x[4]-0.634-3.0*(Ireal[i][j]/A))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*l/A;
	    V_ohm=Ireal[i][j]*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((Ireal[i][j]/A)/Jmax));
	    V_dummy[i][j]=N*(V_Ernst-V_act-V_ohm-V_con);
     }	    
	}
	if(i==3)
    {
	 T=343.15;    
	 Pa=1.5;
	 Pb=1.5; 
	 Pa_atm=bartoatm(Pa);
	 Pb_atm=bartoatm(Pb);  
	 for(int j=0;j<15;j++)
     { 		
	    PO2=RHb*psat(T)*((1.0/(Math.exp((4.192*Ireal[i][j]/A)/(Math.pow(T,1.334)))*(RHb*psat(T)/Pb_atm)))-1.0);
		PH2=0.5*RHa*psat(T)*((1.0/(Math.exp((1.635*Ireal[i][j]/A)/(Math.pow(T,1.334)))*(RHa*psat(T)/Pa_atm)))-1.0);
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(Ireal[i][j])));
        ro_m=181.6*(1.0+(0.03*(Ireal[i][j]/A))+(0.062*(T/303.0)*(T/303.0)*Math.pow((Ireal[i][j]/A),2.5)))/((x[4]-0.634-3.0*(Ireal[i][j]/A))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*l/A;
	    V_ohm=Ireal[i][j]*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((Ireal[i][j]/A)/Jmax));
	    V_dummy[i][j]=N*(V_Ernst-V_act-V_ohm-V_con);
     }	    
	}
	
}

double val=0.0;	
for(int i=0;i<4;i++)
{
  for(int j=0;j<15;j++)
  {
  val+=(V_real[i][j]-V_dummy[i][j])*(V_real[i][j]-V_dummy[i][j]);   	  
  }	
}

return val;	
	
}	


double psat(double T)
{
return	Math.pow(10.0,((2.95e-2*(T-273.15))-((9.18e-5)*(T-273.15)*(T-273.15))+((1.44e-7)*(T-273.15)*(T-273.15)*(T-273.15))-2.18));	
}	
	
double bartoatm(double barr)
{return barr*0.986923267;}	
}

class par_est4 extends f_xj
{
	
public double func(double x[])
{
double[] I1={0.01,4.99101,10.009,15.0,19.964,25.036,30.027};
double[] V11={30.1304,24.8478,22.1087,20.25,19.2717,18.6848,17.1196};
double[] V_dummy1=new double[V11.length];
double N=32.0;
//double Jmax=0.86;//I/cm2
double RHa=1.0;
double RHb=1.0;
double A=50.6;//cm2
double l=178.0e-4;//cm
//initialize
double Rm=0.0;
double T=333.0;//K
double PO2=0.2095;//atm
double PH2=1.000;
double V_Ernst=0.0;
double CO2=0.0;
double V_act=0.0;
double ro_m=0.0;
double R_m=0.0;
double V_ohm=0.0;
double V_con=0.0;

for(int i=0;i<V11.length;i++)
{
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(  (I1[i]+(x[10]/x[9]))   )));
        ro_m=181.6*(1.0+(0.03*(   (I1[i]+(x[10]/x[9]))    /x[9]  ))+(0.062*(T/303.0)*(T/303.0)*Math.pow((     (I1[i]+(x[10]/x[9]))   /   x[9]),2.5)))/((x[4]-0.634-3.0*(   (I1[i]+(x[10]/x[9]))  /    x[9]))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*x[7]/x[9];
	    V_ohm=(I1[i]+(x[10]/x[9]))*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((( (I1[i]+(x[10]/x[9]))  /  x[9]))/x[8]));
	    V_dummy1[i]=N*(V_Ernst-V_act-V_ohm-V_con);
}

double val1=0.0;	
for(int i=0;i<V11.length;i++)
{val1+=(V11[i]-V_dummy1[i])*(V11[i]-V_dummy1[i]);}

return val1;	
	
}	


double psat(double T)
{return	Math.pow(10.0,((2.95e-2*(T-273.15))-((9.18e-5)*(T-273.15)*(T-273.15))+((1.44e-7)*(T-273.15)*(T-273.15)*(T-273.15))-2.18));}	
	
double bartoatm(double barr)
{return barr*0.986923267;}	
	
}


class par_est3 extends f_xj
{
	
public double func(double x[])
{
double[] I1={0.227213,1.3085,2.6534,3.99747,5.36203,6.70621,8.05038,10.7587,13.4467,16.1351,17.4995,18.8436,20.208,21.5522,22.9172};
double[] V1={23.5485,21.4567,20.3221,19.935,19.4284,18.9516,18.4748,17.8501,17.2553,16.212,15.8548,15.4976,15.1405,14.6338,13.6786};
double[] I2={0.224307,1.30506,2.6509,3.99674,5.36297,6.70881,8.05465,10.7463,13.4584,16.1705,17.5163,18.8825,20.2284,21.5742,22.9201};
double[] V2={21.5522,19.7015,18.7463,18.0,17.5522,17.1642,16.6567,15.9104,15.1642,14.4478,14.0299,13.5522,12.6567,10.8955,8.89552};
double[] I3={0.285837,1.32988,2.66286,4.05778,5.39127,6.76593,8.079,10.7874,13.4959,16.1836,17.5584,18.8716,20.2667,21.5792,22.9325};
double[] V3={23.235,21.0279,20.0851,19.4364,18.9054,18.4626,18.0199,17.2814,16.543,15.6869,15.3029,14.9484,14.4762,13.5923,12.5024};
double[] I4={0.248484,1.29844,2.65425,4.03034,5.36496,6.74076,8.07542,10.7858,13.4961,16.1653,17.5409,18.8553,20.2521,21.5878,22.9242};
double[] V4={22.6822,20.1581,19.2495,18.5463,18.1662,17.698,17.2885,16.4695,15.7092,14.9782,14.6862,14.0711,13.2212,12.0188,10.1705};


double[][] V_dummy=new double[4][15];
double[][] V_real=new double[4][15];
double[][] Ireal=new double[4][15];

for(int j=0;j<15;j++)
{V_real[0][j]=V1[j];}	
for(int j=0;j<15;j++)
{V_real[1][j]=V2[j];}	
for(int j=0;j<15;j++)
{V_real[2][j]=V3[j];}	
for(int j=0;j<15;j++)
{V_real[3][j]=V4[j];}

for(int j=0;j<15;j++)
{Ireal[0][j]=I1[j];}	
for(int j=0;j<15;j++)
{Ireal[1][j]=I2[j];}	
for(int j=0;j<15;j++)
{Ireal[2][j]=I3[j];}	
for(int j=0;j<15;j++)
{Ireal[3][j]=I4[j];}

double N=24.0;
//double Jmax=0.86;//I/cm2
double RHa=1.0;
double RHb=1.0;
//double A=27.0;//cm2
//double l=127.0e-4;//cm


//initialize





double Rm=0.0;


double Pa_atm=0.0;
double Pb_atm=0.0;
double T=0.0;//K
double Pa=0.0;//bar
double Pb=0.0;//bar
double PO2=0.0;//atm
double PH2=0.0;
double V_Ernst=0.0;
double CO2=0.0;
double V_act=0.0;
double ro_m=0.0;
double R_m=0.0;
double V_ohm=0.0;
double V_con=0.0;

for(int i=0;i<4;i++)
{
    if(i==0)
    {
	 T=353.15;    
	 Pa=3.0;
	 Pb=5.0; 
	 Pa_atm=bartoatm(Pa);
	 Pb_atm=bartoatm(Pb);  
	 for(int j=0;j<15;j++)
     { 		
	    PO2=RHb*psat(T)*((1.0/(Math.exp((4.192*Ireal[i][j]/x[9])/(Math.pow(T,1.334)))*(RHb*psat(T)/Pb_atm)))-1.0);
		PH2=0.5*RHa*psat(T)*((1.0/(Math.exp((1.635*Ireal[i][j]/x[9])/(Math.pow(T,1.334)))*(RHa*psat(T)/Pa_atm)))-1.0);
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(Ireal[i][j])));
        ro_m=181.6*(1.0+(0.03*(Ireal[i][j]/x[9]))+(0.062*(T/303.0)*(T/303.0)*Math.pow((Ireal[i][j]/x[9]),2.5)))/((x[4]-0.634-3.0*(Ireal[i][j]/x[9]))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*x[7]/x[9];
	    V_ohm=Ireal[i][j]*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((Ireal[i][j]/x[9])/x[8]));
	    V_dummy[i][j]=N*(V_Ernst-V_act-V_ohm-V_con);
     }	    
	}
	if(i==1)
    {
	 T=343.15;    
	 Pa=1.0;
	 Pb=1.0; 
	 Pa_atm=bartoatm(Pa);
	 Pb_atm=bartoatm(Pb);  
	 for(int j=0;j<15;j++)
     { 		
	    PO2=RHb*psat(T)*((1.0/(Math.exp((4.192*Ireal[i][j]/x[9])/(Math.pow(T,1.334)))*(RHb*psat(T)/Pb_atm)))-1.0);
		PH2=0.5*RHa*psat(T)*((1.0/(Math.exp((1.635*Ireal[i][j]/x[9])/(Math.pow(T,1.334)))*(RHa*psat(T)/Pa_atm)))-1.0);
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(Ireal[i][j])));
        ro_m=181.6*(1.0+(0.03*(Ireal[i][j]/x[9]))+(0.062*(T/303.0)*(T/303.0)*Math.pow((Ireal[i][j]/x[9]),2.5)))/((x[4]-0.634-3.0*(Ireal[i][j]/x[9]))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*x[7]/x[9];
	    V_ohm=Ireal[i][j]*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((Ireal[i][j]/x[9])/x[8]));
	    V_dummy[i][j]=N*(V_Ernst-V_act-V_ohm-V_con);
     }	    
	}	
	if(i==2)
    {
	 T=343.15;    
	 Pa=2.5;
	 Pb=3.0; 
	 Pa_atm=bartoatm(Pa);
	 Pb_atm=bartoatm(Pb);  
	 for(int j=0;j<15;j++)
     { 		
	    PO2=RHb*psat(T)*((1.0/(Math.exp((4.192*Ireal[i][j]/x[9])/(Math.pow(T,1.334)))*(RHb*psat(T)/Pb_atm)))-1.0);
		PH2=0.5*RHa*psat(T)*((1.0/(Math.exp((1.635*Ireal[i][j]/x[9])/(Math.pow(T,1.334)))*(RHa*psat(T)/Pa_atm)))-1.0);
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(Ireal[i][j])));
        ro_m=181.6*(1.0+(0.03*(Ireal[i][j]/x[9]))+(0.062*(T/303.0)*(T/303.0)*Math.pow((Ireal[i][j]/x[9]),2.5)))/((x[4]-0.634-3.0*(Ireal[i][j]/x[9]))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*x[7]/x[9];
	    V_ohm=Ireal[i][j]*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((Ireal[i][j]/x[9])/x[8]));
	    V_dummy[i][j]=N*(V_Ernst-V_act-V_ohm-V_con);
     }	    
	}
	if(i==3)
    {
	 T=343.15;    
	 Pa=1.5;
	 Pb=1.5; 
	 Pa_atm=bartoatm(Pa);
	 Pb_atm=bartoatm(Pb);  
	 for(int j=0;j<15;j++)
     { 		
	    PO2=RHb*psat(T)*((1.0/(Math.exp((4.192*Ireal[i][j]/x[9])/(Math.pow(T,1.334)))*(RHb*psat(T)/Pb_atm)))-1.0);
		PH2=0.5*RHa*psat(T)*((1.0/(Math.exp((1.635*Ireal[i][j]/x[9])/(Math.pow(T,1.334)))*(RHa*psat(T)/Pa_atm)))-1.0);
        V_Ernst=1.229-(0.85e-3*(T-298.15))+(4.3085e-5*T*(Math.log(PH2)+0.5*Math.log(PO2)));
        CO2=PO2/(5.086e6*Math.exp(-498.0/T));
        V_act=-(x[0]+(x[1]*T)+(x[2]*T*Math.log(CO2))+(x[3]*T*Math.log(Ireal[i][j])));
        ro_m=181.6*(1.0+(0.03*(Ireal[i][j]/x[9]))+(0.062*(T/303.0)*(T/303.0)*Math.pow((Ireal[i][j]/x[9]),2.5)))/((x[4]-0.634-3.0*(Ireal[i][j]/x[9]))*Math.exp(4.18*((T-303.0)/T)));
	    R_m=ro_m*x[7]/x[9];
	    V_ohm=Ireal[i][j]*(R_m+x[5]);
	    V_con=-x[6]*Math.log(1.0-((Ireal[i][j]/x[9])/x[8]));
	    V_dummy[i][j]=N*(V_Ernst-V_act-V_ohm-V_con);
     }	    
	}
	
}

double val=0.0;	
for(int i=0;i<4;i++)
{
  for(int j=0;j<15;j++)
  {
  val+=(V_real[i][j]-V_dummy[i][j])*(V_real[i][j]-V_dummy[i][j]);   	  
  }	
}

return val;	
	
}	


double psat(double T)
{
return	Math.pow(10.0,((2.95e-2*(T-273.15))-((9.18e-5)*(T-273.15)*(T-273.15))+((1.44e-7)*(T-273.15)*(T-273.15)*(T-273.15))-2.18));	
}	
	
double bartoatm(double barr)
{return barr*0.986923267;}	
}


class solar2 extends f_xj
{
double func(double x[])
{
// double  diode	
double[] Vt={-0.2057,-0.1291,-0.0588,0.0057,0.0646,0.1185,0.1678,0.2132,0.2545,0.2924,0.3269,0.3585,0.3873,0.4137,0.4373,0.459,0.4784,0.496,0.5119,0.5265,0.5398,0.5521,0.5633,0.5736,0.5833,0.59};
double[] It={0.764,0.762,0.7605,0.7605,0.76,0.759,0.757,0.757,0.7555,0.754,0.7505,0.7465,0.7385,0.728,0.7065,0.6755,0.632,0.573,0.499,0.413,0.3165,0.212,0.1035,-0.01,-0.123,-0.21};
//Rs    x[0]
//Rsh   x[1]
//IPh   x[2]	
//Isd1  x[3]	
//n1    x[4]
//Isd2  x[5]
//n2    x[6]
double q=1.602176565e-19;// electronic charge
double kb=1.3806505e-23;//Boltzmann
double T=273.15+33.0;

int m=Vt.length;
double sum=0.0;


for(int i=0;i<m;i++)
{sum+=(It[i]-x[2]+(x[3]*(Math.exp(q*(Vt[i]+x[0]*It[i])/(x[4]*kb*T))-1.0))+(x[5]*(Math.exp(q*(Vt[i]+x[0]*It[i])/(x[6]*kb*T))-1.0))+((Vt[i]+x[0]*It[i])/(x[1])))*(It[i]-x[2]+(x[3]*(Math.exp(q*(Vt[i]+x[0]*It[i])/(x[4]*kb*T))-1.0))+(x[5]*(Math.exp(q*(Vt[i]+x[0]*It[i])/(x[6]*kb*T))-1.0))+((Vt[i]+x[0]*It[i])/(x[1]))) ;}
	
return Math.sqrt(sum/(double)m);	
}	
}

class solar1 extends f_xj// Himmelblau f(x)=0.0  @x=(3.0,2.0),(-2.8051,3.1313),(-3.7793,-3.2831),(3.5844,-1.8481)   -6.0<=x[i]<=6.0 i=0,1
{
double func(double x[])
{
// single diode	
double[] Vt={-0.2057,-0.1291,-0.0588,0.0057,0.0646,0.1185,0.1678,0.2132,0.2545,0.2924,0.3269,0.3585,0.3873,0.4137,0.4373,0.459,0.4784,0.496,0.5119,0.5265,0.5398,0.5521,0.5633,0.5736,0.5833,0.59};
double[] It={0.764,0.762,0.7605,0.7605,0.76,0.759,0.757,0.757,0.7555,0.754,0.7505,0.7465,0.7385,0.728,0.7065,0.6755,0.632,0.573,0.499,0.413,0.3165,0.212,0.1035,-0.01,-0.123,-0.21};
//Rs   x[0]
//Rsh  x[1]
//IPh  x[2]	
//Isd  x[3]	
//n    x[4]
double q=1.602176565e-19;// electronic charge
double kb=1.3806505e-23;//Boltzmann
double T=273.15+33.0;

int m=Vt.length;
double sum=0.0;


for(int i=0;i<m;i++)
{sum+=(It[i]-x[2]+(x[3]*(Math.exp(q*(Vt[i]+x[0]*It[i])/(x[4]*kb*T))-1.0))+((Vt[i]+x[0]*It[i])/(x[1])))*(It[i]-x[2]+(x[3]*(Math.exp(q*(Vt[i]+x[0]*It[i])/(x[4]*kb*T))-1.0))+((Vt[i]+x[0]*It[i])/(x[1])));}
	
return Math.sqrt(sum/(double)m);	
}	
}


class pv extends f_xj// Himmelblau f(x)=0.0  @x=(3.0,2.0),(-2.8051,3.1313),(-3.7793,-3.2831),(3.5844,-1.8481)   -6.0<=x[i]<=6.0 i=0,1
{
double func(double x[])
{
// double  diode	
double[] Vt={0.1248,1.8093,3.3511,4.7622,6.0538,7.2364,8.3189,9.3097,10.2163,11.0449,11.8018,12.4929,13.1231,13.6983,14.2221,14.6995,15.1346,15.5311,15.8929,16.2229,16.5241,16.7987,17.0499,17.2793,17.4885};
double[] It={1.0315,1.0300,1.0260,1.0220,1.0180,1.0155,1.0140,1.0100,1.0035,0.9880,0.9630,0.9255,0.8725,0.8075,0.7265,0.6345,0.5345,0.4275,0.3185,0.2085,0.1010,-0.0080,-0.1110,-0.2090,-0.3030 };
//Rs    x[0]
//Rsh   x[1]
//IPh   x[2]	
//Isd1  x[3]	
//n1    x[4]

double q=1.602176565e-19;// electronic charge
double kb=1.3806505e-23;//Boltzmann
double T=273.15+45.0;
double Np=1.0;
double Ns=36.0;
double Vtt=kb*T/q;

int m=Vt.length;
double sum=0.0;


for(int i=0;i<m;i++)
{sum+=(It[i]-x[2]+(x[3]*(Math.exp((Vt[i]+It[i]*x[0])/(x[4]*Vtt*Ns))-1.0))+(((Vt[i]+It[i]*x[0])/(x[1]))))*(It[i]-x[2]+(x[3]*(Math.exp((Vt[i]+It[i]*x[0])/(x[4]*Vtt*Ns))-1.0))+(((Vt[i]+It[i]*x[0])/(x[1]))))    ;}
	
return Math.sqrt(sum/(double)m);	
}	
}

class f2001 extends f_xj //optimal chiller case 1 coelho
{
	
double[] RT={1280.0,1280.0,1280.0,1280.0,1250.0,1250.0};
double CL=5334.0;

public double func(double x[]) 
{
double P[]=new double[6]; 	
double a[]={399.345,287.116,-120.505,-19.121,-95.029,191.750};
double b[]={-122.12,80.04,1525.99,898.76,1202.39,224.86};	
double c[]={770.46,700.48,-502.14,-98.15,-352.16,524.04};


double sum=0.0;
for(int i=0;i<6;i++)
{P[i]=a[i]+b[i]*x[i]+c[i]*x[i]*x[i];}
for(int i=0;i<6;i++)
{sum+=P[i];}
return sum+5.0*H(x);
}

double H(double x[])
{
double sum1=0.0;	
for(int i=0;i<6;i++)
{sum1+=x[i]*RT[i];}
return Math.abs(sum1-CL)-1e-8;
}
}


class f2002 extends f_xj //optimal chiller case 2 coelho
{
double RT=800.0;
double CL=960.0;

public double func(double x[]) 
{
double P[]=new double[3]; 	
double a[]={100.95,66.598,130.09};
double b[]={818.61,606.34,304.5};	
double c[]={-973.43,-380.58,14.377};
double d[]={788.55,275.95,99.8};


double sum=0.0;
for(int i=0;i<3;i++)
{P[i]=a[i]+b[i]*x[i]+c[i]*x[i]*x[i]+d[i]*x[i]*x[i]*x[i];}
for(int i=0;i<3;i++)
{sum+=P[i];}
return sum+5.0*H(x);
}

double H(double x[])
{
double sum1=0.0;	
for(int i=0;i<3;i++)
{sum1+=x[i]*RT;}
return Math.abs(sum1-CL)-1e-8;
}
}

class f2003 extends f_xj //optimal chiller case 3 genetic chang 
{
double[] RT={450.0,450,1000.0,1000.0};
double CL=1450.0;

public double func(double x[]) 
{
double P[]=new double[4]; 	
double a[]={104.09,-67.15,384.71,541.63};
double b[]={166.57,1177.79,-779.13,413.48};	
double c[]={-430.13,-2174.53,1151.42,-3626.50};
double d[]={512.53,1456.53,-63.20,4021.41};


double sum=0.0;
for(int i=0;i<4;i++)
{P[i]=a[i]+b[i]*x[i]+c[i]*x[i]*x[i]+d[i]*x[i]*x[i]*x[i];}
for(int i=0;i<4;i++)
{sum+=P[i];}
return sum+5.0*H(x);
}

double H(double x[])
{
double sum1=0.0;	
for(int i=0;i<4;i++)
{sum1+=x[i]*RT[i];}
return Math.abs(sum1-CL)-1e-8;
}
}


class dua4 extends f_xj 
{
double func(double x[])
{
 
 
   double[] t={0.0, 2.0,4.0,6.0,8.0,10.0,12.0,14.0,16.0,18.0,20.0,22.0,24.0,26.0,28.0,30.0,32.0,34.0,36.0,38.0,40.0,42.0,44.0,46.0,48.0,50.0};
 
   double[] z1real={0.0000,8.5138,15.1169, 20.3773, 24.6452, 28.1613 ,31.0893, 33.5484, 35.6286, 37.3976, 38.9090, 40.2051, 41.3200, 42.2817, 43.1129, 43.8326, 44.4569, 44.9990, 45.4704, 45.8807, 46.2380, 46.5495, 46.8212,  47.0584, 47.2654, 47.4462             };
     
   double[] z1=new double[t.length];
      z1[0]=0.0;
 
   double p1=x[0];
   double p2=x[1];
  
   
   double h=(50.0-0.0)/((double)t.length-1);
 

   for(int i=0;i<t.length-1;i++)
   {
	   z1[i+1]= (((p1*(126.2-z1[i])*(91.9-z1[i])*(91.9-z1[i]))-p2*z1[i]*z1[i])*h)+z1[i];
      
   }
   
   double sum1=0.0;double sum2=0.0;double sum3=0.0;
   for(int i=0;i<=t.length-1;i++)
   {sum1+=(z1[i]-z1real[i])*(z1[i]-z1real[i]);}
   sum1=Math.sqrt(sum1)/(double)t.length;
  
   double ret=sum1;
  


return ret;	
}	
}


class case1 extends f_xj 
{
double func(double x[])
{
  
// x[0]=0.336;	//Ds      0.3-0.6
// x[1]=19.0e-3;//dout   12.0e-3 - 25e-3
// x[2]=0.5;//tubelayout   30 45 90           0 - 1
// x[3]=0.3;// number of tube pass 1 2 4 6 8  0 - 1
// x[4]=4.3;// length of tubes;    3 - 10
// x[5]=25e-3; // pitch pt  15e-3  30e-3  
// x[6]=0.279;//central baffle spacing Lbc  0.2 - 0.5
// x[7]=0.318;//inner baffle spacing   Lbi  0.2 - 0.5 
// x[8]=0.318;//outer baffle spacing   Lbo  0.2 - 0.5
// x[9]=0.258;// baffle cut                 0.15 - 0.4
// x[10]=19.0e-3;//with of bypass lane wp   10e-3  30e-3
// x[11]=0.794e-3;//                0.1e-3   1e-3
// x[12]=2.946e-3;//                1e-3     5e-3   

double NSSS=0.0;
if((x[13]>0.0)&&(x[13]<=0.25))
{NSSS=1.0;}
else if((x[13]>0.25)&&(x[13]<=0.50))
{NSSS=2.0;}
else if((x[13]>0.50)&&(x[13]<=0.75))
{NSSS=4.0;}
else if((x[13]>0.75)&&(x[13]<=1.00))
{NSSS=8.0;}

double tubelayout=0.0;
if(x[2]<0.3333)
{tubelayout=30.0;}
else if((x[2]>0.3333)&&(x[2]<0.6666))
{tubelayout=45.0;}
else if((x[2]>0.6666))
{tubelayout=90.0;}

double np=0.0;

if(x[3]<=0.2)
{np=1.0;}
else if((x[3]>0.2) && (x[3]<=0.4))
{np=2.0;}
else if((x[3]>0.4) && (x[3]<=0.6))
{np=4.0;}
else if((x[3]>0.6) && (x[3]<=0.8))
{np=6.0;}
else if((x[3]>0.8) && (x[3]<=1.0))
{np=8.0;}
double Ds=x[0];	
double dout=x[1];	
double din=x[1]-(2.0*x[14]);
double L=x[4];
double DDDD=x[5];
double Lbc=x[6];
double Lbi=x[7];
double Lbo=x[8];
double lc=Ds*x[9];	
double Nss=NSSS; // number of sealing strip pairs
double pt=1.25*dout;
double C1=0.0;
double n11=0.0;

if((tubelayout == 30.0))
{
     if(np==1.0)	
	 {C1=0.319;n11=2.142;}
	 else if(np==2.0)	
	 {C1=0.249;n11=2.207;}
	 else if(np==4.0)	
	 {C1=0.175;n11=2.285;}
	 else if(np==6.0)	
	 {C1=0.0743;n11=2.499;}
	 else if(np==8.0)	
	 {C1=0.0365;n11=2.675;}
}
else if((tubelayout == 90.0)||(tubelayout == 45.0))
{
     if(np==1.0)	
	 {C1=0.215;n11=2.207;}
	 else if(np==2.0)	
	 {C1=0.156;n11=2.291;}
	 else if(np==4.0)	
	 {C1=0.158;n11=2.263;}
	 else if(np==6.0)	
	 {C1=0.0402;n11=2.617;}
	 else if(np==8.0)	
	 {C1=0.0331;n11=2.643;}
}	
	
double Xt=0.0;
double Xl=0.0;
double sigma=0.0;


if(tubelayout==30.0)
{
Xt=pt;
Xl=(Math.sqrt(3.0)/2.0)*pt;
sigma=(pt-dout)/pt;	
}
else if(tubelayout==45.0)
{
Xt=Math.sqrt(2)*pt;
Xl=pt/Math.sqrt(2);	
sigma=(2.0*(pt-dout))/(Math.sqrt(2)*pt);
}
else if(tubelayout==90.0)
{
Xt=pt;	
Xl=pt;
sigma=(pt-dout)/pt;
}
double wp=x[10];
double Np=2.0;
double Dotl= Ds-(15e-3);// diameter of outer tube limit
double delta_tb=x[11];//	tube to baffle hole diameter clearence
double delta_sb=x[12];// shell to baffle diameter clearence

///////////////////////////////////////////
double ro_oil=723;
double ros=ro_oil;
double cp_oil=2679.55;
double cps=cp_oil;
double mu_oil=0.00049;
double mus=mu_oil;
double k_oil=0.1;
double ks=k_oil;
double Pr_oil=mu_oil*cp_oil/k_oil;
double ro_wat= 678;
double cp_wat= 3161.03;
double mu_wat= 0.00032;
double k_wat= 0.089;
double Pr_wat=mu_wat*cp_wat/k_wat;

// Operating conditions
double m_oil=102.12;
double ms=m_oil;
double T_oilin=209;
double T_sin=T_oilin;
double Ro_oil=0.0006;
double Rs=Ro_oil;
double m_water=29.36;
double T_watin=319;
double R_wat=0.0006;
double ktube=47.0;
///////////////////////////////
double Nt=C1*Math.pow(Ds/dout,n11);	
Nt=Math.round(Nt);
double teta_b=2.0*Math.acos(1.0- (2.0*lc/Ds)); // baffle cut angle	
double teta_b_degree=180.0*teta_b/3.141592;
double Afr_w=(Ds*Ds/4.0)*((teta_b/2.0)-(1.0-(2.0*lc/Ds))*Math.sin(teta_b/2.0));
double Dctl=Dotl-dout;
double teta_ctl=2.0*Math.acos( (Ds-(2.0*lc))/Dctl  );
double Fw=(teta_ctl/(2.0*3.141592))-(Math.sin(teta_ctl)/(2.0*3.141592));  //Fraction of total tubes in the window section
double Nt_w=Fw*Nt;// number of tubes in window section
double Afr_t=0.25*3.141592*dout*dout*Fw*Nt; // the area occupied by the tubes	
double Ao_w=Afr_w-Afr_t;// net flow areain one window section
double Dh_w=(4.0*Ao_w)/ ((3.141592*dout*Nt_w)+(3.141592*Ds*(teta_b/(2.0*3.141592))));
double Nr_cw=(0.8/Xl)*(lc-0.5*(Ds-Dctl));
double Fc=1.0-2.0*Fw; // total number of tubes in crossflow section
double Nr_cc=(Ds-2.0*lc)/Xl;
double Ao_cr=0.0;
if ((tubelayout == 30.0) || (tubelayout == 90.0))
{Ao_cr=(Ds-Dotl+(Dctl/Xt)*(Xt-dout))*Lbc;}
else if (tubelayout == 45.0)
{Ao_cr=Lbc*(Ds-Dotl+ ((2.0*Dctl/Xt)*(pt-dout)));}
double Nb=((L-Lbi-Lbo)/Lbc)+1.0;
Nb=Math.round(Nb);
double Ao_bp=Lbc*(Ds-Dotl+0.5*Np*wp); // fraction of crossflow area available for flow bypass
double Fbp=Ao_bp/Ao_cr;
double Ao_tb=3.141592*dout*delta_tb*Nt*(1.0-Fw)/2.0;
double Ao_sb=3.141592*Ds*delta_sb*0.5*(1.0-(teta_b/(2.0*3.141592)));
double a1=0.0;double a2=0.0;double a3=0.0;double a4=0.0;
double b1=0.0;double b2=0.0;double b3=0.0;double b4=0.0;
double C=pt-dout;
double As=Ds*C*Lbc/pt;
double Res=dout*ms/(mu_oil*As);
if(tubelayout == 30.0)
{
  if(Res<10)
  {a1=1.400;a2=-0.667;a3=0.0;a4=0.0;b1=48.0;b2=-1.0;b3=0.0;b4=0.0;}	
  else if ((Res>=10) && (Res<100)) 	
  {a1=1.360;a2=-0.657;a3=0.0;a4=0.0;b1=45.1;b2=-0.973;b3=0.0;b4=0.0;}	
  else if ((Res>=100) && (Res<1000)) 	
  {a1=0.593;a2=-0.477;a3=0.0;a4=0.0;b1=4.57;b2=-0.476;b3=0.0;b4=0.0;}	
  else if((Res>=1000) && (Res<10000)) 	
  {a1=0.321;a2=-0.388;a3=0.0;a4=0.0;b1=0.486;b2=-0.152;b3=0.0;b4=0.0;}	
  else if((Res>=10000) && (Res<1000000)) 	
  {a1=0.321;a2=-0.388;a3=1.450;a4=0.519;b1=0.372;b2=-0.123;b3=7.0;b4=0.5;}	

}
else if(tubelayout == 45.0)
{
  if(Res<10)
  {a1=1.550;a2=-0.667;a3=0.0;a4=0.0;b1=32.0;b2=-1.0;b3=0.0;b4=0.0;}	
  else if((Res>=10) && (Res<100)) 	
  {a1=0.498;a2=-0.656;a3=0.0;a4=0.0;b1=26.2;b2=-0.913;b3=0.0;b4=0.0;}	
  else if((Res>=100) && (Res<1000)) 	
  {a1=0.730;a2=-0.500;a3=0.0;a4=0.0;b1=3.50;b2=-0.476;b3=0.0;b4=0.0;}	
  else if((Res>=1000) && (Res<10000)) 	
  {a1=0.370;a2=-0.396;a3=0.0;a4=0.0;b1=0.333;b2=-0.136;b3=0.0;b4=0.0;}	
  else if((Res>=10000) && (Res<1000000)) 	
  {a1=0.321;a2=-0.396;a3=1.930;a4=0.500;b1=0.303;b2=-0.126;b3=6.59;b4=0.520;}	
}
else if(tubelayout == 90.0)
{
  if(Res<10)
  {a1=0.970;a2=-0.667;a3=0.0;a4=0.0;b1=35.0;b2=-1.0;b3=0.0;b4=0.0;}	
  else if((Res>=10) && (Res<100)) 	
  {a1=0.900;a2=-0.631;a3=0.0;a4=0.0;b1=32.1;b2=-0.963;b3=0.0;b4=0.0;}	
  else if((Res>=100) && (Res<1000)) 	
  {a1=0.408;a2=-0.460;a3=0.0;a4=0.0;b1=6.09;b2=-0.602;b3=0.0;b4=0.0;}	
  else if((Res>=1000) && (Res<10000)) 	
  {a1=0.107;a2=-0.266;a3=0.0;a4=0.0;b1=0.0815;b2=0.022;b3=0.0;b4=0.0;}	
  else if((Res>=10000) && (Res<1000000)) 	
  {a1=0.370;a2=-0.395;a3=1.187;a4=0.370;b1=0.391;b2=-0.148;b3=6.30;b4=0.378;}	
}
double aa=a3/(1.0+0.14*Math.pow(Res,a4));
double ji=a1*Math.pow((1.33/(pt/dout)),aa)*Math.pow(Res,a2); 
double bb=b3/(1.0+0.14*Math.pow(Res,b4));
double fi=b1*Math.pow((1.33/(pt/dout)),bb)*Math.pow(Res,b2);
double hid=ji*cps*(ms/As)*Math.pow((ks/(cps*mus)),0.6666);
double Jc=0.55+0.72*Fc;
double rs=Ao_sb/(Ao_sb+Ao_tb);
double rlm=(Ao_sb+Ao_tb)/Ao_cr;
double Jl=(0.44*(1-rs))+(1.0-(0.44*(1.0-rs)))*Math.exp(-2.2*rlm);
double CC=0.0;
if(Res<=100)
{CC=1.35;}
else  
{CC=1.25;}
double Nsdot=Nss/Nr_cc;
double rb=Ao_bp/Ao_cr;
double Jb=0.0;
if(Nsdot>=0.5)
{Jb=1.0;}
else
{Jb=Math.exp(-CC*rb*(1.0-Math.pow(2.0*Nsdot,1.0/3.0)));}
double Lidot=Lbi/Lbc;
double Lodot=Lbo/Lbc;
double nn=0.0;
if(Res>200)
{nn=0.6;}
else
{nn=1.0/3.0;}
double Js=(Nb-1.0+Math.pow(Lidot,1.0-nn)+Math.pow(Lodot,1.0-nn))/(Nb-1.0+Lidot+Lodot);
double Nrc=Nr_cc+Nr_cw;
double Jr=0.0;
double tan=(100.0-20.0)/(1.0 -Math.pow(10.0/Nrc,0.18));
if(Res>100)
{Jr=1.0;}
else if((Res<100) && (Res>20))
{Jr=Res/tan;}
else if(Res<20)
{Jr=Math.pow(10.0/Nrc,0.18);}
double hs=hid*Jc*Jl*Jb*Js*Jr;

double Gs=ms/Ao_cr;
double dp_bid=4.0*fi*Gs*Gs*Nr_cc/(2.0*ros);
double eta_b=0.0;
double DD=0.0;
if(Res<=100)
{DD=4.5;}
else
{DD=3.7;}
if(Nsdot<0.5)
{eta_b=Math.exp(-DD*rb*(1.0-Math.pow(2.0*Nsdot,(1.0/3.0))));}
else
{eta_b=1.0;}
double pp=(-0.15*(1.0+rs)+0.8);
double eta_l=Math.exp(-1.33*(1.0+rs)*Math.pow(rlm,pp));
double ndot=0.0;
if(Res>200)
{ndot=0.2;}
else if (Res<200)
{ndot=1.0;}
double eta_s=Math.pow((Lbc/Lbo),(2.0-ndot))+Math.pow((Lbc/Lbi),(2.0-ndot));
double dp_cr=dp_bid*(Nb-1)*eta_b*eta_l;
double Gw=ms/Math.sqrt(Ao_cr*Ao_w);
double dp_w=Nb*(2.0+0.6*Nr_cw)*eta_l*Gw*Gw/(2.0*ros);
double dp_io=2.0*dp_bid*(1.0+(Nr_cw/Nr_cc))*eta_b*eta_s;
double dps=dp_cr+dp_w+dp_io;
double Nt_p=Nt/np;
double Ao_t=3.141592*0.25*din*din*Nt_p;
double Ret=m_water*din/(Ao_t*mu_wat);
double Nut=0.024*Math.pow(Ret,0.8)*Math.pow(Pr_wat,0.4);
double hi=Nut*k_wat/din;
double Uo=1.0/((1/hs)+Rs+ (dout*Math.log(dout/din)/(2.0*ktube))+(R_wat*(dout/din))+((1.0/hi)*(dout/din)));

double Ain=3.141592*din*din*0.25;
double Atp=Nt*Ain/np;
double Gtp=m_water/Atp;
double Rett=Gtp*din/mu_wat;
double fin=Math.pow((1.58*Math.log(Rett)-3.28),-2.0);
double vm=Gtp/ro_wat;
double dpt=((4.0*fin*L*np/din)+2.5*np)*ro_wat*vm*vm/2;
double Ao=Nt*3.141592*L*dout;

double pomp_eff=0.65;
double CCCi=8000+259.2*Math.pow(Ao,0.91);
double mc_wat=m_water*cp_wat;
double mc_oil=m_oil*cp_oil;
double mc_max=Math.max(mc_wat,mc_oil);
double mc_min=Math.min(mc_wat,mc_oil);
double Cdot=mc_min/mc_max;
double NTU=Uo*Ao/mc_min;
double gamma=NTU*Math.sqrt(1.0+Cdot*Cdot);
double coth2=(1.0+Math.exp(-gamma))/(1.0-Math.exp(-gamma));
double epps=2.0/((1.0+Cdot)+Math.sqrt(1.0+Cdot*Cdot)*coth2);
///////////////////////////////////////////
double Tit=319.0;
double Tot=269.0;
double Tis=209.0;
double Tos=225.95;
double RR1=(Tis-Tos)/(Tot-Tit);
double PP1=(Tot-Tit)/(Tis-Tit);
double FF=(Math.sqrt(RR1*RR1+1.0)/(RR1-1.0))*Math.log((1.0-PP1)/(1.0-PP1*RR1))/Math.log((2.0-(PP1*(RR1+1.0-Math.sqrt(RR1*RR1+1.0))))/(2.0-(PP1*(RR1+1.0+Math.sqrt(RR1*RR1+1.0))))); 
double dTlm=((Tis-Tot)-(Tos-Tit))/Math.log((Tis-Tot)/(Tos-Tit)); 
//////////////////////////////////////////
double Qload=epps*mc_min*(T_watin-T_oilin);
double PP=(((m_water*dpt/ro_wat)+(ms*dps/ros))*(1.0/pomp_eff));
double Ce=0.12e-3;
double H=7000.0;
double Co=PP*Ce*H;
double Cod=0.0;
for(int i=1;i<=10;i++)
{Cod+=Co/(Math.pow(1.0+0.1,(double)i));}
double Ctot=CCCi+Cod;

return Ctot+100000.0*(G1(x)+G2(x)+G3(x))  ;	
}	


double G1(double x[])
{
	
double NSSS=0.0;
if((x[13]>0.0)&&(x[13]<=0.25))
{NSSS=1.0;}
else if((x[13]>0.25)&&(x[13]<=0.50))
{NSSS=2.0;}
else if((x[13]>0.50)&&(x[13]<=0.75))
{NSSS=4.0;}
else if((x[13]>0.75)&&(x[13]<=1.00))
{NSSS=8.0;}

	
double tubelayout=0.0;
if(x[2]<0.3333)
{tubelayout=30.0;}
else if((x[2]>0.3333)&&(x[2]<0.6666))
{tubelayout=45.0;}
else if((x[2]>0.6666))
{tubelayout=90.0;}

double np=0.0;

if(x[3]<=0.2)
{np=1.0;}
else if((x[3]>0.2) && (x[3]<=0.4))
{np=2.0;}
else if((x[3]>0.4) && (x[3]<=0.6))
{np=4.0;}
else if((x[3]>0.6) && (x[3]<=0.8))
{np=6.0;}
else if((x[3]>0.8) && (x[3]<=1.0))
{np=8.0;}
double Ds=x[0];	
double dout=x[1];	
double din=x[1]-(2.0*x[14]);
double L=x[4];
double pt=x[5];
double Lbc=x[6];
double Lbi=x[7];
double Lbo=x[8];
double lc=Ds*x[9];	
double Nss=NSSS; // number of sealing strip pairs	
	
double C1=0.0;
double n11=0.0;

if((tubelayout == 30.0))
{
     if(np==1.0)	
	 {C1=0.319;n11=2.142;}
	 else if(np==2.0)	
	 {C1=0.249;n11=2.207;}
	 else if(np==4.0)	
	 {C1=0.175;n11=2.285;}
	 else if(np==6.0)	
	 {C1=0.0743;n11=2.499;}
	 else if(np==8.0)	
	 {C1=0.0365;n11=2.675;}
}
else if((tubelayout == 90.0)||(tubelayout == 45.0))
{
     if(np==1.0)	
	 {C1=0.215;n11=2.207;}
	 else if(np==2.0)	
	 {C1=0.156;n11=2.291;}
	 else if(np==4.0)	
	 {C1=0.158;n11=2.263;}
	 else if(np==6.0)	
	 {C1=0.0402;n11=2.617;}
	 else if(np==8.0)	
	 {C1=0.0331;n11=2.643;}
}
double Nt=C1*Math.pow(Ds/dout,n11);
Nt=Math.round(Nt);	
double Ao=Nt*3.141592*L*dout;

		
return Math.pow( Math.max(0.0,   Ao-200.0),(2.0));	
}



double G3(double x[])
{
	
double Ds=x[0];	
double dout=x[1];	
double din=x[1]-(2.0*x[14]);
double L=x[4];
double DDDD=x[5];
double Lbc=x[6];
double Lbi=x[7];
double Lbo=x[8];
double lc=Ds*x[9];	
 
double pt=1.25*dout;	
 

		
return Math.abs(pt-1.25*dout);	
}




double G2(double x[])
{
double NSSS=0.0;
if((x[13]>0.0)&&(x[13]<=0.25))
{NSSS=1.0;}
else if((x[13]>0.25)&&(x[13]<=0.50))
{NSSS=2.0;}
else if((x[13]>0.50)&&(x[13]<=0.75))
{NSSS=4.0;}
else if((x[13]>0.75)&&(x[13]<=1.00))
{NSSS=8.0;}

double tubelayout=0.0;
if(x[2]<0.3333)
{tubelayout=30.0;}
else if((x[2]>0.3333)&&(x[2]<0.6666))
{tubelayout=45.0;}
else if((x[2]>0.6666))
{tubelayout=90.0;}

double np=0.0;

if(x[3]<=0.2)
{np=1.0;}
else if((x[3]>0.2) && (x[3]<=0.4))
{np=2.0;}
else if((x[3]>0.4) && (x[3]<=0.6))
{np=4.0;}
else if((x[3]>0.6) && (x[3]<=0.8))
{np=6.0;}
else if((x[3]>0.8) && (x[3]<=1.0))
{np=8.0;}
double Ds=x[0];	
double dout=x[1];	
double din=x[1]-(2.0*x[14]);
double L=x[4];
double DDDD=x[5];
double Lbc=x[6];
double Lbi=x[7];
double Lbo=x[8];
double lc=Ds*x[9];	
double Nss=NSSS; // number of sealing strip pairs
double pt=1.25*dout;
double C1=0.0;
double n11=0.0;

if((tubelayout == 30.0))
{
     if(np==1.0)	
	 {C1=0.319;n11=2.142;}
	 else if(np==2.0)	
	 {C1=0.249;n11=2.207;}
	 else if(np==4.0)	
	 {C1=0.175;n11=2.285;}
	 else if(np==6.0)	
	 {C1=0.0743;n11=2.499;}
	 else if(np==8.0)	
	 {C1=0.0365;n11=2.675;}
}
else if((tubelayout == 90.0)||(tubelayout == 45.0))
{
     if(np==1.0)	
	 {C1=0.215;n11=2.207;}
	 else if(np==2.0)	
	 {C1=0.156;n11=2.291;}
	 else if(np==4.0)	
	 {C1=0.158;n11=2.263;}
	 else if(np==6.0)	
	 {C1=0.0402;n11=2.617;}
	 else if(np==8.0)	
	 {C1=0.0331;n11=2.643;}
}	
	
double Xt=0.0;
double Xl=0.0;
double sigma=0.0;


if(tubelayout==30.0)
{
Xt=pt;
Xl=(Math.sqrt(3.0)/2.0)*pt;
sigma=(pt-dout)/pt;	
}
else if(tubelayout==45.0)
{
Xt=Math.sqrt(2)*pt;
Xl=pt/Math.sqrt(2);	
sigma=(2.0*(pt-dout))/(Math.sqrt(2)*pt);
}
else if(tubelayout==90.0)
{
Xt=pt;	
Xl=pt;
sigma=(pt-dout)/pt;
}
double wp=x[10];
double Np=2.0;
double Dotl= Ds-(15e-3);// diameter of outer tube limit
double delta_tb=x[11];//	tube to baffle hole diameter clearence
double delta_sb=x[12];// shell to baffle diameter clearence

///////////////////////////////////////////
double ro_oil=723;
double ros=ro_oil;
double cp_oil=2679.55;
double cps=cp_oil;
double mu_oil=0.00049;
double mus=mu_oil;
double k_oil=0.1;
double ks=k_oil;
double Pr_oil=mu_oil*cp_oil/k_oil;
double ro_wat= 678;
double cp_wat= 3161.03;
double mu_wat= 0.00032;
double k_wat= 0.089;
double Pr_wat=mu_wat*cp_wat/k_wat;

// Operating conditions
double m_oil=102.12;
double ms=m_oil;
double T_oilin=209;
double T_sin=T_oilin;
double Ro_oil=0.0006;
double Rs=Ro_oil;
double m_water=29.36;
double T_watin=319;
double R_wat=0.0006;
double ktube=47.0;
///////////////////////////////
double Nt=C1*Math.pow(Ds/dout,n11);	
Nt=Math.round(Nt);
double teta_b=2.0*Math.acos(1.0- (2.0*lc/Ds)); // baffle cut angle	
double teta_b_degree=180.0*teta_b/3.141592;
double Afr_w=(Ds*Ds/4.0)*((teta_b/2.0)-(1.0-(2.0*lc/Ds))*Math.sin(teta_b/2.0));
double Dctl=Dotl-dout;
double teta_ctl=2.0*Math.acos( (Ds-(2.0*lc))/Dctl  );
double Fw=(teta_ctl/(2.0*3.141592))-(Math.sin(teta_ctl)/(2.0*3.141592));  //Fraction of total tubes in the window section
double Nt_w=Fw*Nt;// number of tubes in window section
double Afr_t=0.25*3.141592*dout*dout*Fw*Nt; // the area occupied by the tubes	
double Ao_w=Afr_w-Afr_t;// net flow areain one window section
double Dh_w=(4.0*Ao_w)/ ((3.141592*dout*Nt_w)+(3.141592*Ds*(teta_b/(2.0*3.141592))));
double Nr_cw=(0.8/Xl)*(lc-0.5*(Ds-Dctl));
double Fc=1.0-2.0*Fw; // total number of tubes in crossflow section
double Nr_cc=(Ds-2.0*lc)/Xl;
double Ao_cr=0.0;
if ((tubelayout == 30.0) || (tubelayout == 90.0))
{Ao_cr=(Ds-Dotl+(Dctl/Xt)*(Xt-dout))*Lbc;}
else if (tubelayout == 45.0)
{Ao_cr=Lbc*(Ds-Dotl+ ((2.0*Dctl/Xt)*(pt-dout)));}
double Nb=((L-Lbi-Lbo)/Lbc)+1.0;
Nb=Math.round(Nb);
double Ao_bp=Lbc*(Ds-Dotl+0.5*Np*wp); // fraction of crossflow area available for flow bypass
double Fbp=Ao_bp/Ao_cr;
double Ao_tb=3.141592*dout*delta_tb*Nt*(1.0-Fw)/2.0;
double Ao_sb=3.141592*Ds*delta_sb*0.5*(1.0-(teta_b/(2.0*3.141592)));
double a1=0.0;double a2=0.0;double a3=0.0;double a4=0.0;
double b1=0.0;double b2=0.0;double b3=0.0;double b4=0.0;
double C=pt-dout;
double As=Ds*C*Lbc/pt;
double Res=dout*ms/(mu_oil*As);
if(tubelayout == 30.0)
{
  if(Res<10)
  {a1=1.400;a2=-0.667;a3=0.0;a4=0.0;b1=48.0;b2=-1.0;b3=0.0;b4=0.0;}	
  else if ((Res>=10) && (Res<100)) 	
  {a1=1.360;a2=-0.657;a3=0.0;a4=0.0;b1=45.1;b2=-0.973;b3=0.0;b4=0.0;}	
  else if ((Res>=100) && (Res<1000)) 	
  {a1=0.593;a2=-0.477;a3=0.0;a4=0.0;b1=4.57;b2=-0.476;b3=0.0;b4=0.0;}	
  else if((Res>=1000) && (Res<10000)) 	
  {a1=0.321;a2=-0.388;a3=0.0;a4=0.0;b1=0.486;b2=-0.152;b3=0.0;b4=0.0;}	
  else if((Res>=10000) && (Res<1000000)) 	
  {a1=0.321;a2=-0.388;a3=1.450;a4=0.519;b1=0.372;b2=-0.123;b3=7.0;b4=0.5;}	

}
else if(tubelayout == 45.0)
{
  if(Res<10)
  {a1=1.550;a2=-0.667;a3=0.0;a4=0.0;b1=32.0;b2=-1.0;b3=0.0;b4=0.0;}	
  else if((Res>=10) && (Res<100)) 	
  {a1=0.498;a2=-0.656;a3=0.0;a4=0.0;b1=26.2;b2=-0.913;b3=0.0;b4=0.0;}	
  else if((Res>=100) && (Res<1000)) 	
  {a1=0.730;a2=-0.500;a3=0.0;a4=0.0;b1=3.50;b2=-0.476;b3=0.0;b4=0.0;}	
  else if((Res>=1000) && (Res<10000)) 	
  {a1=0.370;a2=-0.396;a3=0.0;a4=0.0;b1=0.333;b2=-0.136;b3=0.0;b4=0.0;}	
  else if((Res>=10000) && (Res<1000000)) 	
  {a1=0.321;a2=-0.396;a3=1.930;a4=0.500;b1=0.303;b2=-0.126;b3=6.59;b4=0.520;}	
}
else if(tubelayout == 90.0)
{
  if(Res<10)
  {a1=0.970;a2=-0.667;a3=0.0;a4=0.0;b1=35.0;b2=-1.0;b3=0.0;b4=0.0;}	
  else if((Res>=10) && (Res<100)) 	
  {a1=0.900;a2=-0.631;a3=0.0;a4=0.0;b1=32.1;b2=-0.963;b3=0.0;b4=0.0;}	
  else if((Res>=100) && (Res<1000)) 	
  {a1=0.408;a2=-0.460;a3=0.0;a4=0.0;b1=6.09;b2=-0.602;b3=0.0;b4=0.0;}	
  else if((Res>=1000) && (Res<10000)) 	
  {a1=0.107;a2=-0.266;a3=0.0;a4=0.0;b1=0.0815;b2=0.022;b3=0.0;b4=0.0;}	
  else if((Res>=10000) && (Res<1000000)) 	
  {a1=0.370;a2=-0.395;a3=1.187;a4=0.370;b1=0.391;b2=-0.148;b3=6.30;b4=0.378;}	
}
double aa=a3/(1.0+0.14*Math.pow(Res,a4));
double ji=a1*Math.pow((1.33/(pt/dout)),aa)*Math.pow(Res,a2); 
double bb=b3/(1.0+0.14*Math.pow(Res,b4));
double fi=b1*Math.pow((1.33/(pt/dout)),bb)*Math.pow(Res,b2);
double hid=ji*cps*(ms/As)*Math.pow((ks/(cps*mus)),0.6666);
double Jc=0.55+0.72*Fc;
double rs=Ao_sb/(Ao_sb+Ao_tb);
double rlm=(Ao_sb+Ao_tb)/Ao_cr;
double Jl=(0.44*(1-rs))+(1.0-(0.44*(1.0-rs)))*Math.exp(-2.2*rlm);
double CC=0.0;
if(Res<=100)
{CC=1.35;}
else  
{CC=1.25;}
double Nsdot=Nss/Nr_cc;
double rb=Ao_bp/Ao_cr;
double Jb=0.0;
if(Nsdot>=0.5)
{Jb=1.0;}
else
{Jb=Math.exp(-CC*rb*(1.0-Math.pow(2.0*Nsdot,1.0/3.0)));}
double Lidot=Lbi/Lbc;
double Lodot=Lbo/Lbc;
double nn=0.0;
if(Res>200)
{nn=0.6;}
else
{nn=1.0/3.0;}
double Js=(Nb-1.0+Math.pow(Lidot,1.0-nn)+Math.pow(Lodot,1.0-nn))/(Nb-1.0+Lidot+Lodot);
double Nrc=Nr_cc+Nr_cw;
double Jr=0.0;
double tan=(100.0-20.0)/(1.0 -Math.pow(10.0/Nrc,0.18));
if(Res>100)
{Jr=1.0;}
else if((Res<100) && (Res>20))
{Jr=Res/tan;}
else if(Res<20)
{Jr=Math.pow(10.0/Nrc,0.18);}
double hs=hid*Jc*Jl*Jb*Js*Jr;

double Gs=ms/Ao_cr;
double dp_bid=4.0*fi*Gs*Gs*Nr_cc/(2.0*ros);
double eta_b=0.0;
double DD=0.0;
if(Res<=100)
{DD=4.5;}
else
{DD=3.7;}
if(Nsdot<0.5)
{eta_b=Math.exp(-DD*rb*(1.0-Math.pow(2.0*Nsdot,(1.0/3.0))));}
else
{eta_b=1.0;}
double pp=(-0.15*(1.0+rs)+0.8);
double eta_l=Math.exp(-1.33*(1.0+rs)*Math.pow(rlm,pp));
double ndot=0.0;
if(Res>200)
{ndot=0.2;}
else if (Res<200)
{ndot=1.0;}
double eta_s=Math.pow((Lbc/Lbo),(2.0-ndot))+Math.pow((Lbc/Lbi),(2.0-ndot));
double dp_cr=dp_bid*(Nb-1)*eta_b*eta_l;
double Gw=ms/Math.sqrt(Ao_cr*Ao_w);
double dp_w=Nb*(2.0+0.6*Nr_cw)*eta_l*Gw*Gw/(2.0*ros);
double dp_io=2.0*dp_bid*(1.0+(Nr_cw/Nr_cc))*eta_b*eta_s;
double dps=dp_cr+dp_w+dp_io;
double Nt_p=Nt/np;
double Ao_t=3.141592*0.25*din*din*Nt_p;
double Ret=m_water*din/(Ao_t*mu_wat);
double Nut=0.024*Math.pow(Ret,0.8)*Math.pow(Pr_wat,0.4);
double hi=Nut*k_wat/din;
double Uo=1.0/((1/hs)+Rs+ (dout*Math.log(dout/din)/(2.0*ktube))+(R_wat*(dout/din))+((1.0/hi)*(dout/din)));

double Ain=3.141592*din*din*0.25;
double Atp=Nt*Ain/np;
double Gtp=m_water/Atp;
double Rett=Gtp*din/mu_wat;
double fin=Math.pow((1.58*Math.log(Rett)-3.28),-2.0);
double vm=Gtp/ro_wat;
double dpt=((4.0*fin*L*np/din)+2.5*np)*ro_wat*vm*vm/2;
double Ao=Nt*3.141592*L*dout;

double pomp_eff=0.65;
double CCCi=8000+259.2*Math.pow(Ao,0.91);
double mc_wat=m_water*cp_wat;
double mc_oil=m_oil*cp_oil;
double mc_max=Math.max(mc_wat,mc_oil);
double mc_min=Math.min(mc_wat,mc_oil);
double Cdot=mc_min/mc_max;
double NTU=Uo*Ao/mc_min;
double gamma=NTU*Math.sqrt(1.0+Cdot*Cdot);
double coth2=(1.0+Math.exp(-gamma))/(1.0-Math.exp(-gamma));
double epps=2.0/((1.0+Cdot)+Math.sqrt(1.0+Cdot*Cdot)*coth2);

///////////////////////////////////////////
double Tit=319.0;
double Tot=269.0;
double Tis=209.0;
double Tos=225.95;
double RR1=(Tis-Tos)/(Tot-Tit);
double PP1=(Tot-Tit)/(Tis-Tit);
double FF=(Math.sqrt(RR1*RR1+1.0)/(RR1-1.0))*Math.log((1.0-PP1)/(1.0-PP1*RR1))/Math.log((2.0-(PP1*(RR1+1.0-Math.sqrt(RR1*RR1+1.0))))/(2.0-(PP1*(RR1+1.0+Math.sqrt(RR1*RR1+1.0))))); 
double dTlm=((Tis-Tot)-(Tos-Tit))/Math.log((Tis-Tot)/(Tos-Tit)); 
//////////////////////////////////////////

double Qload=epps*mc_min*(T_watin-T_oilin);
	
 
return Math.abs(4.64039204e6-Qload);	
}


 

}



class dam2 extends f_xj
{
  
 

double func(double x[])
{
// x[0]  x[1]  x[2]  x[3]  x[4]  x[5]  x[6]  x[7]  x[8]  x[9]  x[10]  x[11]  x[12]  x[13]  x[14]  x[15]  x[16]  x[17]  x[18]  x[19]  x[20]  x[21]   x[22]   x[23] 
// R1    R2    R3    R4    R5    R6    R7    R8    R9    R10   R11    R12    S1     S2     S3     S4     S5     S6     S7     S8     S9     S10     S11     S12

double[] irrigation_demand={0.0, 0.0, 3.28,  5.36,  3.0, 3.22, 3.22, 1.24, 8.06,  7.65,  7.65,  4.71 };
//                          Jan, Feb, March, Aprl,  May, June, July, Agst, Sptmr, Octbr, Nvmbr, Dcmbr
double[] deficit=new double[12];

for(int i=0;i<12;i++)
{deficit[i]=irrigation_demand[i]-x[i];}
 
double sum=0.0;
for(int i=0;i<12;i++)
{sum+= deficit[i]*deficit[i];}
return sum+(50*(H1(x)+H2(x)+H3(x)+H4(x)+H5(x)+H6(x)+H7(x)+H8(x)+H9(x)+H10(x)+H11(x)+H12(x)))    ;
}	

double H1(double x[])
{
	
double St1=0.0;
double St2=0.0;
 
St1=x[12];
St2=x[13];

double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78}; 
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};  
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47}; 

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 
 

double 	A0=0.002*St1*St1+0.3446*St1+2.6533;//m^2	
double Evap0=A0*evap_rate[0];
double hh1=St2-(St1+I_2011[0]-x[0]-Evap0);
return Math.abs(hh1);
}

double H2(double x[])
{
 
 
double St2=x[13];
double St3=x[14];
 
	
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47};  

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 

double 	A1=0.002*St2*St2+0.3446*St2+2.6533;//m^2		
double Evap1=A1*evap_rate[1];
double hh2=St3-(St2+I_2011[1]-x[1]-Evap1);
return Math.abs(hh2);
}
	
double H3(double x[])
{
double St3=x[14];
double St4=x[15];	
	
 
	
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78}; 
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47}; 

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 

double 	A2=0.002*St3*St3+0.3446*St3+2.6533;//m^2		
double Evap2=A2*evap_rate[2];
double hh3=St4-(St3+I_2011[2]-x[2]-Evap2 );
return Math.abs(hh3);
}	

double H4(double x[])
{

double St4=x[15];
double St5=x[16];		

 	
	
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47}; 

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 
 
double 	A3=0.002*St4*St4+0.3446*St4+2.6533;//m^2	
double Evap3=A3*evap_rate[3];
double hh4=St5-(St4+I_2011[3]-x[3]-Evap3);
return Math.abs(hh4);
}	

double H5(double x[])
{
	
double St5=x[16];
double St6=x[17];	
	
 
	
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47};  

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 
 
double 	A4=0.002*St5*St5+0.3446*St5+2.6533;//m^2	
double Evap4=A4*evap_rate[4];
double hh5=St6-(St5+I_2011[4]-x[4]-Evap4 );
return Math.abs(hh5);
}


double H6(double x[])
{

double St6=x[17];
double St7=x[18];		
 	
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47}; 
  
double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 


double 	A5=0.002*St6*St6+0.3446*St6+2.6533;//m^2	
double Evap5=A5*evap_rate[5];
double hh6=St7-(St6+I_2011[5]-x[5]-Evap5 );
return Math.abs(hh6);
}


double H7(double x[])
{
	
double St7=x[18];
double St8=x[19];		
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47}; 

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 
 
double 	A6=0.002*St7*St7+0.3446*St7+2.6533;//m^2	
double Evap6=A6*evap_rate[6];
double hh7=St8-(St7+I_2011[6]-x[6]-Evap6 );
return Math.abs(hh7);
}



double H8(double x[])
{
double St8=x[19];
double St9=x[20];		
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47}; 

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 
 
double 	A7=0.002*St8*St8+0.3446*St8+2.6533;//m^2	
double Evap7=A7*evap_rate[7];
double hh8=St9-(St8+I_2011[7]-x[7]-Evap7 );
return Math.abs(hh8);
}


double H9(double x[])
{
	
double St9=x[20];
double St10=x[21];			
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47}; 

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 

double 	A8=0.002*St9*St9+0.3446*St9+2.6533;//m^2	
double Evap8=A8*evap_rate[8];
double hh9=St10-(St9+I_2011[8]-x[8]-Evap8 );
return Math.abs(hh9);
}


double H10(double x[])
{
	
double St10=x[21];
double St11=x[22];		
	
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47}; 

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 
 
double 	A9=0.002*St10*St10+0.3446*St10+2.6533;//m^2	
double Evap9=A9*evap_rate[9];
double hh10=St11-(St10+I_2011[9]-x[9]-Evap9);
return Math.abs(hh10);
}


double H11(double x[])
{

double St11=x[22];
double St12=x[23];	
		
	
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47};  

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 
 
double 	A10=0.002*St11*St11+0.3446*St11+2.6533;//m^2		
double Evap10=A10*evap_rate[10] ;
double hh11=St12-(St11+I_2011[10]-x[10]-Evap10);
return Math.abs(hh11);
}

	
double H12(double x[])
{
	
double St12=x[23];
double St1=x[12];
 
double evap_rate[]={0.115, 0.1223, 0.1336, 0.1161, 0.1083, 0.0938, 0.0963, 0.0972, 0.0928, 0.0862, 0.0814, 0.0846 }; // m/month
double[]   I_med={ 4.17, 2.03, 3.79, 3.43, 3.37, 3.26,  5.71, 6.31,5.6, 14.87, 18.17, 12.78};
double[]  I_high={ 9.73, 3.83, 7.18, 9.71, 8.37, 7.16, 11.75, 14.80,14.66, 28.76, 40.77, 26.39};   
double[]   I_medhigh = { 7.09, 2.96, 5.66, 6.80, 5.92, 5.34, 10.74, 10.93, 9.97, 23.64, 32.36,  22.47}; 

double[]   I_2000={ 5.04,  2.32,  3.10,  3.26,  3.46,  3.08,  1.29,  4.58,  3.59,  6.53, 23.41,16.06}; 
double[]   I_2001={ 5.52,  1.51,  1.78,  3.50,  3.06,  1.72,  0.76,  6.58,  1.06,  3.17,  3.99, 1.80};
double[]   I_2002={ 0.16,  0.07,  0.53,  2.37,  0.98,  0.06,  1.58,  2.12,  3.65,  3.47,  5.98, 4.86}; 
double[]   I_2003={ 0.52,  0.03,  4.71,  0.75,  1.93,  0.75,  1.67,  5.99,  6.94, 11.45,  8.24, 8.92}; 
double[]   I_2004={ 2.78,  1.60,  0.89,  2.19,  2.98,  2.46,  0.99,  1.27,  2.56,  1.37,  0.42, 0.30}; 
double[]   I_2005={ 0.09,  0.44,  4.97,  8.05,  0.92,  1.66,  1.05,  2.13,  1.41,  4.71,  6.62,10.60}; 
double[]   I_2006={ 0.27,  2.71,  6.35,  3.27,  4.94,  8.00, 11.74,  4.44,  7.83, 20.91,  9.03, 3.72}; 
double[]   I_2007={ 3.82,  0.81,  3.20,  3.54,  3.09,  5.01, 11.75,  8.57, 10.21, 19.99, 28.85,18.18}; 
double[]   I_2008={ 4.98,  2.58,  5.00,  4.38,  3.27,  4.37,  1.10,  2.79,  3.48, 17.78, 22.78,26.93}; 
double[]   I_2009={ 5.88,  2.26,  8.00, 10.31,  7.24,  3.48,  9.74, 16.30, 22.43, 24.44, 62.49,15.27}; 
double[]   I_2010={11.16,  3.04,  2.99,  4.49,  1.40,  2.85,  4.60,  2.46,  5.03, 11.39, 55.38,21.83}; 
double[]   I_2011={13.66,  3.25, 12.10, 12.37,  9.62,  3.79,  3.82,  7.27, 41.77, 24.95,  8.84, 2.23}; 
 
double 	A11=0.002*St12*St12+0.3446*St12+2.6533;//m^2		
double Evap11=A11*evap_rate[11];
double hh12=St1-(St12+I_2011[11]-x[11]-Evap11);
return Math.abs(hh12);
}


 



 


}



class dam1 extends f_xj
{
  
double seep_losses=0.08;//10^9 per month
double absorp_losses=0.25;//10^9 per month	

double func(double x[])
{
// x[0]  x[1]  x[2]  x[3]  x[4]  x[5]  x[6]  x[7]  x[8]  x[9]  x[10]  x[11]  x[12]  x[13]  x[14]  x[15]  x[16]  x[17]  x[18]  x[19]  x[20]  x[21]   x[22]   x[23] 
// R1    R2    R3    R4    R5    R6    R7    R8    R9    R10   R11    R12    S1     S2     S3     S4     S5     S6     S7     S8     S9     S10     S11     S12

double[] irrigation_demand={3.54*1.4,3.585*1.4,4.025*1.4,3.885*1.4,4.825*1.4,6.27*1.4,6.685*1.4,5.88*1.4,3.785*1.4,3.48*1.4,3.615*1.4,3.405*1.4};

double[] deficit=new double[12];

for(int i=0;i<12;i++)
{
   
    deficit[i]=irrigation_demand[i]-x[i]; 
 	
	
}
double sum=0.0;
for(int i=0;i<12;i++)
{sum+= deficit[i]*deficit[i];}
return sum+(1.5*(H1(x)+H2(x)+H3(x)+H4(x)+H5(x)+H6(x)+H7(x)+H8(x)+H9(x)+H10(x)+H11(x)+H12(x)))+(5.0*(G11(x)+G12(x)+G21(x)+G22(x)+G31(x)+G32(x)+G41(x)+G42(x)+G51(x)+G52(x)+G61(x)+G62(x)+G71(x)+G72(x)+G81(x)+G82(x)+G91(x)+G92(x)+G101(x)+G102(x)+G111(x)+G112(x)+G121(x)+G122(x)));
}	

double H1(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8}; 
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 
 
         
          
 
double 	H=79.97+0.0369*x[12]+18.87*Math.log(x[12]);//m
double 	A0=(-3164.28+25.49*x[12]+1092.92*Math.log(x[12]))*1000000.0;//m^2	
double Evap0=A0*evap_rate[0]/1e9;
double hh1=x[13]-(x[12]+I_1969_70[0]-x[0]-Evap0-seep_losses-absorp_losses);
return Math.abs(hh1);
}

double H2(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,  202e-3,  234e-3,  249e-3,  302e-3,  317e-3,  324e-3,  283e-3,  229e-3,  205e-3}; // m/month
double[]     I_med={2.8,   1.6,   1.3,     0.8,     1.1,     1.4,     4.1,     18.8,    22.4,    14.3,    6.5,     3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8};  
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 

double 	A1=(-3164.28+25.49*x[13]+1092.92*Math.log(x[13]))*1000000.0;//m^2	
double Evap1=A1*evap_rate[1]/1e9;;
double hh2=x[14]-(x[13]+I_1969_70[1]-x[1]-Evap1-seep_losses-absorp_losses);
return Math.abs(hh2);
}
	
double H3(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8};  
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 


double 	A2=(-3164.28+25.49*x[14]+1092.92*Math.log(x[14]))*1000000.0;//m^2	
double Evap2=A2*evap_rate[2]/1e9;;
double hh3=x[15]-(x[14]+I_1969_70[2]-x[2]-Evap2-seep_losses-absorp_losses);
return Math.abs(hh3);
}	

double H4(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8};  
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 

 
double 	A3=(-3164.28+25.49*x[15]+1092.92*Math.log(x[15]))*1000000.0;//m^2	
double Evap3=A3*evap_rate[3]/1e9;;
double hh4=x[16]-(x[15]+I_1969_70[3]-x[3]-Evap3-seep_losses-absorp_losses);
return Math.abs(hh4);
}	

double H5(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8}; 
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 
 
double 	H=79.97+0.0369*x[16]+18.87*Math.log(x[16]);//m
double 	A4=(-3164.28+25.49*x[16]+1092.92*Math.log(x[16]))*1000000.0;//m^2	
double Evap4=A4*evap_rate[4]/1e9;;
double hh5=x[17]-(x[16]+I_1969_70[4]-x[4]-Evap4-seep_losses-absorp_losses);
return Math.abs(hh5);
}


double H6(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8}; 
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 

 
double 	H=79.97+0.0369*x[17]+18.87*Math.log(x[17]);//m
double 	A5=(-3164.28+25.49*x[17]+1092.92*Math.log(x[17]))*1000000.0;//m^2	
double Evap5=A5*evap_rate[5]/1e9;;
double hh6=x[18]-(x[17]+I_1969_70[5]-x[5]-Evap5-seep_losses-absorp_losses);
return Math.abs(hh6);
}


double H7(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8}; 
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 

 
double 	H=79.97+0.0369*x[18]+18.87*Math.log(x[18]);//m
double 	A6=(-3164.28+25.49*x[18]+1092.92*Math.log(x[18]))*1000000.0;//m^2	
double Evap6=A6*evap_rate[6]/1e9;;
double hh7=x[19]-(x[18]+I_1969_70[6]-x[6]-Evap6-seep_losses-absorp_losses);
return Math.abs(hh7);
}



double H8(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8}; 
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 

 
double 	H=79.97+0.0369*x[19]+18.87*Math.log(x[19]);//m
double 	A7=(-3164.28+25.49*x[19]+1092.92*Math.log(x[19]))*1000000.0;//m^2	
double Evap7=A7*evap_rate[7]/1e9;;
double hh8=x[20]-(x[19]+I_1969_70[7]-x[7]-Evap7-seep_losses-absorp_losses);
return Math.abs(hh8);
}


double H9(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8}; 
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 


double 	H=79.97+0.0369*x[20]+18.87*Math.log(x[20]);//m
double 	A8=(-3164.28+25.49*x[20]+1092.92*Math.log(x[20]))*1000000.0;//m^2	
double Evap8=A8*evap_rate[8]/1e9;;
double hh9=x[21]-(x[20]+I_1969_70[8]-x[8]-Evap8-seep_losses-absorp_losses);
return Math.abs(hh9);
}


double H10(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8}; 
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 

 
double 	H=79.97+0.0369*x[21]+18.87*Math.log(x[21]);//m
double 	A9=(-3164.28+25.49*x[21]+1092.92*Math.log(x[21]))*1000000.0;//m^2	
double Evap9=A9*evap_rate[9]/1e9;;
double hh10=x[22]-(x[21]+I_1969_70[9]-x[9]-Evap9-seep_losses-absorp_losses);
return Math.abs(hh10);
}


double H11(double x[])
{
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]   I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]   I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8}; 
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 

 
double 	H=79.97+0.0369*x[22]+18.87*Math.log(x[22]);//m
double 	A10=(-3164.28+25.49*x[22]+1092.92*Math.log(x[22]))*1000000.0;//m^2	
double Evap10=A10*evap_rate[10]/1e9;;
double hh11=x[23]-(x[22]+I_1969_70[10]-x[10]-Evap10-seep_losses-absorp_losses);
return Math.abs(hh11);
}

	
double H12(double x[])
{
double x24=x[12];	
double evap_rate[]={179e-3,159e-3,189e-3,202e-3,234e-3,249e-3,302e-3,317e-3,324e-3,283e-3,229e-3,205e-3}; // m/month
double[]      I_med={2.8, 1.6, 1.3, 0.8, 1.1, 1.4, 4.1, 18.8, 22.4, 14.3,  6.5, 3.8};
double[]      I_low={1.6, 0.5, 0.3, 0.1, 0.5, 0.7, 2.3, 13.7, 17.1, 10.2,  4.1, 2.3};
double[]   I_medlow={2.2, 1.1, 0.8, 0.5, 0.8, 1.1, 3.3, 16.4, 20.0, 12.4,  5.4, 3.1}; 
double[]   I_medhigh={3.5, 2.3, 2.1, 1.5, 1.6, 1.9, 5.4, 22.0, 25.7, 16.9, 8.1, 4.8}; 
double[]   I_high={4.8, 3.7, 3.5, 2.7, 2.5, 2.8, 7.7, 27.5, 31.0, 21.2, 10.9, 6.5};
double[]   I_2000_1={4.58, 3.61, 3.33, 3.45, 3.65, 3.07, 7.38, 21.36, 18.54, 11.58, 8.42, 5.56 };
double[]   I_1999_0={4.85, 4.18, 3.85, 4.28, 3.75, 3.35, 6.38, 22.66, 23.19, 15.40,10.06, 6.37 };
double[]   I_1998_99={4.30, 3.90, 3.67, 3.54, 3.56, 3.35, 7.77, 24.29, 32.79, 17.37,10.59, 5.63 };
double[]   I_1997_98={4.35, 3.63, 3.41, 3.78, 4.00, 2.93, 6.15, 17.10, 10.48,  7.31, 8.54, 5.82 };
double[]   I_1996_97={4.51, 3.84, 3.59, 3.95, 3.82, 3.47, 7.91, 21.36, 20.33, 11.61, 6.18, 4.97 };
double[]   I_1995_96={4.03, 3.36, 3.12, 3.29, 3.63, 3.90,11.03, 18.53, 14.36,  7.00, 4.94, 4.19 };
double[]   I_1994_95={4.38, 3.54, 3.44, 3.18, 3.47, 2.55, 4.83, 23.39, 25.48, 10.02, 5.63, 4.69 };
double[]   I_1993_94={4.24, 3.45, 3.09, 3.35, 3.57, 2.71, 6.59, 19.07, 20.36, 11.48, 7.49, 5.79 };
double[]   I_1992_93={4.27, 3.62, 3.38, 3.89, 3.82, 4.28, 7.97, 15.23, 17.95, 10.38, 8.17, 5.11 };
double[]   I_1991_92={4.39, 3.43, 3.20, 4.06, 3.35, 2.60, 4.83, 18.70, 18.84,  7.56, 5.95, 4.95 };
double[]   I_1990_91={3.70, 2.79, 2.81, 3.12, 2.95, 2.44, 6.36, 13.09, 14.91,  8.42, 5.15, 4.30 };
double[]   I_1989_90={4.22, 3.43, 3.07, 3.80, 3.64, 2.37, 3.23, 14.13, 16.91, 10.14, 6.15, 4.70 };
double[]   I_1988_89={5.18, 3.84, 3.49, 3.88, 3.98, 3.09, 5.84, 26.18, 25.52, 15.23, 9.14, 5.49 };
double[]   I_1987_88={3.73, 3.01, 2.88, 3.35, 2.39, 2.09, 8.20, 11.18, 10.36,  6.37, 5.32, 4.20 };
double[]   I_1986_87={3.26, 2.67, 2.39, 2.83, 2.91, 3.51, 5.23, 13.97, 13.88,  7.44, 4.99, 3.78 };
double[]   I_1985_86={3.85, 3.23, 3.03, 3.43, 3.23, 2.27, 5.80, 15.35, 20.48,  7.82, 5.42, 4.80 };
double[]   I_1984_85={3.35, 2.91, 2.80, 3.22, 3.11, 2.80, 6.65, 10.47,  7.31,  5.97, 4.27, 3.53 };
double[]   I_1983_84={4.34, 3.82, 3.09, 3.75, 3.22, 2.38, 5.05, 12.32, 15.46,  8.58, 6.55, 4.62 };
double[]   I_1982_83={3.60, 2.98, 2.86, 3.74, 2.95, 2.61, 3.21, 11.54, 11.78,  9.23, 5.68, 4.71 };
double[]   I_1981_82={4.31, 3.80, 2.96, 3.70, 3.19, 2.73, 3.58, 17.33, 18.11, 11.39, 5.84, 4.82 };
double[]   I_1980_81={4.14, 3.29, 3.09, 3.63, 3.43, 2.65, 4.78, 20.06, 16.65,  7.12, 5.98, 4.70  };
double[]   I_1979_80={3.76, 2.90, 2.85, 3.61, 3.62, 2.79, 5.66, 15.47, 11.52,  7.88, 5.65, 4.78  };
double[]   I_1978_79={4.66, 3.77, 3.67, 4.14, 3.81, 3.19, 5.22, 18.81, 14.65, 12.41, 6.10, 4.85  };
double[]   I_1977_78={4.77, 3.73, 3.28, 3.39, 3.38, 3.17, 6.67, 23.08, 16.09,  9.70, 8.02, 5.28  };
double[]   I_1976_77={4.42, 3.28, 3.11, 3.73, 3.66, 3.06, 6.03, 17.94, 15.85,  8.14, 6.00, 5.26  }; 
double[]   I_1975_76={5.24, 4.29, 3.44, 3.43, 4.22, 3.47, 6.60, 24.20, 30.01, 16.39, 8.01, 6.41  };
double[]   I_1974_75={4.58, 3.72, 3.30, 4.07, 3.64, 2.49, 5.30, 22.89, 21.04, 12.14, 7.00, 5.08  };
double[]   I_1973_74={5.26, 3.05, 3.04, 5.26, 3.41, 3.07, 9.62, 20.66, 16.78, 10.67, 5.59, 3.31  };
double[]   I_1972_73={3.72, 2.73, 2.73, 3.43, 3.40, 3.10, 4.17, 12.55, 10.44,  9.01, 5.12, 4.16  }; 
double[]   I_1971_72={5.07, 4.02, 3.34, 4.47, 3.61, 2.75, 6.28, 20.07, 19.62,  9.33, 6.56, 5.37  }; 
double[]   I_1970_71={4.44, 4.40, 3.83, 4.42, 4.72, 3.03, 4.87, 18.82, 21.76,  9.83, 7.16, 4.67  }; 
double[]   I_1969_70={4.65, 3.63, 3.21, 4.29, 3.67, 2.46, 3.54, 20.58, 18.13,  7.86, 5.45, 4.91  }; 
double[]   I_1968_69={4.13, 3.27, 3.62, 4.73, 4.38, 2.80, 5.98, 18.11, 12.18, 10.72, 5.95, 5.09  }; 
double[]   I_1967_68={5.37, 4.68, 4.00, 4.08, 3.63, 3.42, 5.76, 20.24, 22.44, 15.41, 8.69, 6.60  }; 
double[]   I_1966_67={5.79, 4.28, 3.10, 3.32, 3.73, 3.32, 4.43, 13.58, 18.33, 10.32, 5.83, 5.76  }; 
double[]   I_1965_66={5.03, 3.92, 3.44, 3.32, 3.86, 2.95, 5.15, 13.68, 18.14, 12.64,10.28, 6.35  }; 
double[]   I_1964_65={6.57, 6.04, 5.81, 4.46, 4.10, 4.91, 5.34, 24.74, 27.59, 18.73,12.85, 7.48  }; 
double[]   I_1963_64={5.03, 3.83, 3.34, 2.86, 3.71, 3.94, 5.87, 20.45, 21.92, 13.33, 6.39, 5.39  }; 
double[]   I_1962_63={4.29, 3.49, 3.21, 2.73, 2.87, 3.04, 7.04, 15.85, 22.62, 16.83, 7.50, 5.01  }; 
double[]   I_1961_62={4.40, 3.34, 3.08, 3.27, 3.07, 3.24, 4.72, 22.65, 27.12, 18.73, 9.65, 5.96  }; 
double[]   I_1960_61={3.20, 2.24, 2.51, 2.85, 2.45, 1.41, 5.43, 17.15, 21.02, 14.63, 6.85, 4.02  }; 
double[]   I_1959_60={3.91, 2.68, 2.52, 2.77, 2.60, 1.83, 3.82, 18.75, 29.72, 16.73, 9.91, 5.37  }; 
double[]   I_1958_59={3.69, 2.68, 2.55, 2.73, 2.57, 1.72, 3.40, 24.65, 24.62, 15.83, 8.65, 5.15  }; 
double[]   I_1957_58={2.82, 2.11, 2.38, 2.46, 1.93, 1.40, 5.03, 17.65, 21.52, 10.05, 5.13, 3.66  }; 
double[]   I_1956_57={4.25, 3.37, 2.68, 2.94, 3.24, 3.06, 4.48, 19.75, 20.82, 18.53,12.64, 6.04 }; 
double[]   I_1955_56={3.84, 3.06, 2.65, 2.62, 2.78, 2.47, 5.81, 19.25, 22.82, 18.13, 7.65, 4.86 }; 
double[]   I_1954_55={4.08, 2.97, 2.38, 2.59, 2.72, 2.28, 4.35, 24.85, 28.42, 20.63, 8.79, 5.33 }; 
double[]   I_1953_54={3.13, 2.09, 2.85, 2.58, 2.05, 1.28, 5.66, 22.05, 20.92, 14.03, 6.57, 4.40 }; 
double[]   I_1952_53={3.05, 2.15, 2.48, 2.72, 1.83, 1.56, 3.89, 14.95, 21.22, 12.63, 6.48, 3.99 }; 
double[]   I_1951_52={3.54, 2.64, 2.98, 2.41, 1.84, 1.39, 3.24, 15.45, 17.92, 12.13, 8.03, 5.22 }; 
double[]   I_1950_51={3.65, 2.50, 2.57, 2.65, 2.38, 1.28, 2.88, 20.85, 22.62, 14.93, 6.85, 4.74 }; 

 
double 	A11=(-3164.28+25.49*x[23]+1092.92*Math.log(x[23]))*1000000.0;//m^2	
double Evap11=A11*evap_rate[11]/1e9;;
double hh12=x24-(x[23]+I_1969_70[11]-x[11]-Evap11-seep_losses-absorp_losses);
return Math.abs(hh12);
}


///////////////////////////////////////////////////



double G11(double x[])
{
double 	H1=79.97+0.0369*x[12]+18.87*Math.log(x[12]);//m	
return Math.pow((Math.max(0,   (147.0-H1))),2.0);
}

double G12(double x[])
{
double 	H1=79.97+0.0369*x[12]+18.87*Math.log(x[12]);//m	
return Math.pow((Math.max(0,   (H1-183.0))),2.0);
}

///////////////////////////////////////////////////////////

double G21(double x[])
{
double 	H2=79.97+0.0369*x[13]+18.87*Math.log(x[13]);//m;//m	
return Math.pow((Math.max(0,   (147.0-H2))),2.0);
}

double G22(double x[])
{
double 	H2=79.97+0.0369*x[13]+18.87*Math.log(x[13]);//m	
return Math.pow((Math.max(0,   (H2-183.0))),2.0);
}

////////////////////////////////////////////////////////////////

double G31(double x[])
{
double 	H3=79.97+0.0369*x[14]+18.87*Math.log(x[14]);//m
return Math.pow((Math.max(0,   (147.0-H3))),2.0);
}

double G32(double x[])
{
double 	H3=79.97+0.0369*x[14]+18.87*Math.log(x[14]);//m
return Math.pow((Math.max(0,   (H3-183.0))),2.0);
}

/////////////////////////////////////////////////////////////

double G41(double x[])
{
double 	H4=79.97+0.0369*x[15]+18.87*Math.log(x[15]);//m
return Math.pow((Math.max(0,   (147.0-H4))),2.0);
}

double G42(double x[])
{
double 	H4=79.97+0.0369*x[15]+18.87*Math.log(x[15]);//m
return Math.pow((Math.max(0,   (H4-183.0))),2.0);
}

/////////////////////////////////////////////////////////////


double G51(double x[])
{
double 	H5=79.97+0.0369*x[16]+18.87*Math.log(x[16]);//m
return Math.pow((Math.max(0,   (147.0-H5))),2.0);
}

double G52(double x[])
{
double 	H5=79.97+0.0369*x[16]+18.87*Math.log(x[16]);//m
return Math.pow((Math.max(0,   (H5-183.0))),2.0);
}

///////////////////////////////////////////////////////////

double G61(double x[])
{
double 	H6=79.97+0.0369*x[17]+18.87*Math.log(x[17]);//m
return Math.pow((Math.max(0,   (147.0-H6))),2.0);
}

double G62(double x[])
{
double 	H6=79.97+0.0369*x[17]+18.87*Math.log(x[17]);//m
return Math.pow((Math.max(0,   (H6-183.0))),2.0);
}

///////////////////////////////////////////////////////////

double G71(double x[])
{
double 	H7=79.97+0.0369*x[18]+18.87*Math.log(x[18]);//m
return Math.pow((Math.max(0,   (147.0-H7))),2.0);
}

double G72(double x[])
{
double 	H7=79.97+0.0369*x[18]+18.87*Math.log(x[18]);//m
return Math.pow((Math.max(0,   (H7-183.0))),2.0);
}

///////////////////////////////////////////////////////////

double G81(double x[])
{
double 	H8=79.97+0.0369*x[19]+18.87*Math.log(x[19]);//m
return Math.pow((Math.max(0,   (147.0-H8))),2.0);
}

double G82(double x[])
{
double 	H8=79.97+0.0369*x[19]+18.87*Math.log(x[19]);//m
return Math.pow((Math.max(0,   (H8-183.0))),2.0);
}

//////////////////////////////////////////////////////////

double G91(double x[])
{
double 	H9=79.97+0.0369*x[20]+18.87*Math.log(x[20]);//m
return Math.pow((Math.max(0,   (147.0-H9))),2.0);
}

double G92(double x[])
{
double 	H9=79.97+0.0369*x[20]+18.87*Math.log(x[20]);//m
return Math.pow((Math.max(0,   (H9-183.0))),2.0);
}

//////////////////////////////////////////////////////////////

double G101(double x[])
{
double 	H10=79.97+0.0369*x[21]+18.87*Math.log(x[21]);//m
return Math.pow((Math.max(0,   (147.0-H10))),2.0);
}

double G102(double x[])
{
double 	H10=79.97+0.0369*x[21]+18.87*Math.log(x[21]);//m
return Math.pow((Math.max(0,   (H10-183.0))),2.0);
}

////////////////////////////////////////////////////////////

double G111(double x[])
{
double 	H11=79.97+0.0369*x[22]+18.87*Math.log(x[22]);//m
return Math.pow((Math.max(0,   (147.0-H11))),2.0);
}

double G112(double x[])
{
double 	H11=79.97+0.0369*x[22]+18.87*Math.log(x[22]);//m
return Math.pow((Math.max(0,   (H11-183.0))),2.0);
}

////////////////////////////////////////////////////////

double G121(double x[])
{
double 	H12=79.97+0.0369*x[23]+18.87*Math.log(x[23]);//m
return Math.pow((Math.max(0,   (147.0-H12))),2.0);
}

double G122(double x[])
{
double 	H12=79.97+0.0369*x[23]+18.87*Math.log(x[23]);//m
return Math.pow((Math.max(0,   (H12-183.0))),2.0);
}


}



class helical_coil extends f_xj// Himmelblau f(x)=0.0  @x=(3.0,2.0),(-2.8051,3.1313),(-3.7793,-3.2831),(3.5844,-1.8481)   -6.0<=x[i]<=6.0 i=0,1
{
double func(double x[])
{
 	
 
	///////////////////////////////////////
	 double m_A=1350.0/3600.0; //kg/s
	 double m_B=2141.0/3600.0; //kg/s
	 double T_A_in=400.0; //K
	 double T_A_out=373.0; //K
	 double T_B_in=303.0; //K
	 double T_B_out=320.0; //K
	 double Cp_A=4186.0; // J/kgK
	 double Cp_B=4186.0; // J/kgK
	 double k_A=4186.0*0.4191/3600.0; //W/mK
	 double k_B=4186.0*0.4075/3600.0; //W/mK
	 double vis_A=1.89/3600.0; //kg/ms
	 double vis_B=5.76/3600.0; //kg/ms	
	 double ro_A=870.0; //kg/m3 
	 double ro_B=935.0; //kg/m3 
	 double Pr_A=vis_A*Cp_A/k_A;
	 double Pr_B=vis_B*Cp_B/k_B;
	 double k_tube=14.4;//Stainless steel thermal conductivity
	 double R_A=8.2e-4*3600.0/4186.0;
	 double R_B=8.2e-4*3600.0/4186.0;
	 double HH=8000.0;//h/yr	
	 double Ce=0.12; //euro/kWh
	 double eta=0.75;
	 
	///////////////////////////////////////// 
	
	 ///////////////////////////////////////// 
	 double B=x[0];//0.340; //outside diameter of the inner cycle (m)   0.3--0.4   x[0]
	 double C=x[1];//0.460; //inside diameter of the outer cylinder (m) 0.45 -- 0.55  x[1]
	 double d0=x[2];//0.030; // outside diameter of coil  0.025--0.035  x[2] 
	 double D=x[3];//0.025; // inside diameter of coil    0.015--0.022  x[3]
	 double DH=(B+C)/2; // average diameter of coil
	 double p=d0*1.5;
	///////////////////////////////////////////////////////// 
     double L=Math.sqrt(2*Math.PI*(DH/2)*2*Math.PI*(DH/2)+p*p);
     
     double Vf=((Math.PI/4)*(C*C-B*B)*p)-((Math.PI/4)*(d0*d0)*L);
	 double De=(4*Vf)/(Math.PI*d0*L);
	 double GB=m_B/((Math.PI/4)*((C*C-B*B)-((C-d0)*(C-d0)-(B+d0)*(B+d0))));
	 double ReB=GB*De/vis_B;
	 //System.out.println();
	 double ho=0.0;
	 if((ReB>50.0)&&(ReB<10000))
	 {ho= 0.6*Math.pow(ReB,0.5)*Math.pow(Pr_B,0.31)*k_B/De;}
	 else if(ReB>10000)
	 {ho=0.0360*Math.pow(ReB,0.55)*Math.pow(Pr_B,0.333)*1.0*k_B/De;}
	 double Af=(Math.PI*D*D*0.25);
	 double V_A=m_A/(ro_A*Af);
	 double Re_A=ro_A*V_A*D/vis_A;
	 double f_A=0.046*Math.pow(Re_A,-0.2);//for smooth tubes
	 //double hi=(((f_A/2)*(Re_A-1000)*Pr_A)/(1+12.7*(Math.pow(Pr_A,0.666)-1)*Math.sqrt(f_A/2)))*k_A/D;
	 double hi=0.023*Math.pow(Re_A,0.8)*Math.pow(Pr_A,0.3)*k_A/D;
	 //System.out.println(ReB);
	 double thck=(d0-D)/2;
	 double U=1/((1/ho)+(1/hi)+(thck/k_tube)+R_A+R_B);
	 double dTlm=((T_A_in-T_B_in)-(T_A_out-T_B_out))/Math.log(((T_A_in-T_B_in)/(T_A_out-T_B_out)));
	 double dtc=dTlm*0.99;
	 double Qload=m_A*Cp_A*(T_A_in-T_A_out);
	 double A=Qload/(U*dtc);
	 double N=A/(Math.PI*d0*L);
	 N=Math.round(N);
     double H=N*1.5*d0+d0;
	 double Ci=8000+259.2*Math.pow(A,0.91); 
	 double fc=f_A*Math.pow((Re_A*(D/DH)*(D/DH)),0.05);
     double dP_coil=(2.0*fc*ro_A*L*N*(m_A/(ro_A*(Math.PI*D*D*0.25)))*(m_A/(ro_A*(Math.PI*D*D*0.25))))/D;
	 //System.out.println("dP_coil = "+dP_coil);
     double f_shell=8.0*0.023*Math.pow(ReB,-0.2);
	 double dP_shell=(f_shell*ro_B*(p*N)*(GB/ro_B)*(GB/ro_B))/(2.0*De);
	 //System.out.println("dP_shell = "+dP_shell);
	 double Ppump=(((m_B/ro_B)*(dP_shell ))+((m_A/ro_A)*(dP_coil )))*(1/eta);
	 double Co=Ppump*Ce*HH;
	 //System.out.println("Co = "+Co);
	 double top=0.0;
	 for(int k=1;k<=15;k++)
	 {top+=Co/Math.pow((1+0.1),(double)k);}
	 double Cod=top;
     //System.out.println("Cod = "+Cod); 
	 double Ctot=Cod+Ci;
	 //System.out.println("Ctot = "+(Ctot));
  	
return Ctot+100000.0*G1(x);	
}	

double G1(double x[])
{
	///////////////////////////////////////
	 double m_A=1350.0/3600.0; //kg/s
	 double m_B=2141.0/3600.0; //kg/s
	 double T_A_in=400.0; //K
	 double T_A_out=373.0; //K
	 double T_B_in=303.0; //K
	 double T_B_out=320.0; //K
	 double Cp_A=4186.0; // J/kgK
	 double Cp_B=4186.0; // J/kgK
	 double k_A=4186.0*0.4191/3600.0; //W/mK
	 double k_B=4186.0*0.4075/3600.0; //W/mK
	 double vis_A=1.89/3600.0; //kg/ms
	 double vis_B=5.76/3600.0; //kg/ms	
	 double ro_A=870.0; //kg/m3 
	 double ro_B=935.0; //kg/m3 
	 double Pr_A=vis_A*Cp_A/k_A;
	 double Pr_B=vis_B*Cp_B/k_B;
	 double k_tube=14.4;//Stainless steel thermal conductivity
	 double R_A=8.2e-4*3600.0/4186.0;
	 double R_B=8.2e-4*3600.0/4186.0;
	 double HH=8000.0;//h/yr	
	 double Ce=0.12; //euro/kWh
	 double eta=0.75;
	 
	///////////////////////////////////////// 
	
	 ///////////////////////////////////////// 
	 double B=x[0];//0.340; //outside diameter of the inner cycllinde (m)   0.3--0.4   x[0]
	 double C=x[1];//0.460; //inside diameter of the outer cylinder (m) 0.45 -- 0.55  x[1]
	 double d0=x[2];//0.030; // Outside diameter of coil  0.025--0.035  x[2] 
	 double D=x[3];//0.025; // Inside diameter of coil    0.015--0.022  x[3]
	 double DH=(B+C)/2; // Average diameter of coil
	 double p=d0*1.5;
	///////////////////////////////////////////////////////// 
     double L=Math.sqrt(2*Math.PI*(DH/2)*2*Math.PI*(DH/2)+p*p);
     
     double Vf=((Math.PI/4)*(C*C-B*B)*p)-((Math.PI/4)*(d0*d0)*L);
	 double De=(4*Vf)/(Math.PI*d0*L);
	 double GB=m_B/((Math.PI/4)*((C*C-B*B)-((C-d0)*(C-d0)-(B+d0)*(B+d0))));
	 double ReB=GB*De/vis_B;
	 //System.out.println();
	 double ho=0.0;
	 if((ReB>50.0)&&(ReB<10000))
	 {ho= 0.6*Math.pow(ReB,0.5)*Math.pow(Pr_B,0.31)*k_B/De;}
	 else if(ReB>10000)
	 {ho=0.0360*Math.pow(ReB,0.55)*Math.pow(Pr_B,0.333)*1.0*k_B/De;}
	 double Af=(Math.PI*D*D*0.25);
	 double V_A=m_A/(ro_A*Af);
	 double Re_A=ro_A*V_A*D/vis_A;
	 double f_A=0.046*Math.pow(Re_A,-0.2);//for smooth tubes
	 //double hi=(((f_A/2)*(Re_A-1000)*Pr_A)/(1+12.7*(Math.pow(Pr_A,0.666)-1)*Math.sqrt(f_A/2)))*k_A/D;
	 double hi=0.023*Math.pow(Re_A,0.8)*Math.pow(Pr_A,0.3)*k_A/D;
	 //System.out.println(ReB);
	 double thck=(d0-D)/2;
	 double U=1/((1/ho)+(1/hi)+(thck/k_tube)+R_A+R_B);
	 double dTlm=((T_A_in-T_B_in)-(T_A_out-T_B_out))/Math.log(((T_A_in-T_B_in)/(T_A_out-T_B_out)));
	 double dtc=dTlm*0.99;
	 double Qload=m_A*Cp_A*(T_A_in-T_A_out);
	 double A=Qload/(U*dtc);
	 double N=A/(Math.PI*d0*L);
	 N=Math.round(N);
     double H=N*1.5*d0+d0;





return Math.pow((Math.max(0,   (-0.9+H)   )),2.0);
}

double G2(double x[])
{
	///////////////////////////////////////
	 double m_A=1350.0/3600.0; //kg/s
	 double m_B=2141.0/3600.0; //kg/s
	 double T_A_in=400.0; //K
	 double T_A_out=373.0; //K
	 double T_B_in=303.0; //K
	 double T_B_out=320.0; //K
	 double Cp_A=4186.0; // J/kgK
	 double Cp_B=4186.0; // J/kgK
	 double k_A=4186.0*0.4191/3600.0; //W/mK
	 double k_B=4186.0*0.4075/3600.0; //W/mK
	 double vis_A=1.89/3600.0; //kg/ms
	 double vis_B=5.76/3600.0; //kg/ms	
	 double ro_A=870.0; //kg/m3 
	 double ro_B=935.0; //kg/m3 
	 double Pr_A=vis_A*Cp_A/k_A;
	 double Pr_B=vis_B*Cp_B/k_B;
	 double k_tube=14.4;//Stainless steel thermal conductivity
	 double R_A=8.2e-4*3600.0/4186.0;
	 double R_B=8.2e-4*3600.0/4186.0;
	 double HH=8000.0;//h/yr	
	 double Ce=0.12; //euro/kWh
	 double eta=0.75;
	 
	///////////////////////////////////////// 
	
	 ///////////////////////////////////////// 
	 double B=x[0];//0.340; //outside diameter of the inner cycle (m)   0.3--0.4   x[0]
	 double C=x[1];//0.460; //inside diameter of the outer cylinder (m) 0.45 -- 0.55  x[1]
	 double d0=x[2];//0.030; // Outside diameter of coil  0.025--0.035  x[2] 
	 double D=x[3];//0.025; // Inside diameter of coil    0.015--0.022  x[3]
	 double DH=(B+C)/2; // Average diameter of coil
	 double p=d0*1.5;
	///////////////////////////////////////////////////////// 
     double L=Math.sqrt(2*Math.PI*(DH/2)*2*Math.PI*(DH/2)+p*p);
     
     double Vf=((Math.PI/4)*(C*C-B*B)*p)-((Math.PI/4)*(d0*d0)*L);
	 double De=(4*Vf)/(Math.PI*d0*L);
	 double GB=m_B/((Math.PI/4)*((C*C-B*B)-((C-d0)*(C-d0)-(B+d0)*(B+d0))));
	 double ReB=GB*De/vis_B;
	 //System.out.println();
	 double ho=0.0;
	 if((ReB>50.0)&&(ReB<10000))
	 {ho= 0.6*Math.pow(ReB,0.5)*Math.pow(Pr_B,0.31)*k_B/De;}
	 else if(ReB>10000)
	 {ho=0.0360*Math.pow(ReB,0.55)*Math.pow(Pr_B,0.333)*1.0*k_B/De;}
	 double Af=(Math.PI*D*D*0.25);
	 double V_A=m_A/(ro_A*Af);
	 double Re_A=ro_A*V_A*D/vis_A;
	 double f_A=0.046*Math.pow(Re_A,-0.2);//for smooth tubes
	 //double hi=(((f_A/2)*(Re_A-1000)*Pr_A)/(1+12.7*(Math.pow(Pr_A,0.666)-1)*Math.sqrt(f_A/2)))*k_A/D;
	 double hi=0.023*Math.pow(Re_A,0.8)*Math.pow(Pr_A,0.3)*k_A/D;
	 //System.out.println(ReB);
	 double thck=(d0-D)/2;
	 double U=1/((1/ho)+(1/hi)+(thck/k_tube)+R_A+R_B);
	 double dTlm=((T_A_in-T_B_in)-(T_A_out-T_B_out))/Math.log(((T_A_in-T_B_in)/(T_A_out-T_B_out)));
	 double dtc=dTlm*0.99;
	 double Qload=m_A*Cp_A*(T_A_in-T_A_out);
	 double A=Qload/(U*dtc);
	 double N=A/(Math.PI*d0*L);
	 N=Math.round(N);
     double H=N*1.5*d0+d0;





return Math.pow((Math.max(0,   (V_A-1.4)   )),2.0);
}

}





public class Aquila_Optimizer_test
{
public static void main(String args[])
{	

                 //f20 levy                
                // double[] Lower={-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,};//  f20 levy
                // double[] Upper={10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,};
      
               //f36 ackley
               //double[] Lower={-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768,-32.768};//  f36 ackley
               //double[] Upper={32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768,32.768};
  
               // f11 griewank
               //double[] Lower={-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,};//  f11 griewank
               //double[] Upper={10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0};
  
               // f34 Rastrigin
                //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f34 Rastrigin
                //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12};
      
               //f19 zakharov
                //double[] Lower={-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0};//  f19 Zakharov
                //double[] Upper={10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0};                  
 
               // f101 alpine//
                //double[] Lower={-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0, };//  f11 griewank
                //double[] Upper={10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0 };
            
              //f41 penal1
               //double[] Lower={-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,};//  f41 Penalized
               //double[] Upper={5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0};

              // f42 Penalized 2
              //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
              //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
           
              //f123 // Quintic   function     x(pi,pi)   
              //double[] Lower={-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,};//  f11 griewank
              //double[] Upper={10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,};
     
               //f125 // Csendes   function     x(pi,pi)   
              //  double[] Lower={-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,};//  f11 griewank
              //  double[] Upper={10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,};
      
               // f29 Schaffer
               //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
 
               // f108 Salomon
               // double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               // double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
    
               // f133  Inverted cosine
               //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
             
               //f152  Wavy function
               //double[] Lower={-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14,-3.14};//  f31 sphere
               //double[] Upper={3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14,3.14 };
            
               // f146 Hyperellipsoid Function 
                //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
                //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
         
               // f107 Path
               //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
        
               // f31 Sphere
                // double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
                // double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
  
                  
                 
               // f30 Rosenbrock
                //double[] Lower={-2.048,-2.048,-2.0480,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048,-2.0480,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048,-2.0480,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048,-2.048};//  f30 Rosenbrock
                //double[] Upper={2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048,2.048};

               //f27 sche
                 //double[] Lower={-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,-5.0,};//  f40 step
                 //double[] Upper={5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0};
     
               // f143 Schwefel 2.23 Function 
               //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
           
               // f141  Schwefel 2.25 Function
               //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
              
               //f121 Brown
               double[] Lower={-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,};//  f11 griewank
               double[] Upper={10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,};
              
               //f122 // Streched V Sine Wave   function     x(pi,pi)   
               //double[] Lower={-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,};//  f11 griewank
               //double[] Upper={10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,};
       
               //f124 // Powell   function     x(pi,pi)   
               //double[] Lower={-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,-10.0,};//  f11 griewank
               //double[] Upper={10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,10.0,};
           
               // f130 Sum of different power
               //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
            
               // f131 High conditioned elliptic
                //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
                //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
            
               // f132  Sum squares
               //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
           
               //  f149   // Bent Cigar 
               //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
             
               //  f150   // Discus
               //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
               //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
             
                 //f151  // Different Powers Function  
                //double[] Lower={-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12,-5.12};//  f31 sphere
                //double[] Upper={5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12,5.12 };
      
                 //double[] Lower={ 0.0,  0.0,   0.0,    0.0,  0.0,     0.0,   0.0,   0.0,   0.0,   0.0,   0.0,   0.0,  28.74, 28.74, 28.74, 28.74 ,28.74, 28.74, 28.74, 28.74, 28.74, 28.74, 28.74, 28.74};
                 //double[] Upper={46.56, 46.56, 46.56, 46.56, 46.56, 46.56, 46.56, 46.56, 46.56, 46.56, 56.56, 46.56,  40.00, 40.00, 40.00, 40.00, 40.00, 40.00, 40.00, 40.00, 40.00, 40.00, 40.00, 40.00};
     
 
            
 
f34 ff=new f34(); 
int Maxiter=100;
int N=20;
  
 
Aquila_Optimizer mo=new Aquila_Optimizer(Lower,Upper,N,ff,Maxiter);	 

 
mo.toStringnew();
 
 

Aquila_Optimizer_test.main(args);



}	

}