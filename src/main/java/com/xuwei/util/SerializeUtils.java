
package com.xuwei.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerializeUtils {


	public static byte[] serializeObject(Object object) {
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream saos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(saos);
			oos.writeObject(object);
			oos.flush();
			return saos.toByteArray();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}finally{
			if(oos!=null){
				try {
					oos.close();
				} catch (IOException e) {
				}
			}
		}

	}


	public static Object deserializeObject(byte[] buf) {
		if(buf == null) {
			return null;
		}
		ObjectInputStream ois = null;
		try {
			Object object = null;
			ByteArrayInputStream sais = new ByteArrayInputStream(buf);
			ois = new ObjectInputStream(sais);
			object = ois.readObject();
			return object;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}finally{
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
