public class test {
    public static void main(String[] args) {
        String inputText = " 5";
        int x = 0;
        try {
            x = Integer.parseInt(inputText);
            x *= 2;
        } catch (Exception e) {
            x = 1;
        }
        x *= -1;
        System.out.println(x);
    }
}