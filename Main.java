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

    static boolean isCompressible(int arr[], char mode){
        boolean zSeries = true;
        switch (mode) {
            case 'W':
                for(int i = arr.length-1; i >=0; i--){
                    if(zSeries && arr[i] !=0){
                        zSeries = false;
                    }
                    if ((zSeries == false) && (arr[i] == 0)) return true;
                }
                break;
            case 'S':
                for(int i = 0; i <arr.length; i++){
                    if(zSeries && arr[i] !=0){
                        zSeries = false;
                    }
                    if ((zSeries == false) && (arr[i] == 0)) return true;
                }
                break;
            case 'A':
                for(int i = arr.length-1; i >=0; i--){
                    if(zSeries && arr[i] !=0){
                        zSeries = false;
                    }
                    if ((zSeries == false) && (arr[i] == 0)) return true;
                }
                break;
            case 'D':
                for(int i = 0; i <arr.length; i++){
                    if(zSeries && arr[i] !=0){
                        zSeries = false;
                    }
                    if ((zSeries == false) && (arr[i] == 0)) return true;
                }
                break;
        
            default:
                break;
        }
        
        return false;
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

    static void compress(char direction){
        switch (direction) {
            case 'W':
                for (int i = 0; i < board[0].length; i++){
                    int NSarrray[] = {board[0][i],board[1][i],board[2][i],board[3][i]};
                    while (isCompressible(NSarrray, 'W')) {
                        int prevDigit = 7; // initialising with any random no. except 0
                        for (int j = 0; j <NSarrray.length; j++){
                            if (prevDigit == 0){
                                NSarrray[j-1] = NSarrray[j];
                                NSarrray[j] = 0;
                            }
                            prevDigit = NSarrray[j];
                        }
                    }

                    for (int k =0; k <NSarrray.length; k++){
                        board[k][i] = NSarrray[k];
                    }
                }
                break;
            case 'S':
            for (int i = 0; i < board[0].length; i++){
                int NSarrray[] = {board[0][i],board[1][i],board[2][i],board[3][i]};
                while (isCompressible(NSarrray, 'S')) {
                    for (int j = 0; j < board[0].length-1; j++){
                        if (NSarrray[j+1] == 0){
                            NSarrray[j+1] = NSarrray[j];
                            NSarrray[j] = 0;
                        }
                    }
                }

                for (int k =0; k <NSarrray.length; k++){
                    board[k][i] = NSarrray[k];
                }
            }
                break;
            case 'A':
                for (int i = 0; i < board[0].length; i++){
                    while (isCompressible(board[i],'A')){
                        int prevDigit = 7; // initialising with any random no. except 0
                        for (int j = 0; j <board[0].length; j++){
                            if (prevDigit == 0){
                                board[i][j-1] = board[i][j];
                                board[i][j] = 0;
                            }
                            prevDigit = board[i][j];
                        }
                    }
                }
                break;
            case 'D':
                for (int i = 0; i < board[0].length; i++){ 
                    while (isCompressible(board[i],'D')){
                        for (int j = 0; j < board[0].length-1; j++){
                            if (board[i][j+1] == 0){
                                board[i][j+1] = board[i][j];
                                board[i][j] = 0;
                            }
                        }
                    }
                }
                break;
            default:
                break;
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
            compress(inp);
            merge(inp);
            compress(inp);
            ranGen();
            display(board);
        }
        if(isGameOver() == 1)System.out.println("You win");
        else System.out.println("You Lose");
        
    }

    
}