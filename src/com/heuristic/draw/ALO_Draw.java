package com.heuristic.draw;

import com.heuristic.ExcelUtils;
import com.heuristic.algorithm.ALO.Random_walk_around_antlion;
import com.heuristic.algorithm.ALO.RouletteWheelSelection;
import com.heuristic.f_xj;

import java.io.IOException;

public class ALO_Draw {
    int N;
    int dim;
    int maxIter;
    f_xj ff;
    double lb[];
    double ub[];
    double Elite_antlion_fitness;
    double Elite_antlion_position[];
    double antlion_position[][];
    double ant_position[][];
    double Sorted_antlions[][];
    double antlions_fitness[];
    double ants_fitness[];
    double sorted_antlion_fitness[];
    double[][] Result;
    double[][] arrRandomBestVal;
    double inf = 10E+50;

    //for draw
    double F_min[]; //F min tai moi lan lap
    double F_avg[]; //F trung binh cua cac search agents sau moi lan lap
    double X_1[];   //gia tri x1 cua search agent dau tien sau moi lan lap
    double X_2[];   //gia tri x2 cua search agent dau tien sau moi lan lap
    static int Search_agent_1_pos = 0;
    static int orderOfF;

    public ALO_Draw(f_xj iff, double[] Lower, double[] Upper, int imaxIter, int iN, int orderOfF) {
        maxIter = imaxIter;
        ff = iff;
        lb = Lower;
        ub = Upper;
        N = iN;
        dim = ub.length;
        arrRandomBestVal = new double[maxIter][dim];
        Elite_antlion_fitness = inf;
        Elite_antlion_position = new double[dim];
        antlions_fitness = new double[N];
        sorted_antlion_fitness = new double[N];
        ants_fitness = new double[N];
        Sorted_antlions = new double[N][dim];
        ant_position = new double[N][dim];
        antlion_position = new double[N][dim];

        F_min = new double[maxIter];
        F_avg = new double[maxIter];
        X_1 = new double[maxIter];
        X_2 = new double[maxIter];
        ALO_Draw.orderOfF = orderOfF;
    }

    void init() throws IOException {
        //init antlion position and ant position
        for (int i=0; i<N; i++){
            for (int j=0; j<dim; j++){
                antlion_position[i][j] = lb[j] + (ub[j] - lb[j]) * nextRand();
                Sorted_antlions[i][j] = antlion_position[i][j];
                ant_position[i][j] = lb[j] + (ub[j] - lb[j]) * nextRand();
            }
        }

        //Calculate the fitness of initial antlions and sort them
        for (int i=0; i<N; i++){
            antlions_fitness[i] = ff.func(antlion_position[i]);
            sorted_antlion_fitness[i] = antlions_fitness[i];
        }

        for (int i=0; i<N-1; i++){
            for (int j=i+1; j<N; j++){
                if (sorted_antlion_fitness[i]>sorted_antlion_fitness[j]){
                    double finess_temp = sorted_antlion_fitness[i];
                    double position_temp[] = new double[dim];
                    for (int k=0; k<dim; k++){
                        position_temp[k] = Sorted_antlions[i][k];
                    }
                    sorted_antlion_fitness[i] = sorted_antlion_fitness[j];
                    for (int k=0; k<dim; k++){
                        Sorted_antlions[i][k] = Sorted_antlions[j][k];
                    }
                    sorted_antlion_fitness[j] = finess_temp;
                    for (int k=0; k<dim; k++){
                        Sorted_antlions[j][k] = position_temp[k];
                    }
                }
            }
        }

        for (int i=0; i<dim; i++){
            Elite_antlion_position[i] = Sorted_antlions[0][i];
        }
        Elite_antlion_fitness = sorted_antlion_fitness[0];
        arrRandomBestVal[0] = Elite_antlion_position;
        System.out.println("Iteration: 1");
        System.out.println("Best score: "+Elite_antlion_fitness);
        F_min[0] = Elite_antlion_fitness;
        double sumF = 0.0;
        for (int i=0; i<N; i++){
            sumF+= ff.func(Sorted_antlions[i]);
        }
        F_avg[0] = sumF/N;
        X_1[0] = Sorted_antlions[0][0];
        X_2[0] = Sorted_antlions[0][1];
    }

    double[][] solution() throws IOException {
        init();
        // Main loop start from the second iteration since the first iteration
        // was dedicated to calculating the fitness of antlions
        int iter = 2;
        while(iter <= maxIter) {
            // This for loop simulate random walks
            for (int i=0; i<N; i++){
                // Select ant lions based on their fitness (the better anlion the higher chance of catching ant)
                double [] weights = new double[N];
                for (int j=0; j<N; j++){
                    weights[j] = 1.0/sorted_antlion_fitness[j];
                }
                int Rolette_index = RouletteWheelSelection.choice(weights);
                if (Rolette_index == -1) {
                    Rolette_index=0;
                }

                // RA is the random walk around the selected antlion by rolette wheel
                double [][] RA;
                double [][] RE;
                double [] antlion_RA = new double[dim];
                double [] antlion_RE = new double[dim];
                for (int j=0; j<dim; j++){
                    antlion_RA[j] = Sorted_antlions[Rolette_index][j];
                    antlion_RE[j] = Elite_antlion_position[j];
                }
                Random_walk_around_antlion rw_RA = new Random_walk_around_antlion(dim, maxIter, lb, ub, antlion_RA, iter);
                Random_walk_around_antlion rw_RE = new Random_walk_around_antlion(dim, maxIter, lb, ub, antlion_RE, iter);
                RA = rw_RA.RWs();
                RE = rw_RE.RWs();

                for (int j=0; j<dim; j++){
                    ant_position[i][j] = (RA[iter-1][j]+RE[iter-1][j])/2;
                }

                // Boundar checking (bring back the antlions of ants inside search space if they go beyoud the boundaries
                for (int j=0; j<dim; j++){
                    if (ant_position[i][j] > ub[j]){
                        ant_position[i][j] = ub[j];
                    }
                    if (ant_position[i][j] < lb[j]){
                        ant_position[i][j] = lb[j];
                    }
                }

                ants_fitness[i] = ff.func(ant_position[i]);
            }

            // Update antlion positions and fitnesses based of the ants (if an ant
            // becomes fitter than an antlion we assume it was cought by the antlion
            // and the antlion update goes to its position to build the trap)

            double [][] double_population = new double[N*2][dim];
            double [] double_fitness = new double[N*2];

            for (int i=0; i<N*2; i++){
                for (int j=0; j<dim; j++){
                    if (i<N){
                        double_population[i][j] = Sorted_antlions[i][j];
                    } else {
                        double_population[i][j] = ant_position[i-N][j];
                    }
                }
                if (i<N){
                    double_fitness[i] = sorted_antlion_fitness[i];
                } else {
                    double_fitness[i] = ants_fitness[i-N];
                }
            }

            for (int i=0; i<N*2-1; i++){
                for (int j=i+1; j<N*2; j++){
                    if (double_fitness[i]>double_fitness[j]){
                        double finess_temp = double_fitness[i];
                        double position_temp[] = new double[dim];
                        for (int k=0; k<dim; k++){
                            position_temp[k] = double_population[i][k];
                        }
                        double_fitness[i] = double_fitness[j];
                        for (int k=0; k<dim; k++){
                            double_population[i][k] = double_population[j][k];
                        }
                        double_fitness[j] = finess_temp;
                        for (int k=0; k<dim; k++){
                            double_population[j][k] = position_temp[k];
                        }
                    }
                }
            }

            for (int i=0; i<N; i++){
                antlions_fitness[i] = double_fitness[i];
                for (int j=0; j<dim; j++){
                    Sorted_antlions[i][j] = double_population[i][j];
                }
            }

            // Update the position of elite if any antlinons becomes fitter than it
            if (antlions_fitness[0] < Elite_antlion_fitness){
                Elite_antlion_fitness = antlions_fitness[0];
                for (int i=0; i<dim; i++){
                    Elite_antlion_position[i] = Sorted_antlions[0][i];
                }
            }

            // Keep the elite in the population
            antlions_fitness[0] = Elite_antlion_fitness;
            for (int i=0; i<dim; i++){
                Sorted_antlions[0][i] = Elite_antlion_position[i];
            }

            arrRandomBestVal[iter-1] = Elite_antlion_position;
            System.out.println("Iteration: "+(iter));
            System.out.println("Best score: "+Elite_antlion_fitness);

            F_min[iter-1] = Elite_antlion_fitness;
            double sumF = 0.0;
            for (int i=0; i<N; i++){
                sumF+= ff.func(Sorted_antlions[i]);
            }
            F_avg[iter-1] = sumF/N;
            X_1[iter-1] = Sorted_antlions[0][0];
            X_2[iter-1] = Sorted_antlions[0][1];

            iter++;
        }

        double[][] out = new double[2][dim];

        for (int i = 0; i < dim; i++){
            out[1][i] = Elite_antlion_position[i];
        }

        out[0][0] = Elite_antlion_fitness;
        return out;
    }

    public double getRes() throws IOException {
        ExcelUtils.fillForDrawFunctionToExcel(F_min, F_avg, X_1, X_2, 121, 269, 417, 566, orderOfF, maxIter);
        return Elite_antlion_fitness;
    }

    double nextRand(){
//        return 0.7;
//        position++;
//        return randomm[position-1];
        return Math.random();
    }
}
