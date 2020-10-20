import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * description
 *
 * @author xyx
 * @date 2020/10/20 15:44
 */
public class MyClassLoader extends ClassLoader {

    private String classPath;

    private String fileExtension;

    public MyClassLoader(String classPath, String fileExtension) {
        // 破坏双亲委派
        super(null);
        this.classPath = classPath;
        this.fileExtension = fileExtension;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] data = readBytes(name);
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }


    private byte[] readBytes(String name) throws IOException {

        String path = classPath + name + fileExtension;
        File classFile = new File(path);
        byte[] bytes;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             InputStream is = new FileInputStream(classFile)) {
            int length;
            byte[] readBytes = new byte[1024];
            while ((length = is.read(readBytes)) != -1) {
                for (int i = 0; i < length; i++) {
                    readBytes[i] = (byte) (255 - readBytes[i]);
                }
                bos.write(readBytes, 0, length);
            }
            bytes = bos.toByteArray();
        }
        return bytes;
    }


    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, InstantiationException {
        MyClassLoader cl = new MyClassLoader("Week_01/", ".xlass");
        Class<?> clazz = cl.loadClass("Hello");
        Object obj = clazz.newInstance();
        clazz.getDeclaredMethod("hello").invoke(obj);
    }

}
