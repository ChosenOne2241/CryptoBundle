import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class RSA
{
	private static final int RSA_KEYSIZE = 1024; // The length of key is 1024-bit.

	private static RSA instance = new RSA(); // Singleton.

	private RSA() {}

	public static synchronized RSA getInstance()
	{
		return instance;
	}

	public void generateKeyPair(String savePath) throws NoSuchAlgorithmException, FileNotFoundException, IOException
	{
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = new SecureRandom();
		keygen.initialize(RSA_KEYSIZE, random);
		KeyPair keyPair = keygen.generateKeyPair();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savePath + File.separator + "Public.key"));
		out.writeObject(keyPair.getPublic());
		out.close();
		out = new ObjectOutputStream(new FileOutputStream(savePath + File.separator + "Private.key"));
		out.writeObject(keyPair.getPrivate());
		out.close();
	}

    public void encrypt(String filePath, String savePath, String keyFilePath) throws FileNotFoundException, IOException, ClassNotFoundException, GeneralSecurityException
    {
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		SecureRandom random = new SecureRandom();
		keygen.init(random);
		SecretKey key = keygen.generateKey();

		ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(keyFilePath));
		Key publicKey = (Key) keyIn.readObject();
		keyIn.close();

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.WRAP_MODE, publicKey);
		byte[] wrappedKey = cipher.wrap(key);
		DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
		out.writeInt(wrappedKey.length);
		out.write(wrappedKey);

		InputStream in = new FileInputStream(filePath);
		cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		crypt(in, out, cipher);
		in.close();
		out.close();
	}

	public void decrypt(String filePath, String savePath, String keyFilePath) throws IOException, ClassNotFoundException, GeneralSecurityException
	{
		DataInputStream in = new DataInputStream(new FileInputStream(filePath));
		int length = in.readInt();
		byte[] wrappedKey = new byte[length];
		in.read(wrappedKey, 0, length);

		ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(keyFilePath));
		Key privateKey = (Key) keyIn.readObject();
		keyIn.close();

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.UNWRAP_MODE, privateKey);
		Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);

		OutputStream out = new FileOutputStream(savePath);
		cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);

		crypt(in, out, cipher);
		in.close();
		out.close();
	}

	public void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException
	{
		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);
		byte[] inBytes = new byte[blockSize];
		byte[] outBytes = new byte[outputSize];

		int inLength = 0;
		boolean next = true;
		while (next)
		{
			inLength = in.read(inBytes);
			if (inLength == blockSize)
			{
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			}
			else
			{
				next = false;
			}
		}
		if (inLength > 0)
		{
			outBytes = cipher.doFinal(inBytes, 0, inLength);
		}
		else
		{
			outBytes = cipher.doFinal();
		}
		out.write(outBytes);
	}
}
