import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class GenerateKeyPanel extends JPanel
{
	private JPanel saveDirPanel;
	private JPanel generatePanel;
	private JTextField saveDirectory;
	private JButton browseButton;
	private JButton generateButton;

	private String savePath;
	private JFileChooser fileChooser = new JFileChooser();

	public GenerateKeyPanel()
	{
		saveDirPanel = new JPanel();
		JLabel label = new JLabel("Save Directory: ");
		saveDirPanel.add(label, BorderLayout.WEST);
		saveDirectory = new JTextField(16);
		saveDirPanel.add(saveDirectory, BorderLayout.CENTER);
		browseButton = new JButton("Browse...");
		browseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setDialogTitle("Save Key Pair");
				int result = fileChooser.showDialog(GenerateKeyPanel.this, "Yes");
				if (result == JFileChooser.APPROVE_OPTION)
				{
					saveDirectory.setText(fileChooser.getSelectedFile().getPath());
				}
			}
		});

		Border etchedBorder = BorderFactory.createEtchedBorder();
		saveDirPanel.add(browseButton, BorderLayout.EAST);
		Border saveDirPanelBorder = BorderFactory.createTitledBorder(etchedBorder, "Select Save Directory");
		saveDirPanel.setBorder(saveDirPanelBorder);

		generatePanel = new JPanel();
		generateButton = new JButton("Generate Key Pair");
		generateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				savePath = saveDirectory.getText();
				String message;
				if ((new File(savePath)).isDirectory())
				{
					try
					{
						RSA.getInstance().generateKeyPair(savePath);
					}
					catch (NoSuchAlgorithmException | IOException e)
					{
						e.printStackTrace();
					}
					message = "Successfully generate key pair.\n" + "Public key: " + savePath + File.separator + "Public.key\n" + "Private key: " + savePath + File.separator + "Private.key"; 
				}
				else
				{
					message = "Cannot find the directory.";
				}
				JOptionPane.showMessageDialog(GenerateKeyPanel.this, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		generatePanel.add(generateButton, BorderLayout.CENTER);

		this.setLayout(new GridLayout(4, 1));
		this.add(saveDirPanel);
		this.add(generatePanel);
	}
}
