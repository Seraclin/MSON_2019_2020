import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
public class SeamCarver {

    // create a seam carver object based on the given picture
    // x = columns, y = rows
    // (0, 0) is top left and (width -1, height -1) is the bottom right corner
    private Picture pic;
    private double[][] energyTO;
    private boolean isTransposed;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        pic = new Picture(picture);
        isTransposed = false;
    }

    /**
     * Returns the current picture.
     *
     * @return the current picture.
     */
    public Picture picture() {
        return new Picture(pic);
    }

    /**
     * Return the width of the current picture.
     *
     * @return the width of the current picture.
     */
    public int width() {
        return pic.width();
    }

    /**
     * Return the height of the current picture.
     *
     * @return the height of the current picture;
     */
    public int height() {
        return pic.height();
    }

    /**
     * Find the energy of a pixel at column x and row y.
     * Energy: √(Δ^2x(x,y)+Δ^2y(x,y)).
     *
     * @param x the column of the pixel.
     * @param y the row of the pixel.
     * @return the energy of the pixel.
     */
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x > width() - 1 || y > height() - 1) {
            throw new IllegalArgumentException("Out of bounds");
        }
        return energyFinder(x, y); // remember that x is columns and y is row (2D array is switched)
    }

    /**
     * Helper method to find the energy.
     * @param x this is the column of pixel
     * @param y this is the row of pixel
     * @return the energy at that pixel
     */
    private double energyFinder(int x, int y) {
        double energy = 0;
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) { // pixel along the border = 1000 energy
            return 1000;
        } else {
            // RGB (0, 255), this uses pic so x is col, and y is row
            Color left = pic.get(x - 1, y);
            Color right = pic.get(x + 1, y);
            Color top = pic.get(x, y - 1);
            Color bot = pic.get(x, y + 1);
            int rx = right.getRed() - left.getRed();
            int gx = right.getGreen() - left.getGreen();
            int bx = right.getBlue() - left.getBlue();
            int x2 = rx * rx + gx * gx + bx * bx;

            int ry = bot.getRed() - top.getRed();
            int gy = bot.getGreen() - top.getGreen();
            int by = bot.getBlue() - top.getBlue();
            int y2 = ry * ry + gy * gy + by * by;

            int total = x2 + y2;
            energy = Math.sqrt(total);
        }
        return energy;

    }
    /**
     * Finds the sequence of indices for horizontal seam.
     * @return an int[] of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {
        if (!isTransposed) {
            transpose();
        }
        return findVerticalSeam();
    }
    /**
     * Finds the sequences of indices for vertical seam.
     * @return an int[] of indices for vertical seam.
     */
    public int[] findVerticalSeam() {
        int[] seam = new int[height()];
        energyTO = new double[width()][height()];
        for (int y = 0; y < height(); y++) { // set to infinity
            for (int x = 0; x < width(); x++) {
                if (y == 0) {
                    energyTO[x][y] = 0; // top row should be zero
                } else {
                    energyTO[x][y] = Double.POSITIVE_INFINITY;
                }
            }
        }
        for (int y = 0; y < height(); y++) { // relax, if energyTo[x2][y2] > energyTo[x1][y1] + energy of (x2, y2)
            for (int x = 0; x < width(); x++) {
                if (y + 1 < height()) {
                    if (x - 1 >= 0) { // left
                        relax(x, y, x - 1, y + 1);
                    }
                    relax(x, y, x,y + 1); // middle
                    if (x + 1 < width()) { // right
                        relax(x, y, x + 1, y + 1);
                    }
                }
                // System.out.print(energyTO[x][y]+" ");
            }
            // System.out.println();
        }
        int minEnergyToCol = 0;
        for (int x = 0; x < width(); x++) { // find lowest energy value in last row
            if (energyTO[x][height() - 1] < energyTO[minEnergyToCol][height() - 1]) {
                minEnergyToCol = x;
            }
        }
        seam[height() - 1] = minEnergyToCol;
        int prevX = minEnergyToCol;
        for (int h = height() - 2; h >= 0; h--) {
            double left = Double.POSITIVE_INFINITY;
            double right = Double.POSITIVE_INFINITY;
            double mid = energyTO[minEnergyToCol][h];
            if (minEnergyToCol - 1 >= 0) {
                left = energyTO[minEnergyToCol - 1][h];
            }
            if (minEnergyToCol + 1 < width()) {
                right = energyTO[minEnergyToCol + 1][h];
            }
            if (left <= mid && left <= right) {
                // System.out.println("L");
                prevX = minEnergyToCol - 1;
            } else if (right < mid && right < left) {
                // System.out.println("R");
                prevX = minEnergyToCol + 1;
            } else {
                // System.out.println("M");
                prevX = minEnergyToCol;
            }
            seam[h] = prevX;
            minEnergyToCol = prevX;
        }
        if (isTransposed) { // if rotated horizontally (not vertical)
            transpose();
        }
        return seam;
    }
    /**
     * Remove a horizontal seam from the current picture.
     * @param seam the horizontal seam to be removed
     */
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || height() <= 1 || !validSeam(seam, false)) {
            throw new IllegalArgumentException();
        }
        if (!isTransposed) {
            transpose();
        }
        removeVerticalSeam(seam);
    }

    /**
     * Remove vertical seam from current picture.
     * @param seam the vertical seam ot be removed.
     */
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length < height() || width() <= 1 || !validSeam(seam, true)) {
            throw new IllegalArgumentException();
        }
        Picture smallerPic  = new Picture(width() - 1, height());
        for (int y = 0; y < height(); y++) { // set to infinity
            int count = 0;
            for (int x = 0; x < width(); x++) {
                if (seam[y] != x) { // skipping any x values that are in the seam parameter
                    smallerPic.setRGB(count, y, pic.getRGB(x, y));
                    count++;
                }
            }
        }
        pic = smallerPic;
        if (isTransposed) { // if rotated horizontally (not vertical)
            transpose();
        }
    }
    /**
     * Helper method to check if the seam's points are in the bounds.
     * A seam is not valid when array has the wrong length, if an entry outside range,
     * or two adjacent entries differ by more than 1.
     * @param seam the seam to check
     * @param isVertical whether the seam is vertical if true, or false not vertical.
     * @return whether the seam is valid.
     */
    private boolean validSeam(int[] seam, boolean isVertical) {
        for (int i = 0; i < seam.length; i++) {
            if (isVertical) {
                if (seam.length != height()) {
                    return false;
                }
                if (i - 1 >= 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                    return false;
                }
                if (seam[i] < 0 || seam[i] > width() - 1) {
                    return  false;
                }
            } else {
                if (seam.length != width()) {
                    return false;
                }
                if (i - 1 >= 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                    return false;
                }
                if (seam[i] < 0 || seam[i] > height() - 1) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Returns the input picture rotated 90 degrees to the right.
     */
    private void transpose() {
        // x = columns, y = rows
        Picture transPic = new Picture(height(), width());
        for (int i = 0; i < width(); i++) {
            for (int k = 0; k < height(); k++) {
                transPic.setRGB(k, i, pic.getRGB(i, k));
            }
        }
        pic = transPic;
        isTransposed = !isTransposed;
    }
    /**
     * Helper method to relax a pixel.
     * @param x1 the first pixel row
     * @param y1 the first pixel col
     * @param x2 the second pixel row
     * @param y2 the second pixel col
     */
    private void relax (int x1, int y1, int x2, int y2) {
        double energy2 = energy(x2, y2); // I reversed everything except energy
        if (energyTO[x2][y2] > energyTO[x1][y1] + energy2) {
            energyTO[x2][y2] = energyTO[x1][y1] + energy2;
        }
    }
}