package util;

import java.io.*;

public class SerializationUtil {
    public static byte[] serialize(Serializable object) throws IOException {
        ByteArrayOutputStream bos =  new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeObject(object);
            oos.flush();
            return bos.toByteArray();
        }
    }

    public static Object deserialize(byte[] array) throws IOException, ClassNotFoundException{
        ByteArrayInputStream bis = new ByteArrayInputStream(array);
        try(ObjectInputStream in = new ObjectInputStream(bis)){
            return in.readObject();
        }
    }
}
