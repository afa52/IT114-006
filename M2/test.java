import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<String>();
        words.add("Hello");
        words.add("Hi");
        words.add("Hey there");
        words.add("Hello there");
        for(int i = 0; i < words.size();i++){
            System.out.println(String.format("Index %s is %s", i, words.get(i)));
        }
    }
}