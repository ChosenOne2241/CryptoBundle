import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	private JTabbedPane tabbedPane;
	private GenerateKeyPanel generateKeyPanel;
	private RSAEncryptPanel rsaEncryptPanel;
	private RSADecryptPanel rsaDecryptPanel;
	private DESEncryptPanel desEncryptPanel;
	private DESDecryptPanel desDecryptPanel;

	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 400;

	public MainFrame()
	{
		setTitle("DES & RSA");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		tabbedPane = new JTabbedPane();
		generateKeyPanel = new GenerateKeyPanel();
		rsaEncryptPanel = new RSAEncryptPanel();
		rsaDecryptPanel = new RSADecryptPanel();
		desEncryptPanel = new DESEncryptPanel();
		desDecryptPanel = new DESDecryptPanel();

		tabbedPane.addTab("RSA Key Generation", generateKeyPanel);
		tabbedPane.addTab("RSA Encryption", rsaEncryptPanel);
		tabbedPane.addTab("RSA Decryption", rsaDecryptPanel);
		tabbedPane.addTab("DES Encryption", desEncryptPanel);
		tabbedPane.addTab("DES Decryption", desDecryptPanel);

		add(tabbedPane, BorderLayout.CENTER);
	}
}
