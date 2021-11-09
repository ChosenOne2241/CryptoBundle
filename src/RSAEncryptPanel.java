import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class RSAEncryptPanel extends JPanel
{
	private JPanel filePanel;
	private JPanel keyPanel;
	private JTextField fileTextField;
	private JTextField keyTextField;
	private JButton encryptButton;
	private JButton fileBrowseButton;
	private JButton keyBrowseButton;
	private JFileChooser fileChooser = new JFileChooser();
	private String filePath;
	private String keyFilePath;
	private String savePath;

	public RSAEncryptPanel()
	{
		filePanel = new JPanel();
		JLabel fileLabel = new JLabel("File Directory: ");
		filePanel.add(fileLabel, BorderLayout.WEST);
		fileTextField = new JTextField(16);
		filePanel.add(fileTextField, BorderLayout.CENTER);
		fileBrowseButton = new JButton("Browse...");
		fileBrowseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fileChooser.resetChoosableFileFilters();
				fileChooser.setAcceptAllFileFilterUsed(true);
				int result = fileChooser.showOpenDialog(RSAEncryptPanel.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					filePath = fileChooser.getSelectedFile().getPath();
					fileTextField.setText(filePath);
				}
			}
		});

		Border etchedBorder = BorderFactory.createEtchedBorder();
		filePanel.add(fileBrowseButton, BorderLayout.EAST);
		Border filePanelBorder = BorderFactory.createTitledBorder(etchedBorder, "Select File To Be Encrypted");
		filePanel.setBorder(filePanelBorder);

		keyPanel = new JPanel();
		JLabel keyLabel = new JLabel("Public Key Directory: ");
		keyPanel.add(keyLabel, BorderLayout.WEST);
		keyTextField = new JTextField(16);
		keyPanel.add(keyTextField, BorderLayout.CENTER);
		keyBrowseButton = new JButton("Browse...");
		keyBrowseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fileChooser.resetChoosableFileFilters();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Key Files", "key"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				int result = fileChooser.showOpenDialog(RSAEncryptPanel.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					keyFilePath = fileChooser.getSelectedFile().getPath();
					keyTextField.setText(keyFilePath);
				}
			}
		});
		keyPanel.add(keyBrowseButton, BorderLayout.EAST);
		Border keyPanelBorder = BorderFactory.createTitledBorder(etchedBorder, "Select Public Key");
		keyPanel.setBorder(keyPanelBorder);

		JPanel encryptButtonPanel = new JPanel();
		encryptButton = new JButton("Encrypt");
		encryptButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				String message;
				if ((new File(filePath)).isFile())
				{
					if ((new File(keyFilePath)).isFile())
					{
						File initFile = new File(filePath);
						fileChooser.setCurrentDirectory(initFile);
						fileChooser.resetChoosableFileFilters();
						fileChooser.setAcceptAllFileFilterUsed(true);
						int result = fileChooser.showSaveDialog(RSAEncryptPanel.this);
						if (result == JFileChooser.APPROVE_OPTION)
						{
							savePath = fileChooser.getSelectedFile().getPath();
							String suffix;
							int lastDotIndex = filePath.lastIndexOf('.');
							if (lastDotIndex == -1)
							{
								suffix = "";
							}
							else
							{
								suffix = filePath.substring(lastDotIndex);
							}
							if (!savePath.endsWith(suffix))
							{
								savePath = savePath + suffix;
							}
							try
							{
								long startTime = System.nanoTime();
								RSA.getInstance().encrypt(filePath, savePath, keyFilePath);
								long estimatedTime = System.nanoTime() - startTime;
								System.out.println("RSA Encryption Time: " + estimatedTime);
							}
							catch (ClassNotFoundException | IOException | GeneralSecurityException e)
							{
								e.printStackTrace();
							}
						}
						message = "Successfully encrypted.\nEncrypted File: " + savePath;
						JOptionPane.showMessageDialog(RSAEncryptPanel.this, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						message = "Please select a valid public key file.";
						JOptionPane.showMessageDialog(RSAEncryptPanel.this, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else
				{
					message = "Please select a valid file to be encrypted.";
					JOptionPane.showMessageDialog(RSAEncryptPanel.this, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		encryptButtonPanel.add(encryptButton);

		this.setLayout(new GridLayout(4, 1));
		this.add(filePanel);
		this.add(keyPanel);
		this.add(encryptButtonPanel);
	}
}
