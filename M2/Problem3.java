import java.util.Arrays;
public class Problem3 {
    public static void main(String[] args) {
        //Don't edit anything here
        Integer[] a1 = new Integer[]{-1, -2, -3, -4, -5, -6, -7, -8, -9, -10};
        Integer[] a2 = new Integer[]{-1, 1, -2, 2, 3, -3, -4, 5};
        Double[] a3 = new Double[]{-0.01, -0.0001, -.15};
        String[] a4 = new String[]{"-1", "2", "-3", "4", "-5", "5", "-6", "6", "-7", "7"};
        
        bePositive(a1);
        bePositive(a2);
        bePositive(a3);
        bePositive(a4);
    }
    static <T> void bePositive(T[] arr){
        System.out.println("Processing Array:" + Arrays.toString(arr));
        //your code should set the indexes of this array
        Object[] output = new Object[arr.length];
        //TODO convert each value to positive
        //set the result to the proper index of the output array
        //hint: don't forget to handle the data types properly
        /***************************************************************************
         * afa52
         * 2/5/2023
         * The code implemented here is a for-loop that iterates thru the array and 
         * checks if each object is an instance of the data type listed.
         * The first if statement checks if the object in the current iteration is 
         * an instance of an integer, the second is an instance of a double, and the
         * third is an instance of String. The instanceof keyword compares the instance
         * (object) with type, the return value is either T/F.
         * If the current element in iteration follows true to one of the conditions, 
         * then the element would be converted to a postitive number using Math.abs()
         * That data that comes in iteration is also converted to an object using a 
         * wrapper class (java.lang.Integer/Double/Double.parseDouble), parseDouble 
         * returns a new double initialized to the value represented by the specified 
         * String, and then that object is assigned to an index in the output array. 
         *****************************************************************************/
        for(int i=0; i < arr.length; i++){
            if (arr[i] instanceof Integer){
                output[i] = Math.abs((Integer)arr[i]);
            }
                else if(arr[i] instanceof Double){
                output[i] = Math.abs((Double)arr[i]);
            }
                else if(arr[i] instanceof String){
                output[i] = Math.abs(Double.parseDouble((String)arr[i]));
                }
            }
        //end edit section

        StringBuilder sb = new StringBuilder();
        for(Object i : output){
            if(sb.length() > 0){
                sb.append(",");
            }
            sb.append(String.format("%s (%s)", i, i.getClass().getSimpleName().substring(0,1)));
        }
        System.out.println("Result: " + sb.toString());
    }
}
