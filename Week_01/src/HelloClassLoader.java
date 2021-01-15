import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {
  public static void main(String[] args) throws Exception {
//      Hello hello = new Hello();
//      hello.hello();

      Class clazz  =  new HelloClassLoader().findClass("Hello.xlass");
      Method method = clazz.getMethod("hello");
      method.invoke(clazz.newInstance());
  }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[2048];
        try (FileInputStream fileInputStream = new FileInputStream(new File(name))) {
            fileInputStream.read(bytes);
            for(int i = 0; i< bytes.length;i++){
                bytes[i] = (byte) (255 - bytes[i]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.defineClass("Hello",bytes,0, bytes.length);
    }
}
