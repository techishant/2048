public class tmp {
    public static void main(String args[]){
        int arr[] = {0,4,0,4};

        for (int i = 0; i<arr.length-1; i++){
            if (arr[i] == arr[i+1]){
                arr[i] = arr[i] + arr[i+1];
                arr[i+1] = 0;
            }
        }

        for (int i: arr){
            System.out.println(i);
        }
    }
}
