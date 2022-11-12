package textapp;

import com.twilio.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



public class TwilioClass extends JFrame{
	private String _accountSID;
	private String _authToken;
	private String _message;
	private String _outgoingPhone;
	private ArrayList<String> _nums;
	private JOptionPane JOPane;
	private int count;
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	
	public TwilioClass() {

	
	}

	public void Send(String accountSID, String authToken, String message, String outgoingPhone,
			ArrayList<String> nums) {

		
		
		for (int i = 0; i < nums.size(); i++) {
			Twilio.init(accountSID, authToken);
			Message messageSend = Message.creator(new com.twilio.type.PhoneNumber(nums.get(i)),
					new com.twilio.type.PhoneNumber(outgoingPhone), message).create();

			//System.out.println(messageSend.getSid());
			
			
				Date dt = new Date();
				System.out.println(nums.get(i) + " recieved message at " + dt.toString() );
				logger.info(nums.get(i) + " recieved message");
			

		}
		//System.out.println(count + " of " +nums.size() + " messages successfully sent!");

		JOPane.showMessageDialog(null, "Sent!","Messages Sent",1);

	}

}
