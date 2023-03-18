import java.util.Scanner;

public class Main{
    public static int board[][] = new int[4][4];

    static void ranGen(){
        int ranRow[] = new int[2];
        for (int i = 0; i < ranRow.length; i++) {
            ranRow[i] = (int) Math.round(Math.random()*3);
        }
        int ranCol[] = new int[2];
        for (int i = 0; i < ranCol.length; i++) {
            ranCol[i] = (int) Math.round(Math.random()*3);
        }
        
        if (slotsEmpty() > 1){
            if (board[ranRow[0]][ranCol[0]] == 0 && board[ranRow[1]][ranCol[1]]==0){
                for (int i =0; i<2; i++){
                    board[ranRow[i]][ranCol[i]] = (int) (Math.round(Math.random()+1) *2);
                }
            }else{
                ranGen();
            } 
        }else{
            if (board[ranRow[0]][ranCol[0]] == 0){
                    board[ranRow[0]][ranCol[0]] = (int) (Math.round(Math.random()+1) *2);
            }else{
                ranGen();
            } 
        }
        
        // TODO: if only one blank is there it gives error coz of two random variables
    }

    static void wipe(){
        for (int i = 0; i<board[0].length; i++){
            for (int j = 0; j<board[i].length; j++){
                board[i][j] = 0;
            }
        }
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
            for(int j =0; j<board[0].length; j++){
                if (board[i][j] == 2048){
                    return 1;
                }else if(board[i][j] == 0){
                    zCount++;
                }
            }
        }
        if(zCount > 0 || isMergePoss('W') || isMergePoss('A') || isMergePoss('S') || isMergePoss('D')){
            return 0;
        }else{
            return -1;
        }
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

    static void merge(char direction){
        switch (direction) {
            case 'W':
            case 'S':
            for(int i=0; i<board.length; i++){
                int NSarrray[] = new int[4];
                for(int j=0; j<board.length;j++){
                    NSarrray[j] = board[j][i];
                }
                for (int k = 0; k<NSarrray.length-1; k++){
                    if (NSarrray[k] == NSarrray[k+1]){
                        NSarrray[k] = NSarrray[k] + NSarrray[k+1];
                        NSarrray[k+1] = 0;
                    }
                }
                for (int k =0; k <NSarrray.length; k++){
                    board[k][i] = NSarrray[k];
                }
            }

                
                break;
            case 'A':
            case 'D':
                for(int i=0; i<board.length; i++){
                    int WEarray[] = new int[4];
                    for(int j = 0; j<board.length;j++){
                        WEarray[j] = board[i][j];
                    }
                    for (int k = 0; k<WEarray.length-1; k++){
                        if (WEarray[k] == WEarray[k+1]){
                            WEarray[k] = WEarray[k] + WEarray[k+1];
                            WEarray[k+1] = 0;
                        }
                    }
                    for (int k =0; k <WEarray.length; k++){
                        board[i][k] = WEarray[k];
                    }
                }
                break;
        
            default:
                break;
        } 
    }
    
    static boolean isMergePoss(char direction){
        switch (direction) {
            case 'W':
            case 'S':
            for(int i=0; i<board.length; i++){
                int NSarrray[] = new int[4];
                for(int j=0; j<board.length;j++){
                    NSarrray[j] = board[j][i];
                }
                for (int k = 0; k<NSarrray.length-1; k++){
                    if (NSarrray[k] == NSarrray[k+1]){
                        return true;
                    }
                }
                for (int k =0; k <NSarrray.length; k++){
                    board[k][i] = NSarrray[k];
                }
            }

                
                break;
            case 'A':
            case 'D':
                for(int i=0; i<board.length; i++){
                    int WEarray[] = new int[4];
                    for(int j = 0; j<board.length;j++){
                        WEarray[j] = board[i][j];
                    }
                    for (int k = 0; k<WEarray.length-1; k++){
                        if (WEarray[k] == WEarray[k+1]){
                            return true;
                        }
                    }
                    for (int k =0; k <WEarray.length; k++){
                        board[i][k] = WEarray[k];
                    }
                }
                break;
        
            default:
                break;
        }
        return false;
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("2048");
        wipe();
        display(board);

        while (isGameOver() == 0){
            char inp = in.next().charAt(0);
            int[] v = new int[2];
            switch(inp){
                case 'W': v[0] =  0; v[1] = 1; break;
                case 'A': v[0] = -1; v[1] = 0; break;
                case 'S': v[0] =  0; v[1] = 1; break;
                case 'D': v[0] =  1; v[1] = 0; break;
            }
            compress(v);
            merge(inp);
            compress(v);
            ranGen();
            display(board);
        }
        if(isGameOver() == 1)System.out.println("You win");
        else System.out.println("You Lose");
        in.close();
    }
}