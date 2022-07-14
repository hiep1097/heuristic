package com.heuristic.draw;

public class DrawFunction {
    static double GWO_res[] = new double[23];
    static double DA_res[] = new double[23];
    static double DA_GWO_res[] = new double[23];
    static double PSO_res[] = new double[23];
    static double ALO_res[] = new double[23];

    public static void main(String[] args) throws Exception {
        f_test_draw f = new f_test_draw();
        for (int i=0; i<23; i++){
            String fname = "f"+(i+1);
            f.getFunctionDetail(fname);
//            GWO_Draw result_GWO = new GWO_Draw(f.getF(), f.getLowerBound(), f.getUpperBound(), f_test_draw.maxiter, f_test_draw.N,i);
//            GWO_res[i] = result_GWO.getRes();
//
//            DA_Draw result_DA = new DA_Draw(f.getF(), f.getLowerBound(), f.getUpperBound(), f_test_draw.maxiter, f_test_draw.N,i);
//            result_DA.solution();
//            DA_res[i] = result_DA.getRes();
//
//            DA_GWO_Draw result_DA_GWO = new DA_GWO_Draw(f.getF(), f.getLowerBound(), f.getUpperBound(), f_test_draw.maxiter, f_test_draw.N,i);
//            result_DA_GWO.solution();
//            DA_GWO_res[i] = result_DA_GWO.getRes();

            PSO_Draw result_PSO = new PSO_Draw(f.getF(), f.getLowerBound(), f.getUpperBound(), f_test_draw.maxiter, f_test_draw.N,i);
            result_PSO.solution();
            PSO_res[i] = result_PSO.getRes();

            ALO_Draw result_ALO = new ALO_Draw(f.getF(), f.getLowerBound(), f.getUpperBound(), f_test_draw.maxiter, f_test_draw.N,i);
            result_ALO.solution();
            ALO_res[i] = result_ALO.getRes();
        }
    }
}
