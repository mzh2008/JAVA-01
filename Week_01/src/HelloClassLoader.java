import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {
  public static void main(String[] args) throws Exception {


      Class clazz  =  new HelloClassLoader().findClass("Hello.xlass");
      Method method = clazz.getMethod("hello");
      method.invoke(clazz.newInstance());

  }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//    1:
//        byte[] bytes = null;
//        try (FileInputStream fileInputStream = new FileInputStream(new File(name))) {
//            bytes = IOUtils.toByteArray(fileInputStream);
//            for(int i = 0; i< bytes.length;i++){
//                bytes[i] = (byte) ((byte)255 - bytes[i]);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        // 2:
        byte[] bytes ;
        try (FileInputStream fileInputStream = new FileInputStream(new File(name));
             ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
            int bt;
            while((bt = fileInputStream.read())!=-1){
                bos.write((byte)255-(byte)bt);
            }
            bytes = bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return super.defineClass("Hello",bytes,0, bytes.length);
    }
}
