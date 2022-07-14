package com.heuristic.draw;

import com.heuristic.ExcelUtils;
import com.heuristic.algorithm.PSO.Particles;
import com.heuristic.algorithm.PSO.Swarm;
import com.heuristic.f_xj;

import java.io.IOException;

public class PSO_Draw {
    double r1, r2, c1, c2, w, wMax, wMin;
    int N;
    int dim;
    int maxIter;
    double lb[];
    double ub[];
    double vMax[];
    double vMin[];
    f_xj ff;
    double X[][];
    double[][] Result;
    double[][] arrRandomBestVal;
    Swarm swarm;

    //for draw
    double F_min[]; //F min tai moi lan lap
    double F_avg[]; //F trung binh cua cac search agents sau moi lan lap
    double X_1[];   //gia tri x1 cua search agent dau tien sau moi lan lap
    double X_2[];   //gia tri x2 cua search agent dau tien sau moi lan lap
    static int Search_agent_1_pos = 0;
    static int orderOfF;

    public PSO_Draw(f_xj iff, double[] Lower, double[] Upper, int imaxIter, int iN, int orderOfF) {
        maxIter = imaxIter;
        ff = iff;
        lb = Lower;
        ub = Upper;
        vMax = new double[lb.length];
        vMin = new double[ub.length];
        N = iN;
        dim = ub.length;
        X = new double[N][dim];
        arrRandomBestVal = new double[maxIter][dim];

        F_min = new double[maxIter];
        F_avg = new double[maxIter];
        X_1 = new double[maxIter];
        X_2 = new double[maxIter];
        PSO_Draw.orderOfF = orderOfF;
    }

    void init() throws IOException {
        wMax = 0.9;
        wMin = 0.2;
        c1 = 2;
        c2 = 2;
        for (int i = 0; i < lb.length; i++) {
            vMax[i] = (ub[i] - lb[i]) * 0.2;
            vMin[i] = -vMax[i];
        }

        swarm = new Swarm(N, dim);
        for (int i = 0; i < N; i++) {
            swarm.particles[i] = new Particles(dim);
            for (int j = 0; j < dim; j++) {
                swarm.particles[i].X[j] = lb[j] + (ub[j] - lb[j]) * nextRand();
            }
        }
    }

    double[][] solution() throws IOException {
        init();
        int iter = 1;
        while (iter <= maxIter) {
            // Calcualte the objective value
            for (int i = 0; i < N; i++) {
                double[] currentX = new double[dim];
                for (int j = 0; j < dim; j++) {
                    currentX[j] = swarm.particles[i].X[j];
                }
                swarm.particles[i].O = ff.func(currentX);

                //Update the PBEST
                if (swarm.particles[i].O < swarm.particles[i].PBEST.O) {
                    for (int j = 0; j < dim; j++) {
                        swarm.particles[i].PBEST.X[j] = swarm.particles[i].X[j];
                    }
                    swarm.particles[i].PBEST.O = swarm.particles[i].O;
                }

                //Update the GBEST
                if (swarm.particles[i].O < swarm.GBEST.O) {
                    for (int j = 0; j < dim; j++) {
                        swarm.GBEST.X[j] = swarm.particles[i].X[j];
                    }
                    swarm.GBEST.O = swarm.particles[i].O;
                }
            }

            //Update the X and V vectors
            w = wMax - iter * ((wMax - wMin) / maxIter);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < dim; j++) {
                    r1 = nextRand();
                    r2 = nextRand();
                    swarm.particles[i].V[j] = w * swarm.particles[i].V[j] + c1 * r1 * (swarm.particles[i].PBEST.X[j] - swarm.particles[i].X[j])
                            + c2 * r2 * (swarm.GBEST.X[j] - swarm.particles[i].X[j]);

                    //Check velocities
                    if (swarm.particles[i].V[j] > vMax[j]) {
                        swarm.particles[i].V[j] = vMax[j];
                    }
                    if (swarm.particles[i].V[j] < vMin[j]) {
                        swarm.particles[i].V[j] = vMin[j];
                    }

                    swarm.particles[i].X[j] = swarm.particles[i].X[j] + swarm.particles[i].V[j];

                    //Check positions
                    if (swarm.particles[i].X[j] > ub[j]) {
                        swarm.particles[i].X[j] = ub[j];
                    }
                    if (swarm.particles[i].X[j] < lb[j]) {
                        swarm.particles[i].X[j] = lb[j];
                    }
                }
            }

            arrRandomBestVal[iter - 1] = swarm.GBEST.X;
            System.out.println("Iteration: " + iter);
            System.out.println("Best score: " + swarm.GBEST.O);

            F_min[iter-1] = swarm.GBEST.O;
            double sumF = 0.0;
            for (int i=0; i<N; i++){
                sumF+= ff.func(swarm.particles[i].X);
            }
            F_avg[iter-1] = sumF/N;
            X_1[iter-1] = swarm.particles[0].X[0];
            X_2[iter-1] = swarm.particles[0].X[1];
            iter++;
        }

        double[][] out = new double[2][dim];

        for (int i = 0; i < dim; i++) {
            out[1][i] = swarm.GBEST.X[i];
        }

        out[0][0] = swarm.GBEST.O;
        return out;
    }

    public double getRes() throws IOException {
        ExcelUtils.fillForDrawFunctionToExcel(F_min, F_avg, X_1, X_2, 92, 240, 388, 537, orderOfF, maxIter);
        return swarm.GBEST.O;
    }

    double nextRand() {
//        return 0.7;
//        position++;
//        return randomm[position-1];
        return Math.random();
    }

}
