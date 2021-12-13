import javax.sound.midi.Soundbank;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Text {


    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<?> c1 = Class.forName("Student");
        // Constructor<?> constructor = c1.getConstructor(String.class,int.class);
        // Object object = constructor.newInstance("林青霞",30);
        // System.out.println(object);
        // // Constructor<?> constructor1 = c1.getConstructor(String.class);
        // Constructor<?> constructor1 = c1.getDeclaredConstructor(String.class);
        // System.out.println(constructor1);
        // constructor1.setAccessible(true);
        // Object o = constructor1.newInstance("王祖贤");
        // System.out.println(o);
        Field name = c1.getDeclaredField("name");
        Constructor<?> constructor = c1.getConstructor();
        // constructor.setAccessible(true);
        Object o = constructor.newInstance();
        name.setAccessible(true);
        name.set(o,"张曼玉");
        System.out.println(o);



    }
}



