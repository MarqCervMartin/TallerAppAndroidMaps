package com.example.apprepartidor;
import org.jgap.*;
import org.jgap.impl.*;
import org.jgap.impl.salesman.Salesman;
public class Repartidor extends Salesman{
    public static final int CITIES = 10;
    private double[][] CITYARRAY = new double[][] {

            {19.776266, -99.119753}, {19.767437, -99.123009},
            {19.746360, -99.097878},
            {19.752176, -99.093930}, {19.753761, -99.099530}, {19.760044, -99.091653},
            {19.791929, -99.082839}, {19.827808, -99.078333}, {19.824172, -99.116228},
            {19.776283, -99.119924}
    };

    public double[][] getCITYARRAY() {
        return CITYARRAY;
    }

    public void setCITYARRAY(double[][] CITYARRAY) {
        this.CITYARRAY = CITYARRAY;
    }

    @Override
    public double distance(Gene a_from, Gene a_to) {
        //Formula de haversine
        IntegerGene geneB = (IntegerGene) a_to;
        IntegerGene geneA = (IntegerGene) a_from;
        //IntegerGene geneB = (IntegerGene) a_to;
        int a = geneA.intValue();
        int b = geneB.intValue();
        double x1 = CITYARRAY[a][0];
        //System.out.println("X1: "+x1);
        double y1 = CITYARRAY[a][1];
        double x2 = CITYARRAY[b][0];
        double y2 = CITYARRAY[b][1];
        //return Math.sqrt( (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

        //public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2)
        //double radioTierra = 3958.75;//en millas
        double radioTierra = 6371;//en kilómetros
        double dLat = Math.toRadians(x2 - x1);
        double dLng = Math.toRadians(y2 - y1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(x1)) * Math.cos(Math.toRadians(x2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;

        return distancia;
    }

    @Override
    public IChromosome createSampleChromosome(Object a_initial_data) {
        try {
            Gene[] genes = new Gene[CITIES];
            for (int i = 0; i < genes.length; i++) {
                genes[i] = new IntegerGene(getConfiguration(), 0, CITIES - 1);
                genes[i].setAllele(new Integer(i));
            }
            IChromosome sample = new Chromosome(getConfiguration(), genes);
            return sample;
        }
        catch (InvalidConfigurationException iex) {
            throw new IllegalStateException(iex.getMessage());
        }
    }
}
