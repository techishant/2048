//import java.util.Scanner;
Main g = new Main();
char inp;

int gameOverStatus = 0;

void setup(){
  size(600,600);
  
  g.wipe();
}

void keyPressed(){
  if(key == 'W') inp = 'W';
  if(key == 'A') inp = 'A';
  if(key == 'S') inp = 'S';
  if(key == 'D') inp = 'D';
}

void draw(){
  background(0);
  g.display();
  //while (gameOverStatus != 0){
      int[] v = new int[2];
      
      switch(inp){
          case 'W': v[0] =  0; v[1] = -1; break;
          case 'A': v[0] = -1; v[1] =  0; break;
          case 'S': v[0] =  0; v[1] =  1; break;
          case 'D': v[0] =  1; v[1] =  0; break;
          //default: println("oops");
      }
      if(inp != '0'){
      g.compress(v);
      g.merge(v);
      g.compress(v);
      g.ranGen();
      gameOverStatus = g.isGameOver();
      inp = '0';  
    }
      g.display();
      
  //}
  if(gameOverStatus == 1)System.out.println("You win");
  else if(gameOverStatus == -1) System.out.println("You Lose");
  //noLoop();
  
}
