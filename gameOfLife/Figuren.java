package gameOfLife;

public class Figuren {
  static public int[][] blinker =
      new int[][] {
          {0, 1, 0},
          {0, 1, 0},
          {0, 1, 0}
      };
  static public int[][] uhr =
      new int[][] {
          {0, 0, 1, 0, 0, 0},
          {0, 0, 1, 0, 1, 0},
          {0, 1, 0, 1, 0, 0},
          {0, 0, 0, 1, 0, 0}
      };
  static public int[][] oktagon =
      new int[][] {
          {0, 0, 0, 0, 0, 0, 0},
          {0, 0, 1, 0, 0, 1, 0},
          {0, 1, 0, 1, 1, 0, 1},
          {0, 0, 1, 0, 0, 1, 0},
          {0, 0, 1, 0, 0, 1, 0},
          {0, 1, 0, 1, 1, 0, 1},
          {0, 0, 1, 0, 0, 1, 0}
      };
  static public int[][] gleiter =
      new int[][] {
          {0, 1, 0},
          {0, 0, 1},
          {1, 1, 1}
      };
  static public int[][] lwss =
      new int[][] {
          {0, 1, 1, 1, 1},
          {1, 0, 0, 0, 1},
          {0, 0, 0, 0, 1},
          {1, 0, 0, 1, 0}
      };
  static public int[][] mwss =
      new int[][] {
          {0, 1, 1, 1, 1, 1},
          {1, 0, 0, 0, 0, 1},
          {0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 1, 0},
          {0, 0, 1, 0, 0, 0}
      };
}
