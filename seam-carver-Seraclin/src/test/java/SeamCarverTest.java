import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Picture;

public class SeamCarverTest {

  SeamCarver sc6x5;
  Picture picture;

  @Before
  public void setup() throws Exception {
    picture = new Picture("seam-test-files/6x5.png");
    sc6x5 = new SeamCarver(picture);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSeamCarver() {
    SeamCarver test = new SeamCarver(null);
  }

  @Test
  public void testPicture() {
    Picture picture = new Picture("seam-test-files/6x5.png");
    assertEquals(sc6x5.picture(), picture);
  }
  @Test
  public void testWidth() {
    assertEquals(sc6x5.width(), 6);
  }

  @Test
  public void testWidthSmall() {
    Picture picture = new Picture("seam-test-files/1x1.png");
    SeamCarver sc6x5 = new SeamCarver(picture);
    assertEquals(sc6x5.width(), 1);
  }
  @Test
  public void testWidthBig() {
    Picture picture = new Picture("seam-test-files/12x10.png");
    SeamCarver sc6x5 = new SeamCarver(picture);
    assertEquals(sc6x5.width(), 12);
  }

  @Test
  public void testHeight() {
    assertEquals(sc6x5.height(), 5);
  }

  @Test
  public void testHeightSmall() {
    Picture picture = new Picture("seam-test-files/1x1.png");
    SeamCarver sc6x5 = new SeamCarver(picture);
    assertEquals(sc6x5.width(), 1);
  }
  @Test
  public void testHeightBig() {
    Picture picture = new Picture("seam-test-files/12x10.png");
    SeamCarver sc6x5 = new SeamCarver(picture);
    assertEquals(sc6x5.height(), 10);
  }
  @Test
  public void testEnergyTopBorder() {
    for(int i = 0; i < sc6x5.width(); i++) {
      assertEquals(1000, sc6x5.energy(i, 0), 0.001);
    }
  }
  @Test
  public void testEnergyLeftBorder() {
    for(int i = 0; i < sc6x5.height(); i++) {
      assertEquals(1000, sc6x5.energy(0, i), 0.001);
    }
  }
  @Test
  public void testEnergyRightBorder() {
    for(int i = 0; i < sc6x5.height(); i++) {
      assertEquals(1000, sc6x5.energy(sc6x5.width() - 1, i), 0.001);
    }
  }
  @Test
  public void testEnergyBotBorder() {
    for(int i = 0; i < sc6x5.width(); i++) {
      assertEquals(1000, sc6x5.energy(i, sc6x5.height() - 1), 0.001);
    }
  }

  @Test
  public void testEnergy6x5() {
    assertEquals(237.35, sc6x5.energy(1, 1), 0.01);
  }

  @Test
  public void testEnergy12x10() {
    Picture picture = new Picture("seam-test-files/12x10.png");
    SeamCarver sc6x5 = new SeamCarver(picture);
    assertEquals(343.91, sc6x5.energy(2, 3), 0.01);
  }

  @Test
  public void testEnergy3x4() {
    Picture picture = new Picture("seam-test-files/3x4.png");
    SeamCarver sc6x5 = new SeamCarver(picture);
    assertEquals(228.53, sc6x5.energy(1, 1), 0.01);
  }

  @Test
  public void testFindVerticalSeam() {
    int[] expected = {3, 4, 3, 2, 1};
    int[] actual = sc6x5.findVerticalSeam();
    for(int i = 0; i < sc6x5.height(); i++) {
      assertEquals(expected[i], actual[i]);
    }
  }
  @Test
  public void testFindVerticalSeamSmall() {
    Picture picture = new Picture("seam-test-files/3x4.png");
    SeamCarver sc3x4 = new SeamCarver(picture);
    int[] expected = {0, 1, 1, 0};
    int[] actual = sc3x4.findVerticalSeam();
    for(int i = 0; i < sc3x4.height(); i++) {
      if (expected[i] != actual[i]) {
        System.out.print("Problem Row" + i);
      }
      assertEquals(expected[i], actual[i]);
    }
  }
  @Test
  public void testFindVerticalSeamBig() {
    Picture picture = new Picture("seam-test-files/12x10.png");
    SeamCarver sc3x4 = new SeamCarver(picture);
    int[] expected = {6, 7, 7, 6, 6, 7, 7, 7, 8, 7};
    int[] actual = sc3x4.findVerticalSeam();
    for(int i = 0; i < sc3x4.height(); i++) {
      if (expected[i] != actual[i]) {
        System.out.print("Problem Row" + i);
      }
      assertEquals(expected[i], actual[i]);
    }
  }

  @Test
  public void testFindHorizontalSeam() {
    int[] expected = {1, 2, 1, 2, 1, 0};
    int[] actual = sc6x5.findHorizontalSeam();
    for(int i = 0; i < sc6x5.width(); i++) {
      System.out.println(actual[i]);
      assertEquals(expected[i], actual[i]);
    }
  }
  @Test
  public void testFindHorizontalSeamSmall() {
    Picture picture = new Picture("seam-test-files/3x4.png");
    SeamCarver sc3x4 = new SeamCarver(picture);
    int[] expected = {1, 2, 1};
    int[] actual = sc3x4.findHorizontalSeam();
    for(int i = 0; i < sc3x4.width(); i++) {
      if (expected[i] != actual[i]) {
        System.out.print("Problem Row" + i);
      }
      assertEquals(expected[i], actual[i]);
    }
  }
  @Test
  public void testFindHorizontalSeamBig() {
    Picture picture = new Picture("seam-test-files/12x10.png");
    SeamCarver sc3x4 = new SeamCarver(picture);
    int[] expected = { 7, 8, 7, 8, 7, 6, 5, 6, 6, 5, 4, 3};
    int[] actual = sc3x4.findHorizontalSeam();
    for(int i = 0; i < sc3x4.width(); i++) {
      if (expected[i] != actual[i]) {
        System.out.print("Problem Row" + i);
      }
      assertEquals(expected[i], actual[i]);
    }
  }
  @Test
  public void testRemoveVerticalSeamCheckPixelColors() {
    Picture original = sc6x5.picture();
    int[] seam = sc6x5.findVerticalSeam();
    sc6x5.removeVerticalSeam(seam);
    assertEquals("The width should decrease by 1", 5, sc6x5.width());
    for(int i = 0; i < sc6x5.height(); i++) {
      if(seam[i] != sc6x5.width()) {
        assertEquals(original.get(seam[i] + 1, i), sc6x5.picture().get(seam[i], i));
      }
      else {
        assertEquals("If removed last column, last column should be previous pixel",
            original.get(seam[i] - 1, i), sc6x5.picture().get(seam[i], i));
      }
    }

  }
  @Test
  public void testRemoveVerticalSeamCheckPixelColors3x4() {
    Picture picture = new Picture("seam-test-files/3x4.png");
    SeamCarver sc3x4 = new SeamCarver(picture);
    int[] seam = sc3x4.findVerticalSeam();
    sc3x4.removeVerticalSeam(seam);
    assertEquals("The width should decrease by 1", 2, sc3x4.width());
    for(int i = 0; i < sc3x4.height(); i++) {
      if(seam[i] != sc3x4.width()) {
        assertEquals(picture.get(seam[i] + 1, i), sc3x4.picture().get(seam[i], i));
      }
      else {
        assertEquals("If removed last column, last column should be previous pixel",
                picture.get(seam[i] - 1, i), sc3x4.picture().get(seam[i], i));
      }
    }

  }

  //TODO: write testRemoveVerticalSeamCheckPixelColors test cases for two other input files (one small, one large)

  @Test
  public void testRemoveVerticalSeamCheckPixelEnergies() {
    Picture picture = new Picture("seam-test-files/6x5.png");
    SeamCarver sc6x5RemovedSeam = new SeamCarver(picture);
    int[] seam = sc6x5.findVerticalSeam();
    sc6x5RemovedSeam.removeVerticalSeam(seam);
    assertEquals("The width should decrease by 1", 5, sc6x5RemovedSeam.width());
    for(int i = 0; i < sc6x5.height(); i++) {
      if(seam[i] != 0 || seam[i] != sc6x5.width()) {
        // the energy should change after removing the seam
        assertNotEquals(sc6x5.energy(seam[i], i), sc6x5RemovedSeam.energy(seam[i], i), 0.01);
      }
      else {
        assertEquals("If removed border pixel, set next pixel energy to 1000", 1000, sc6x5.picture().get(seam[i], i));
      }
    }
  }

  @Test
  public void testRemoveHorizontalSeamCheckPixelColors() {
    //TODO write testRemoveHoriztonalCheckPixelColors using sc6x5
    fail("Not yet implemented");
  }
  
  @Test
  public void testRemoveHorizontalSeamCheckEnergies() {
    //TODO write testRemoveHoriztonalCheckEnergies using sc6x5
    fail("Not yet implemented");
  }

  //TODO: write testRemoveHorizontalSeam test cases for two other input files (one small, one large)

}
