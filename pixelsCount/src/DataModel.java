

public class DataModel {

    private int strand;
    private int t0;
    private double a0;
    private double h0;
    private double dy;
    private double Po;
    private double Py;
    private double Kny;
    private double Lyf;
    private double R0;
    private double Ry;
    private int G;

    public DataModel() {
    }

    public DataModel(int strand, int t0, double a0, double h0, double R0, double Ry, double dy,
                     double Po, double Py, double Kny, double Lyf, int G) {
        this.strand = strand;
        this.t0 = t0;
        this.a0 = a0;
        this.h0 = h0;
        this.R0 = R0;
        this.Ry = Ry;
        this.dy = dy;
        this.Po = Po;
        this.Py = Py;
        this.Kny = Kny;
        this.Lyf = Lyf;
        this.G = G;
    }

    public int getStrand() {
        return strand;
    }

    public void setStrand(int strand) {
        this.strand = strand;
    }

    public int getT0() {
        return t0;
    }

    public void setT0(int t0) {
        this.t0 = t0;
    }

    public double getA0() {
        return a0;
    }

    public void setA0(double a0) {
        this.a0 = a0;
    }

    public double getH0() {
        return h0;
    }

    public void setH0(double h0) {
        this.h0 = h0;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getKny() {
        return Kny;
    }

    public void setKny(double kny) {
        Kny = kny;
    }

    public double getLyf() {
        return Lyf;
    }

    public void setLyf(double Lyf) {
        this.Lyf = Lyf;

    }

    public double getR0() {
        return R0;
    }

    public void setR0(double r0) {
        R0 = r0;
    }

    public double getRy() {
        return Ry;
    }

    public void setRy(double ry) {
        Ry = ry;
    }

    public double getPo() {
        return Po;
    }

    public void setPo(double po) {
        Po = po;
    }

    public double getPy() {
        return Py;
    }

    public void setPy(double py) {
        Py = py;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }
}
