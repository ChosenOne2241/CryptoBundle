import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DES
{
	private static DES instance = new DES(); // Singleton.
	
	private DES() {}
	
	public static synchronized DES getInstance()
	{
		return instance;
	}

	public void encrypt(String filePath, String savePath, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException
	{
		FileInputStream f = new FileInputStream(filePath);
		FileOutputStream outputFile = new FileOutputStream(savePath);
		byte key[] = password.getBytes(); // Key has to be 8 bits for DES.
		SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
		Cipher encryption = Cipher.getInstance("DES");
		encryption.init(Cipher.ENCRYPT_MODE, secretKey);
		CipherOutputStream c = new CipherOutputStream(outputFile, encryption);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = f.read(buffer)) != -1)
		{
			c.write(buffer, 0, read);
		}
		f.close();
		c.close();
		outputFile.flush();
	}

	public void decrypt(String filePath, String savePath, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException
	{
		FileInputStream f = new FileInputStream(filePath);
		FileOutputStream outputFile = new FileOutputStream(savePath);
		byte key[] = password.getBytes(); // Key has to be 8 bits for DES.
		SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
		Cipher decryption = Cipher.getInstance("DES");
		decryption.init(Cipher.DECRYPT_MODE, secretKey);
		CipherOutputStream c = new CipherOutputStream(outputFile, decryption);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = f.read(buffer)) != -1)
		{
			c.write(buffer, 0, read);
		}
		f.close();
		c.close();
		outputFile.flush();
	}
}
