package notepad;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

//import notepad.Notepad;

//import javax.xml.soap.Text;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class Notepad extends JFrame {

	private File lastFile;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notepad frame = new Notepad();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Notepad() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTextArea txtMain = new JTextArea();
		contentPane.add(txtMain, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mnFile.setMnemonic('F');

		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		mntmNew.setMnemonic('N');
		mntmNew.setAccelerator(KeyStroke.getKeyStroke("control N"));
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtMain.getText().equals("")) {
					txtMain.setText("");
				} else {
					int input = JOptionPane.showConfirmDialog(null,
							"Do you want to save changes before creating a new file?");
					// 0=yes, 1=no, 2=cancel
					try {
						if (input == 0 && lastFile != null) {
							BufferedWriter writer = new BufferedWriter(new FileWriter(lastFile));
							writer.write(txtMain.getText());
							writer.flush();
							txtMain.setText("");
						} else if (input == 0) {
							JFileChooser fc = new JFileChooser();
							int result;
							result = fc.showSaveDialog(Notepad.this);
							if (result == JFileChooser.APPROVE_OPTION) {
								FileNameExtensionFilter filter;
								File f = fc.getSelectedFile();
								lastFile = f;
								try {
									BufferedWriter writer = new BufferedWriter(new FileWriter(f));
									writer.write(txtMain.getText());
									writer.flush();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							txtMain.setText("");
						} else if (input == 1) {
							txtMain.setText("");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		mntmOpen.setMnemonic('O');
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke("control O"));
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String data = txtMain.getText().trim();
				if (data.contentEquals("")) {
					txtMain.setText("");
					JFileChooser FC = new JFileChooser();
					FC.setCurrentDirectory(new File(System.getProperty("user.home")));
					FC.setFileSelectionMode(JFileChooser.FILES_ONLY);
					FC.addChoosableFileFilter(new FileNameExtensionFilter("Text Document", "txt"));

					FC.setAcceptAllFileFilterUsed(true);

					int result;
					result = FC.showOpenDialog(Notepad.this);

					if (result == JFileChooser.APPROVE_OPTION) {
						File k = FC.getSelectedFile();
						lastFile = k;

						try {
							BufferedReader reader = new BufferedReader(new FileReader(k));
							String line;
							boolean moreStuff = true;
							while (moreStuff) {
								line = reader.readLine();

								if (line == null) {
									moreStuff = false;
								} else {
									txtMain.setText(line);
								}
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					int input = JOptionPane.showConfirmDialog(null,
							"Do you want to save changes before creating a new file?");
					// 0=yes, 1=no, 2=cancel
					try {
						if (input == 0 && lastFile != null) {
							BufferedWriter writer = new BufferedWriter(new FileWriter(lastFile));
							writer.write(txtMain.getText());
							writer.flush();
							open(txtMain);
						} else if (input == 0) {
							JFileChooser fc = new JFileChooser();
							int result;
							result = fc.showSaveDialog(Notepad.this);
							if (result == JFileChooser.APPROVE_OPTION) {

								File f = fc.getSelectedFile();
								lastFile = f;
								try {
									BufferedWriter writer = new BufferedWriter(new FileWriter(f));
									writer.write(txtMain.getText());
									writer.flush();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							open(txtMain);
						} else if (input == 1) {
							open(txtMain);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

			private void open(JTextArea txtMain) {
				txtMain.setText("");
				JFileChooser fc = new JFileChooser();
				int result;
				result = fc.showOpenDialog(Notepad.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					lastFile = f;
					try {
						BufferedReader reader = new BufferedReader(new FileReader(f));
						String line;
						boolean moreStuff = true;
						while (moreStuff) {
							line = reader.readLine();
							if (line == null) {
								moreStuff = false;
							} else {
								txtMain.setText(txtMain.getText() + line + "\n");
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSave.setAccelerator(KeyStroke.getKeyStroke("control S"));
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (lastFile != null) {
						BufferedWriter writer = new BufferedWriter(new FileWriter(lastFile));
						writer.write(txtMain.getText());
						writer.flush();
					} else {
						JFileChooser fc = new JFileChooser();
						int result;
						result = fc.showSaveDialog(Notepad.this);
						if (result == JFileChooser.APPROVE_OPTION) {
							FileNameExtensionFilter filter;
							File f = fc.getSelectedFile();
							lastFile = f;
							try {
								BufferedWriter writer = new BufferedWriter(new FileWriter(f));
								writer.write(txtMain.getText());
								writer.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mnFile.add(mntmSaveAs);
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveas(txtMain);

			}

			private void saveas(JTextArea txtMain) {
				JFileChooser fc = new JFileChooser();
				int result;

				FileNameExtensionFilter filter = new FileNameExtensionFilter("ss", ".txt");
				fc.setFileFilter(filter);
				result = fc.showSaveDialog(Notepad.this);

				if (result == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					lastFile = f;

					try {

						BufferedWriter writer = new BufferedWriter(new FileWriter(f));
						writer.write(txtMain.getText());
						writer.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		//mntmQuit.addActionListener(new mntmQuit());
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int input = JOptionPane.showConfirmDialog(null, "Do you want to save changes before exiting?");
				// 0=yes, 1=no, 2=cancel
				try {
					if (input == 0 && lastFile != null) {
						BufferedWriter writer = new BufferedWriter(new FileWriter(lastFile));
						writer.write(txtMain.getText());
						writer.flush();
					} else if (input == 0) {
						JFileChooser fc = new JFileChooser();
						int result;
						result = fc.showSaveDialog(Notepad.this);
						if (result == JFileChooser.APPROVE_OPTION) {
							FileNameExtensionFilter filter;
							File f = fc.getSelectedFile();
							lastFile = f;
							try {
								BufferedWriter writer = new BufferedWriter(new FileWriter(f));
								writer.write(txtMain.getText());
								writer.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else if (input == 1) {
						System.exit(0);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		mnFile.add(mntmQuit);
		mntmQuit.setMnemonic('Q');
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke("control Q"));

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		mnEdit.setMnemonic('E');

	}

	public boolean accept(File f) {
		if (extension(f).equals(format)) {
			return true;
		} else
			return false;
	}

	public String format = "txt";
	public char DotIndex = '.';

	public String extension(File f) {
		String FileName = f.getName();
		int IndexFile = FileName.lastIndexOf(DotIndex);
		if (IndexFile > 0 && IndexFile < FileName.length() - 1) {
			return FileName.substring(IndexFile + 1);
		} else {
			return "";
		}

	}

}
