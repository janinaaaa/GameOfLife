package gameOfLife;

public enum Directions {

  LEFT(0,-1),UP(-1,0),RIGHT(0,1),DOWN(1,0),LEFTUP(-1,-1),RIGHTUP(-1,1),LEFTDOWN(1,-1),RIGHTDOWN(1,1);

  int i;
  int j;

  Directions(int i, int j){
    this.i = i;
    this.j = j;
  }
}
