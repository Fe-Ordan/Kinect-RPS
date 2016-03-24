import hex.genmodel.*;

public class main{
	public static void main(String[] args) throws Exception {
	    gbm_test g = new gbm_test();

	    double[] p = {533,842,753,733,1148,1127};
	    double[] a = {7826,18493,11969,11534,13009,12647};
	    double[] l = {105,168,166,162,197,193};
	    double[] h = {132,149,119,108,113,110};

	    for(int i = 0; i < p.length; i++){
	    	double[] data = {p[i], a[i], l[i], h[i]};
	    	double[] pred = new double[4];

	    	double[] results = g.score0(data, pred);
	    	System.out.println(results[0]);
	    }
	}
}