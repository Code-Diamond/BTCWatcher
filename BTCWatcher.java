import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Calendar;

public class BTCWatcher extends JFrame
{
	public BTCWatcher() throws IOException
	{
		//Add menu bar
		createMenuBar();
		JPanel panel = new JPanel();
		//Add BTC price which updates every 10 minutes
		JLabel btcPrice = new JLabel("BTC/USD: " + GetCoinBaseBTCPrice());
		Timer btcTimer = new Timer(600000, new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	try
		    	{
		    		btcPrice.setText("BTC/USD: " + GetCoinBaseBTCPrice());	
		    	}catch(Exception exc){}
		        
		    }
		});
		btcTimer.start();
		//Add current time which updates every second
		JTextField time = new JTextField(10);
		time.setHorizontalAlignment(JTextField.CENTER);
		time.setEditable(false);
		javax.swing.Timer timeTimer = new javax.swing.Timer(1000,
              new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                      Calendar now = Calendar.getInstance();
                      int h = now.get(Calendar.HOUR);
                      if(h==0)
                      {
                      	h=12;
                      }
                      int m = now.get(Calendar.MINUTE);
                      int s = now.get(Calendar.SECOND);
                      int amPM = now.get(Calendar.AM_PM);
                      if(amPM == 0)
                      {
                      	time.setText("" + h + ":" + m + ":" + s + " AM");
                      }
                      else
                      {
                      	time.setText("" + h + ":" + m + ":" + s + " PM");	
                      }
                  }
              });
        timeTimer.start();


		Container content = this.getContentPane();
        content.setLayout(new FlowLayout());
		
		panel.add(btcPrice);
		panel.add(time);
		add(panel);
		setSize(400,400);
		setTitle("BTCWatcher");
		frame.setLocationRelativeTo(null);
		pack();
		//Show to user
		setVisible(true);
		//Disable resizing
		setResizable(false);
		//Ensure fully exits
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	private String GetCoinBaseBTCPrice() throws IOException
	{
		String url = "https://www.coinbase.com/price/bitcoin";
        Document doc = Jsoup.connect(url).get();
        Elements result = doc.select("div.ChartPriceHeader__BigAmount-azzilf-3.fLPTSE").select("span");
        String price = result.text();
        return price;
	}
	

	private void createMenuBar() {

        JMenuBar menubar = new JMenuBar();
        ImageIcon exitIcon = new ImageIcon("src/main/resources/btcPricepng");

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
		JMenuItem eMenuItem = new JMenuItem("Exit", exitIcon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        file.add(eMenuItem);

        menubar.add(file);

        setJMenuBar(menubar);
 }

	public static void main(String[] args) throws IOException 
	{
		new BTCWatcher();
	}
}