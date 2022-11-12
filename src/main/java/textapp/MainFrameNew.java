package textapp;

//package ECAA_Texting_App;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class MainFrameNew extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField ExcelField;
	private JTextField SIDField;
	private JTextField AuthField;
	private JTextField PhoneField;
	private JButton SendButton;
	private JTextArea MessageField;
	private ArrayList<String> Nums;
	private ExcelClass ec;
	private TwilioClass tc;
	private JOptionPane JOPane;
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// ExcelClass ec = new ExcelClass("/Users/a.deezy/Desktop/nums.xlsx");
					// ec.GetExcelData("/Users/a.deezy/Desktop/nums.xlsx");
					MainFrameNew frame = new MainFrameNew();
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
	public MainFrameNew() {
		setTitle("ECAA New Texting App");
		setSize(800, 601);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 788, 561);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel ExcelLocationLabel = new JLabel("Excel File Location: ");
		ExcelLocationLabel.setBounds(158, 28, 138, 43);
		panel.add(ExcelLocationLabel);

		ExcelField = new JTextField();
		ExcelField.setBounds(359, 34, 258, 31);
		panel.add(ExcelField);
		ExcelField.setColumns(10);

		JLabel lblNewLabel_2_1 = new JLabel("Account SID: ");
		lblNewLabel_2_1.setBounds(158, 83, 138, 43);
		panel.add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_2 = new JLabel("Auth Token: ");
		lblNewLabel_2_2.setBounds(158, 138, 138, 43);
		panel.add(lblNewLabel_2_2);

		JLabel lblNewLabel_2_3 = new JLabel("Sending Phone Number: ");
		lblNewLabel_2_3.setBounds(158, 193, 154, 43);
		panel.add(lblNewLabel_2_3);

		JLabel lblNewLabel_2_4 = new JLabel("Message: ");
		lblNewLabel_2_4.setBounds(158, 248, 138, 43);
		panel.add(lblNewLabel_2_4);

		SIDField = new JTextField();
		SIDField.setColumns(10);
		SIDField.setBounds(359, 89, 258, 31);
		panel.add(SIDField);

		AuthField = new JTextField();
		AuthField.setColumns(10);
		AuthField.setBounds(359, 138, 258, 31);
		panel.add(AuthField);

		PhoneField = new JTextField();
		PhoneField.setColumns(10);
		PhoneField.setBounds(359, 199, 258, 31);
		panel.add(PhoneField);

		MessageField = new JTextArea();
		MessageField.setLineWrap(true);
		MessageField.setBounds(359, 261, 258, 94);
		panel.add(MessageField);

		SendButton = new JButton("Send Message");
		SendButton.setBounds(310, 367, 126, 47);
		panel.add(SendButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(66, 415, 654, 140);
		panel.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(66,415,654,140);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane);
		
		
		
		System.setOut(new PrintStream(new OutputStream() {
		      @Override
		      public void write(int b) throws IOException {
		        textArea.append(String.valueOf((char) b));
		      }
		    }));
		
		

		SendButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		JOPane = new JOptionPane();
		if (e.getSource() == SendButton) {
			//System.out.println("Button Works");
			if (MessageField.getText().isEmpty()) {
				int result = JOPane.showConfirmDialog(this, "Message cannot be empty!", "Error",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			} else if (MessageField.getText().length() >= 159) {
				int result = JOPane.showConfirmDialog(this, "Message cannot be over 160 characters in length!", "Error",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			} else {
				int result = JOPane.showConfirmDialog(this,
						"Are you sure you would like to send the message :\n" + "\"" + MessageField.getText() + "\" ?",
						"Please Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					//System.out.println("yes button works");
					ec = new ExcelClass();
					
					Nums = ec.GetExcelData(ExcelField.getText());
					
					System.out.println("Message being sent: \"" + MessageField.getText()+"\"");
					logger.info("Message being sent: \"" + MessageField.getText()+"\"");
					tc = new TwilioClass();
				
					tc.Send(SIDField.getText(), AuthField.getText(), MessageField.getText(), PhoneField.getText(), Nums);
					System.out.println("Messages Sent!");
				}
			}

		}

	}
	
	
}
