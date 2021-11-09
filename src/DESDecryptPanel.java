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

@SuppressWarnings("serial")
public class DESDecryptPanel extends JPanel
{
	private JPanel filePanel;
	private JPanel passwordPanel;
	private JTextField fileTextField;
	private JTextField keyTextField;
	private JButton encryptButton;
	private JButton fileBrowseButton;
	private JFileChooser fileChooser = new JFileChooser();
	private String filePath;
	private String savePath;

	public DESDecryptPanel()
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
				int result = fileChooser.showOpenDialog(DESDecryptPanel.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					filePath = fileChooser.getSelectedFile().getPath();
					fileTextField.setText(filePath);
				}
			}
		});

		Border etchedBorder = BorderFactory.createEtchedBorder();
		filePanel.add(fileBrowseButton, BorderLayout.EAST);
		Border filePanelBorder = BorderFactory.createTitledBorder(etchedBorder, "Select File To Be Decrypted");
		filePanel.setBorder(filePanelBorder);

		passwordPanel = new JPanel();
		JLabel keyLabel = new JLabel("Password: ");
		passwordPanel.add(keyLabel, BorderLayout.WEST);
		keyTextField = new JTextField(16);
		passwordPanel.add(keyTextField, BorderLayout.CENTER);
		Border keyPanelBorder = BorderFactory.createTitledBorder(etchedBorder, "Input Password");
		passwordPanel.setBorder(keyPanelBorder);

		JPanel encryptButtonPanel = new JPanel();
		encryptButton = new JButton("Decrypt");
		encryptButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				String message;
				String password = keyTextField.getText();
				if (password.isEmpty() || password.length() != 8)
				{
					message = "Please select a valid password.";
					JOptionPane.showMessageDialog(DESDecryptPanel.this, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					File initFile = new File(filePath);
					fileChooser.setCurrentDirectory(initFile);
					fileChooser.resetChoosableFileFilters();
					fileChooser.setAcceptAllFileFilterUsed(true);
					int result = fileChooser.showSaveDialog(DESDecryptPanel.this);
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
							DES.getInstance().decrypt(filePath, savePath, password);
							long estimatedTime = System.nanoTime() - startTime;
							System.out.println("DES Decryption Time: " + estimatedTime);
						}
						catch (IOException | GeneralSecurityException e)
						{
							e.printStackTrace();
						}
					}
					message = "Successfully encrypted.\nDecrypted File: " + savePath;
					JOptionPane.showMessageDialog(DESDecryptPanel.this, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		encryptButtonPanel.add(encryptButton);
		
		this.setLayout(new GridLayout(4, 1));
		this.add(filePanel);
		this.add(passwordPanel);
		this.add(encryptButtonPanel);
	}
}
