import java.util.Scanner;

public class Main{
    public static int board[][] = new int[4][4];
    static int[] n = {0,1};
    static int[] e = {1,0};
    static int[] s = {0, -1};
    static int[] w = {-1,0};

    static void ranGen(){
        if(slotsEmpty()==0) return;
        int[][] possibleGenerations = new int[16][2];
        int k = 0;
        for(int y = 0; y<board.length; y++){
            for(int x = 0; x<board[y].length; x++){
                if(board[x][y] == 0) {
                    possibleGenerations[k][0] = x;
                    possibleGenerations[k][1] = y;
                    k++;
                }
            }
        }
        
        int randomPos = (int) (Math.random()*(k+1));
        int randomValues[] = {2,4};
        board[possibleGenerations[randomPos][0]][possibleGenerations[randomPos][1]] = randomValues[(int)(Math.random()*2)];
        
    }

    static void wipe(){
        for (int i = 0; i<board[0].length; i++){
            for (int j = 0; j<board[i].length; j++){
                board[i][j] = 0;
            }
        }
        ranGen();
        ranGen();
    }

    static void display(int board[][]){
        for (int i = 0; i<board[0].length; i++){
            for (int j = 0; j<board[i].length; j++){
                System.out.print(board[i][j] + "    ");
            }
            System.out.print("\n\n");
        }
    }
    
    static int slotsEmpty(){
        int count = 0;
        for(int i = 0; i<board.length; i++){
            for(int j =0; j<board[0].length; j++){
                if(board[i][j] == 0) count++;
            }
        }
        return count;
    }

    static int isGameOver(){
        int zCount = 0;
        for(int i =0; i<board.length; i++){
            for(int j =0; j<board[i].length; j++){
                if (board[j][i] == 2048){
                    return 1;
                }
                if(board[j][i] == 0){
                    zCount++;
                }
            }
        }
        if(zCount == 0 && !isMergePoss()){
            return -1;
        }
        return 0; // ongoing
    }

    static void compress(int[] v){
        boolean swapped = true; // to displace again and again until all the values are displace completely
        int startX, startY, endX, endY;
        // calculating the starting and ending value for the loop to iterate
        if(v[0] < 0 || v[1] < 0){
            startX = Math.abs(v[0]);
            startY = Math.abs(v[1]);
            endY = board.length;
            endX = board[0].length;
        }else{
            startX = 0;
            startY = 0;
            endY = board.length-v[1];
            endX = board[0].length-v[0];
        }
        while(swapped){
            swapped = false;
            for(int y = startY; y<endY; y++){
                // recalculating the ending values for the arrays that are not perfect( i mean have different lengths)
                if(v[0] < 0 || v[1] < 0){
                    endY = board.length;
                    endX = board[y].length;
                }else{
                    endY = board.length-v[1];
                    endX = board[y].length-v[0];
                }
                for(int x = startX; x<endX; x++){
                    if(board[y][x] > 0 && board[y+v[1]][x+v[0]] == 0){
                        board[y+v[1]][x+v[0]] = board[y][x];
                        board[y][x] = 0;
                        swapped  = true;
                    }
                }
            }
        }
    }

    static void merge(int[] v){
        boolean swapped = true; // to displace again and again until all the values are displace completely
        int startX, startY, endX, endY;
        // calculating the starting and ending value for the loop to iterate
        if(v[0] < 0 || v[1] < 0){
            startX = Math.abs(v[0]);
            startY = Math.abs(v[1]);
            endY = board.length;
            endX = board[0].length;
        }else{
            startX = 0;
            startY = 0;
            endY = board.length-v[1];
            endX = board[0].length-v[0];
        }
        while(swapped){
            swapped = false;
            for(int y = startY; y<endY; y++){
                // recalculating the ending values for the arrays that are not perfect( i mean have different lengths)
                if(v[0] < 0 || v[1] < 0){
                    endY = board.length;
                    endX = board[y].length;
                }else{
                    endY = board.length-v[1];
                    endX = board[y].length-v[0];
                }
                for(int x = startX; x<endX; x++){
                    if(board[y][x] == board[y+v[1]][x+v[0]] && board[y][x] != 0){
                        board[y][x] = board[y+v[1]][x+v[0]] + board[y][x];
                        board[y+v[1]][x+v[0]] = 0;
                        swapped  = true;
                    }
                }
            }
        }
    }
    
    static boolean isMergePoss(){
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                int[][] valIndex = {{j+n[0], i+n[1]}, {j+s[0], i+s[1]}, {j+e[0], i+e[1]}, {j+w[0], i+w[1]}};
                for(int k = 0; k<valIndex.length; k++){
                    if(valIndex[k][0]>=0 && valIndex[k][1]>=0){
                    if(valIndex[k][0]<4 && valIndex[k][1]<4){
                        if(board[j][i] == board[valIndex[k][0]][valIndex[k][1]] && board[j][i] != 0) return true;
                    }
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("2048");
        wipe();
        display(board);
        int gameOverStatus = 0;
        while (gameOverStatus == 0){
            char inp = in.next().charAt(0);
            int[] v = new int[2];
            switch(inp){
                case 'W': v[0] =  0; v[1] = -1; break;
                case 'A': v[0] = -1; v[1] =  0; break;
                case 'S': v[0] =  0; v[1] =  1; break;
                case 'D': v[0] =  1; v[1] =  0; break;
            }
            compress(v);
            merge(v);
            compress(v);
            ranGen();
            display(board);
            
            gameOverStatus = isGameOver();
        }
        if(gameOverStatus == 1)System.out.println("You win");
        else if(gameOverStatus == -1) System.out.println("You Lose");
        in.close();
    }
}