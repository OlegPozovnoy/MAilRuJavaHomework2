import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String args[]){
        Map<Integer,String> map = new HashMap<>();
        System.out.println(map.remove(null));
        System.out.println(map.put(null,"1"));
        System.out.println(map.size());
        System.out.println(map.get(null));
    }
}
